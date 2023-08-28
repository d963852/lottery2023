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
import com.jeesite.modules.lotterycore.entity.BetOrder;
import com.jeesite.modules.lotterycore.service.BetOrderService;

/**
 * 投注订单Controller
 * @author thinkgem
 * @version 2023-08-26
 */
@Controller
@RequestMapping(value = "${adminPath}/lotterycore/betOrder")
public class BetOrderController extends BaseController {

	@Autowired
	private BetOrderService betOrderService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public BetOrder get(String id, boolean isNewRecord) {
		return betOrderService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("lotterycore:betOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(BetOrder betOrder, Model model) {
		model.addAttribute("betOrder", betOrder);
		return "modules/lotterycore/betOrderList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("lotterycore:betOrder:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<BetOrder> listData(BetOrder betOrder, HttpServletRequest request, HttpServletResponse response) {
		betOrder.setPage(new Page<>(request, response));
		Page<BetOrder> page = betOrderService.findPage(betOrder);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("lotterycore:betOrder:view")
	@RequestMapping(value = "form")
	public String form(BetOrder betOrder, Model model) {
		model.addAttribute("betOrder", betOrder);
		return "modules/lotterycore/betOrderForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("lotterycore:betOrder:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated BetOrder betOrder) {
		betOrderService.save(betOrder);
		return renderResult(Global.TRUE, text("保存投注订单成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("lotterycore:betOrder:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(BetOrder betOrder) {
		betOrderService.delete(betOrder);
		return renderResult(Global.TRUE, text("删除投注订单成功！"));
	}
	
}