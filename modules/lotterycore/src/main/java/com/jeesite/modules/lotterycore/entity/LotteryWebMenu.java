package com.jeesite.modules.lotterycore.entity;

import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.constraints.Size;

/**
 * 网站彩种菜单设置Entity
 * @author tg
 * @version 2023-06-09
 */
@Table(name="lottery_web_menu", alias="a", label="网站彩种设置信息", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="web_site", attrName="webSite", label="网站代码", queryType=QueryType.LIKE),
		@Column(name="menu_group", attrName="menuGroup", label="菜单分组", isQuery=false),
		@Column(name="menu_group_sort", attrName="menuGroupSort", label="菜单分组排序", isQuery=false, isUpdateForce=true),
		@Column(name="menu_group_name", attrName="menuGroupName", label="菜单分组分组名称", queryType=QueryType.LIKE),
		@Column(name="game_code", attrName="gameCode", label="游戏代码"),
		@Column(name="game_name", attrName="gameName", label="游戏名称", queryType=QueryType.LIKE),
		@Column(name="game_sort", attrName="gameSort", label="游戏排序", isUpdateForce=true),
		@Column(name="game_hot", attrName="gameHot", label="是否热门", isQuery=false),
		@Column(name="biz_status", attrName="bizStatus", label="业务状态", isQuery=false),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
		@Column(name="deleted", attrName="deleted", label="是否删除", isQuery=false),
		@Column(name="tenant_id", attrName="tenantId", label="租户编号", isQuery=false),
	},
		// 联合查询出外键编码的名称数据（attrName="this"，指定this代表，当前实体）
		joinTable={
				@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity=Game.class, attrName="this", alias="b",
						on="b.game_code = a.game_code",
						columns={
								@Column(name="id", attrName="gameId", label="游戏id"),
						}),
		}, orderBy="a.game_group ASC"
)
public class LotteryWebMenu extends DataEntity<LotteryWebMenu> {
	
	private static final long serialVersionUID = 1L;
	private String webSite;		// 网站代码
	private String menuGroup;		// 菜单分组
	private Long menuGroupSort;		// 菜单分组排序
	private String menuGroupName;		// 菜单分组分组名称
	private String gameCode;		// 游戏代码
	private String gameName;		// 游戏名称
	private Long gameSort;		// 游戏排序
	private String gameHot;		// 是否热门
	private String bizStatus;		// 业务状态
	private String deleted;		// 是否删除
	private String tenantId;		// 租户编号
	private String gameId;		// 租户编号

	public LotteryWebMenu() {
		this(null);
	}
	
	public LotteryWebMenu(String id){
		super(id);
	}
	
	@Size(min=0, max=64, message="网站代码长度不能超过 64 个字符")
	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	
	@Size(min=0, max=64, message="菜单分组长度不能超过 64 个字符")
	public String getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(String menuGroup) {
		this.menuGroup = menuGroup;
	}
	
	public Long getMenuGroupSort() {
		return menuGroupSort;
	}

	public void setMenuGroupSort(Long menuGroupSort) {
		this.menuGroupSort = menuGroupSort;
	}
	
	@Size(min=0, max=64, message="菜单分组分组名称长度不能超过 64 个字符")
	public String getMenuGroupName() {
		return menuGroupName;
	}

	public void setMenuGroupName(String menuGroupName) {
		this.menuGroupName = menuGroupName;
	}
	
	@Size(min=0, max=64, message="游戏代码长度不能超过 64 个字符")
	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	
	@Size(min=0, max=64, message="游戏名称长度不能超过 64 个字符")
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	public Long getGameSort() {
		return gameSort;
	}

	public void setGameSort(Long gameSort) {
		this.gameSort = gameSort;
	}
	
	@Size(min=0, max=64, message="是否热门长度不能超过 64 个字符")
	public String getGameHot() {
		return gameHot;
	}

	public void setGameHot(String gameHot) {
		this.gameHot = gameHot;
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

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
}