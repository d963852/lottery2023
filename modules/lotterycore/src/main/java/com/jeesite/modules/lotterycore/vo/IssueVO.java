package com.jeesite.modules.lotterycore.vo;

import lombok.Data;

import java.util.Date;

/**
 * 彩票期号Entity
 *
 * @author DUKE
 * @version 2023-04-23
 */
@Data
public class IssueVO {
    private String gameCode;        // 所属游戏代码
    private String issueNum;        // 期数
    private Date lotteryDate;        // 开奖日期
    private String lotteryNum;        // 开奖号码
    private String lotterySource;        // 开奖来源
    private Date lotteryTime;        // 开奖时间
    private Date startTime;        // 开始时间
    private Date endTime;        // 结束时间
    private String state;        // 期号状态
    private Date syncTime;        // 计划开奖时间
    private Date planSyncTime;        // 实际开奖时间
}