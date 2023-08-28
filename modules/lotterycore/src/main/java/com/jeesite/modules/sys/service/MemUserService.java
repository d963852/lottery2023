/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sys.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.sys.dao.MemUserDao;
import com.jeesite.modules.sys.entity.MemUser;
import com.jeesite.modules.sys.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 会员用户Service
 * @author ThinkGem
 * @version 2020-9-19
 */
@Service
@Transactional(readOnly=true)
public class MemUserService extends CrudService<MemUserDao, MemUser> {

    @Autowired
    private UserService userService;
    @Autowired
    private MemberService memberService;

    /**
     * 获取单条数据
     */
    @Override
    public MemUser get(MemUser memUser) {
        //解决Parent懒加载问题
        MemUser theMemUser = super.get(memUser);
        theMemUser.getMember().setParent(memberService.get(theMemUser.getMember().getParent()));
        return theMemUser;
    }

    /**
     * 分页查询数据
     */
    @Override
    public Page<MemUser> findPage(MemUser memUser) {
        //解决Parent懒加载问题
        Page<MemUser> page = super.findPage(memUser);
        List<MemUser> memUserList = page.getList();
        for (MemUser theMemUser: memUserList) {
            Member parent = theMemUser.getMember().getParent();
            if(parent != null){
                parent = memberService.get(parent.getMemId());
                theMemUser.getMember().setParent(parent);
            }
        }
        page.setList(memUserList);
        return page;
    }

    /**
     * 分页查询数据
     */
    @Override
    public List<MemUser> findList(MemUser memUser) {
        //解决Parent懒加载问题
        List<MemUser> memUserList = super.findList(memUser);
        for (MemUser theMemUser: memUserList) {
            Member parent = theMemUser.getMember().getParent();
            if(parent != null){
                parent = memberService.get(parent.getMemId());
                theMemUser.getMember().setParent(parent);
            }
        }
        return memUserList;
    }

    /**
     * 保存用户会员
     */
    @Override
    @Transactional(readOnly=false)
    public void save(MemUser user) {
        // 1、初始化用户信息
        if (user.getIsNewRecord()){
            userService.genId(user, user.getLoginCode());
            user.setUserCode(user.getUserCode()+"_"+IdGen.randomBase62(4).toLowerCase());
            user.setUserType(MemUser.USER_TYPE_MEMBER);
            user.setMgrType(MemUser.MGR_TYPE_NOT_ADMIN);
        }
        Member member = user.getMember();
        // 生成会员编号
        if (user.getIsNewRecord()) {
            member.setMemId(user.getUserCode());
        }
        // 如果会员姓名为空，则使用昵称名
        if (StringUtils.isBlank(member.getMemName())){
            member.setMemName(user.getUserName());
        }
        // 2、保存用户
        user.setRefCode(member.getMemId());
        user.setRefName(member.getMemName());
        userService.save(user);
        // 3、保存员工
        member.setIsNewRecord(user.getIsNewRecord());
        memberService.save(member);
    }

    /**
     * 更新状态
     */
    @Override
    @Transactional(readOnly=false)
    public void updateStatus(MemUser memUser) {
        userService.updateStatus(memUser);
        memberService.updateStatus(memUser.getMember());
    }

    /**
     * 删除用户
     */
    @Override
    @Transactional(readOnly=false)
    public void delete(MemUser memUser) {
        userService.delete(memUser);
        memberService.delete(memUser.getMember());
    }

}
