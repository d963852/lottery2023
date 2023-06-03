package com.jeesite.modules.lotterycore.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.lotterycore.entity.Issue;

/**
 * 彩票期号DAO接口
 * @author DUKE
 * @version 2023-04-23
 */
@MyBatisDao(dataSourceName="bet")
public interface IssueDao extends CrudDao<Issue> {
	
}