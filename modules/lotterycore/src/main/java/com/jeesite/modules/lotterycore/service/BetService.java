package com.jeesite.modules.lotterycore.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.jeesite.common.codec.EncodeUtils;
import com.jeesite.common.service.BaseService;
import com.jeesite.modules.lotterycore.common.exception.BizError;
import com.jeesite.modules.lotterycore.common.exception.BizException;
import com.jeesite.modules.lotterycore.common.utils.BetUtils;
import com.jeesite.modules.lotterycore.constants.Constant;
import com.jeesite.modules.lotterycore.entity.*;
import com.jeesite.modules.lotterycore.param.BetInfo;
import com.jeesite.modules.lotterycore.param.BetRequest;
import com.jeesite.modules.lotterycore.param.R;
import com.jeesite.modules.sys.entity.Member;
import com.jeesite.modules.sys.service.MemberService;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 投注Service
 *
 * @author DUKE
 * @version 2023-04-23
 */
@Service
public class BetService extends BaseService {

    @Autowired
    private GameService gameService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private IssueService issueService;
    @Autowired
    private PlayMethodGroupService playMethodGroupService;
    @Autowired
    private PlayMethodService playMethodService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private BetOrderService betOrderService;
    @Autowired
    private AccountChangeLogService accountChangeLogService;

