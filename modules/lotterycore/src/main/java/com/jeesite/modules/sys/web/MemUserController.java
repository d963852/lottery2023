/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sys.web;

import com.alibaba.fastjson.JSONValidator;
import com.jeesite.common.codec.EncodeUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.shiro.realm.AuthorizingRealm;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sys.entity.MemUser;
import com.jeesite.modules.sys.entity.Member;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.MemUserService;
import com.jeesite.modules.sys.service.RoleService;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 会员用户Controller
 *
 * @author ThinkGem
 * @version 2020-9-19
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/memUser")
@ConditionalOnProperty(name = "web.core.enabled", havingValue = "true", matchIfMissing = true)
public class MemUserController extends BaseController {

    @Autowired
    private MemUserService memUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @ModelAttribute
    public MemUser get(String userCode, boolean isNewRecord) {
        return memUserService.get(userCode, isNewRecord);
    }

    @RequiresPermissions("sys:memUser:view")
    @RequestMapping(value = "list")
    public String list(MemUser memUser, Model model) {
        model.addAttribute("memUser", memUser);
        return "modules/sys/memUserList";
    }

    @RequiresPermissions("sys:memUser:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<MemUser> listData(MemUser memUser, HttpServletRequest request, HttpServletResponse response) {
        memUser.setPage(new Page<>(request, response));
        Page<MemUser> page = memUserService.findPage(memUser);
        return page;
    }

    @RequiresPermissions("sys:memUser:view")
    @RequestMapping(value = "form")
    public String form(MemUser memUser, String op, Model model) {
        // 获取当前编辑用户的角色和权限
        if (StringUtils.inString(op, Global.OP_AUTH)) {
            // 获取当前用户所拥有的角色
            Role role = new Role();
            role.setUserCode(memUser.getUserCode());
            model.addAttribute("roleList", roleService.findListByUserCode(role));

        }
        // 操作类型：add: 全部； edit: 编辑； auth: 授权
        model.addAttribute("op", op);

        // 各项数值系统上限
        double maxRebate = Double.parseDouble(Global.getConfig("lottery.member.rebate", "15"));
        double maxOdds = Double.parseDouble(Global.getConfig("lottery.member.odds", "2"));
        double maxWage = Double.parseDouble(Global.getConfig("lottery.member.wage", "2.5"));
        double maxBonus = Double.parseDouble(Global.getConfig("lottery.member.bonus", "15"));
        if(memUser.getMember().getParent() != null){
            // 如果有上级用户，则上级用户为上限
            maxRebate = memUser.getMember().getParent().getRebate();
            maxOdds = memUser.getMember().getParent().getOdds();
            maxWage = memUser.getMember().getParent().getWage();
            maxBonus = memUser.getMember().getParent().getBonus();
        }
        // 添加下级用户，预设值上级信息
        if ("addSub".equals(op)) {
            MemUser subMemUser = get("", true);
            subMemUser.getMember().setParent(memUser.getMember());
            // 单独设置各项数据上限
            maxRebate = memUser.getMember().getRebate();
            maxOdds = memUser.getMember().getOdds();
            maxWage = memUser.getMember().getWage();
            maxBonus = memUser.getMember().getBonus();

            model.addAttribute("memUser", subMemUser);
        } else {
            model.addAttribute("memUser", memUser);
        }



        model.addAttribute("maxRebate", maxRebate);
        model.addAttribute("maxOdds", maxOdds);
        model.addAttribute("maxWage", maxWage);
        model.addAttribute("maxBonus", maxBonus);

        return "modules/sys/memUserForm";
    }

