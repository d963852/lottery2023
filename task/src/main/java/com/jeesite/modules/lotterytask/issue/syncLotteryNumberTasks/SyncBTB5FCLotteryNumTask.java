package com.jeesite.modules.lotterytask.issue.syncLotteryNumberTasks;

import com.jeesite.modules.lotterycore.constants.Constant;
import com.jeesite.modules.lotterytask.issue.BaseSyncLotteryNumTask;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.springframework.stereotype.Component;

/**
 * 【比特币5分彩】开奖数据同步类
 * 由MakeSyncClass自动生成
 */
@Component
@RocketMQMessageListener(
  consumeMode = ConsumeMode.CONCURRENTLY,
  topic = "SYNC_LOTTERY_NUMBER",
  consumerGroup = "LotteryConsumerGroup_BTB5FC",
  selectorType = SelectorType.TAG,
  selectorExpression = "BTB5FC"
)
public class SyncBTB5FCLotteryNumTask extends BaseSyncLotteryNumTask {
  public String GAME_CODE = "BTB5FC";
}