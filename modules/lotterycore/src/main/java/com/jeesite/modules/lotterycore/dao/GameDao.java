package com.jeesite.modules.lotterycore.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.lotterycore.entity.Game;

/**
 * 游戏DAO接口
 * @author DUKE
 * @version 2023-04-22
 */
@MyBatisDao(dataSourceName="bet")
public interface GameDao extends CrudDao<Game> {
	public Game getGameByCode(String gameCode);
}