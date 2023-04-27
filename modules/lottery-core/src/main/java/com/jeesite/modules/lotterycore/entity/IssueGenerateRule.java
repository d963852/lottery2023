package com.jeesite.modules.lotterycore.entity;

import javax.validation.constraints.Size;

import com.jeesite.common.entity.BaseEntity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.Table;

import java.util.Date;

/**
 * 期号生成规则Entity
 *
 * @author DUKE
 * @version 2023-04-23
 */
@Table(name = "issue_generate_rule", alias = "a", label = "期号生成规则信息", columns = {
        @Column(name = "id", attrName = "id", label = "主键id", isPK = true),
        @Column(name = "game_id", attrName = "gameId", label = "对应游戏id"),
        @Column(name = "game_code", attrName = "gameCode", label = "对应游戏Code"),
        @Column(name = "issue_count", attrName = "issueCount", label = "期数", isUpdateForce = true),
        @Column(name = "issue_setting_id", attrName = "issueSettingId", label = "对应期号设置id"),
        @Column(name = "order_no", attrName = "orderNo", label = "排序号", isUpdateForce = true),
        @Column(name = "start_time", attrName = "startTime", label = "开始时间"),
        @Column(name = "time_interval", attrName = "timeInterval", label = "时间间隔", isUpdateForce = true),
        @Column(name = "date_format", attrName = "dateFormat", label = "日期部分格式"),
        @Column(name = "separator", attrName = "separator", label = "分隔符"),
        @Column(name = "issue_format", attrName = "issueFormat", label = "期号部分格式"),
        @Column(name = "next_day", attrName = "nextDay", label = "是否跨天"),
        @Column(name = "issue_begin_number", attrName = "issueBeginNumber", label = "起始期号号码"),
        @Column(name = "issue_begin_date", attrName = "issueBeginDate", label = "起始期号日期"),
        @Column(name = "bet_cut_off_time", attrName = "betCutOffTime", label = "投注截止时长（秒）"),
        @Column(name="automatic_lottery", attrName="automaticLottery", label="自动开奖"),
        @Column(name="automatic_settlement", attrName="automaticSettlement", label="自动结算"),
        @Column(name = "biz_status", attrName = "bizStatus", label = "业务状态", isQuery = false),
        @Column(includeEntity = DataEntity.class),
        @Column(includeEntity = BaseEntity.class),
        @Column(name = "deleted", attrName = "deleted", label = "是否删除", isQuery = false),
        @Column(name = "tenant_id", attrName = "tenantId", label = "租户编号", isQuery = false),
},
        // 联合查询出外键编码的名称数据（attrName="this"，指定this代表，当前实体）
        joinTable = {
                @JoinTable(type = JoinTable.Type.LEFT_JOIN, entity = Game.class, attrName = "this", alias = "b",
                        on = "b.id = a.game_id",
                        columns = {
                                @Column(name = "game_name", attrName = "gameName", label = "游戏名称"),
                        }),
        },
        orderBy = "a.update_date DESC"
)
public class IssueGenerateRule extends DataEntity<IssueGenerateRule> {

    private static final long serialVersionUID = 1L;
    private String gameId;        // 对应游戏id
    private String gameCode;        // 对应游戏code
    private Long issueCount;        // 期数
    private String issueSettingId;        // 对应期号设置id
    private Double orderNo;        // 排序号
    private String startTime;        // 开始时间
    private Double timeInterval;        // 时间间隔
    private String dateFormat;        // 日期部分格式
    private String separator;        // 分隔符
    private String issueFormat;        // 期号部分格式
    private String nextDay;        // 是否跨天
    private String bizStatus;        // 业务状态
    private String deleted;        // 是否删除
    private String tenantId;        // 租户编号
    private Integer betCutOffTime;        // 投注截止时长
    private String automaticLottery;		// 自动开奖
    private String automaticSettlement;		// 自动结算
    private Integer issueBeginNumber;		// 起始期号
    private Date issueBeginDate;		// 起始期号日期

    private String gameName;        // 游戏名称


    public IssueGenerateRule() {
        this(null);
    }

    public IssueGenerateRule(String id) {
        super(id);
    }

    @Size(min = 0, max = 64, message = "对应游戏id长度不能超过 64 个字符")
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @Size(min = 0, max = 255, message = "对应游戏code长度不能超过 255 个字符")
    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public Long getIssueCount() {
        return issueCount;
    }

    public void setIssueCount(Long issueCount) {
        this.issueCount = issueCount;
    }

    @Size(min = 0, max = 32, message = "对应期号设置id长度不能超过 32 个字符")
    public String getIssueSettingId() {
        return issueSettingId;
    }

    public void setIssueSettingId(String issueSettingId) {
        this.issueSettingId = issueSettingId;
    }

    public Double getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Double orderNo) {
        this.orderNo = orderNo;
    }

    @Size(min = 0, max = 255, message = "开始时间长度不能超过 255 个字符")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Double getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Double timeInterval) {
        this.timeInterval = timeInterval;
    }

    @Size(min = 0, max = 255, message = "日期部分格式长度不能超过 255 个字符")
    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Size(min = 0, max = 255, message = "期号部分格式长度不能超过 255 个字符")
    public String getIssueFormat() {
        return issueFormat;
    }

    public void setIssueFormat(String issueFormat) {
        this.issueFormat = issueFormat;
    }

    @Size(min = 0, max = 1, message = "是否跨天长度不能超过 1 个字符")
    public String getNextDay() {
        return nextDay;
    }

    public void setNextDay(String nextDay) {
        this.nextDay = nextDay;
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

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getBetCutOffTime() {
        return betCutOffTime;
    }

    public void setBetCutOffTime(Integer betCutOffTime) {
        this.betCutOffTime = betCutOffTime;
    }

    @Size(min=0, max=1, message="自动开奖长度不能超过 1 个字符")
    public String getAutomaticLottery() {
        return automaticLottery;
    }

    public void setAutomaticLottery(String automaticLottery) {
        this.automaticLottery = automaticLottery;
    }

    @Size(min=0, max=1, message="自动结算长度不能超过 1 个字符")
    public String getAutomaticSettlement() {
        return automaticSettlement;
    }

    public void setAutomaticSettlement(String automaticSettlement) {
        this.automaticSettlement = automaticSettlement;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public Integer getIssueBeginNumber() {
        return issueBeginNumber;
    }

    public void setIssueBeginNumber(Integer issueBeginNumber) {
        this.issueBeginNumber = issueBeginNumber;
    }

    public Date getIssueBeginDate() {
        return issueBeginDate;
    }

    public void setIssueBeginDate(Date issueBeginDate) {
        this.issueBeginDate = issueBeginDate;
    }
}