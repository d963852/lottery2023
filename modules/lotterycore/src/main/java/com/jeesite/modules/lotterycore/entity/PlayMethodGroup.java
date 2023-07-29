package com.jeesite.modules.lotterycore.entity;

import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.constraints.Size;

/**
 * 玩法分组Entity
 * @author tg
 * @version 2023-06-20
 */
@Table(name="play_method_group", alias="a", label="玩法分组信息", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="game_category", attrName="gameCategory", label="游戏分类", queryType=QueryType.LIKE),
		@Column(name="group_name", attrName="groupName", label="分组名称", queryType=QueryType.LIKE),
		@Column(name="sort", attrName="sort", label="排序", isQuery=false, isUpdateForce=true),
		@Column(name="positioned", attrName="positioned", label="是否不定位"),
		@Column(name="android", attrName="android", label="移动端是否可用"),
		@Column(name="biz_status", attrName="bizStatus", label="业务状态", isQuery=false),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
		@Column(name="deleted", attrName="deleted", label="是否删除", isQuery=false),
		@Column(name="tenant_id", attrName="tenantId", label="租户编号", isQuery=false),
	}, orderBy="a.sort ASC"
)
public class PlayMethodGroup extends DataEntity<PlayMethodGroup> {
	
	private static final long serialVersionUID = 1L;
	private String gameCategory;		// 游戏分类
	private String groupName;		// 分组名称
	private Long sort;		// 排序
	private String positioned;		// 是否不定位
	private String android;		// 移动端是否可用
	private String bizStatus;		// 业务状态
	private String deleted;		// 是否删除
	private String tenantId;		// 租户编号

	public PlayMethodGroup() {
		this(null);
	}
	
	public PlayMethodGroup(String id){
		super(id);
	}
	
	@Size(min=0, max=64, message="游戏分类长度不能超过 64 个字符")
	public String getGameCategory() {
		return gameCategory;
	}

	public void setGameCategory(String gameCategory) {
		this.gameCategory = gameCategory;
	}
	
	@Size(min=0, max=64, message="分组名称长度不能超过 64 个字符")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Size(min=0, max=64, message="是否不定位长度不能超过 64 个字符")
	public String getPositioned() {
		return positioned;
	}

	public void setPositioned(String positioned) {
		this.positioned = positioned;
	}
	
	@Size(min=0, max=64, message="移动端是否可用长度不能超过 64 个字符")
	public String getAndroid() {
		return android;
	}

	public void setAndroid(String android) {
		this.android = android;
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