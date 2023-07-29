package com.jeesite.modules.lotterytask.issue;

import cn.hutool.json.JSONUtil;
import com.jeesite.modules.lotterycore.common.exception.BizError;
import com.jeesite.modules.lotterycore.common.exception.BizException;
import com.jeesite.modules.lotterycore.constants.Constant;
import com.jeesite.modules.lotterycore.entity.Issue;
import com.jeesite.modules.lotterycore.param.SyncLotteryNumMsg;
import com.jeesite.modules.lotterycore.service.GameService;
import com.jeesite.modules.lotterycore.service.IssueService;
import com.jeesite.modules.lotterycore.service.game.SyncLotteryNumberService;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseSyncLotteryNumTask implements RocketMQListener<String> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IssueService issueService;

    @Autowired
    private GameService gameService;

    @Autowired
    private SyncLotteryNumberService syncLotteryNumberService;

    public void onMessage(String message) {
        logger.info("收到消息：" + message.toString());
        SyncLotteryNumMsg syncLotteryNumMsg = JSONUtil.toBean(message, SyncLotteryNumMsg.class);
        // 判断这个期号是否已经开奖
        Issue issueSC = new Issue();
        issueSC.setIssueNum(syncLotteryNumMsg.getIssueNum());
        issueSC.setGameCode(syncLotteryNumMsg.getGameCode());
        List<Issue> issueList = issueService.findList(issueSC);
        if (issueList.size() < 1) {
            logger.error("消费异常：找不到对应的投注期号" + message.toString());
            return;
        }
        Issue theIssue = issueList.get(0);
        if (!theIssue.getState().equals(Constant.期号状态_未开奖)) {
            logger.error("消费异常：【" + theIssue.getGameCode() + "】第【" + theIssue.getIssueNum() + "】期已经开奖");
            return;
        }
        try {
            logger.info("执行同步" + theIssue.getGameCode() + "开奖号码任务start");
            syncLotteryNumberService.syncLotteryNumber(theIssue);

            // TODO 开奖

            logger.info("执行同步" + theIssue.getGameCode() + "开奖号码任务end");
        } catch (Exception e) {
            throw new BizException(BizError.参数异常.getCode(), "执行同步" + theIssue.getGameCode() + "开奖号码定时任务发生异常：" + e.getMessage());
        }
    }
}