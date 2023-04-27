package com.jeesite.modules.lotterycore.entity;

import javax.validation.constraints.Size;
import com.jeesite.common.entity.BaseEntity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 多彩网彩种Entity
 * @author DUKE
 * @version 2023-04-23
 */
@Table(name="duocai_lottery_type", alias="a", label="多彩网彩种信息", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="lottery_name", attrName="lotteryName", label="彩种名称", queryType=QueryType.LIKE),
		@Column(name="lottery_code", attrName="lotteryCode", label="彩票代码"),
		@Column(name="lottery_issue_format", attrName="lotteryIssueFormat", label="奖期格式"),
		@Column(name="biz_status", attrName="bizStatus", label="是否采集"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
		@Column(name="deleted", attrName="deleted", label="是否删除", isQuery=false),
		@Column(name="tenant_id", attrName="tenantId", label="租户编号", isQuery=false),
	}, orderBy="a.update_date DESC"
)
public class DuocaiLotteryType extends DataEntity<DuocaiLotteryType> {
	
	private static final long serialVersionUID = 1L;
	private String lotteryName;		// 彩种名称
	private String lotteryCode;		// 彩票代码
	private String lotteryIssueFormat;		// 奖期格式
	private String bizStatus;		// 是否采集
	private String deleted;		// 是否删除
	private String tenantId;		// 租户编号

	public DuocaiLotteryType() {
		this(null);
	}
	
	public DuocaiLotteryType(String id){
		super(id);
	}
	
	@Size(min=0, max=64, message="彩种名称长度不能超过 64 个字符")
	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	
	@Size(min=0, max=64, message="彩票代码长度不能超过 64 个字符")
	public String getLotteryCode() {
		return lotteryCode;
	}

	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}
	
	@Size(min=0, max=64, message="奖期格式长度不能超过 64 个字符")
	public String getLotteryIssueFormat() {
		return lotteryIssueFormat;
	}

	public void setLotteryIssueFormat(String lotteryIssueFormat) {
		this.lotteryIssueFormat = lotteryIssueFormat;
	}
	
	@Size(min=0, max=64, message="是否采集长度不能超过 64 个字符")
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