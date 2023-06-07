package com.jeesite.modules.lotterycore.entity;

import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.constraints.Size;

/**
 * 游戏Entity
 *
 * @author DUKE
 * @version 2023-04-22
 */
@Table(name = "game", alias = "a", label = "游戏信息", columns = {
        @Column(name = "id", attrName = "id", label = "主键id", isPK = true),
        @Column(name = "game_code", attrName = "gameCode", label = "游戏代码", queryType = QueryType.LIKE),
        @Column(name = "game_name", attrName = "gameName", label = "游戏名称", queryType = QueryType.LIKE),
        @Column(name = "order_no", attrName = "orderNo", label = "排序号", isUpdateForce = true),
        @Column(name = "lottery_number_count", attrName = "lotteryNumberCount", label = "开奖结果数字个数", isUpdateForce = true),
        @Column(name = "lottery_number_min", attrName = "lotteryNumberMin", label = "开奖结果最小数字", isUpdateForce = true),
        @Column(name = "lottery_number_max", attrName = "lotteryNumberMax", label = "开奖结果最大数字", isUpdateForce = true),
        @Column(name = "lottery_number_list", attrName = "lotteryNumberList", label = "开奖号码列表", isUpdateForce = true),
        @Column(name = "lottery_number_repeat", attrName = "lotteryNumberRepeat", label = "开奖号码是否能重复", isUpdateForce = true),
        @Column(name = "lottery_delay_second", attrName = "lotteryDelaySecond", label = "开奖延迟秒数", isUpdateForce = true),
        @Column(name = "duocai_game_code", attrName = "duocaiGameCode", label = "对应多彩游戏代码", isUpdateForce = true),
        @Column(name = "lottery_url", attrName = "lotteryUrl", label = "开奖地址", queryType = QueryType.LIKE, isUpdateForce = true),
        @Column(name = "lottery_local_instead", attrName = "lotteryLocalInstead", label = "是否本地开奖代替", queryType = QueryType.EQ, isUpdateForce = true),
        @Column(name = "state", attrName = "state", label = "状态,启用", comment = "状态,启用:1;禁用:0", isQuery = false),
        @Column(name = "game_desc", attrName = "gameDesc", label = "游戏说明", queryType = QueryType.LIKE),
        @Column(name = "game_category", attrName = "gameCategory", label = "对应游戏类别名称", queryType = QueryType.LIKE),
        @Column(name = "game_category_id", attrName = "gameCategoryId", label = "对应游戏类别id"),
        @Column(name = "hot_game_flag", attrName = "hotGameFlag", label = "是否热门"),
        @Column(name = "biz_status", attrName = "bizStatus", label = "业务状态", isQuery = false),
        @Column(includeEntity = DataEntity.class),
        @Column(includeEntity = BaseEntity.class),
        @Column(name = "deleted", attrName = "deleted", label = "是否删除", isQuery = false),
        @Column(name = "tenant_id", attrName = "tenantId", label = "租户编号", isQuery = false),
}, orderBy = "a.order_no ASC"
)
public class Game extends DataEntity<Game> {

    private static final long serialVersionUID = 1L;
    private String gameCode;        // 游戏代码
    private String gameName;        // 游戏名称
    private Double orderNo;        // 排序号
    private Integer lotteryNumberCount;        // 开奖结果数字个数
    private Integer lotteryNumberMin;        // 开奖结果最小数字
    private Integer lotteryNumberMax;        // 开奖结果最大数字
    private Integer lotteryDelaySecond;        // 开奖延迟秒数
    private String duocaiGameCode;        // 对应多彩游戏代码
    private String lotteryUrl;        // 开奖地址
    private String lotteryLocalInstead;        // 是否本地开奖代替
    private String state;        // 状态,启用:1;禁用:0
    private String gameDesc;        // 游戏说明
    private String gameCategory;        // 对应游戏类别名称
    private String gameCategoryId;        // 对应游戏类别id
    private String hotGameFlag;        // 是否热门
    private String bizStatus;        // 业务状态
    private String deleted;        // 是否删除
    private String tenantId;        // 租户编号
    private String lotteryNumberList;        // 开奖号码列表
    private String lotteryNumberRepeat;        // 开奖号码是否能重复


