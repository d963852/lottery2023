package com.jeesite.modules.lotterycore.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.lotterycore.entity.AccountChangeLog;

/**
 * 账变日志DAO接口
 * @author thinkgem
 * @version 2023-08-26
 */
@MyBatisDao(dataSourceName="bet")
public interface AccountChangeLogDao extends CrudDao<AccountChangeLog> {
	
}