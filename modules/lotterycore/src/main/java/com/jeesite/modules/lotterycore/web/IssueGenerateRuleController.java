package com.jeesite.modules.lotterycore.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.service.GameService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.lotterycore.entity.IssueGenerateRule;
import com.jeesite.modules.lotterycore.service.IssueGenerateRuleService;

/**
 * 期号生成规则Controller
 * @author DUKE
 * @version 2023-04-23
 */
@Controller
@RequestMapping(value = "${adminPath}/lotterycore/issueGenerateRule")
public class IssueGenerateRuleController extends BaseController {

	@Autowired
	private IssueGenerateRuleService issueGenerateRuleService;
	@Autowired
	private GameService gameService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public IssueGenerateRule get(String id, boolean isNewRecord) {
		return issueGenerateRuleService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("lotterycore:issueGenerateRule:view")
	@RequestMapping(value = {"list", ""})
	public String list(IssueGenerateRule issueGenerateRule, Model model) {
		Game game = gameService.get(issueGenerateRule.getGameId());
		model.addAttribute("game", game);
		model.addAttribute("issueGenerateRule", issueGenerateRule);
		return "modules/lotterycore/issueGenerateRuleList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("lotterycore:issueGenerateRule:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<IssueGenerateRule> listData(IssueGenerateRule issueGenerateRule, HttpServletRequest request, HttpServletResponse response) {
		issueGenerateRule.setPage(new Page<>(request, response));
		Page<IssueGenerateRule> page = issueGenerateRuleService.findPage(issueGenerateRule);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("lotterycore:issueGenerateRule:view")
	@RequestMapping(value = "form")
	public String form(IssueGenerateRule issueGenerateRule, Model model) {
		Game game = gameService.get(issueGenerateRule.getGameId());
		model.addAttribute("game", game);
		model.addAttribute("issueGenerateRule", issueGenerateRule);
		return "modules/lotterycore/issueGenerateRuleForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("lotterycore:issueGenerateRule:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated IssueGenerateRule issueGenerateRule) {
		issueGenerateRuleService.save(issueGenerateRule);
		return renderResult(Global.TRUE, text("保存期号生成规则成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("lotterycore:issueGenerateRule:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(IssueGenerateRule issueGenerateRule) {
		issueGenerateRuleService.delete(issueGenerateRule);
		return renderResult(Global.TRUE, text("删除期号生成规则成功！"));
	}
	
}