    /**
     * 投注核心方法
     *
     * @param betRequest
     */
    @Transactional
    public R bet(String betRequest) throws Exception {
        // 会员信息
        Member currentUser = UserUtils.getUser().getRefObj();
        // 字符串安全验证
        String filteredBetRequest = EncodeUtils.xssFilter(betRequest);
        filteredBetRequest = EncodeUtils.sqlFilter(filteredBetRequest);
        if (StrUtil.isBlankIfStr(filteredBetRequest) || filteredBetRequest.length() != betRequest.length()) {
            throw new BizException(BizError.参数安全异常);
        }
        BetRequest betRequestBean;
        try {
            betRequestBean = JSONUtil.toBean(filteredBetRequest, BetRequest.class);
        } catch (Exception e) {
            logger.error("投注请求解析失败:" + e.getMessage());
            throw new BizException(BizError.投注参数解析异常);
        }

        /**
         * 游戏及期号验证
         */
        // 验证游戏是否正常
        if (StrUtil.isBlankIfStr(betRequestBean.getGameCode())) {
            throw new BizException(BizError.参数异常);
        }
        Game game = gameService.validGameByGameCode(betRequestBean.getGameCode());
        if (game == null) {
            throw new BizException(BizError.游戏不存在);
        }
        // 期号相关验证
        Issue issue = new Issue();
        issue.setGameCode(game.getGameCode());
        issue.setIssueNum(betRequestBean.getIssue());
        List<Issue> issueList = issueService.findList(issue);
        if (issueList.size() < 1) {
            throw new BizException(BizError.期号非法);
        }
        issue = issueList.get(0);
        if (!issue.getState().equals(Constant.期号状态_未开奖)) {
            throw new BizException(BizError.该期已开奖无法投注);
        }
        if (issue.getEndTime().before(new Date())) {
            throw new BizException(BizError.该期已封盘无法投注);
        }

        /**
         *  对每一注做相关验证
         */
        double totalBetAmount = 0.0d;
        int listIndex = 0;
        List<BetOrder> betOrderList = new ArrayList<>();
        for (BetInfo betInfo : betRequestBean.getBetList()) {
            // 是否有这个玩法
            if (StrUtil.isBlankIfStr(betInfo.getPlayMethodId())) {
                throw new BizException(BizError.参数异常);
            }
            PlayMethod playMethod = playMethodService.get(betInfo.getPlayMethodId());
            if (playMethod == null || playMethod.getIsNewRecord()) {
                throw new BizException(BizError.游戏玩法不存在);
            }

            // 玩法对应的游戏组中是否包含这类游戏
            PlayMethodGroup playMethodGroup = playMethodGroupService.get(playMethod.getGroupId());
            if (playMethodGroup == null || playMethodGroup.getIsNewRecord()) {
                throw new BizException(BizError.游戏玩法不存在);
            }
            if (!StrUtil.contains(playMethodGroup.getGameCategory(), game.getGameCode())) {
                throw new BizException(BizError.该游戏不能用这个玩法下注);
            }

            // 验证投注数字是否符合玩法规则
            if (StrUtil.isBlankIfStr(betInfo.getBetNumber())) {
                throw new BizException(BizError.参数异常);
            }

            // 验证倍数
            if (betInfo.getBetMultiplier() < 1 || betInfo.getBetMultiplier() > 1000) {
                throw new BizException(BizError.投注倍数验证错误);
            }

            // 注数验证
            // betNumber格式为 【5,6,7,8,9|7|2|6,0|2,1】
            // 分别对应从右往左按位置上的值，以|做分隔
            String[] betNumberList = betInfo.getBetNumber().split("\\|");
            if (betNumberList.length != Integer.parseInt(playMethod.getSelectNumber())) {
                throw new BizException(BizError.投注选号位数不符合玩法规则);
            }
            try {
                int validBetCount = BetUtils.calcBetCount(betInfo.getBetNumber(), playMethod.getBetCountFun());
                int betCount = validBetCount * betInfo.getBetMultiplier(); //投注数=计算注数*倍数
                if (betCount != betInfo.getFinalCount()) {
                    throw new BizException(BizError.投注数验证错误);
                }
            } catch (BizException biz) {
                throw biz;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // 投注金额验证
            // 投注金额 = 投注数*基准售价
            double betAmount = betInfo.getFinalCount() * Constant.系统_单注销售单价;
            if (betAmount == betInfo.getBetAmount()) {
                throw new BizException(BizError.投注金额验证错误);
            }

            // 验证货币单位
            if (betInfo.getBetUnit() == null) {
                throw new BizException(BizError.货币单位验证错误);
            }
            if (betInfo.getBetUnit().getValue() != 1.0d &&
                    betInfo.getBetUnit().getValue() != 0.1d &&
                    betInfo.getBetUnit().getValue() != 0.01d &&
                    betInfo.getBetUnit().getValue() != 0.001d) {
                throw new BizException(BizError.货币单位不在范围内);
            }


            // 验证返点
            // 不能大于用户最大返点和系统最大返点

            if (betInfo.getRebate() < 0 || betInfo.getRebate() > currentUser.getRebate() || betInfo.getRebate() > Constant.系统_会员_返点上限) {
                throw new BizException(BizError.返点超限);
            }

            // 验证投注金额和返点金额
            /**
             * 投注返点和奖金计算
             * 若系统投注返点上限15，客户投注返点上限不能超过15
             * 投注时允许客户选择奖金和返点比例，返点是投注就给（相当于打折），奖金是中奖才给
             * 中奖奖金金额=(玩法最大奖金-玩法最小奖金)*(客户投注返点上限-客户选择返点)/系统投注返点上限+玩法最小奖金
             * 投注返点金额=投注金额*客户选择返点
             */
            double bonusDiff = playMethod.getMaxBonus() - playMethod.getMinBonus();
            double rebateDiff = currentUser.getRebate() - betInfo.getRebate();
            double bonusAdd = bonusDiff * rebateDiff / Constant.系统_会员_返点上限;
            double bonusAmount = bonusAdd + playMethod.getMinBonus();//奖金金额
            double rebate = currentUser.getRebate() / 100; // 返点率
            double rebateAmount = rebate * betAmount; //返点金额=返点率*投注金额
            if (bonusAmount != betInfo.getBonusAmount()) {
                throw new BizException(BizError.奖金金额验证错误);
            }
            if (rebate != betInfo.getRebate()) {
                throw new BizException(BizError.投注返点比例验证错误);
            }
            if (rebateAmount != betInfo.getRebateAmount()) {
                throw new BizException(BizError.投注返点金额验证错误);
            }

            // 累加投注金额
            totalBetAmount += betAmount;

            // 保存投注记录
            listIndex++;
            BetOrder betOrder = new BetOrder();
            String orderNo = StrUtil.format("{}-{}-{}-{}", betRequestBean.getGameCode(), DateUtil.format(DateUtil.date(), "yyMMddHHmmss"), StrUtil.fillBefore(String.valueOf(listIndex), '0', 2), RandomUtil.randomStringWithoutStr(6, ""));
            betOrder.setOrderNo(orderNo);
            betOrder.setExtNo(StrUtil.format("{}-OUT", orderNo));
            betOrder.setUserId(currentUser.getId());
            betOrder.setUserAccount(currentUser.getMemName());
            betOrder.setUserIp(UserUtils.getSession().getHost());
            betOrder.setUserWay(UserUtils.getSession().getAttribute("deviceType").toString());
            betOrder.setUserEquipment(UserUtils.getSession().getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY.primaryPrincipal.params.device").toString());
            betOrder.setBasePrice(Constant.系统_单注销售单价);
            betOrder.setBetTime(DateUtil.date());
            betOrder.setGameCode(betRequestBean.getGameCode());
            betOrder.setPlayMethodGroupId(playMethodGroup.getId());
            betOrder.setPlayMethodGroup(playMethodGroup.getGroupName());
            betOrder.setPlayMethodId(playMethod.getId());
            betOrder.setPlayMethod(playMethod.getPlayMethodName());
            betOrder.setIssueId(issue.getId());
            betOrder.setIssue(issue.getIssueNum());
            betOrder.setBetNumber(betInfo.getBetNumber());
//            betOrder.setExtBetNumber(); //附加号码
            betOrder.setBetCount((long) betInfo.getBetCount());
            betOrder.setBetMultiple((long) betInfo.getBetCount());
            betOrder.setTotalBetCount((long) betInfo.getFinalCount());
            betOrder.setBetUnit(betInfo.getBetUnit().getValue());
            betOrder.setBetAmount(betInfo.getBetAmount());
//            betOrder.setNotPosition();//不定位
            betOrder.setRebate(betInfo.getRebate());
            betOrder.setRebateAmount(betInfo.getRebateAmount());
            betOrder.setBonusAmount(betInfo.getBonusAmount());
//            betOrder.setSyndicate();//合买
//            betOrder.setFrisbee();//飞盘
//            betOrder.setChasing();//追号
//            betOrder.setChasingIssue();
//            betOrder.setChasingIssueRemain();
//            betOrder.setChasingWinStop();
            betOrder.setBizStatus(Constant.投注订单状态_未开奖);

            betOrderList.add(betOrder);
        }

        /**
         * 会员相关验证
         */
        // 资金验证
        if (currentUser.getBalance() < totalBetAmount) {
            throw new BizException(BizError.余额不足无法投注);
        }

        /**
         * 验证通过开始投注
         */
        for (BetOrder betOrder : betOrderList) {
            // 保存投注记录
            betOrderService.save(betOrder);
            Member member = UserUtils.getUser().getRefObj();

            // 开始变动余额
            double newBalance = currentUser.getBalance();//原始余额
            // 投注扣款
            double changeAmount = 0 - betOrder.getBetAmount();
            newBalance += changeAmount;
            // 记录账变日志
            accountChangeLogService.add(member,
                    changeAmount,
                    Constant.账变日志类型_出账_投注扣款,
                    Constant.操作人_系统自动,
                    BetOrder.class.getName(),
                    betOrder.getIssueId());

            // 发放客户投注返点
            changeAmount = betOrder.getRebateAmount();
            newBalance += changeAmount;
            // 记录账变日志
            accountChangeLogService.add(member,
                    changeAmount,
                    Constant.账变日志类型_入账_投注返点,
                    Constant.操作人_系统自动,
                    BetOrder.class.getName(),
                    betOrder.getIssueId());

            // 客户账变完毕，保存余额
            member.setBalance(newBalance);
            memberService.save(member);

            // TODO 发放客户上级投注返点
            payRebateAmountToParent(member, betOrder.getBetAmount(), betOrder.getId());
        }

        return R.success();
    }

    /**
     * 发放上级投注返点=下家投注金额*上下级返点差
     *
     * @param member
     * @param betAmount
     */
    @Transactional
    public void payRebateAmountToParent(Member member, double betAmount, String bizId) throws Exception {
        if (!"0".equals(member.getParentCode())) {
            Member parentMember = memberService.get(member.getParentCode());
            // 计算返点差
            double rebateDiff = parentMember.getRebate() - member.getRebate();
            if (rebateDiff < 0) {
                throw new BizException(BizError.上级返点小于下级);
            }
            // 发放下家的返点金额
            double rebateAmount = rebateDiff * betAmount / 100;
            // 发放下家的返点金额
            parentMember.setBalance(parentMember.getBalance() + rebateAmount);
            // 记录账变日志
            accountChangeLogService.add(parentMember,
                    betAmount,
                    Constant.账变日志类型_入账_下家投注返点,
                    Constant.操作人_系统自动,
                    BetOrder.class.getName(),
                    bizId);
            memberService.save(parentMember);

            //递归调用直到所有上级都返点
            payRebateAmountToParent(parentMember, betAmount, bizId);
        }
    }


}