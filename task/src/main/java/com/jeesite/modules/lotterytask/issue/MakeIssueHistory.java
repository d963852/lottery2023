package com.jeesite.modules.lotterytask.issue;

import cn.hutool.core.date.DateUtil;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.entity.Issue;
import com.jeesite.modules.lotterycore.service.GameService;
import com.jeesite.modules.lotterycore.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 将3天前的期号信息移动到历史表中
 */
@Component
public class MakeIssueHistory {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IssueService issueService;
    @Autowired
    private GameService gameService;

    //@Scheduled(cron = "0 50 23 * * *")
    public void execute() {
        try {
            logger.info("执行移动历史期号任务start");
            Issue issueSC = new Issue();
            issueSC.setLotteryTime_lte(DateUtil.offsetDay(DateUtil.beginOfDay(DateUtil.date()),-3));
            issueSC.setState("1");
            List<Issue> issueList = issueService.findList(issueSC);

            List<Game> gameList = gameService.findList(new Game());
            for (Game game : gameList) {
                try {
//                    issueService.makeIssue(DateUtil.date(), DateUtil.tomorrow(), game.getId());
                    issueService.makeIssue(DateUtil.tomorrow(), DateUtil.tomorrow(), game.getId());
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            logger.info("执行移动历史期号任务end");
        } catch (Exception e) {
            logger.error("执行生成期号数据定时任务发生异常", e);
        }
    }

}
