/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.modules.api.web;

import com.jeesite.common.web.BaseController;
import com.jeesite.modules.lotterycore.common.exception.BizException;
import com.jeesite.modules.lotterycore.param.R;
import com.jeesite.modules.lotterycore.service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 演示实例Controller
 *
 * @author ThinkGem
 * @version 2018-03-24
 */
@RestController
@RequestMapping(value = "${adminPath}/api/bet")
public class BetApiController extends BaseController {
    @Autowired
    private BetService betService;

    /**
     * 下注
     *
     * @return
     */
    @RequestMapping(value = "bet")
    public R bet(String betRequest) {
        try {
            return betService.bet(betRequest);
        } catch (BizException e) {
            return R.failure().message(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.failure().message("系统异常，请重新投注");
        }
    }

}