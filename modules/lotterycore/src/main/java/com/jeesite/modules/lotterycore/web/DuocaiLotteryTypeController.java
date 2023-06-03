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
import com.alibaba.fastjson.JSONValidator;
import com.jeesite.common.codec.EncodeUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.lotterycore.entity.DuocaiLotteryType;
import com.jeesite.modules.lotterycore.service.DuocaiLotteryTypeService;

/**
 * 多彩网彩种Controller
 * @author DUKE
 * @version 2023-04-23
 */
@Controller
@RequestMapping(value = "${adminPath}/lotterycore/duocaiLotteryType")
public class DuocaiLotteryTypeController extends BaseController {

	@Autowired
	private DuocaiLotteryTypeService duocaiLotteryTypeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public DuocaiLotteryType get(String id, boolean isNewRecord) {
		return duocaiLotteryTypeService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("lotterycore:duocaiLotteryType:view")
	@RequestMapping(value = {"list", ""})
	public String list(DuocaiLotteryType duocaiLotteryType, Model model) {
		model.addAttribute("duocaiLotteryType", duocaiLotteryType);
		return "modules/lotterycore/duocaiLotteryTypeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("lotterycore:duocaiLotteryType:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<DuocaiLotteryType> listData(DuocaiLotteryType duocaiLotteryType, HttpServletRequest request, HttpServletResponse response) {
		duocaiLotteryType.setPage(new Page<>(request, response));
		Page<DuocaiLotteryType> page = duocaiLotteryTypeService.findPage(duocaiLotteryType);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("lotterycore:duocaiLotteryType:view")
	@RequestMapping(value = "form")
	public String form(DuocaiLotteryType duocaiLotteryType, Model model) {
		model.addAttribute("duocaiLotteryType", duocaiLotteryType);
		return "modules/lotterycore/duocaiLotteryTypeForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("lotterycore:duocaiLotteryType:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated DuocaiLotteryType duocaiLotteryType) {
		duocaiLotteryTypeService.save(duocaiLotteryType);
		return renderResult(Global.TRUE, text("保存多彩网彩种成功！"));
	}
	
	/**
	 * 停用数据
	 */
	@RequiresPermissions("lotterycore:duocaiLotteryType:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(DuocaiLotteryType duocaiLotteryType) {
		duocaiLotteryType.setStatus(DuocaiLotteryType.STATUS_DISABLE);
		duocaiLotteryTypeService.updateStatus(duocaiLotteryType);
		return renderResult(Global.TRUE, text("停用多彩网彩种成功"));
	}
	
	/**
	 * 启用数据
	 */
	@RequiresPermissions("lotterycore:duocaiLotteryType:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(DuocaiLotteryType duocaiLotteryType) {
		duocaiLotteryType.setStatus(DuocaiLotteryType.STATUS_NORMAL);
		duocaiLotteryTypeService.updateStatus(duocaiLotteryType);
		return renderResult(Global.TRUE, text("启用多彩网彩种成功"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("lotterycore:duocaiLotteryType:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(DuocaiLotteryType duocaiLotteryType) {
		duocaiLotteryTypeService.delete(duocaiLotteryType);
		return renderResult(Global.TRUE, text("删除多彩网彩种成功！"));
	}
	
	/**
	 * 列表选择对话框
	 */
	@RequiresPermissions("lotterycore:duocaiLotteryType:view")
	@RequestMapping(value = "duocaiLotteryTypeSelect")
	public String duocaiLotteryTypeSelect(DuocaiLotteryType duocaiLotteryType, String selectData, Model model) {
		String selectDataJson = EncodeUtils.decodeUrl(selectData);
		if (selectDataJson != null && JSONValidator.from(selectDataJson).validate()){
			model.addAttribute("selectData", selectDataJson);
		}
		model.addAttribute("duocaiLotteryType", duocaiLotteryType);
		return "modules/lotterycore/duocaiLotteryTypeSelect";
	}

	/**
	 * 停用数据
	 */
	@RequiresPermissions("lotterycore:duocaiLotteryType:edit")
	@RequestMapping(value = "disableCollect")
	@ResponseBody
	public String disableCollect(DuocaiLotteryType duocaiLotteryType) {
		duocaiLotteryType.setBizStatus("1");
		duocaiLotteryTypeService.update(duocaiLotteryType);
		return renderResult(Global.TRUE, text("停用多彩网采集成功"));
	}

	/**
	 * 启用数据
	 */
	@RequiresPermissions("lotterycore:duocaiLotteryType:edit")
	@RequestMapping(value = "enableCollect")
	@ResponseBody
	public String enableCollect(DuocaiLotteryType duocaiLotteryType) {
		duocaiLotteryType.setBizStatus("0");
		duocaiLotteryTypeService.update(duocaiLotteryType);
		return renderResult(Global.TRUE, text("启用多彩网采集成功"));
	}
	
}