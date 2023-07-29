package com.jeesite.modules.lotterycore.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.lotterycore.entity.PlayMethod;

/**
 * 玩法管理DAO接口
 * @author tg
 * @version 2023-06-20
 */
@MyBatisDao(dataSourceName="bet")
public interface PlayMethodDao extends CrudDao<PlayMethod> {
	
}