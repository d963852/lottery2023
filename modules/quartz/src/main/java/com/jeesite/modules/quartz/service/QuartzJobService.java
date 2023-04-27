/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.quartz.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.quartz.dao.QuartzJobDao;
import com.jeesite.modules.quartz.entity.QuartzJob;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Quartz定时任务表Service
 * @author 淡梦如烟
 * @version 2020-03-02
 */
@Service
@Transactional(readOnly=true)
public class QuartzJobService extends CrudService<QuartzJobDao, QuartzJob> {

	@Autowired
	private Scheduler scheduler;

	/**
	 * 获取单条数据
	 * @param quartzJob
	 * @return
	 */
	@Override
	public QuartzJob get(QuartzJob quartzJob) {
		return super.get(quartzJob);
	}
	
	/**
	 * 查询分页数据
	 * @param quartzJob 查询条件
	 * @return
	 */
	@Override
	public Page<QuartzJob> findPage(QuartzJob quartzJob) {
		return super.findPage(quartzJob);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param quartzJob
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(QuartzJob quartzJob) {
		super.preSave(quartzJob);
		User user = UserUtils.getUser();
		if (quartzJob.getIsNewRecord()) {
			//新增
			if("0".equals(quartzJob.getStatus())){
				// 定时器添加
				this.schedulerAdd(quartzJob.getJobClassName().trim(), quartzJob.getCronExpression().trim(), quartzJob.getParameter());
			}
			quartzJob.setCreateBy(user.getLoginCode());
			quartzJob.setCreateTime(new Date());
			super.insert(quartzJob);
		} else {
			//修改
			if("0".equals(quartzJob.getStatus())){
				//定时器删除
				this.schedulerDelete(quartzJob.getJobClassName().trim());
				// 定时器添加
				this.schedulerAdd(quartzJob.getJobClassName().trim(), quartzJob.getCronExpression().trim(), quartzJob.getParameter());
			}else{
				try {
					//定时器暂停
					scheduler.pauseJob(JobKey.jobKey(quartzJob.getJobClassName().trim()));
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
			}
			quartzJob.setUpdateBy(user.getLoginCode());
			quartzJob.setUpdateTime(new Date());
			super.update(quartzJob);
		}
	}

	/**
	 * 更新状态
	 * @param quartzJob
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(QuartzJob quartzJob) {
		super.updateStatus(quartzJob);
	}
	
	/**
	 * 删除数据
	 * @param quartzJob
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(QuartzJob quartzJob) {
		schedulerDelete(quartzJob.getJobClassName().trim());
		super.delete(quartzJob);
	}

	/**
	 * 添加定时任务
	 * @param jobClassName 类名
	 * @param cronExpression cron表达式
	 * @param parameter 参数
	 */
	private void schedulerAdd(String jobClassName, String cronExpression, String parameter) {
		try {
			// 启动调度器
			scheduler.start();

			// 构建job信息
			JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName).usingJobData("parameter", parameter).build();

			// 表达式调度构建器(即任务执行的时间)
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

			// 按新的cronExpression表达式构建一个新的trigger
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName).withSchedule(scheduleBuilder).build();

			scheduler.scheduleJob(jobDetail, trigger);
			logger.info("定时任务[" + jobClassName + "]已添加");
		} catch (SchedulerException e) {
			throw new RuntimeException("创建定时任务失败", e);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage(), e);
		}catch (Exception e) {
			throw new RuntimeException("后台找不到该类名：" + jobClassName, e);
		}
	}

	/**
	 * 获取Class
	 * @param classname Class类名
	 * @return
	 * @throws Exception
	 */
	private static Job getClass(String classname) throws Exception {
		Class<?> class1 = Class.forName(classname);
		return (Job) class1.newInstance();
	}

	/**
	 * 删除定时任务
	 *
	 * @param jobClassName
	 */
	private void schedulerDelete(String jobClassName) {
		try {
			scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName));
			scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName));
			scheduler.deleteJob(JobKey.jobKey(jobClassName));
			logger.info("定时任务[" + jobClassName + "]已删除");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("删除定时任务失败");
		}
	}

	/**
	 * 切换任务调度
	 * @param quartzJob
	 */
	@Transactional(readOnly = false)
	public void switchQuartz(QuartzJob quartzJob) throws Exception{
		QuartzJob quartzJob1 = new QuartzJob();
		quartzJob1.setId(quartzJob.getId());
		QuartzJob quartzJob2 = super.get(quartzJob1);
		quartzJob2.setStatus(quartzJob.getStatus());
		User user = UserUtils.getUser();
		//修改
		if ("0".equals(quartzJob2.getStatus())) {
			//定时器删除
			this.schedulerDelete(quartzJob2.getJobClassName().trim());
			// 定时器添加
			this.schedulerAdd(quartzJob2.getJobClassName().trim(), quartzJob2.getCronExpression().trim(), quartzJob2.getParameter());
		} else {
			try {
				//定时器暂停
				scheduler.pauseJob(JobKey.jobKey(quartzJob2.getJobClassName().trim()));
				logger.info("定时任务[" + quartzJob2.getJobClassName() + "]已暂停");
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
		quartzJob2.setUpdateBy(user.getLoginCode());
		quartzJob2.setUpdateTime(new Date());
		super.update(quartzJob2);
		super.updateStatus(quartzJob2);
	}

	/**
	 * 系统启动时启动所有开启的定时任务作业
	 */
	@Transactional(readOnly = false)
	public void autoRun() {
		try {
			QuartzJob quartzJob1 = new QuartzJob();
			//已启用的任务
			quartzJob1.setStatus("0");
			List<QuartzJob> list = super.findList(quartzJob1);
			logger.info("已启用的任务共[" + list.size() + "]条,正在依次启动>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			for (QuartzJob quartzJob2 : list) {
				try {
					this.switchQuartz(quartzJob2);
				} catch (Exception e) {
					logger.error("启动失败! 任务类名[" + quartzJob2.getJobClassName() + "],描述[" +
							quartzJob2.getDescription() + "],详细信息:");
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}