package com.jeesite.modules.autoRun;

import com.jeesite.common.lang.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 多彩网开奖数据采集
 * 
 * @Author Scott
 */
@Slf4j
public class DuocaiLotteryDrawDataCollection implements Job {

	/**
	 * 若参数变量名修改 QuartzJobController中也需对应修改
	 */
	private String parameter;

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		// 获取并解析
	}
}
