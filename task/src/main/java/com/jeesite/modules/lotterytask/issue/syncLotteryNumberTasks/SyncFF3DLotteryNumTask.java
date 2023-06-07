package com.jeesite.modules.lotterytask.issue.syncLotteryNumberTasks;

import com.jeesite.modules.lotterycore.constants.Constant;
import com.jeesite.modules.lotterytask.issue.BaseSyncLotteryNumTask;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.springframework.stereotype.Component;

/**
 * 【分分3D】开奖数据同步类
 * 由MakeSyncClass自动生成
 */
@Component
@RocketMQMessageListener(
  consumeMode = ConsumeMode.CONCURRENTLY,
  topic = "SYNC_LOTTERY_NUMBER",
  consumerGroup = "LotteryConsumerGroup_FF3D",
  selectorType = SelectorType.TAG,
  selectorExpression = "FF3D"
)
public class SyncFF3DLotteryNumTask extends BaseSyncLotteryNumTask {
  public String GAME_CODE = "FF3D";
}