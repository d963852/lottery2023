package com.jeesite.modules.lotterycore.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.lotterycore.entity.Issue;

import java.util.List;

/**
 * 彩票期号DAO接口
 * @author DUKE
 * @version 2023-04-23
 */
@MyBatisDao(dataSourceName="bet")
public interface IssueDao extends CrudDao<Issue> {

    /**
     * 获取指定游戏的当前期号
     * @param gameCode
     * @return
     */
    public Issue getCurrentIssue(String gameCode);
    /**
     * 获取指定游戏的上期期号
     * @param gameCode
     * @return
     */
    public Issue getLastIssue(String gameCode);

    /**
     * 根据游戏代码和游戏期号查找期号
     * @param gameCode
     * @param issueNumber
     * @return
     */
    public Issue getByGameCodeAndIssueNumber(String gameCode,String issueNumber);

    /**
     * 获取游戏开奖历史数据
     * @param gameCode
     * @param limitNumber
     * @return
     */
    public List<Issue> findHistory(String gameCode, int limitNumber);

}