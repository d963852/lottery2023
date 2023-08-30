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
 * 投注订单Entity
 * @author thinkgem
 * @version 2023-08-26
 */
@Table(name="bet_order", alias="a", label="投注订单信息", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="order_no", attrName="orderNo", label="订单号"),
		@Column(name="ext_no", attrName="extNo", label="外部关联号"),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="user_account", attrName="userAccount", label="用户帐号", queryType=QueryType.LIKE),
		@Column(name="user_ip", attrName="userIp", label="投注IP", queryType=QueryType.LIKE),
		@Column(name="user_way", attrName="userWay", label="投注渠道", queryType=QueryType.LIKE),
		@Column(name="user_equipment", attrName="userEquipment", label="投注设备", queryType=QueryType.LIKE),
		@Column(name="base_price", attrName="basePrice", label="投注单价", isUpdateForce=true),
		@Column(name="bet_time", attrName="betTime", label="投注时间", isUpdateForce=true),
		@Column(name="game_code", attrName="gameCode", label="游戏代码"),
		@Column(name="play_method_group_id", attrName="playMethodGroupId", label="玩法组id"),
		@Column(name="play_method_group", attrName="playMethodGroup", label="玩法组", queryType=QueryType.LIKE),
		@Column(name="play_method_id", attrName="playMethodId", label="玩法id"),
		@Column(name="play_method", attrName="playMethod", label="玩法", queryType=QueryType.LIKE),
		@Column(name="issue_id", attrName="issueId", label="期号id"),
		@Column(name="issue", attrName="issue", label="期号", queryType=QueryType.LIKE),
		@Column(name="bet_number", attrName="betNumber", label="投注号码"),
		@Column(name="ext_bet_number", attrName="extBetNumber", label="附加号码"),
		@Column(name="bet_count", attrName="betCount", label="注数", isUpdateForce=true),
		@Column(name="bet_multiple", attrName="betMultiple", label="倍数", isUpdateForce=true),
		@Column(name="total_bet_count", attrName="totalBetCount", label="总注数", isUpdateForce=true),
		@Column(name="bet_unit", attrName="betUnit", label="货币单位", isUpdateForce=true),
		@Column(name="bet_amount", attrName="betAmount", label="投注金额", isUpdateForce=true),
		@Column(name="not_position", attrName="notPosition", label="不定位"),
		@Column(name="rebate", attrName="rebate", label="返点", isUpdateForce=true),
		@Column(name="rebate_amount", attrName="rebateAmount", label="返点金额", isUpdateForce=true),
		@Column(name="bonus_amount", attrName="bonusAmount", label="奖金金额", isUpdateForce=true),
		@Column(name="syndicate", attrName="syndicate", label="合买"),
		@Column(name="frisbee", attrName="frisbee", label="飞盘"),
		@Column(name="chasing", attrName="chasing", label="追号"),
		@Column(name="chasing_issue", attrName="chasingIssue", label="追号期数", isUpdateForce=true),
		@Column(name="chasing_issue_remain", attrName="chasingIssueRemain", label="剩余追号期数", isUpdateForce=true),
		@Column(name="chasing_win_stop", attrName="chasingWinStop", label="中奖停止追号"),
		@Column(name="lottery_number", attrName="lotteryNumber", label="开奖号码", queryType=QueryType.LIKE),
		@Column(name="lottery_time", attrName="lotteryTime", label="开奖时间", isUpdateForce=true),
		@Column(name="lottery_source", attrName="lotterySource", label="开奖来源", queryType=QueryType.LIKE),
		@Column(name="win_count", attrName="winCount", label="中奖注数", isUpdateForce=true),
		@Column(name="win_amount", attrName="winAmount", label="中奖金额", isUpdateForce=true),
		@Column(name="total_rebate_amount", attrName="totalRebateAmount", label="中奖金额", isUpdateForce=true),
		@Column(name="profit_and_loss", attrName="profitAndLoss", label="盈亏", isUpdateForce=true),
		@Column(name="cancel_time", attrName="cancelTime", label="撤单时间", isUpdateForce=true),
		@Column(name="version", attrName="version", label="乐观锁版本号", isUpdateForce=true),
		@Column(name="biz_status", attrName="bizStatus", label="业务状态"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
		@Column(name="deleted", attrName="deleted", label="是否删除"),
		@Column(name="tenant_id", attrName="tenantId", label="租户编号"),
	}, orderBy="a.update_date DESC"
)
public class BetOrder extends DataEntity<BetOrder> {
	
