package com.jeesite.modules.lotterycore.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeesite.modules.lotterycore.entity.AccountChangeLog;
import com.jeesite.modules.lotterycore.service.AccountChangeLogService;

/**
 * 账变日志Controller
 * @author thinkgem
 * @version 2023-08-26
 */
@Controller
@RequestMapping(value = "${adminPath}/lotterycore/accountChangeLog")
public class AccountChangeLogController extends BaseController {

	@Autowired
	private AccountChangeLogService accountChangeLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AccountChangeLog get(String id, boolean isNewRecord) {
		return accountChangeLogService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("lotterycore:accountChangeLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(AccountChangeLog accountChangeLog, Model model) {
		model.addAttribute("accountChangeLog", accountChangeLog);
		return "modules/lotterycore/accountChangeLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("lotterycore:accountChangeLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AccountChangeLog> listData(AccountChangeLog accountChangeLog, HttpServletRequest request, HttpServletResponse response) {
		accountChangeLog.setPage(new Page<>(request, response));
		Page<AccountChangeLog> page = accountChangeLogService.findPage(accountChangeLog);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("lotterycore:accountChangeLog:view")
	@RequestMapping(value = "form")
	public String form(AccountChangeLog accountChangeLog, Model model) {
		model.addAttribute("accountChangeLog", accountChangeLog);
		return "modules/lotterycore/accountChangeLogForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("lotterycore:accountChangeLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AccountChangeLog accountChangeLog) {
		accountChangeLogService.save(accountChangeLog);
		return renderResult(Global.TRUE, text("保存账变日志成功！"));
	}
	
}