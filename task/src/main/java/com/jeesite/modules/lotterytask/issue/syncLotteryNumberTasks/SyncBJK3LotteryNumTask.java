package com.jeesite.modules.lotterytask.issue.syncLotteryNumberTasks;

import com.jeesite.modules.lotterycore.constants.Constant;
import com.jeesite.modules.lotterytask.issue.BaseSyncLotteryNumTask;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.springframework.stereotype.Component;

/**
 * 【北京快三】开奖数据同步类
 * 由MakeSyncClass自动生成
 */
@Component
@RocketMQMessageListener(
  consumeMode = ConsumeMode.CONCURRENTLY,
  topic = "SYNC_LOTTERY_NUMBER",
  consumerGroup = "LotteryConsumerGroup_BJK3",
  selectorType = SelectorType.TAG,
  selectorExpression = "BJK3"
)
public class SyncBJK3LotteryNumTask extends BaseSyncLotteryNumTask {
  public String GAME_CODE = "BJK3";
}