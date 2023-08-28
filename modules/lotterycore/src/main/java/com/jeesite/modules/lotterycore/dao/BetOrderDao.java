package com.jeesite.modules.lotterycore.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.lotterycore.entity.BetOrder;

/**
 * 投注订单DAO接口
 * @author thinkgem
 * @version 2023-08-26
 */
@MyBatisDao(dataSourceName="bet")
public interface BetOrderDao extends CrudDao<BetOrder> {
	
}