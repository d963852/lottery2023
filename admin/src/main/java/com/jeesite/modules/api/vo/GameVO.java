package com.jeesite.modules.api.vo;

import lombok.Data;

import java.util.Date;

@Data
public class GameVO {
    private String id;
    private String gameCode;        // 游戏代码
    private String gameName;        // 游戏名称
    private Double orderNo;        // 排序号
    private String gameDesc;        // 游戏说明
    private String gameCategory;        // 对应游戏类别名称
    private String gameCategoryId;        // 对应游戏类别id
    private String hotGameFlag;        // 是否热门
    private String currentIssueNumber;        // 当前期号
    private Date currentIssueEndTime;        // 当前期号投注截止时间
    private String lastIssueNumber;        // 上期期号
    private String lastIssueLotteryNumber;        // 上期期号开奖结果
    private String imgUrl;

}
