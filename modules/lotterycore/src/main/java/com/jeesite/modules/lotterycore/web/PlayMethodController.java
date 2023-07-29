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
import com.jeesite.modules.lotterycore.entity.PlayMethod;
import com.jeesite.modules.lotterycore.service.PlayMethodService;

/**
 * 玩法管理Controller
 * @author tg
 * @version 2023-06-20
 */
@Controller
@RequestMapping(value = "${adminPath}/lotterycore/playMethod")
public class PlayMethodController extends BaseController {

	@Autowired
	private PlayMethodService playMethodService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public PlayMethod get(String id, boolean isNewRecord) {
		return playMethodService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("lotterycore:playMethod:view")
	@RequestMapping(value = {"list", ""})
	public String list(PlayMethod playMethod, Model model) {
		model.addAttribute("playMethod", playMethod);
		return "modules/lotterycore/playMethodList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("lotterycore:playMethod:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<PlayMethod> listData(PlayMethod playMethod, HttpServletRequest request, HttpServletResponse response) {
		playMethod.setPage(new Page<>(request, response));
		Page<PlayMethod> page = playMethodService.findPage(playMethod);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("lotterycore:playMethod:view")
	@RequestMapping(value = "form")
	public String form(PlayMethod playMethod, Model model) {
		model.addAttribute("playMethod", playMethod);
		return "modules/lotterycore/playMethodForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("lotterycore:playMethod:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated PlayMethod playMethod) {
		playMethodService.save(playMethod);
		return renderResult(Global.TRUE, text("保存玩法管理成功！"));
	}
	
	/**
	 * 停用数据
	 */
	@RequiresPermissions("lotterycore:playMethod:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(PlayMethod playMethod) {
		playMethod.setStatus(PlayMethod.STATUS_DISABLE);
		playMethodService.updateStatus(playMethod);
		return renderResult(Global.TRUE, text("停用玩法管理成功"));
	}
	
	/**
	 * 启用数据
	 */
	@RequiresPermissions("lotterycore:playMethod:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(PlayMethod playMethod) {
		playMethod.setStatus(PlayMethod.STATUS_NORMAL);
		playMethodService.updateStatus(playMethod);
		return renderResult(Global.TRUE, text("启用玩法管理成功"));
	}
	
}