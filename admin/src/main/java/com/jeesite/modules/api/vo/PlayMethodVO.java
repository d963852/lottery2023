package com.jeesite.modules.api.vo;

import lombok.Data;

@Data
public class PlayMethodVO {
    private String id;
    private String playMethodName;		// 玩法名称
    private Double maxBonus;		// 最大奖金额
    private Double minBonus;		// 最小奖金额
    private String selectNumber;		// 每注选几个号码
    private String simpleInfo;		// 玩法简单说明
    private String info;		// 玩法说明
    private String example;		// 中奖举例
    private String playMethodTpl;		// 玩法模版
    private String android;		// 移动端是否可用
    private Long sort;		// 排序
    private Double minConsume;		// 最低消费额度
    private String betCountFun;		// 注数计算函数
    private Long allBetCount;		// 总注数
    private Long maxBetCount;		// 最大注数

}
