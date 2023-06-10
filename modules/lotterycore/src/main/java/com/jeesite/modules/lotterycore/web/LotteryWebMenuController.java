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
import com.jeesite.modules.lotterycore.entity.LotteryWebMenu;
import com.jeesite.modules.lotterycore.service.LotteryWebMenuService;

/**
 * 网站彩种菜单设置Controller
 * @author tg
 * @version 2023-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/lotterycore/lotteryWebMenu")
public class LotteryWebMenuController extends BaseController {

	@Autowired
	private LotteryWebMenuService lotteryWebMenuService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public LotteryWebMenu get(String id, boolean isNewRecord) {
		return lotteryWebMenuService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("lotterycore:lotteryWebMenu:view")
	@RequestMapping(value = {"list", ""})
	public String list(LotteryWebMenu lotteryWebMenu, Model model) {
		model.addAttribute("lotteryWebMenu", lotteryWebMenu);
		return "modules/lotterycore/lotteryWebMenuList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("lotterycore:lotteryWebMenu:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<LotteryWebMenu> listData(LotteryWebMenu lotteryWebMenu, HttpServletRequest request, HttpServletResponse response) {
		lotteryWebMenu.setPage(new Page<>(request, response));
		Page<LotteryWebMenu> page = lotteryWebMenuService.findPage(lotteryWebMenu);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("lotterycore:lotteryWebMenu:view")
	@RequestMapping(value = "form")
	public String form(LotteryWebMenu lotteryWebMenu, Model model) {
		model.addAttribute("lotteryWebMenu", lotteryWebMenu);
		return "modules/lotterycore/lotteryWebMenuForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("lotterycore:lotteryWebMenu:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated LotteryWebMenu lotteryWebMenu) {
		lotteryWebMenuService.save(lotteryWebMenu);
		return renderResult(Global.TRUE, text("保存网站彩种设置成功！"));
	}
	
	/**
	 * 停用数据
	 */
	@RequiresPermissions("lotterycore:lotteryWebMenu:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(LotteryWebMenu lotteryWebMenu) {
		lotteryWebMenu.setStatus(LotteryWebMenu.STATUS_DISABLE);
		lotteryWebMenuService.updateStatus(lotteryWebMenu);
		return renderResult(Global.TRUE, text("停用网站彩种设置成功"));
	}
	
	/**
	 * 启用数据
	 */
	@RequiresPermissions("lotterycore:lotteryWebMenu:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(LotteryWebMenu lotteryWebMenu) {
		lotteryWebMenu.setStatus(LotteryWebMenu.STATUS_NORMAL);
		lotteryWebMenuService.updateStatus(lotteryWebMenu);
		return renderResult(Global.TRUE, text("启用网站彩种设置成功"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("lotterycore:lotteryWebMenu:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(LotteryWebMenu lotteryWebMenu) {
		lotteryWebMenuService.delete(lotteryWebMenu);
		return renderResult(Global.TRUE, text("删除网站彩种设置成功！"));
	}
	
}