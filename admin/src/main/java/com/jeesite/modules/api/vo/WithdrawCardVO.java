package com.jeesite.modules.api.vo;

import lombok.Data;

@Data
public class WithdrawCardVO {
    private String id;
    private String userId;		// 用户ID
    private String userAccount;		// 用户帐号
    private String cardType;		// 卡类型（U/支付宝/微信/银行卡/闪付/其他）
    private String bankName;		// 开户行名称
    private String bankAccount;		// 账号
    private String bankAccountName;		// 开户人
    private String bankAddress;		// 开户行地址
    private String subBankName;		// 支行名称
}
