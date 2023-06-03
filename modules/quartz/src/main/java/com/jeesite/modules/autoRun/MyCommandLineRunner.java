package com.jeesite.modules.autoRun;

import com.jeesite.modules.quartz.service.QuartzJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @program: jeesite4.1.5
 * @description: MyCommandLineRunner
 * @author: 淡梦如烟
 * @create: 2020-02-10 16:54
 */
@Component
@Order(value = 2)
public class MyCommandLineRunner implements CommandLineRunner {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Quartz定时任务模块
     */
    @Autowired
    private QuartzJobService quartzJobService;

    @Override
    public void run(String... args) throws Exception {;
        logger.info("启动Quartz定时任务模块>>>>>>>>>>>>>>>>>>>>>>>>");
        quartzJobService.autoRun();
    }
}