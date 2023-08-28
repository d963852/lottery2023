/**
  * Copyright 2023 bejson.com 
  */
package com.jeesite.modules.lotterycore.param;
import lombok.Data;

import java.util.List;

@Data
public class BetRequest {
    private String gameCode;
    private String issue;
    private List<BetInfo> betList;
}