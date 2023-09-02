package com.jeesite.modules.lotterycore.entity;

import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.constraints.Size;

/**
 * 提现信息Entity
 * @author thinkgem
 * @version 2023-09-01
 */
@Table(name="withdraw_card", alias="a", label="提现信息信息", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="user_account", attrName="userAccount", label="用户帐号", queryType=QueryType.LIKE),
		@Column(name="card_type", attrName="cardType", label="卡类型", comment="卡类型（U/支付宝/微信/银行卡/闪付/其他）"),
		@Column(name="bank_name", attrName="bankName", label="开户行名称", queryType=QueryType.LIKE),
		@Column(name="bank_account", attrName="bankAccount", label="账号", queryType=QueryType.LIKE),
		@Column(name="bank_account_name", attrName="bankAccountName", label="开户人", queryType=QueryType.LIKE),
		@Column(name="bank_address", attrName="bankAddress", label="开户行地址"),
		@Column(name="sub_bank_name", attrName="subBankName", label="支行名称", queryType=QueryType.LIKE),
		@Column(name="biz_status", attrName="bizStatus", label="业务状态"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
		@Column(name="deleted", attrName="deleted", label="是否删除"),
		@Column(name="tenant_id", attrName="tenantId", label="租户编号"),
	}, orderBy="a.update_date DESC"
)
public class WithdrawCard extends DataEntity<WithdrawCard> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String userAccount;		// 用户帐号
	private String cardType;		// 卡类型（U/支付宝/微信/银行卡/闪付/其他）
	private String bankName;		// 开户行名称
	private String bankAccount;		// 账号
	private String bankAccountName;		// 开户人
	private String bankAddress;		// 开户行地址
	private String subBankName;		// 支行名称
	private String bizStatus;		// 业务状态
	private String deleted;		// 是否删除
	private String tenantId;		// 租户编号

	public WithdrawCard() {
		this(null);
	}
	
	public WithdrawCard(String id){
		super(id);
	}
	
	@Size(min=0, max=64, message="用户ID长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Size(min=0, max=64, message="用户帐号长度不能超过 64 个字符")
	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	@Size(min=0, max=64, message="卡类型长度不能超过 64 个字符")
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	@Size(min=0, max=64, message="开户行名称长度不能超过 64 个字符")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Size(min=0, max=128, message="账号长度不能超过 128 个字符")
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	@Size(min=0, max=64, message="开户人长度不能超过 64 个字符")
	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	
	@Size(min=0, max=64, message="开户行地址长度不能超过 64 个字符")
	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	
	@Size(min=0, max=64, message="支行名称长度不能超过 64 个字符")
	public String getSubBankName() {
		return subBankName;
	}

	public void setSubBankName(String subBankName) {
		this.subBankName = subBankName;
	}
	
	@Size(min=0, max=64, message="业务状态长度不能超过 64 个字符")
	public String getBizStatus() {
		return bizStatus;
	}

	public void setBizStatus(String bizStatus) {
		this.bizStatus = bizStatus;
	}
	
	@Size(min=0, max=64, message="是否删除长度不能超过 64 个字符")
	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
	@Size(min=0, max=64, message="租户编号长度不能超过 64 个字符")
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
}