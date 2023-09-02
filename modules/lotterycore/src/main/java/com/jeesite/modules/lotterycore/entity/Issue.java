package com.jeesite.modules.lotterycore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 彩票期号Entity
 *
 * @author DUKE
 * @version 2023-04-23
 */
@Table(name = "issue", alias = "a", label = "彩票期号信息", columns = {
        @Column(name = "id", attrName = "id", label = "ID", isPK = true),
        @Column(name = "game_code", attrName = "gameCode", label = "所属游戏代码"),
        @Column(name = "issue_num", attrName = "issueNum", label = "期数", queryType = QueryType.LIKE),
        @Column(name = "lottery_date", attrName = "lotteryDate", label = "开奖日期", isUpdateForce = true),
        @Column(name = "lottery_num", attrName = "lotteryNum", label = "开奖号码"),
        @Column(name = "lottery_num_inner", attrName = "lotteryNumInner", label = "开奖号码（内部用）", isUpdateForce = true),
        @Column(name = "lottery_source", attrName = "lotterySource", label = "开奖来源"),
        @Column(name = "lottery_time", attrName = "lotteryTime", label = "开奖时间", isUpdateForce = true),
        @Column(name = "start_time", attrName = "startTime", label = "开始时间", isUpdateForce = true),
        @Column(name = "end_time", attrName = "endTime", label = "结束时间", isUpdateForce = true),
        @Column(name = "state", attrName = "state", label = "期号状态"),
        @Column(name = "sync_time", attrName = "syncTime", label = "实际开奖时间", isUpdateForce = true),
        @Column(name = "plan_sync_time", attrName = "planSyncTime", label = "计划开奖时间", isUpdateForce = true),
        @Column(name = "version", attrName = "version", label = "乐观锁版本号", isQuery = false, isUpdateForce = true),
        @Column(name = "settlement_time", attrName = "settlementTime", label = "结算时间", isUpdateForce = true),
        @Column(name = "automatic_lottery", attrName = "automaticLottery", label = "自动开奖"),
        @Column(name = "automatic_settlement", attrName = "automaticSettlement", label = "自动结算"),
        @Column(name = "issue_num_inner", attrName = "issueNumInner", label = "期数", comment = "期数(内部用)", isUpdateForce = true),
        @Column(name = "biz_status", attrName = "bizStatus", label = "业务状态"),
        @Column(includeEntity = DataEntity.class),
//        @Column(includeEntity = BaseEntity.class),
        @Column(name = "deleted", attrName = "deleted", label = "是否删除", isQuery = false),
        @Column(name = "tenant_id", attrName = "tenantId", label = "租户编号", isQuery = false),
}, joinTable = {
        @JoinTable(type = JoinTable.Type.LEFT_JOIN, entity = Game.class, attrName = "this", alias = "b",
                on = "b.game_code = a.game_code",
                columns = {
                        @Column(name = "game_name", attrName = "gameName", label = "名称"),
                }),
}, orderBy = "a.issue_num DESC"
)
public class Issue extends DataEntity<Issue> {

    private static final long serialVersionUID = 1L;
    private String gameCode;        // 所属游戏代码
    private String issueNum;        // 期数
    private Date lotteryDate;        // 开奖日期
    private String lotteryNum;        // 开奖号码
    private String lotteryNumInner;        // 开奖号码（内部用）
    private String lotterySource;        // 开奖来源
    private Date lotteryTime;        // 开奖时间
    private Date startTime;        // 开始时间
    private Date endTime;        // 结束时间
    private String state;        // 期号状态
    private Date syncTime;        // 计划开奖时间
    private Date planSyncTime;        // 实际开奖时间
    private Long version;        // 乐观锁版本号
    private Date settlementTime;        // 结算时间
    private String automaticLottery;        // 自动开奖
    private String automaticSettlement;        // 自动结算
    private Long issueNumInner;        // 期数(内部用)
    private String bizStatus;        // 业务状态
    private String deleted;        // 是否删除
    private String tenantId;        // 租户编号

    private String gameName;        // 游戏名称

    public Issue() {
        this(null);
    }

    public Issue(String id) {
        super(id);
    }

    @Size(min = 0, max = 255, message = "所属游戏代码长度不能超过 255 个字符")
    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    @Size(min = 0, max = 64, message = "期数长度不能超过 64 个字符")
    public String getIssueNum() {
        return issueNum;
    }

