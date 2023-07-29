package com.jeesite.modules.lotterycore.entity;

import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.constraints.Size;

/**
 * 玩法管理Entity
 * @author tg
 * @version 2023-06-20
 */
@Table(name="play_method", alias="a", label="玩法管理信息", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="play_method_name", attrName="playMethodName", label="玩法名称", queryType=QueryType.LIKE),
		@Column(name="group_id", attrName="groupId", label="玩法分组"),
		@Column(name="max_bonus", attrName="maxBonus", label="最大奖金额", isQuery=false, isUpdateForce=true),
		@Column(name="min_bonus", attrName="minBonus", label="最小奖金额", isQuery=false, isUpdateForce=true),
		@Column(name="select_number", attrName="selectNumber", label="每注选几个号码"),
		@Column(name="simple_info", attrName="simpleInfo", label="玩法简单说明"),
		@Column(name="info", attrName="info", label="玩法说明"),
		@Column(name="example", attrName="example", label="中奖举例"),
		@Column(name="win_rule_fun", attrName="winRuleFun", label="中奖规则函数"),
		@Column(name="max_win_number_fun", attrName="maxWinNumberFun", label="最大中奖号码计算函数"),
		@Column(name="play_method_tpl", attrName="playMethodTpl", label="玩法模版"),
		@Column(name="android", attrName="android", label="移动端是否可用"),
		@Column(name="sort", attrName="sort", label="排序", isUpdateForce=true),
		@Column(name="min_consume", attrName="minConsume", label="最低消费额度", isUpdateForce=true),
		@Column(name="bet_count_fun", attrName="betCountFun", label="注数计算函数"),
		@Column(name="all_bet_count", attrName="allBetCount", label="总注数", isUpdateForce=true),
		@Column(name="max_bet_count", attrName="maxBetCount", label="最大注数", isUpdateForce=true),
		@Column(name="biz_status", attrName="bizStatus", label="业务状态", isQuery=false),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
		@Column(name="deleted", attrName="deleted", label="是否删除", isQuery=false),
		@Column(name="tenant_id", attrName="tenantId", label="租户编号", isQuery=false),
	}, orderBy="a.sort ASC"
)
public class PlayMethod extends DataEntity<PlayMethod> {
	
	private static final long serialVersionUID = 1L;
	private String playMethodName;		// 玩法名称
	private String groupId;		// 玩法分组
	private Double maxBonus;		// 最大奖金额
	private Double minBonus;		// 最小奖金额
	private String selectNumber;		// 每注选几个号码
	private String simpleInfo;		// 玩法简单说明
	private String info;		// 玩法说明
	private String example;		// 中奖举例
	private String winRuleFun;		// 中奖规则函数
	private String maxWinNumberFun;		// 最大中奖号码计算函数
	private String playMethodTpl;		// 玩法模版
	private String android;		// 移动端是否可用
	private Long sort;		// 排序
	private Double minConsume;		// 最低消费额度
	private String betCountFun;		// 注数计算函数
	private Long allBetCount;		// 总注数
	private Long maxBetCount;		// 最大注数
	private String bizStatus;		// 业务状态
	private String deleted;		// 是否删除
	private String tenantId;		// 租户编号

	public PlayMethod() {
		this(null);
	}
	
	public PlayMethod(String id){
		super(id);
	}
	
	@Size(min=0, max=64, message="玩法名称长度不能超过 64 个字符")
	public String getPlayMethodName() {
		return playMethodName;
	}

	public void setPlayMethodName(String playMethodName) {
		this.playMethodName = playMethodName;
	}
	
	@Size(min=0, max=64, message="玩法分组长度不能超过 64 个字符")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public Double getMaxBonus() {
		return maxBonus;
	}

	public void setMaxBonus(Double maxBonus) {
		this.maxBonus = maxBonus;
	}
	
	public Double getMinBonus() {
		return minBonus;
	}

	public void setMinBonus(Double minBonus) {
		this.minBonus = minBonus;
	}
	
	@Size(min=0, max=64, message="每注选几个号码长度不能超过 64 个字符")
	public String getSelectNumber() {
		return selectNumber;
	}

	public void setSelectNumber(String selectNumber) {
		this.selectNumber = selectNumber;
	}
	
	@Size(min=0, max=64, message="玩法简单说明长度不能超过 64 个字符")
	public String getSimpleInfo() {
		return simpleInfo;
	}

	public void setSimpleInfo(String simpleInfo) {
		this.simpleInfo = simpleInfo;
	}
	
	@Size(min=0, max=1640, message="玩法说明长度不能超过 1640 个字符")
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	@Size(min=0, max=1640, message="中奖举例长度不能超过 1640 个字符")
	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}
	
	@Size(min=0, max=64, message="中奖规则函数长度不能超过 64 个字符")
	public String getWinRuleFun() {
		return winRuleFun;
	}

	public void setWinRuleFun(String winRuleFun) {
		this.winRuleFun = winRuleFun;
	}
	
	@Size(min=0, max=64, message="最大中奖号码计算函数长度不能超过 64 个字符")
	public String getMaxWinNumberFun() {
		return maxWinNumberFun;
	}

	public void setMaxWinNumberFun(String maxWinNumberFun) {
		this.maxWinNumberFun = maxWinNumberFun;
	}
	
	@Size(min=0, max=64, message="玩法模版长度不能超过 64 个字符")
	public String getPlayMethodTpl() {
		return playMethodTpl;
	}

	public void setPlayMethodTpl(String playMethodTpl) {
		this.playMethodTpl = playMethodTpl;
	}
	
	@Size(min=0, max=64, message="移动端是否可用长度不能超过 64 个字符")
	public String getAndroid() {
		return android;
	}

	public void setAndroid(String android) {
		this.android = android;
	}
	
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	public Double getMinConsume() {
		return minConsume;
	}

	public void setMinConsume(Double minConsume) {
		this.minConsume = minConsume;
	}
	
	@Size(min=0, max=64, message="注数计算函数长度不能超过 64 个字符")
	public String getBetCountFun() {
		return betCountFun;
	}

	public void setBetCountFun(String betCountFun) {
		this.betCountFun = betCountFun;
	}
	
	public Long getAllBetCount() {
		return allBetCount;
	}

	public void setAllBetCount(Long allBetCount) {
		this.allBetCount = allBetCount;
	}
	
	public Long getMaxBetCount() {
		return maxBetCount;
	}

	public void setMaxBetCount(Long maxBetCount) {
		this.maxBetCount = maxBetCount;
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
	
	public Double getMinConsume_gte() {
		return sqlMap.getWhere().getValue("min_consume", QueryType.GTE);
	}

	public void setMinConsume_gte(Double minConsume) {
		sqlMap.getWhere().and("min_consume", QueryType.GTE, minConsume);
	}
	
	public Double getMinConsume_lte() {
		return sqlMap.getWhere().getValue("min_consume", QueryType.LTE);
	}

	public void setMinConsume_lte(Double minConsume) {
		sqlMap.getWhere().and("min_consume", QueryType.LTE, minConsume);
	}
	
}