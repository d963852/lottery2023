package com.jeesite.modules.lotterycore.service.game;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.lotterycore.common.exception.BizError;
import com.jeesite.modules.lotterycore.common.exception.BizException;
import com.jeesite.modules.lotterycore.common.utils.DuocaiUtils;
import com.jeesite.modules.lotterycore.common.utils.ThreadPoolUtils;
import com.jeesite.modules.lotterycore.constants.Constant;
import com.jeesite.modules.lotterycore.dao.IssueDao;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.entity.Issue;
import com.jeesite.modules.lotterycore.service.GameService;
import com.jeesite.modules.lotterycore.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 获取开彩数据
 *
 * @author DUKE
 * @version 2023-04-23
 */
@Service
public class SyncLotteryNumberService extends CrudService<IssueDao, Issue> {

    @Autowired
    private IssueService issueService;

    @Autowired
    private GenerateLocalLotteryNumberService generateLocalLotteryNumberService;

    @Autowired
    private GameService gameService;

    /**
     * 开奖
     *
     * @param issue 要开奖的游戏期号
     */
    public Issue syncLotteryNumber(@NotNull Issue issue) {
        if (!issue.getState().equals(Constant.期号状态_未开奖)) {
            logger.error("本期已开奖，跳过" + issue.toString());
            return issue;
        }
        Issue latestWithInterface = getLotteryNumberResultFromMultipleWays(issue);
        if (latestWithInterface == null) {
            return issue;
        }
        issue.setLotteryNum(latestWithInterface.getLotteryNum());
        //TODO 风控检测

        issue.setLotterySource(latestWithInterface.getLotterySource());
        issue.setState(Constant.期号状态_已开奖);
        issue.setSyncTime(DateUtil.date());
        issueService.save(issue);
        return issue;
    }

    /**
     * 多线程多渠道开奖
     * 如果lottery_url为local，则直接本地开奖
     * 否则就多彩开奖：如果LotteryLocalInstead设置为1，则多彩开奖失败后本地开奖替代。否则不替代。
     *
     * @return 开奖结果
     */
    private Issue getLotteryNumberResultFromMultipleWays(Issue issue) {
        List<Issue> issueList = ListUtils.newArrayList();
        List<Future<Issue>> futures = ListUtils.newArrayList();
        // 获取对应Game
        Game game = new Game();
        game.setGameCode(issue.getGameCode());
        List<Game> gameList = gameService.findList(game);
        if (gameList.size() > 0) {
            game = gameList.get(0);
        }
        String duocaiGameCode = game.getDuocaiGameCode();

        if (!"local".equals(game.getLotteryUrl())) {
            futures.add(ThreadPoolUtils.getSyncLotteryThreadPool().submit(() -> {
                logger.info("启动多彩线程");
                return getLotteryNumberWithDuocai(issue, duocaiGameCode);
            }));
        }
        if ("local".equals(game.getLotteryUrl()) || "1".equals(game.getLotteryLocalInstead())) {
            //本地可替代，则启动本地线程
            futures.add(ThreadPoolUtils.getSyncLotteryThreadPool().submit(() -> {
                logger.info("启动本地线程");
                return getLotteryNumberLocal(issue);
            }));
        }

        CountDownLatch countlatch = new CountDownLatch(futures.size());
        for (Future<Issue> future : futures) {
            try {
                Issue issueVO = future.get(20, TimeUnit.SECONDS);
                logger.warn("==============线程有返回值：" + issueVO.getLotterySource());
                issueList.add(issueVO);
            } catch (TimeoutException e) {
                logger.error("执行超时");
            } catch (Exception e) {
                logger.error("异步future接口出现错误1", e);
            }
            countlatch.countDown();
        }
        try {
            countlatch.await();
        } catch (InterruptedException e) {
            logger.error("异步future接口出现错误2", e);
        }

        issueList.sort(new Comparator<Issue>() {
            @Override
            public int compare(Issue o1, Issue o2) {
                if (o1 == null) {
                    return -1;
                }
                if (o2 == null) {
                    return -1;
                }
                return o1.getIssueNumInner().compareTo(o2.getIssueNumInner());
            }
        });
        return issueList.isEmpty() ? null : issueList.get(0);
    }

    /**
     * 通过多彩网获取开采数据
     *
     * @return 多彩网获取开采数据
     */
    private Issue getLotteryNumberWithDuocai(Issue issue, String duocaiGameCode) {
        logger.info("开始尝试多彩采集...");
        Issue resultIssue = new Issue();
        String url = StrUtil.format(Global.getProperty("duocai.url", "http://vip.manycai.com/{}/{}/{}.json"), DuocaiUtils.getUserkey(), issue.getIssueNum(), duocaiGameCode);
        try {
            String duocaiData = HttpUtil.get(url);
            logger.warn("第1次采集" + url + "，多彩网返回值为：" + duocaiData);
            for (int i = 2; i < 11 && !duocaiData.startsWith("[{\"issue\":\"" + issue.getIssueNum()); i++) {
                logger.warn("休眠2秒后重试");
                // 2秒后再试一次
                Thread.sleep(2 * 1000);
                duocaiData = HttpUtil.get(url);
                logger.warn("第" + i + "次尝试" + url + ",返回值为：" + duocaiData);
            }

            if (!duocaiData.startsWith("[{\"issue\":\"" + issue.getIssueNum())) {
                throw new BizException(BizError.参数异常.getCode(), url+"多彩网不可用");
            }

            JSONArray array = JSONUtil.parseArray(duocaiData);
            List<Dict> list = JSONUtil.toList(array, Dict.class);
            resultIssue.setLotteryNum(list.get(0).getStr("code"));
            resultIssue.setIssueNumInner(1L);
            resultIssue.setLotterySource(url);
            return resultIssue;
        } catch (Exception e) {
            logger.error("通过多彩网获取：【" + issue.getGameCode() + "】第【" + issue.getIssueNum() + "】期开奖结果发生异常，调取地址：" + url, e.getMessage());
        }
        return null;
    }

    /**
     * 本地开彩
     *
     * @return 多彩网获取开采数据
     */
    private Issue getLotteryNumberLocal(Issue issue) {
        logger.info(">>>>>>>>>>>>>>本地生成");
        Issue resultIssue = new Issue();
        try {
            resultIssue.setLotteryNum(generateLocalLotteryNumberService.generateLocalLotteryNumber(issue));
            resultIssue.setLotterySource("local");
            resultIssue.setIssueNumInner(999L);
            logger.info(">>>>>>>>>>>>>>本地生成结束");
            return resultIssue;
        } catch (Exception e) {
            logger.error("本地开彩：【" + issue.getGameCode() + "】第【" + issue.getIssueNum() + "】期开奖结果发生异常", e);
        }
        return null;
    }
}