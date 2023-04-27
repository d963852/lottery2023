package com.jeesite.modules.lotterytask.issue;
import com.jeesite.modules.lotterycore.common.exception.BizError;
import com.jeesite.modules.lotterycore.common.exception.BizException;
import com.jeesite.modules.lotterycore.constants.Constant;
import com.jeesite.modules.lotterycore.entity.Issue;
import com.jeesite.modules.lotterycore.service.IssueService;
import com.jeesite.modules.lotterycore.service.game.QiqffcService;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RocketMQMessageListener(
        consumeMode= ConsumeMode.CONCURRENTLY,
        topic = Constant.消息主题_同步开奖数据,
        consumerGroup = "LotteryConsumerGroup",
        selectorType = SelectorType.TAG,
        selectorExpression = Constant.游戏_奇趣分分彩
)
public class SyncQiqffcLotteryNumTask implements RocketMQListener<MessageExt> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IssueService issueService;

    @Autowired
    private QiqffcService qiqffcService;

    @Override
    public void onMessage(MessageExt message) {
        // 判断这个期号是否已经开奖
        Issue issueSC = new Issue();
        issueSC.setIssueNum(new String(message.getBody()));
        issueSC.setGameCode(Constant.游戏_奇趣分分彩);
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
            qiqffcService.syncLotteryNumber(theIssue);
            logger.info("执行同步" + theIssue.getGameCode() + "开奖号码任务end");
        } catch (Exception e) {
            throw new BizException(BizError.参数异常.getCode(), "执行同步" + theIssue.getGameCode() + "开奖号码定时任务发生异常：" + e.getMessage());
        }
    }
}