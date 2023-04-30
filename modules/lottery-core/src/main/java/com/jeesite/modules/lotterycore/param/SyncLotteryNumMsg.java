package com.jeesite.modules.lotterycore.param;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SyncLotteryNumMsg {
    private String gameCode;
    private String issueNum;
    private Integer retries;
}
