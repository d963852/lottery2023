package com.jeesite.modules.api.vo;

import lombok.Data;

import java.util.Date;

@Data
public class BetOrderVO {
    private String id;
    private Date betTime;        // 投注时间
    private String gameCode;        // 游戏代码
    private String gameName;        // 游戏名称
    private String playMethodGroup;        // 玩法组
    private String playMethod;        // 玩法
    private String issueId;        // 期号id
    private String issue;        // 期号
    private String betNumber;        // 投注号码
    private String extBetNumber;        // 附加号码
    private Long betCount;        // 注数
    private Long betMultiple;        // 倍数
    private Long totalBetCount;        // 总注数
    private Double betUnit;        // 货币单位
    private Double betAmount;        // 总投注金额
    private Double rebate;        // 返点
    private Double rebateAmount;        // 返点金额
    private Double bonusAmount;        // 奖金金额
    private String syndicate;        // 合买
    private String frisbee;        // 飞盘
    private String chasing;        // 追号
    private Long chasingIssue;        // 追号期数
    private Long chasingIssueRemain;        // 剩余追号期数
    private String chasingWinStop;        // 中奖停止追号
    private String lotteryNumber;        // 开奖号码
    private Date lotteryTime;        // 开奖时间
    private Long winCount;        // 中奖注数
    private Double winAmount;        // 中奖金额
    private Date cancelTime;        // 撤单时间
    private String bizStatus;        // 撤单时间

}
