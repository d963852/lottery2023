package com.jeesite.modules.api.vo;

import lombok.Data;

import java.util.Date;

@Data
public class AccountChangeLogVO {
    private String id;
    private Double changeAmount;		// 账变金额
    private Date changeTime;		// 账变时间
    private String changeType;		// 账变类型
    private Double balance;		// 余额
    private String actionUserAccount;		// 操作人
    private String remarks;		// 备注

}
