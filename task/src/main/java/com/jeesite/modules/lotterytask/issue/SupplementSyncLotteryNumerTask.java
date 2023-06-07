package com.jeesite.modules.lotterytask.issue;

import cn.hutool.core.date.DateUtil;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.entity.Issue;
import com.jeesite.modules.lotterycore.service.GameService;
import com.jeesite.modules.lotterycore.service.IssueService;
import com.jeesite.modules.lotterycore.service.game.SyncLotteryNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 自动补充采集开奖数据
 */
@Component
public class SupplementSyncLotteryNumerTask {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IssueService issueService;
    @Autowired
    private SyncLotteryNumberService syncLotteryNumberService;

    @Scheduled(cron = "0 0/1 * * * *")
    public void execute() {
        try {
            logger.info("执行自动补采任务start");
            Issue issueSC = new Issue();
            // 设置补采范围为过去24小时
            issueSC.setLotteryTime_gte(DateUtil.offsetMinute(DateUtil.date(), -1445));
            issueSC.setLotteryTime_lte(DateUtil.offsetMinute(DateUtil.date(), -5));
            issueSC.setLotteryNum_isNull();
            List<Issue> issueList = issueService.findList(issueSC);
            for (Issue issue : issueList) {
                try {
                    logger.info("自动补采【" + issue.getGameCode() + "】第【" + issue.getIssueNum() + "】期");
                    syncLotteryNumberService.syncLotteryNumber(issue);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            logger.info("执行自动补采任务end");
        } catch (Exception e) {
            logger.error("执行自动补采任务发生异常", e);
        }
    }

}
