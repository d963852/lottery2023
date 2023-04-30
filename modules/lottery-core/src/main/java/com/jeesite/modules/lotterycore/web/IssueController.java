package com.jeesite.modules.lotterycore.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.lotterycore.common.exception.BizError;
import com.jeesite.modules.lotterycore.common.exception.BizException;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.entity.Issue;
import com.jeesite.modules.lotterycore.service.GameService;
import com.jeesite.modules.lotterycore.service.IssueService;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 彩票期号Controller
 *
 * @author DUKE
 * @version 2023-04-23
 */
@Controller
@RequestMapping(value = "${adminPath}/lotterycore/issue")
public class IssueController extends BaseController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private GameService gameService;

    /**
     * 获取数据
     */
    @ModelAttribute
    public Issue get(String id, boolean isNewRecord) {
        return issueService.get(id, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("lotterycore:issue:view")
    @RequestMapping(value = {"list", ""})
    public String list(Issue issue, Model model) {
        model.addAttribute("issue", issue);
        return "modules/lotterycore/issueList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("lotterycore:issue:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<Issue> listData(Issue issue, HttpServletRequest request, HttpServletResponse response) {
        issue.setPage(new Page<>(request, response));
        Page<Issue> page = issueService.findPage(issue);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("lotterycore:issue:view")
    @RequestMapping(value = "form")
    public String form(Issue issue, Model model) {
        model.addAttribute("issue", issue);
        return "modules/lotterycore/issueForm";
    }

    /**
     * 保存数据
     */
    @RequiresPermissions("lotterycore:issue:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated Issue issue) {
        issueService.save(issue);
        return renderResult(Global.TRUE, text("保存彩票期号成功！"));
    }

    /**
     * 停用数据
     */
    @RequiresPermissions("lotterycore:issue:edit")
    @RequestMapping(value = "disable")
    @ResponseBody
    public String disable(Issue issue) {
        issue.setStatus(Issue.STATUS_DISABLE);
        issueService.updateStatus(issue);
        return renderResult(Global.TRUE, text("停用彩票期号成功"));
    }

    /**
     * 启用数据
     */
    @RequiresPermissions("lotterycore:issue:edit")
    @RequestMapping(value = "enable")
    @ResponseBody
    public String enable(Issue issue) {
        issue.setStatus(Issue.STATUS_NORMAL);
        issueService.updateStatus(issue);
        return renderResult(Global.TRUE, text("启用彩票期号成功"));
    }

    /**
     * 生成期号页面
     */
    @RequiresPermissions("lotterycore:issue:view")
    @RequestMapping(value = "makeForm")
    public String makeForm(@Param("gameId") String gameId, Model model) {
        Game gameSC = gameService.get(gameId);
        if (gameSC == null) {
            model.addAttribute("message", BizError.游戏不存在.getMsg());
            return "error/404";
        }
        model.addAttribute("game", gameSC);
        return "modules/lotterycore/issueMakeForm";
    }

    /**
     * 手工生成期号
     *
     * @param beginDate 生成开始日期
     * @param endDate   生成结束日期
     * @param model
     * @return
     */
    @RequiresPermissions("lotterycore:issue:edit")
    @RequestMapping(value = "makeIssue")
    @ResponseBody
    public String makeIssue(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate, @Param("gameId") String gameId, Model model) {
        try {
            issueService.makeIssue(beginDate, endDate, gameId);
        } catch (BizException bizError) {
            return renderResult(Global.FALSE, text(bizError.getMsg()));
        }
        return renderResult(Global.TRUE, text("手工生成期号成功"));
    }

}