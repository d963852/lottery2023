package com.jeesite.modules.lotterytask.issue;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.service.GameService;
import com.jeesite.modules.lotterycore.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class GenerateIssueTask {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IssueService issueService;
    @Autowired
    private GameService gameService;

    @Scheduled(cron = "0 0 0,6 * * *")
    public void execute() {
        try {
            logger.info("执行生成期号数据定时任务start");
            Date tomorrow = DateUtils.addDays(new Date(), 1);

            List<Game> gameList = gameService.findList(new Game());
            for (Game game : gameList) {
                try {
//                    if (game.getGameCode().equals(Constant.游戏_奇趣分分彩)) {
                        issueService.makeIssue(tomorrow, tomorrow, game.getId());
//                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            logger.info("执行生成期号数据定时任务end");
        } catch (Exception e) {
            logger.error("执行生成期号数据定时任务发生异常", e);
        }
    }

}
