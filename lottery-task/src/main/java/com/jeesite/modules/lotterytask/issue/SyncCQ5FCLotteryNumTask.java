package com.jeesite.modules.lotterytask.issue;

import com.jeesite.modules.lotterycore.constants.Constant;
import com.xxl.mq.client.consumer.annotation.MqConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@MqConsumer(topic = SyncCQ5FCLotteryNumTask.TOPIC)
@Component
@Slf4j
public class SyncCQ5FCLotteryNumTask extends BaseSyncLotteryNumTask {
    public static final String TOPIC = "SYNC_LOTTERY_NUM_" + Constant.游戏_重庆五分彩;
}