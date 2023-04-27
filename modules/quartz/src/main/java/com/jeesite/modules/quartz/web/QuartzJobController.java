/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.quartz.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.quartz.entity.QuartzJob;
import com.jeesite.modules.quartz.service.QuartzJobService;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Quartz定时任务表Controller
 * @author 淡梦如烟
 * @version 2020-03-02
 */
@Controller
@RequestMapping(value = "${adminPath}/quartz/quartzJob")
public class QuartzJobController extends BaseController {

	@Autowired
	private QuartzJobService quartzJobService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public QuartzJob get(String id, boolean isNewRecord) {
		return quartzJobService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("quartz:quartzJob:view")
	@RequestMapping(value = {"list", ""})
	public String list(QuartzJob quartzJob, Model model) {
		model.addAttribute("quartzJob", quartzJob);
		return "modules/quartz/quartzJobList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("quartz:quartzJob:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<QuartzJob> listData(QuartzJob quartzJob, HttpServletRequest request, HttpServletResponse response) {
		quartzJob.setPage(new Page<>(request, response));
		Page<QuartzJob> page = quartzJobService.findPage(quartzJob);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("quartz:quartzJob:view")
	@RequestMapping(value = "form")
	public String form(QuartzJob quartzJob, Model model) {
		model.addAttribute("quartzJob", quartzJob);
		return "modules/quartz/quartzJobForm";
	}

	/**
	 * 保存quartz定时任务表
	 */
	@RequiresPermissions("quartz:quartzJob:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated QuartzJob quartzJob) {
		quartzJobService.save(quartzJob);
		return renderResult(Global.TRUE, text("保存quartz定时任务表成功！"));
	}
	
	/**
	 * 删除quartz定时任务表
	 */
	@RequiresPermissions("quartz:quartzJob:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(QuartzJob quartzJob) {
		quartzJobService.delete(quartzJob);
		return renderResult(Global.TRUE, text("删除quartz定时任务表成功！"));
	}

	/**
	 * 显示cron表达式界面
	 * @param id 待写入的input组件的id
	 * @param model 模型
	 * @return 视图
	 */
	@RequiresPermissions("quartz:quartzJob:view")
	@RequestMapping(value = "showCron")
	public String showCron(String id, Model model) {
		model.addAttribute("id", id);
		return "modules/quartz/cron-quartz/index";
	}

	/**
	 * 切换定时任务
	 * @param quartzJob
	 * @return
	 */
	@RequiresPermissions("quartz:quartzJob:edit")
	@RequestMapping(value = "switch")
	public Map switchQuartz(QuartzJob quartzJob, Model model) {
		Map result = new HashMap();
		result.put("code","error");
		result.put("msg","未知错误！");
		try {
			quartzJobService.switchQuartz(quartzJob);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg","切换定时任务失败！" + e.getMessage());
			return result;
		}
		result.put("code","ok");
		result.put("msg","切换定时任务成功！");
		return result;
	}
}