    public void setIssueNum(String issueNum) {
        this.issueNum = issueNum;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getLotteryDate() {
        return lotteryDate;
    }

    public void setLotteryDate(Date lotteryDate) {
        this.lotteryDate = lotteryDate;
    }

    @Size(min = 0, max = 255, message = "开奖号码长度不能超过 255 个字符")
    public String getLotteryNum() {
        return lotteryNum;
    }

    public void setLotteryNum(String lotteryNum) {
        this.lotteryNum = lotteryNum;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getLotteryTime() {
        return lotteryTime;
    }

    public void setLotteryTime(Date lotteryTime) {
        this.lotteryTime = lotteryTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Size(min = 0, max = 255, message = "期号状态长度不能超过 255 个字符")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(Date settlementTime) {
        this.settlementTime = settlementTime;
    }

    @Size(min = 0, max = 1, message = "自动开奖长度不能超过 1 个字符")
    public String getAutomaticLottery() {
        return automaticLottery;
    }

    public void setAutomaticLottery(String automaticLottery) {
        this.automaticLottery = automaticLottery;
    }

    @Size(min = 0, max = 1, message = "自动结算长度不能超过 1 个字符")
    public String getAutomaticSettlement() {
        return automaticSettlement;
    }

    public void setAutomaticSettlement(String automaticSettlement) {
        this.automaticSettlement = automaticSettlement;
    }

    public Long getIssueNumInner() {
        return issueNumInner;
    }

    public void setIssueNumInner(Long issueNumInner) {
        this.issueNumInner = issueNumInner;
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

    public Date getLotteryDate_gte() {
        return sqlMap.getWhere().getValue("lottery_date", QueryType.GTE);
    }

    public void setLotteryDate_gte(Date lotteryDate) {
        sqlMap.getWhere().and("lottery_date", QueryType.GTE, lotteryDate);
    }

    public Date getLotteryDate_lte() {
        return sqlMap.getWhere().getValue("lottery_date", QueryType.LTE);
    }

    public void setLotteryDate_lte(Date lotteryDate) {
        sqlMap.getWhere().and("lottery_date", QueryType.LTE, lotteryDate);
    }

    public Date getLotteryTime_gte() {
        return sqlMap.getWhere().getValue("lottery_time", QueryType.GTE);
    }

    public void setLotteryTime_gte(Date lotteryTime) {
        sqlMap.getWhere().and("lottery_time", QueryType.GTE, lotteryTime);
    }

    public Date getLotteryTime_lte() {
        return sqlMap.getWhere().getValue("lottery_time", QueryType.LTE);
    }

    public void setLotteryTime_lte(Date lotteryTime) {
        sqlMap.getWhere().and("lottery_time", QueryType.LTE, lotteryTime);
    }

    public Date getStartTime_gte() {
        return sqlMap.getWhere().getValue("start_time", QueryType.GTE);
    }

    public void setStartTime_gte(Date startTime) {
        sqlMap.getWhere().and("start_time", QueryType.GTE, startTime);
    }

    public Date getStartTime_lte() {
        return sqlMap.getWhere().getValue("start_time", QueryType.LTE);
    }

    public void setStartTime_lte(Date startTime) {
        sqlMap.getWhere().and("start_time", QueryType.LTE, startTime);
    }

    public Date getEndTime_gte() {
        return sqlMap.getWhere().getValue("end_time", QueryType.GTE);
    }

    public void setEndTime_gte(Date endTime) {
        sqlMap.getWhere().and("end_time", QueryType.GTE, endTime);
    }

    public Date getEndTime_lte() {
        return sqlMap.getWhere().getValue("end_time", QueryType.LTE);
    }

    public void setEndTime_lte(Date endTime) {
        sqlMap.getWhere().and("end_time", QueryType.LTE, endTime);
    }

    public Date getSyncTime_gte() {
        return sqlMap.getWhere().getValue("sync_time", QueryType.GTE);
    }

    public void setSyncTime_gte(Date syncTime) {
        sqlMap.getWhere().and("sync_time", QueryType.GTE, syncTime);
    }

    public Date getSyncTime_lte() {
        return sqlMap.getWhere().getValue("sync_time", QueryType.LTE);
    }

    public void setSyncTime_lte(Date syncTime) {
        sqlMap.getWhere().and("sync_time", QueryType.LTE, syncTime);
    }

    public Date getSettlementTime_gte() {
        return sqlMap.getWhere().getValue("settlement_time", QueryType.GTE);
    }

    public void setSettlementTime_gte(Date settlementTime) {
        sqlMap.getWhere().and("settlement_time", QueryType.GTE, settlementTime);
    }

    public Date getSettlementTime_lte() {
        return sqlMap.getWhere().getValue("settlement_time", QueryType.LTE);
    }

    public void setSettlementTime_lte(Date settlementTime) {
        sqlMap.getWhere().and("settlement_time", QueryType.LTE, settlementTime);
    }

    @Size(min = 0, max = 500, message = "开奖来源不能超过500个字符")
    public String getLotterySource() {
        return lotterySource;
    }

    public void setLotterySource(String lotterySource) {
        this.lotterySource = lotterySource;
    }

    public Date getPlanSyncTime() {
        return planSyncTime;
    }

    public void setPlanSyncTime(Date planSyncTime) {
        this.planSyncTime = planSyncTime;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getLotteryNum_isNull() {
        return this.getLotteryNum();
    }

    public void setLotteryNum_isNull() {
            sqlMap.getWhere().andBracket("lottery_num", QueryType.IS_NULL, null, 2)
                    .or("lottery_num", QueryType.EQ_FORCE, "", 3).endBracket();
            this.setLotteryNum(null);
    }

    public String getLotteryNumInner() {
        return lotteryNumInner;
    }

    public void setLotteryNumInner(String lotteryNumInner) {
        this.lotteryNumInner = lotteryNumInner;
    }

    //设置状态为已开奖的三种
    public void setStateDrawed(){
        sqlMap.getWhere().andBracket("state", QueryType.EQ, "2",1)
                .or("state", QueryType.EQ, "3", 2)
                .or("state", QueryType.EQ, "4", 3).endBracket();
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id='" + id + '\'' +
                ", gameCode='" + gameCode + '\'' +
                ", issueNum='" + issueNum + '\'' +
                ", lotteryDate=" + lotteryDate +
                ", lotteryNum='" + lotteryNum + '\'' +
                ", lotterySource='" + lotterySource + '\'' +
                ", lotteryTime=" + lotteryTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", state='" + state + '\'' +
                ", syncTime=" + syncTime +
                ", planSyncTime=" + planSyncTime +
                ", version=" + version +
                ", settlementTime=" + settlementTime +
                ", automaticLottery='" + automaticLottery + '\'' +
                ", automaticSettlement='" + automaticSettlement + '\'' +
                ", issueNumInner=" + issueNumInner +
                ", bizStatus='" + bizStatus + '\'' +
                ", deleted='" + deleted + '\'' +
                ", tenantId='" + tenantId + '\'' +
                '}';
    }
}