package com.jeesite.modules.lotterycore.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.lotterycore.entity.DuocaiLotteryType;

/**
 * 多彩网彩种DAO接口
 * @author DUKE
 * @version 2023-04-23
 */
@MyBatisDao(dataSourceName="bet")
public interface DuocaiLotteryTypeDao extends CrudDao<DuocaiLotteryType> {
	
}