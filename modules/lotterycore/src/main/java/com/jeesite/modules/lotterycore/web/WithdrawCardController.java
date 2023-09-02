package com.jeesite.modules.lotterycore.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.lotterycore.entity.WithdrawCard;
import com.jeesite.modules.lotterycore.service.WithdrawCardService;
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
 * 提现信息Controller
 * @author thinkgem
 * @version 2023-09-01
 */
@Controller
@RequestMapping(value = "${adminPath}/lotterycore/withdrawCard")
public class WithdrawCardController extends BaseController {

	@Autowired
	private WithdrawCardService withdrawCardService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public WithdrawCard get(String id, boolean isNewRecord) {
		return withdrawCardService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("lotterycore:withdrawCard:view")
	@RequestMapping(value = {"list", ""})
	public String list(WithdrawCard withdrawCard, Model model) {
		model.addAttribute("withdrawCard", withdrawCard);
		return "modules/lotterycore/withdrawCardList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("lotterycore:withdrawCard:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<WithdrawCard> listData(WithdrawCard withdrawCard, HttpServletRequest request, HttpServletResponse response) {
		withdrawCard.setPage(new Page<>(request, response));
		Page<WithdrawCard> page = withdrawCardService.findPage(withdrawCard);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("lotterycore:withdrawCard:view")
	@RequestMapping(value = "form")
	public String form(WithdrawCard withdrawCard, Model model) {
		model.addAttribute("withdrawCard", withdrawCard);
		return "modules/lotterycore/withdrawCardForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("lotterycore:withdrawCard:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated WithdrawCard withdrawCard) {
		withdrawCardService.save(withdrawCard);
		return renderResult(Global.TRUE, text("保存提现信息成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("lotterycore:withdrawCard:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(WithdrawCard withdrawCard) {
		withdrawCardService.delete(withdrawCard);
		return renderResult(Global.TRUE, text("删除提现信息成功！"));
	}
	
}