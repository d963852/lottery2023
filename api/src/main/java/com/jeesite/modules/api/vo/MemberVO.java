package com.jeesite.modules.api.vo;

import lombok.Data;

@Data
public class MemberVO {
    private String id;
    private String memId;		// 会员id
    private String memName;		// 会员名
    private Double balance;		// 余额
    private String realName;		// 真实姓名
    private String accountType;		// 账号类型
    private Long accountLevel;		// 账号级别
    private Double rebate;		// 投注返点
    private Double odds;		// 赔率
    private Double wage;		// 工资
    private Double bonus;		// 分红
    private String parentMemberId;		// 上级账号id
    private String parentMemberName;		// 上级账号名

}