	private static final long serialVersionUID = 1L;
	private String orderNo;		// 订单号
	private String extNo;		// 外部关联号
	private String userId;		// 用户ID
	private String userAccount;		// 用户帐号
	private String userIp;		// 投注IP
	private String userWay;		// 投注渠道
	private String userEquipment;		// 投注设备
	private Double basePrice;		// 投注单价
	private Date betTime;		// 投注时间
	private String gameCode;		// 游戏代码
	private String playMethodGroupId;		// 玩法组id
	private String playMethodGroup;		// 玩法组
	private String playMethodId;		// 玩法id
	private String playMethod;		// 玩法
	private String issueId;		// 期号id
	private String issue;		// 期号
	private String betNumber;		// 投注号码
	private String extBetNumber;		// 附加号码
	private Long betCount;		// 注数
	private Long betMultiple;		// 倍数
	private Long totalBetCount;		// 总注数
	private Double betUnit;		// 货币单位
	private Double betAmount;		// 总投注金额
	private String notPosition;		// 不定位
	private Double rebate;		// 返点
	private Double rebateAmount;		// 返点金额
	private Double bonusAmount;		// 奖金比例
	private String syndicate;		// 合买
	private String frisbee;		// 飞盘
	private String chasing;		// 追号
	private Long chasingIssue;		// 追号期数
	private Long chasingIssueRemain;		// 剩余追号期数
	private String chasingWinStop;		// 中奖停止追号
	private String lotteryNumber;		// 开奖号码
	private Date lotteryTime;		// 开奖时间
	private String lotterySource;		// 开奖地址
	private Long winCount;		// 中奖注数
	private Double totalRebateAmount;		// 总返点金额
	private Double winAmount;		// 中奖金额
	private Double profitAndLoss;		// 盈亏
	private Date cancelTime;		// 撤单时间
	private Long version;		// 乐观锁版本号
	private String bizStatus;		// 业务状态
	private String deleted;		// 是否删除
	private String tenantId;		// 租户编号

	public BetOrder() {
		this(null);
	}
	
	public BetOrder(String id){
		super(id);
	}
	
	@Size(min=0, max=64, message="订单号长度不能超过 64 个字符")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Size(min=0, max=64, message="外部关联号长度不能超过 64 个字符")
	public String getExtNo() {
		return extNo;
	}

	public void setExtNo(String extNo) {
		this.extNo = extNo;
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
	
	@Size(min=0, max=64, message="投注IP长度不能超过 64 个字符")
	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	
	@Size(min=0, max=64, message="投注渠道长度不能超过 64 个字符")
	public String getUserWay() {
		return userWay;
	}

	public void setUserWay(String userWay) {
		this.userWay = userWay;
	}
	
	@Size(min=0, max=64, message="投注设备长度不能超过 64 个字符")
	public String getUserEquipment() {
		return userEquipment;
	}

	public void setUserEquipment(String userEquipment) {
		this.userEquipment = userEquipment;
	}
	
	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBetTime() {
		return betTime;
	}

	public void setBetTime(Date betTime) {
		this.betTime = betTime;
	}
	
	@Size(min=0, max=64, message="游戏代码长度不能超过 64 个字符")
	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	
	@Size(min=0, max=64, message="玩法组id长度不能超过 64 个字符")
	public String getPlayMethodGroupId() {
		return playMethodGroupId;
	}

	public void setPlayMethodGroupId(String playMethodGroupId) {
		this.playMethodGroupId = playMethodGroupId;
	}
	
	@Size(min=0, max=64, message="玩法组长度不能超过 64 个字符")
	public String getPlayMethodGroup() {
		return playMethodGroup;
	}

	public void setPlayMethodGroup(String playMethodGroup) {
		this.playMethodGroup = playMethodGroup;
	}
	
	@Size(min=0, max=64, message="玩法id长度不能超过 64 个字符")
	public String getPlayMethodId() {
		return playMethodId;
	}

	public void setPlayMethodId(String playMethodId) {
		this.playMethodId = playMethodId;
	}
	
	@Size(min=0, max=64, message="玩法长度不能超过 64 个字符")
	public String getPlayMethod() {
		return playMethod;
	}

	public void setPlayMethod(String playMethod) {
		this.playMethod = playMethod;
	}
	
	@Size(min=0, max=64, message="期号id长度不能超过 64 个字符")
	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	
	@Size(min=0, max=64, message="期号长度不能超过 64 个字符")
	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}
	
