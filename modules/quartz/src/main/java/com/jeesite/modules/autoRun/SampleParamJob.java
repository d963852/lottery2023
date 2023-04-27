package com.jeesite.modules.autoRun;

import com.jeesite.common.lang.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 示例带参定时任务
 * 定时回收内存垃圾
 * 
 * @Author Scott
 */
@Slf4j
public class SampleParamJob implements Job {

	/**
	 * 若参数变量名修改 QuartzJobController中也需对应修改
	 */
	private String parameter;

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		System.gc();
		log.info(String.format("任务参数 [%s] 定时回收内存垃圾 ! System.gc()  时间:" + DateUtils.getDateTime(), this.parameter));
	}
}
