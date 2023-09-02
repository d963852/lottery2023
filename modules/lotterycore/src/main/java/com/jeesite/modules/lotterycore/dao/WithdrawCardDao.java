package com.jeesite.modules.lotterycore.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.lotterycore.entity.WithdrawCard;

/**
 * 提现信息DAO接口
 * @author thinkgem
 * @version 2023-09-01
 */
@MyBatisDao(dataSourceName="bet")
public interface WithdrawCardDao extends CrudDao<WithdrawCard> {
	
}