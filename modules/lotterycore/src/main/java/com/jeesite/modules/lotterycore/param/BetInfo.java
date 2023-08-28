/**
  * Copyright 2023 bejson.com 
  */
package com.jeesite.modules.lotterycore.param;

import lombok.Data;

@Data
public class BetInfo {
    private String betNumber;
    private int betCount;
    private int finalCount;
    private int betMultiplier;
    private BetUnit betUnit;
    private double betAmount;
    private double rebate;
    private double bonusAmount;
    private double rebateAmount;
    private String playMethodId;
}