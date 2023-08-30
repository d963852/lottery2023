package com.jeesite.modules.lotterycore.service;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.entity.Page;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.lotterycore.common.exception.BizError;
import com.jeesite.modules.lotterycore.common.exception.BizException;
import com.jeesite.modules.lotterycore.common.utils.WinRulesUtils;
import com.jeesite.modules.lotterycore.constants.Constant;
import com.jeesite.modules.lotterycore.dao.IssueDao;
import com.jeesite.modules.lotterycore.entity.*;
import com.jeesite.modules.lotterycore.param.SyncLotteryNumMsg;
import com.jeesite.modules.sys.entity.Member;
import com.jeesite.modules.sys.service.MemberService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 彩票期号Service
 *
 * @author DUKE
 * @version 2023-04-23
 */
@Service
public class IssueService extends CrudService<IssueDao, Issue> {

    @Autowired
    private IssueGenerateRuleService issueGenerateRuleService;
    @Autowired
    private GameService gameService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private BetOrderService betOrderService;
    @Autowired
    private PlayMethodService playMethodService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AccountChangeLogService accountChangeLogService;

    /**
     * 获取单条数据
     *
     * @param issue
     * @return
     */
    @Override
    public Issue get(Issue issue) {
        return super.get(issue);
    }

    /**
     * 查询分页数据
     *
     * @param issue 查询条件
     * @param issue page 分页对象
     * @return
     */
    @Override
    public Page<Issue> findPage(Issue issue) {
        return super.findPage(issue);
    }

