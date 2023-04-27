package com.jeesite.modules.lotterycore.service.game;

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
import com.jeesite.modules.lotterycore.constants.Constant;
import com.jeesite.modules.lotterycore.dao.IssueDao;
import com.jeesite.modules.lotterycore.entity.Issue;
import com.jeesite.modules.lotterycore.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;

/**
 * 彩票期号Service
 *
 * @author DUKE
 * @version 2023-04-23
 */
@Service
public class QiqffcService extends CrudService<IssueDao, Issue> {

    @Autowired
    private IssueService issueService;

    @Autowired
    private GenerateLocalLotteryNumberService generateLocalLotteryNumberService;

    /**
     * 开奖
     *
     * @param issue 要开奖的游戏期号
     */
    public void syncLotteryNumber(@NotNull Issue issue) {
        if (!issue.getState().equals(Constant.期号状态_未开奖)) {
            logger.error("本期已开奖，跳过" + issue.toString());
            return;
        }
        Issue latestWithInterface = getLotteryNumberResultFromMultipleWays(issue);
        if (latestWithInterface == null) {
            return;
        }
        issue.setLotteryNum(latestWithInterface.getLotteryNum());
        //TODO 风控检测

        issue.setLotterySource(latestWithInterface.getLotterySource());
        issue.setState(Constant.期号状态_已开奖);
        issueService.save(issue);
    }

    /**
     * 多线程多渠道开奖，最后从本地开奖
     *
     * @return 开奖结果
     */
    private Issue getLotteryNumberResultFromMultipleWays(Issue issue) {
        List<Issue> issueList = ListUtils.newArrayList();
        issueList.add(getLotteryNumberWithDuocai(issue));
        issueList.add(getLotteryNumberLocal(issue));
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
    private Issue getLotteryNumberWithDuocai(Issue issue) {
        Issue resultIssue = new Issue();
        String url = StrUtil.format(Global.getProperty("duocai.url", "http://3vip.manycai.com/{}/{}/{}.json"), Global.getProperty("duocai.key", "K2641046fc0438e"), issue.getIssueNum(), issue.getGameCode());
        try {
            String duocaiData = HttpUtil.get(url);
            int retryTimes = 0;
            do {
                retryTimes++;
                Thread.sleep(retryTimes * 1000);
                duocaiData = HttpUtil.get(url);
            } while (!duocaiData.startsWith("[{\"issue\":\"" + issue.getIssueNum()) && retryTimes < 5);
            if (retryTimes == 5) {
                throw new BizException(BizError.参数异常.getCode(), "多彩网不可用");
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
        Issue resultIssue = new Issue();
        try {
            resultIssue.setLotteryNum(generateLocalLotteryNumberService.generateLocalLotteryNumber(issue));
            resultIssue.setLotterySource("local");
            resultIssue.setIssueNumInner(999L);
            return resultIssue;
        } catch (Exception e) {
            logger.error("本地开彩：【" + issue.getGameCode() + "】第【" + issue.getIssueNum() + "】期开奖结果发生异常", e);
        }
        return null;
    }
}