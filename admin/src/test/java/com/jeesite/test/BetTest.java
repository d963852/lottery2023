package com.jeesite.test;

import com.jeesite.common.tests.BaseSpringContextTests;
import com.jeesite.modules.AdminApplication;
import com.jeesite.modules.lotterycore.service.BetService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest(classes = AdminApplication.class)
public class BetTest extends BaseSpringContextTests {

    @Autowired
    private BetService betService;

    @Test
    public void testBet() {
		String betRequest1 = "{\"gameCode\":\"DCTX30\",\"issue\":\"20230829-1148\",\"betList\":[{\"betNumber\":\"1,3,5,7,9|0,1,3,4,6,7,8,9|3|2,0|1\",\"betCount\":80,\"finalCount\":80,\"betMultiplier\":1,\"betUnit\":{\"value\":1,\"label\":\"元\"},\"betAmount\":160,\"rebate\":0,\"bonusAmount\":198000,\"rebateAmount\":0,\"playMethodId\":\"1671041373932040235\"},{\"betNumber\":\"1,3,5,7,9|0,1,2,3,4,5,6,7,8,9|3|2|1\",\"betCount\":50,\"finalCount\":50,\"betMultiplier\":1,\"betUnit\":{\"value\":1,\"label\":\"元\"},\"betAmount\":100,\"rebate\":0,\"bonusAmount\":198000,\"rebateAmount\":0,\"playMethodId\":\"1671041373932040235\"}]}";

        try {
            betService.bet(betRequest1);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