    @RequiresPermissions(value = {"sys:memUser:edit", "sys:memUser:authRole"}, logical = Logical.OR)
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated MemUser memUser, String op, HttpServletRequest request) {
        if (User.isSuperAdmin(memUser.getUserCode())) {
            return renderResult(Global.FALSE, "非法操作，不能够操作此用户！");
        }
        if (!MemUser.USER_TYPE_MEMBER.equals(memUser.getUserType())) {
            return renderResult(Global.FALSE, "非法操作，不能够操作此用户！");
        }

        // 如有上级，不能超过上级投注返点、赔率、工资、分红
        if (memUser.getMember().getParent() != null) {
            MemUser parent = memUserService.get(memUser.getMember().getParent().getId());
            // 投注返点不能超过上级投注返点
            if (memUser.getMember().getRebate() > parent.getMember().getRebate()) {
                return renderResult(Global.FALSE, "投注返点不能超过上级投注返点" + parent.getMember().getRebate());
            }
            // 赔率不能超过上级赔率
            if (memUser.getMember().getOdds() > parent.getMember().getOdds()) {
                return renderResult(Global.FALSE, "赔率不能超过上级赔率" + parent.getMember().getOdds());
            }
            // 工资不能超过上级工资
            if (memUser.getMember().getWage() > parent.getMember().getWage()) {
                return renderResult(Global.FALSE, "工资不能超过上级工资" + parent.getMember().getWage());
            }
            // 分红不能超过上级分红
            if (memUser.getMember().getBonus() > parent.getMember().getBonus()) {
                return renderResult(Global.FALSE, "分红不能超过上级分红" + parent.getMember().getBonus());
            }
        }

        // 各项数据不能超过系统最大限额
        double sysMaxRebate = Double.parseDouble(Global.getConfig("lottery.member.rebate", "15"));
        double sysMaxOdds = Double.parseDouble(Global.getConfig("lottery.member.odds", "2"));
        double sysMaxWage = Double.parseDouble(Global.getConfig("lottery.member.wage", "2.5"));
        double sysMaxBonus = Double.parseDouble(Global.getConfig("lottery.member.bonus", "15"));
        if (sysMaxRebate < memUser.getMember().getRebate()) {
            return renderResult(Global.FALSE, "投注返点数不能超过系统设置的投注返点上限" + sysMaxRebate);
        }
        if (sysMaxOdds < memUser.getMember().getOdds()) {
            return renderResult(Global.FALSE, "赔率不能超过系统设置的赔率上限" + sysMaxOdds);
        }
        if (sysMaxWage < memUser.getMember().getWage()) {
            return renderResult(Global.FALSE, "工资不能超过系统设置的工资上限" + sysMaxWage);
        }
        if (sysMaxBonus < memUser.getMember().getBonus()) {
            return renderResult(Global.FALSE, "分红不能超过系统设置的分红上限" + sysMaxBonus);
        }

        MemUser old = super.getWebDataBinderSource(request);
        if (!Global.TRUE.equals(userService.checkLoginCode(old != null ? old.getLoginCode() : "", memUser.getLoginCode()))) {
            return renderResult(Global.FALSE, text("保存用户失败，登录账号''{0}''已存在", memUser.getLoginCode()));
        }
        Subject subject = UserUtils.getSubject();
        if (StringUtils.inString(op, Global.OP_ADD, Global.OP_EDIT, "addSub") && subject.isPermitted("sys:memUser:edit")) {
            memUserService.save(memUser);
        }
        if (StringUtils.inString(op, Global.OP_ADD, Global.OP_AUTH, "addSub") && subject.isPermitted("sys:memUser:authRole")) {
            userService.saveAuth(memUser);
        }
        return renderResult(Global.TRUE, text("保存用户''{0}''成功", memUser.getUserName()));
    }

    /**
     * 停用用户
     *
     * @param memUser
     * @return
     */
    @RequiresPermissions("sys:memUser:updateStatus")
    @ResponseBody
    @RequestMapping(value = "disable")
    public String disable(MemUser memUser) {
        if (User.isSuperAdmin(memUser.getUserCode())) {
            return renderResult(Global.FALSE, "非法操作，不能够操作此用户！");
        }
        if (!MemUser.USER_TYPE_MEMBER.equals(memUser.getUserType())) {
            return renderResult(Global.FALSE, "非法操作，不能够操作此用户！");
        }
        User currentUser = memUser.currentUser(); // v5.3.0+ 及之后版本
        if (currentUser.getUserCode().equals(memUser.getUserCode())) {
            return renderResult(Global.FALSE, text("停用用户失败，不允许停用当前用户"));
        }
        memUser.setStatus(User.STATUS_DISABLE);
        memUserService.updateStatus(memUser);
        return renderResult(Global.TRUE, text("停用用户''{0}''成功", memUser.getUserName()));
    }

    /**
     * 启用用户
     *
     * @param memUser
     * @return
     */
    @RequiresPermissions("sys:memUser:updateStatus")
    @ResponseBody
    @RequestMapping(value = "enable")
    public String enable(MemUser memUser) {
        if (User.isSuperAdmin(memUser.getUserCode())) {
            return renderResult(Global.FALSE, "非法操作，不能够操作此用户！");
        }
        if (!MemUser.USER_TYPE_MEMBER.equals(memUser.getUserType())) {
            return renderResult(Global.FALSE, "非法操作，不能够操作此用户！");
        }
        memUser.setStatus(User.STATUS_NORMAL);
        memUserService.updateStatus(memUser);
        AuthorizingRealm.isValidCodeLogin(memUser.getLoginCode(), memUser.getCorpCode_(), null, "success");
        return renderResult(Global.TRUE, text("启用用户''{0}''成功", memUser.getUserName()));
    }

    /**
     * 密码重置
     *
     * @param memUser
     * @return
     */
    @RequiresPermissions("sys:memUser:resetpwd")
    @RequestMapping(value = "resetpwd")
    @ResponseBody
    public String resetpwd(MemUser memUser) {
        if (User.isSuperAdmin(memUser.getUserCode())) {
            return renderResult(Global.FALSE, "非法操作，不能够操作此用户！");
        }
        if (!MemUser.USER_TYPE_MEMBER.equals(memUser.getUserType())) {
            return renderResult(Global.FALSE, "非法操作，不能够操作此用户！");
        }
        userService.updatePassword(memUser.getUserCode(), null);
        AuthorizingRealm.isValidCodeLogin(memUser.getLoginCode(), memUser.getCorpCode_(), null, "success");
        return renderResult(Global.TRUE, text("重置用户''{0}''密码成功", memUser.getUserName()));
    }

    /**
     * 删除用户
     *
     * @param memUser
     * @return
     */
    @RequiresPermissions("sys:memUser:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(MemUser memUser) {
        if (User.isSuperAdmin(memUser.getUserCode())) {
            return renderResult(Global.FALSE, "非法操作，不能够操作此用户！");
        }
        if (!MemUser.USER_TYPE_MEMBER.equals(memUser.getUserType())) {
            return renderResult(Global.FALSE, "非法操作，不能够操作此用户！");
        }
        User currentUser = memUser.currentUser(); // v5.3.0+ 及之后版本
        if (currentUser.getUserCode().equals(memUser.getUserCode())) {
            return renderResult(Global.FALSE, text("删除用户失败，不允许删除当前用户"));
        }
        memUserService.delete(memUser);
        return renderResult(Global.TRUE, text("删除用户'{0}'成功", memUser.getUserName()));
    }

    /**
     * 选择会员对话框
     */
    @RequiresPermissions("srv:memUser:view")
    @RequestMapping(value = "memUserSelect")
    public String memSelect(Member member, String selectData, Model model) {
        String selectDataJson = EncodeUtils.decodeUrl(selectData);
        if (selectDataJson != null && JSONValidator.from(selectDataJson).validate()) {
            model.addAttribute("selectData", selectDataJson);
        }
        model.addAttribute("member", member);
        return "modules/srv/memUserSelect";
    }


}

