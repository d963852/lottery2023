package com.jeesite.modules.lotterycore.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.lotterycore.entity.LotteryWebMenu;

/**
 * 网站彩种菜单设置DAO接口
 * @author tg
 * @version 2023-06-09
 */
@MyBatisDao(dataSourceName="bet")
public interface LotteryWebMenuDao extends CrudDao<LotteryWebMenu> {
	
}