	@Size(min=0, max=640, message="投注号码长度不能超过 640 个字符")
	public String getBetNumber() {
		return betNumber;
	}

	public void setBetNumber(String betNumber) {
		this.betNumber = betNumber;
	}
	
	@Size(min=0, max=64, message="附加号码长度不能超过 64 个字符")
	public String getExtBetNumber() {
		return extBetNumber;
	}

	public void setExtBetNumber(String extBetNumber) {
		this.extBetNumber = extBetNumber;
	}
	
	public Long getBetCount() {
		return betCount;
	}

	public void setBetCount(Long betCount) {
		this.betCount = betCount;
	}
	
	public Long getBetMultiple() {
		return betMultiple;
	}

	public void setBetMultiple(Long betMultiple) {
		this.betMultiple = betMultiple;
	}
	
	public Long getTotalBetCount() {
		return totalBetCount;
	}

	public void setTotalBetCount(Long totalBetCount) {
		this.totalBetCount = totalBetCount;
	}
	
	public Double getBetUnit() {
		return betUnit;
	}

	public void setBetUnit(Double betUnit) {
		this.betUnit = betUnit;
	}
	
	public Double getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(Double betAmount) {
		this.betAmount = betAmount;
	}
	
	@Size(min=0, max=64, message="不定位长度不能超过 64 个字符")
	public String getNotPosition() {
		return notPosition;
	}

