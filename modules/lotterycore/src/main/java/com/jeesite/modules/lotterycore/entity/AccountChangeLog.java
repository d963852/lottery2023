package com.jeesite.modules.lotterycore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 账变日志Entity
 * @author thinkgem
 * @version 2023-08-26
 */
@Table(name="account_change_log", alias="a", label="账变日志信息", columns={
		@Column(name="id", attrName="id", label="主键id", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="user_account", attrName="userAccount", label="用户账号", queryType=QueryType.LIKE),
		@Column(name="change_amount", attrName="changeAmount", label="账变金额", isUpdateForce=true),
		@Column(name="change_time", attrName="changeTime", label="账变时间", isUpdateForce=true),
		@Column(name="change_type", attrName="changeType", label="账变类型"),
		@Column(name="balance", attrName="balance", label="余额", isUpdateForce=true),
		@Column(name="biz_type", attrName="bizType", label="关联类型"),
		@Column(name="biz_id", attrName="bizId", label="关联id"),
		@Column(name="freeze", attrName="freeze", label="是否冻结"),
		@Column(name="can_withdraw", attrName="canWithdraw", label="是否可提现"),
//		@Column(name="issue", attrName="issue", label="游戏期号", queryType=QueryType.LIKE),
//		@Column(name="bet_order_no", attrName="betOrderNo", label="投注订单号"),
		@Column(name="action_user_id", attrName="actionUserId", label="操作人id"),
		@Column(name="action_user_account", attrName="actionUserAccount", label="操作人账号", queryType=QueryType.LIKE),
		@Column(name="action_time", attrName="actionTime", label="操作时间", isUpdateForce=true),
		@Column(name="action_ip", attrName="actionIp", label="操作ip"),
		@Column(name="version", attrName="version", label="乐观锁版本号", isQuery=false, isUpdateForce=true),
		@Column(name="biz_status", attrName="bizStatus", label="业务状态"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
		@Column(name="deleted", attrName="deleted", label="是否删除"),
		@Column(name="tenant_id", attrName="tenantId", label="租户编号"),
	}, orderBy="a.update_date DESC,a.id DESC"
)
public class AccountChangeLog extends DataEntity<AccountChangeLog> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String userAccount;		// 用户账号
	private Double changeAmount;		// 账变金额
	private Date changeTime;		// 账变时间
	private String changeType;		// 账变类型
	private Double balance;		// 余额
	private String bizType;		// 关联类型
	private String bizId;		// 关联id
	private String freeze;		// 是否冻结
	private String canWithdraw;		// 是否可提现
//	private String issue;		// 游戏期号
//	private String betOrderNo;		// 投注订单号
	private String actionUserId;		// 操作人id
	private String actionUserAccount;		// 操作人账号
	private Date actionTime;		// 操作时间
	private String actionIp;		// 操作ip
	private Long version;		// 乐观锁版本号
	private String bizStatus;		// 业务状态
	private String deleted;		// 是否删除
	private String tenantId;		// 租户编号

	public AccountChangeLog() {
		this(null);
	}
	
	public AccountChangeLog(String id){
		super(id);
	}
	
	@Size(min=0, max=64, message="用户id长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Size(min=0, max=64, message="用户账号长度不能超过 64 个字符")
	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public Double getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(Double changeAmount) {
		this.changeAmount = changeAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	
	@Size(min=0, max=64, message="账变类型长度不能超过 64 个字符")
	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	@Size(min=0, max=64, message="关联类型长度不能超过 64 个字符")
	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	@Size(min=0, max=64, message="关联id长度不能超过 64 个字符")
	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	
	@Size(min=0, max=10, message="是否冻结长度不能超过 10 个字符")
	public String getFreeze() {
		return freeze;
	}

	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}
	
	@Size(min=0, max=10, message="是否可提现长度不能超过 10 个字符")
	public String getCanWithdraw() {
		return canWithdraw;
	}

	public void setCanWithdraw(String canWithdraw) {
		this.canWithdraw = canWithdraw;
	}
	
//	@Size(min=0, max=64, message="游戏期号长度不能超过 64 个字符")
//	public String getIssue() {
//		return issue;
//	}
//
//	public void setIssue(String issue) {
//		this.issue = issue;
//	}
//
//	@Size(min=0, max=64, message="投注订单号长度不能超过 64 个字符")
//	public String getBetOrderNo() {
//		return betOrderNo;
//	}
//
//	public void setBetOrderNo(String betOrderNo) {
//		this.betOrderNo = betOrderNo;
//	}
	
	@Size(min=0, max=64, message="操作人id长度不能超过 64 个字符")
	public String getActionUserId() {
		return actionUserId;
	}

	public void setActionUserId(String actionUserId) {
		this.actionUserId = actionUserId;
	}
	
	@Size(min=0, max=64, message="操作人账号长度不能超过 64 个字符")
	public String getActionUserAccount() {
		return actionUserAccount;
	}

	public void setActionUserAccount(String actionUserAccount) {
		this.actionUserAccount = actionUserAccount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}
	
	@Size(min=0, max=64, message="操作ip长度不能超过 64 个字符")
	public String getActionIp() {
		return actionIp;
	}

	public void setActionIp(String actionIp) {
		this.actionIp = actionIp;
	}
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
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
	
	public Double getChangeAmount_gte() {
		return sqlMap.getWhere().getValue("change_amount", QueryType.GTE);
	}

	public void setChangeAmount_gte(Double changeAmount) {
		sqlMap.getWhere().and("change_amount", QueryType.GTE, changeAmount);
	}
	
	public Double getChangeAmount_lte() {
		return sqlMap.getWhere().getValue("change_amount", QueryType.LTE);
	}

	public void setChangeAmount_lte(Double changeAmount) {
		sqlMap.getWhere().and("change_amount", QueryType.LTE, changeAmount);
	}
	
	public Date getChangeTime_gte() {
		return sqlMap.getWhere().getValue("change_time", QueryType.GTE);
	}

	public void setChangeTime_gte(Date changeTime) {
		sqlMap.getWhere().and("change_time", QueryType.GTE, changeTime);
	}
	
	public Date getChangeTime_lte() {
		return sqlMap.getWhere().getValue("change_time", QueryType.LTE);
	}

	public void setChangeTime_lte(Date changeTime) {
		sqlMap.getWhere().and("change_time", QueryType.LTE, changeTime);
	}
	
	public Double getBalance_gte() {
		return sqlMap.getWhere().getValue("balance", QueryType.GTE);
	}

	public void setBalance_gte(Double balance) {
		sqlMap.getWhere().and("balance", QueryType.GTE, balance);
	}
	
	public Double getBalance_lte() {
		return sqlMap.getWhere().getValue("balance", QueryType.LTE);
	}

	public void setBalance_lte(Double balance) {
		sqlMap.getWhere().and("balance", QueryType.LTE, balance);
	}
	
	public Date getActionTime_gte() {
		return sqlMap.getWhere().getValue("action_time", QueryType.GTE);
	}

	public void setActionTime_gte(Date actionTime) {
		sqlMap.getWhere().and("action_time", QueryType.GTE, actionTime);
	}
	
	public Date getActionTime_lte() {
		return sqlMap.getWhere().getValue("action_time", QueryType.LTE);
	}

	public void setActionTime_lte(Date actionTime) {
		sqlMap.getWhere().and("action_time", QueryType.LTE, actionTime);
	}
	
}