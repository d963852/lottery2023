/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.modules.xf.web;

import com.jeesite.common.web.BaseController;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 演示实例Controller
 *
 * @author ThinkGem
 * @version 2018-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/lottery")
public class LotteryController extends BaseController {

    @Autowired
    private GameService gameService;

    /**
     * 投注页面
     */
    @RequestMapping(value = "betting")
    public String betting(String gameId, Model model) {
        Game game = gameService.get(gameId);
        if (game == null) {
            return "error/404";
        }
        model.addAttribute("game",game);
        return "modules/web/lottery/betting";
    }

}