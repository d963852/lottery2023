/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.test;

import cn.hutool.core.date.DateUtil;
import com.jeesite.common.tests.BaseSpringContextTests;
import com.jeesite.modules.TaskApplication;
import com.jeesite.modules.lotterycore.constants.Constant;
import com.jeesite.modules.lotterycore.entity.Issue;
import com.jeesite.modules.lotterycore.service.IssueService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("dev")
@SpringBootTest(classes = TaskApplication.class)
public class STest extends BaseSpringContextTests {

    @Autowired
    private IssueService issueService;

    @Test
    public void testCheckWinBet() {
        Issue issueSC = new Issue();
        issueSC.setLotteryTime_lte(DateUtil.date());
        issueSC.setState(Constant.期号状态_已开奖);//找已开奖但未结算的
        issueSC.setGameCode("DCTX30");
        List<Issue> issueList = issueService.findList(issueSC);
        for (Issue issue: issueList) {
            issueService.checkWinBet(issue);
        }
    }

}