	public void setNotPosition(String notPosition) {
		this.notPosition = notPosition;
	}
	
	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}
	
	public Double getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(Double rebateAmount) {
		this.rebateAmount = rebateAmount;
	}
	
	public Double getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(Double bonusAmount) {
		this.bonusAmount = bonusAmount;
	}
	
	@Size(min=0, max=64, message="合买长度不能超过 64 个字符")
	public String getSyndicate() {
		return syndicate;
	}

	public void setSyndicate(String syndicate) {
		this.syndicate = syndicate;
	}
	
	@Size(min=0, max=640, message="飞盘长度不能超过 640 个字符")
	public String getFrisbee() {
		return frisbee;
	}

	public void setFrisbee(String frisbee) {
		this.frisbee = frisbee;
	}
	
	@Size(min=0, max=64, message="追号长度不能超过 64 个字符")
	public String getChasing() {
		return chasing;
	}

	public void setChasing(String chasing) {
		this.chasing = chasing;
	}
	
	public Long getChasingIssue() {
		return chasingIssue;
	}

	public void setChasingIssue(Long chasingIssue) {
		this.chasingIssue = chasingIssue;
	}
	
	public Long getChasingIssueRemain() {
		return chasingIssueRemain;
	}

	public void setChasingIssueRemain(Long chasingIssueRemain) {
		this.chasingIssueRemain = chasingIssueRemain;
	}
	
	@Size(min=0, max=64, message="中奖停止追号长度不能超过 64 个字符")
	public String getChasingWinStop() {
		return chasingWinStop;
	}

	public void setChasingWinStop(String chasingWinStop) {
		this.chasingWinStop = chasingWinStop;
	}
	
	@Size(min=0, max=640, message="开奖号码长度不能超过 640 个字符")
	public String getLotteryNumber() {
		return lotteryNumber;
	}

	public void setLotteryNumber(String lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLotteryTime() {
		return lotteryTime;
	}

	public void setLotteryTime(Date lotteryTime) {
		this.lotteryTime = lotteryTime;
	}
	
	@Size(min=0, max=640, message="开奖地址长度不能超过 640 个字符")
	public String getLotterySource() {
		return lotterySource;
	}

	public void setLotterySource(String lotterySource) {
		this.lotterySource = lotterySource;
	}
	
	public Long getWinCount() {
		return winCount;
	}

	public void setWinCount(Long winCount) {
		this.winCount = winCount;
	}
	
	public Double getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(Double winAmount) {
		this.winAmount = winAmount;
	}
	
	public Double getProfitAndLoss() {
		return profitAndLoss;
	}

	public void setProfitAndLoss(Double profitAndLoss) {
		this.profitAndLoss = profitAndLoss;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
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
	
	public Double getBasePrice_gte() {
		return sqlMap.getWhere().getValue("base_price", QueryType.GTE);
	}

	public void setBasePrice_gte(Double basePrice) {
		sqlMap.getWhere().and("base_price", QueryType.GTE, basePrice);
	}
	
	public Double getBasePrice_lte() {
		return sqlMap.getWhere().getValue("base_price", QueryType.LTE);
	}

	public void setBasePrice_lte(Double basePrice) {
		sqlMap.getWhere().and("base_price", QueryType.LTE, basePrice);
	}
	
	public Date getBetTime_gte() {
		return sqlMap.getWhere().getValue("bet_time", QueryType.GTE);
	}

	public void setBetTime_gte(Date betTime) {
		sqlMap.getWhere().and("bet_time", QueryType.GTE, betTime);
	}
	
	public Date getBetTime_lte() {
		return sqlMap.getWhere().getValue("bet_time", QueryType.LTE);
	}

	public void setBetTime_lte(Date betTime) {
		sqlMap.getWhere().and("bet_time", QueryType.LTE, betTime);
	}
	
	public Long getBetCount_gte() {
		return sqlMap.getWhere().getValue("bet_count", QueryType.GTE);
	}

	public void setBetCount_gte(Long betCount) {
		sqlMap.getWhere().and("bet_count", QueryType.GTE, betCount);
	}
	
	public Long getBetCount_lte() {
		return sqlMap.getWhere().getValue("bet_count", QueryType.LTE);
	}

	public void setBetCount_lte(Long betCount) {
		sqlMap.getWhere().and("bet_count", QueryType.LTE, betCount);
	}
	
	public Long getTotalBetCount_gte() {
		return sqlMap.getWhere().getValue("total_bet_count", QueryType.GTE);
	}

	public void setTotalBetCount_gte(Long totalBetCount) {
		sqlMap.getWhere().and("total_bet_count", QueryType.GTE, totalBetCount);
	}
	
	public Long getTotalBetCount_lte() {
		return sqlMap.getWhere().getValue("total_bet_count", QueryType.LTE);
	}

	public void setTotalBetCount_lte(Long totalBetCount) {
		sqlMap.getWhere().and("total_bet_count", QueryType.LTE, totalBetCount);
	}
	
	public Double getTotalBetAmount_gte() {
		return sqlMap.getWhere().getValue("total_bet_amount", QueryType.GTE);
	}

	public void setTotalBetAmount_gte(Double totalBetAmount) {
		sqlMap.getWhere().and("total_bet_amount", QueryType.GTE, totalBetAmount);
	}
	
	public Double getTotalBetAmount_lte() {
		return sqlMap.getWhere().getValue("total_bet_amount", QueryType.LTE);
	}

	public void setTotalBetAmount_lte(Double totalBetAmount) {
		sqlMap.getWhere().and("total_bet_amount", QueryType.LTE, totalBetAmount);
	}
	
	public Double getRebate_gte() {
		return sqlMap.getWhere().getValue("rebate", QueryType.GTE);
	}

	public void setRebate_gte(Double rebate) {
		sqlMap.getWhere().and("rebate", QueryType.GTE, rebate);
	}
	
	public Double getRebate_lte() {
		return sqlMap.getWhere().getValue("rebate", QueryType.LTE);
	}

	public void setRebate_lte(Double rebate) {
		sqlMap.getWhere().and("rebate", QueryType.LTE, rebate);
	}
	
	public Double getRebateAmount_gte() {
		return sqlMap.getWhere().getValue("rebate_amount", QueryType.GTE);
	}

	public void setRebateAmount_gte(Double rebateAmount) {
		sqlMap.getWhere().and("rebate_amount", QueryType.GTE, rebateAmount);
	}
	
	public Double getRebateAmount_lte() {
		return sqlMap.getWhere().getValue("rebate_amount", QueryType.LTE);
	}

	public void setRebateAmount_lte(Double rebateAmount) {
		sqlMap.getWhere().and("rebate_amount", QueryType.LTE, rebateAmount);
	}
	
	public Double getBonus_gte() {
		return sqlMap.getWhere().getValue("bonus", QueryType.GTE);
	}

	public void setBonus_gte(Double bonus) {
		sqlMap.getWhere().and("bonus", QueryType.GTE, bonus);
	}
	
	public Double getBonus_lte() {
		return sqlMap.getWhere().getValue("bonus", QueryType.LTE);
	}

	public void setBonus_lte(Double bonus) {
		sqlMap.getWhere().and("bonus", QueryType.LTE, bonus);
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
	
	public Long getWinCount_gte() {
		return sqlMap.getWhere().getValue("win_count", QueryType.GTE);
	}

	public void setWinCount_gte(Long winCount) {
		sqlMap.getWhere().and("win_count", QueryType.GTE, winCount);
	}
	
	public Long getWinCount_lte() {
		return sqlMap.getWhere().getValue("win_count", QueryType.LTE);
	}

	public void setWinCount_lte(Long winCount) {
		sqlMap.getWhere().and("win_count", QueryType.LTE, winCount);
	}
	
	public Double getWinAmount_gte() {
		return sqlMap.getWhere().getValue("win_amount", QueryType.GTE);
	}

	public void setWinAmount_gte(Double winAmount) {
		sqlMap.getWhere().and("win_amount", QueryType.GTE, winAmount);
	}
	
	public Double getWinAmount_lte() {
		return sqlMap.getWhere().getValue("win_amount", QueryType.LTE);
	}

	public void setWinAmount_lte(Double winAmount) {
		sqlMap.getWhere().and("win_amount", QueryType.LTE, winAmount);
	}
	
	public Double getProfitAndLoss_gte() {
		return sqlMap.getWhere().getValue("profit_and_loss", QueryType.GTE);
	}

	public void setProfitAndLoss_gte(Double profitAndLoss) {
		sqlMap.getWhere().and("profit_and_loss", QueryType.GTE, profitAndLoss);
	}
	
	public Double getProfitAndLoss_lte() {
		return sqlMap.getWhere().getValue("profit_and_loss", QueryType.LTE);
	}

	public void setProfitAndLoss_lte(Double profitAndLoss) {
		sqlMap.getWhere().and("profit_and_loss", QueryType.LTE, profitAndLoss);
	}
	
	public Date getCancelTime_gte() {
		return sqlMap.getWhere().getValue("cancel_time", QueryType.GTE);
	}

	public void setCancelTime_gte(Date cancelTime) {
		sqlMap.getWhere().and("cancel_time", QueryType.GTE, cancelTime);
	}
	
	public Date getCancelTime_lte() {
		return sqlMap.getWhere().getValue("cancel_time", QueryType.LTE);
	}

	public void setCancelTime_lte(Date cancelTime) {
		sqlMap.getWhere().and("cancel_time", QueryType.LTE, cancelTime);
	}

	public Double getTotalRebateAmount() {
		return totalRebateAmount;
	}

	public void setTotalRebateAmount(Double totalRebateAmount) {
		this.totalRebateAmount = totalRebateAmount;
	}
}