    public Game() {
        this(null);
    }

    public Game(String id) {
        super(id);
    }

    @Size(min = 0, max = 255, message = "游戏代码长度不能超过 255 个字符")
    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    @Size(min = 0, max = 255, message = "游戏名称长度不能超过 255 个字符")
    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Double getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Double orderNo) {
        this.orderNo = orderNo;
    }

    @Size(min = 0, max = 255, message = "状态,启用长度不能超过 255 个字符")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Size(min = 0, max = 255, message = "游戏说明长度不能超过 255 个字符")
    public String getGameDesc() {
        return gameDesc;
    }

    public void setGameDesc(String gameDesc) {
        this.gameDesc = gameDesc;
    }

    @Size(min = 0, max = 255, message = "对应游戏类别名称长度不能超过 255 个字符")
    public String getGameCategory() {
        return gameCategory;
    }

    public void setGameCategory(String gameCategory) {
        this.gameCategory = gameCategory;
    }

    @Size(min = 0, max = 32, message = "对应游戏类别id长度不能超过 32 个字符")
    public String getGameCategoryId() {
        return gameCategoryId;
    }

    public void setGameCategoryId(String gameCategoryId) {
        this.gameCategoryId = gameCategoryId;
    }

    @Size(min = 0, max = 1, message = "是否热门长度不能超过 1 个字符")
    public String getHotGameFlag() {
        return hotGameFlag;
    }

    public void setHotGameFlag(String hotGameFlag) {
        this.hotGameFlag = hotGameFlag;
    }

    @Size(min = 0, max = 64, message = "业务状态长度不能超过 64 个字符")
    public String getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(String bizStatus) {
        this.bizStatus = bizStatus;
    }

    @Size(min = 0, max = 64, message = "是否删除长度不能超过 64 个字符")
    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Size(min = 0, max = 64, message = "租户编号长度不能超过 64 个字符")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getLotteryNumberCount() {
        return lotteryNumberCount;
    }

    public void setLotteryNumberCount(Integer lotteryNumberCount) {
        this.lotteryNumberCount = lotteryNumberCount;
    }

    public Integer getLotteryNumberMin() {
        return lotteryNumberMin;
    }

    public void setLotteryNumberMin(Integer lotteryNumberMin) {
        this.lotteryNumberMin = lotteryNumberMin;
    }

    public Integer getLotteryNumberMax() {
        return lotteryNumberMax;
    }

    public void setLotteryNumberMax(Integer lotteryNumberMax) {
        this.lotteryNumberMax = lotteryNumberMax;
    }

    public Integer getLotteryDelaySecond() {
        return lotteryDelaySecond;
    }

    public void setLotteryDelaySecond(Integer lotteryDelaySecond) {
        this.lotteryDelaySecond = lotteryDelaySecond;
    }

    public String getLotteryUrl() {
        return lotteryUrl;
    }

    public void setLotteryUrl(String lotteryUrl) {
        this.lotteryUrl = lotteryUrl;
    }

    public String getLotteryLocalInstead() {
        return lotteryLocalInstead;
    }

    public void setLotteryLocalInstead(String lotteryLocalInstead) {
        this.lotteryLocalInstead = lotteryLocalInstead;
    }

    public String getDuocaiGameCode() {
        return duocaiGameCode;
    }

    public void setDuocaiGameCode(String duocaiGameCode) {
        this.duocaiGameCode = duocaiGameCode;
    }

    public String getLotteryNumberList() {
        return lotteryNumberList;
    }

    public void setLotteryNumberList(String lotteryNumberList) {
        this.lotteryNumberList = lotteryNumberList;
    }

    public String getLotteryNumberRepeat() {
        return lotteryNumberRepeat;
    }

    public void setLotteryNumberRepeat(String lotteryNumberRepeat) {
        this.lotteryNumberRepeat = lotteryNumberRepeat;
    }
}