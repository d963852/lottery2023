package com.jeesite.modules.lotterytask.issue;

import com.jeesite.modules.lotterycore.constants.Constant;
import com.xxl.mq.client.consumer.annotation.MqConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@MqConsumer(topic = SyncTX2FCSLotteryNumTask.TOPIC)
@Component
@Slf4j
public class SyncTX2FCSLotteryNumTask extends BaseSyncLotteryNumTask {
    public static final String TOPIC = "SYNC_LOTTERY_NUM_" + Constant.游戏_腾讯2分彩;
}