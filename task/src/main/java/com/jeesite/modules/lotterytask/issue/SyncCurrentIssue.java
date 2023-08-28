package com.jeesite.modules.lotterytask.issue;

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
 * 自动补充采集开奖数据
 */
@Component
public class SyncCurrentIssue {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IssueService issueService;
    @Autowired
    private GameService gameService;

    /**
     * 更改游戏的当前期号和下期期号
     */
    @Scheduled(cron = "0,30 * * * * *")
    public void execute() {
        logger.info("执行【更改游戏的当前期号和下期期号任务】开始");
        try {
            Game gameSC = new Game();
            List<Game> gameList = gameService.findList(gameSC);
            for (Game game : gameList) {
                Issue currentIssue = issueService.getCurrentIssue(game.getGameCode());
                Issue lastIssue = issueService.getLastIssue(game.getGameCode());
                if(currentIssue!=null) {
                    game.setCurrentIssueNumber(currentIssue.getIssueNum());
                    game.setCurrentIssueEndTime(currentIssue.getLotteryTime());
                    game.setLastIssueNumber(lastIssue.getIssueNum());
                    game.setLastIssueLotteryNumber(lastIssue.getLotteryNum());
                    gameService.save(game);
                }
            }
        } catch (Exception e) {
            logger.error("执行【更改游戏的当前期号和下期期号任务】异常", e);
        }
        logger.info("执行【更改游戏的当前期号和下期期号任务】完毕");
    }

}
