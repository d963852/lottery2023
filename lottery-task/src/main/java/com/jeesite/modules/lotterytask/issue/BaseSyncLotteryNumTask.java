package com.jeesite.modules.lotterytask.issue;

import com.alibaba.fastjson.JSON;
import com.jeesite.modules.lotterycore.constants.Constant;
import com.jeesite.modules.lotterycore.entity.Issue;
import com.jeesite.modules.lotterycore.param.SyncLotteryNumMsg;
import com.jeesite.modules.lotterycore.service.IssueService;
import com.jeesite.modules.lotterycore.service.game.SyncLotteryNumberService;
import com.xxl.mq.client.consumer.IMqConsumer;
import com.xxl.mq.client.consumer.MqResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class BaseSyncLotteryNumTask implements IMqConsumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IssueService issueService;

    @Autowired
    private SyncLotteryNumberService syncLotteryNumberService;

    public static final String TOPIC = "SYNC_LOTTERY_NUM_" + Constant.游戏_奇趣分分彩;

    private static final List<Integer> RETRY_LEVEL = Arrays.asList(65, 25, 18, 18, 16, 16, 14, 14, 10, 10, 25, 25, 18, 18);

    @Override
    public MqResult consume(String data) {
        SyncLotteryNumMsg message = JSON.parseObject(data, SyncLotteryNumMsg.class);
        logger.info("收到消息" + message.toString() + "，开始消费....");
        // 判断这个期号是否已经开奖
        Issue issueSC = new Issue();
        issueSC.setIssueNum(message.getIssueNum());
        issueSC.setGameCode(message.getGameCode());
        List<Issue> issueList = issueService.findList(issueSC);
        if (issueList.size() < 1) {
            logger.error("消费异常：找不到对应的投注期号" + message.toString());
            return MqResult.SUCCESS;
        }

        try {
            log.info("执行同步开奖号码定时任务start");
            syncLotteryNumberService.syncLotteryNumber(issueList.get(0));
            log.info("执行同步开奖号码定时任务end");
        } catch (Exception e) {
            log.error("执行同步开奖号码定时任务发生异常", e);
        }
        return MqResult.SUCCESS;
    }
}