    /**
     * 查询列表数据
     *
     * @param issue
     * @return
     */
    @Override
    public List<Issue> findList(Issue issue) {
        return super.findList(issue);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param issue
     */
    @Override
    @Transactional
    public void save(Issue issue) {
        super.save(issue);
        // 保存上传图片
        FileUploadUtils.saveFileUpload(issue, issue.getId(), "issue_image");
    }

    /**
     * 更新状态
     *
     * @param issue
     */
    @Override
    @Transactional
    public void updateStatus(Issue issue) {
        super.updateStatus(issue);
    }

    /**
     * 删除数据
     *
     * @param issue
     */
    @Override
    @Transactional
    public void delete(Issue issue) {
        super.delete(issue);
    }

    @Transactional
    /**
     * 根据期号生成规则生成游戏期号
     */
    public void makeIssue(Date beginDate, Date endDate, String gameId) throws BizException {
        Game game = gameService.get(gameId);
        if (null == game) {
            throw new BizException(BizError.游戏不存在);
        }

        beginDate = DateUtil.beginOfDay(beginDate);
        endDate = DateUtil.endOfDay(endDate);

        if (DateUtil.compare(beginDate, endDate, "yyyy-MM-dd") > 0) {
            throw new BizException(BizError.参数异常.getCode(), "结束日期不能早于开始日期");
        } else if (DateUtil.betweenDay(DateUtil.date(), endDate, true) > 1) {
            throw new BizException(BizError.参数异常.getCode(), "结束日期不能超过现在24小时");
        }

        IssueGenerateRule issueGenerateRuleSC = new IssueGenerateRule();
        issueGenerateRuleSC.setGameId(gameId);
        issueGenerateRuleSC.setStatus(Issue.STATUS_NORMAL);
        List<IssueGenerateRule> issueGenerateRuleServiceList = issueGenerateRuleService.findList(issueGenerateRuleSC);
        if (issueGenerateRuleServiceList.size() < 1) {
            throw new BizException(BizError.参数异常.getCode(), game.getGameName() + "没有任何生成规则，无法生成。");
        }

        List<Issue> issueList = ListUtils.newArrayList();
        // 按天生成期号
        Date nowDate = beginDate;
        do {
            for (IssueGenerateRule issueGenerateRule : issueGenerateRuleServiceList) {

                long baseIssueNumber = issueGenerateRule.getIssueBeginNumber();
                if (issueGenerateRule.getIssueBeginDate() != null) {
                    // 期号由 某一日期开始连续累加而成，比如北京PK10这种
                    // 计算和开始日期的差距天数`
                    long betweenDays = DateUtil.between(issueGenerateRule.getIssueBeginDate(), nowDate, DateUnit.DAY);
                    // 计算应该开始的期号
                    long issueBetweenNum = betweenDays * issueGenerateRule.getIssueCount();
                    baseIssueNumber = issueGenerateRule.getIssueBeginNumber() + issueBetweenNum;
                }

                // 按照每期间隔和总期数生成期数
                for (int sn = 0; sn < issueGenerateRule.getIssueCount(); sn++) {
                    Issue theIssue = new Issue();
                    theIssue.setId(IdGen.nextId());
                    // 游戏代码
                    theIssue.setGameCode(game.getGameCode());
                    // 开奖日期
                    theIssue.setLotteryDate(nowDate);
                    // 状态
                    theIssue.setState(Constant.期号状态_未开奖);

                    String dt = DateUtil.format(nowDate, "yyyy-MM-dd") + " " + issueGenerateRule.getStartTime();
                    Date lotteryStartDate = DateUtil.parseDateTime(dt);
                    // 每期间隔（分钟换算成秒）
                    double timeInterval = issueGenerateRule.getTimeInterval() * 60;

                    String issueNum = "";
//                    if (issueGenerateRule.getIssueBeginDate() == null) {
                    // 期号由 日期+期号 组成
                    // 期号日期部分规则
                    String dateFormat = issueGenerateRule.getDateFormat();
                    if ("yyyymmdd".equals(StringUtils.lowerCase(dateFormat))) {
                        dateFormat = DateUtil.format(nowDate, "yyyyMMdd");
                    } else if ("yymmdd".equals(StringUtils.lowerCase(dateFormat))) {
                        dateFormat = StringUtils.substring(DateUtil.format(nowDate, "yyyyMMdd"), 2);
                    } else if ("yymm".equals(StringUtils.lowerCase(dateFormat))) {
                        dateFormat = StringUtils.substring(DateUtil.format(nowDate, "yyyyMM"), 2);
                    } else if ("yyyy".equals(StringUtils.lowerCase(dateFormat))) {
                        dateFormat = DateUtil.format(nowDate, "yyyy");
                    }
                    // 生成每期的期数
                    String issueFormat = "";
                    if (StringUtils.isNotEmpty(issueGenerateRule.getIssueFormat())) {
                        issueFormat = String.format("%0" + issueGenerateRule.getIssueFormat().length() + "d", baseIssueNumber + sn);
                    }
                    issueNum = dateFormat + issueGenerateRule.getSeparator() + issueFormat;
//                    } else {
//                        issueNum = String.valueOf(baseIssueNumber + sn);
//                    }
                    // 保存期号
                    theIssue.setIssueNum(issueNum);

                    // 每期的开奖时间
                    double d = timeInterval * sn;
                    Date lotteryDateTime = DateUtil.offsetSecond(lotteryStartDate, (int) d);
                    if (DateUtil.compare(lotteryDateTime, lotteryStartDate, "yyyy-MM-dd") != 0 && issueGenerateRule.getNextDay().equals("0")) {
                        //说明跨天了，那么需要变为前一天的23:59:59
                        lotteryDateTime = DateUtil.parse(DateUtil.format(beginDate, "yyyy-MM-dd") + " 23:59:59");
                    }
                    theIssue.setLotteryTime(lotteryDateTime);
                    theIssue.setEndTime(DateUtil.offsetSecond(theIssue.getLotteryTime(), -issueGenerateRule.getBetCutOffTime()));
                    theIssue.setAutomaticLottery(issueGenerateRule.getAutomaticLottery());
                    theIssue.setAutomaticSettlement(issueGenerateRule.getAutomaticSettlement());
                    // 采集时间（MQ发送时间）
                    // 不同彩种不同，小于1分钟的8秒，大于1分钟的20秒
//                    int collectDelaySeconds = (int) NumberUtil.min(timeInterval * 0.1d, 20d);
                    Date effectTime = DateUtil.offsetSecond(theIssue.getLotteryTime(), game.getLotteryDelaySecond());
                    theIssue.setPlanSyncTime(effectTime);

                    // 生成每期的期数
                    issueList.add(theIssue);
                }
            }
            nowDate = DateUtil.offsetDay(nowDate, 1);
        } while (DateUtil.compare(nowDate, endDate, "yyyy-MM-dd") <= 0);

        // 移除数据库中已经存在的期数
        Issue issueSC = new Issue();
        issueSC.setGameCode(game.getGameCode());
        issueSC.setLotteryDate_gte(beginDate);
        issueSC.setLotteryDate_lte(endDate);
        List<Issue> issueListOfHaved = findList(issueSC);
        Iterator<Issue> issueIterator = issueList.iterator();
        while (issueIterator.hasNext()) {
            Issue theNewIssue = issueIterator.next();
            for (Issue issueHaved : issueListOfHaved) {
                if (theNewIssue.getIssueNum().equals(issueHaved.getIssueNum())) {
                    issueIterator.remove();
                }
            }
        }

        if (issueList.size() > 0) {
            for (Issue issue : issueList) {
                SyncLotteryNumMsg syncLotteryNumMsg = new SyncLotteryNumMsg();
                syncLotteryNumMsg.setGameCode(game.getGameCode());
                syncLotteryNumMsg.setIssueNum(issue.getIssueNum());
                syncLotteryNumMsg.setRetries(0);
                Message<String> message = MessageBuilder
                        .withPayload(JSONUtil.toJsonStr(syncLotteryNumMsg))
                        .setHeader("KEYS", issue.getId())
                        .build();
                rocketMQTemplate.syncSendDeliverTimeMills(
                        Constant.消息主题_同步开奖数据 + ":" + game.getGameCode(),
                        message,
                        issue.getPlanSyncTime().getTime()
                );

//                之前用的xxl-mq，但是投递消息时间不准确，已更换为rocketmq
//                XxlMqMessage mqMessage = new XxlMqMessage();
//                mqMessage.setTopic("SYNC_LOTTERY_NUM_" + issue.getGameCode());
//                mqMessage.setData(JSON.toJSONString(new SyncLotteryNumMsg(issue.getGameCode(), issue.getIssueNum(), 3)));
//                mqMessage.setEffectTime(issue.getPlanSyncTime());
//                XxlMqProducer.produce(mqMessage);
            }
            // 将没有的期号添加进去
            dao.insertBatch(issueList, 100);
        }
    }

    /**
     * 获取指定游戏的当前期号
     *
     * @param gameCode
     * @return
     */
    public Issue getCurrentIssue(String gameCode) {
        return dao.getCurrentIssue(gameCode);
    }

    /**
     * 获取指定游戏的上期期号
     *
     * @param gameCode
     * @return
     */
    public Issue getLastIssue(String gameCode) {
        return dao.getLastIssue(gameCode);
    }

    /**
     * 根据游戏代码和游戏期号查找期号
     *
     * @param gameCode
     * @param issueNumber
     * @return
     */
    public Issue getByGameCodeAndIssueNumber(String gameCode, String issueNumber) {
        return dao.getByGameCodeAndIssueNumber(gameCode, issueNumber);
    }

    /**
     * 获取游戏开奖历史数据
     *
     * @param gameCode
     * @param limitNumber
     * @return
     */
    public List<Issue> findHistory(String gameCode, int limitNumber) {
        return dao.findHistory(gameCode, limitNumber);
    }

    /**
     * 中奖验证和结算
     *
     * @param issue 要进行中奖验证和结算的期号
     */
    @Transactional
    public void checkWinBet(Issue issue) {
        if (StrUtil.isBlankIfStr(issue.getLotteryNum()) || !issue.getState().equals(Constant.期号状态_已开奖)) {
            throw new BizException(BizError.该期还未开奖无法进行中奖验证和结算);
        }
        // 查找本期所有的投注
        BetOrder betOrderSC = new BetOrder();
        betOrderSC.setIssueId(issue.getId());
        List<BetOrder> betOrderList = betOrderService.findList(betOrderSC);
        for (BetOrder betOrder : betOrderList) {
            // 查找投注对应的玩法
            PlayMethod playMethod = playMethodService.get(betOrder.getPlayMethodId());
            if (playMethod == null || StrUtil.isBlankIfStr(playMethod.getWinRuleFun())) {
                throw new BizException(BizError.valueOf("期号玩法对应的中奖验证方法为空"));
            }
            try {
                // 获取对应的用户，准备进行资金操作
                Member member = memberService.get(betOrder.getUserId());
                if (member == null || member.getIsNewRecord()) {
                    throw new BizException(BizError.用户名不存在);
                }
                // 发放投注返点，只有在开奖后才能发放投注返点，因为这时已经不能撤单
                double totalRebateAmount = payRebateAmountToParent(member, betOrder.getBetAmount(), betOrder.getId(), 0.0d);
                // 调用中奖规则工具类计算中奖注数
                int winCount = WinRulesUtils.calcWinningCount(playMethod.getWinRuleFun(), betOrder.getBetNumber(), issue.getLotteryNum(), betOrder.getExtBetNumber());
                // 计算奖金 = 中奖注数*单注奖金*倍数*货币单位
                double winAmount = NumberUtil.round(winCount * betOrder.getBonusAmount() * betOrder.getBetMultiple() * betOrder.getBetUnit(), 2).doubleValue();
                // TODO 计算盈亏 = 投注金额-投注返点-中奖金额
                double profitAndLoss = NumberUtil.round(betOrder.getBetAmount() - totalRebateAmount - winAmount, 2).doubleValue();

                betOrder.setLotteryNumber(issue.getLotteryNum()); //开奖号码
                betOrder.setLotteryTime(issue.getLotteryTime()); //开奖时间
                betOrder.setLotterySource(issue.getLotterySource()); //开奖来源
                betOrder.setWinCount((long) winCount);//中奖注数
                betOrder.setWinAmount(winAmount);// 奖金
                betOrder.setTotalRebateAmount(totalRebateAmount);//总返点
                betOrder.setProfitAndLoss(profitAndLoss);//总盈亏
                if (winAmount <= 0) {
                    betOrder.setBizStatus(Constant.投注订单状态_未中奖);//改订单状态
                } else {
                    betOrder.setBizStatus(Constant.投注订单状态_已派奖);//改订单状态
                }
                betOrderService.save(betOrder);

                // TODO检查派奖
                if (winAmount > 0) {
                    double newBalance = winAmount + member.getBalance();
                    // 记录账变日志
                    accountChangeLogService.add(member,
                            winAmount,
                            newBalance,
                            Constant.账变日志类型_入账_中奖派奖,
                            Constant.操作人_系统自动,
                            BetOrder.class.getName(),
                            betOrder.getIssueId());
                    member.setBalance(newBalance);
                    memberService.save(member);
                }

            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new BizException(BizError.valueOf(e.getMessage()));
            }
        }
        //改issue状态为已结算
        issue.setState(Constant.期号状态_已结算);
        save(issue);
    }

    /**
     * 发放上级投注返点=下家投注金额*上下级返点差
     *
     * @param member    会员
     * @param betAmount 下家投注金额
     * @param bizId     业务ID
     * @param allAmount 合计金额，用于最后返回
     */
    @Transactional
    public double payRebateAmountToParent(Member member, double betAmount, String bizId, double allAmount) throws Exception {
        if (!"0".equals(member.getParentCode())) {
            Member parentMember = memberService.get(member.getParentCode());
            String memberName = member.getMemName();
            // 计算返点差
            double rebateDiff = parentMember.getRebate() - member.getRebate();
            if (rebateDiff < 0) {
                throw new BizException(BizError.上级返点小于下级);
            }
            // 发放返点金额
            double rebateAmount = rebateDiff * betAmount / 100;
            if (rebateAmount > 0.0d) {
                // 发放返点金额
                parentMember.setBalance(parentMember.getBalance() + rebateAmount);
                // 记录账变日志
                accountChangeLogService.add(parentMember,
                        rebateAmount,
                        parentMember.getBalance(),
                        StrUtil.format("{}【{}】", Constant.账变日志类型_入账_下家投注返点, memberName),
                        Constant.操作人_系统自动,
                        BetOrder.class.getName(),
                        bizId);
                memberService.save(parentMember);
            }
            // 累加合计金额
            allAmount += rebateAmount;

            //递归调用直到所有上级都返点
            return payRebateAmountToParent(parentMember, betAmount, bizId, allAmount);
        }
        return allAmount;
    }
}