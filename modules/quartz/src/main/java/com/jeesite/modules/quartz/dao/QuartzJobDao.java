/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.quartz.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.quartz.entity.QuartzJob;

/**
 * Quartz定时任务表DAO接口
 * @author 淡梦如烟
 * @version 2020-03-02
 */
@MyBatisDao
public interface QuartzJobDao extends CrudDao<QuartzJob> {
	
}