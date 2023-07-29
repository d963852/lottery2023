package com.jeesite.modules.lotterycore.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.lotterycore.entity.PlayMethodGroup;
import com.jeesite.modules.lotterycore.service.PlayMethodGroupService;
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

/**
 * 玩法分组Controller
 * @author tg
 * @version 2023-06-20
 */
@Controller
@RequestMapping(value = "${adminPath}/lotterycore/playMethodGroup")
public class PlayMethodGroupController extends BaseController {

	@Autowired
	private PlayMethodGroupService playMethodGroupService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public PlayMethodGroup get(String id, boolean isNewRecord) {
		return playMethodGroupService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("lotterycore:playMethodGroup:view")
	@RequestMapping(value = {"list", ""})
	public String list(PlayMethodGroup playMethodGroup, Model model) {
		model.addAttribute("playMethodGroup", playMethodGroup);
		return "modules/lotterycore/playMethodGroupList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("lotterycore:playMethodGroup:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<PlayMethodGroup> listData(PlayMethodGroup playMethodGroup, HttpServletRequest request, HttpServletResponse response) {
		playMethodGroup.setPage(new Page<>(request, response));
		Page<PlayMethodGroup> page = playMethodGroupService.findPage(playMethodGroup);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("lotterycore:playMethodGroup:view")
	@RequestMapping(value = "form")
	public String form(PlayMethodGroup playMethodGroup, Model model) {
		model.addAttribute("playMethodGroup", playMethodGroup);
		return "modules/lotterycore/playMethodGroupForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("lotterycore:playMethodGroup:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated PlayMethodGroup playMethodGroup) {
		playMethodGroupService.save(playMethodGroup);
		return renderResult(Global.TRUE, text("保存玩法分组成功！"));
	}
	
	/**
	 * 停用数据
	 */
	@RequiresPermissions("lotterycore:playMethodGroup:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(PlayMethodGroup playMethodGroup) {
		playMethodGroup.setStatus(PlayMethodGroup.STATUS_DISABLE);
		playMethodGroupService.updateStatus(playMethodGroup);
		return renderResult(Global.TRUE, text("停用玩法分组成功"));
	}
	
	/**
	 * 启用数据
	 */
	@RequiresPermissions("lotterycore:playMethodGroup:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(PlayMethodGroup playMethodGroup) {
		playMethodGroup.setStatus(PlayMethodGroup.STATUS_NORMAL);
		playMethodGroupService.updateStatus(playMethodGroup);
		return renderResult(Global.TRUE, text("启用玩法分组成功"));
	}
	
}