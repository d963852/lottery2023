/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.modules.lotteryweb.web;

import com.jeesite.common.web.BaseController;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.entity.Issue;
import com.jeesite.modules.lotterycore.entity.PlayMethod;
import com.jeesite.modules.lotterycore.entity.PlayMethodGroup;
import com.jeesite.modules.lotterycore.service.*;
import com.jeesite.modules.sys.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
    @Autowired
    private IssueService issueService;
    @Autowired
    private LotteryWebMenuService lotteryWebMenuService;
    @Autowired
    private PlayMethodGroupService playMethodGroupService;
    @Autowired
    private PlayMethodService playMethodService;

    /**
     * 获取数据
     */
    @ModelAttribute
    public Game get(String id, boolean isNewRecord) {
        return gameService.get(id, isNewRecord);
    }

    /**
     * 投注页面
     */
    @RequestMapping(value = "betting")
    public String betting(String gameId, Model model) {

        model.addAttribute("menuList", lotteryWebMenuService.findListFromCache());
        model.addAttribute("gameList", gameService.findListFromCache());
        model.addAttribute("menuGroupList", DictUtils.getDictList("lottery_menu_group"));

        Game game = gameService.get(gameId);
        if (game == null) {
            return "error/404";
        }

        // 开奖历史
        List<Issue> issueHistoryList = issueService.findHistory(game.getGameCode(), 10);
        model.addAttribute("issueHistoryList", issueHistoryList);

        // 玩法组
        PlayMethodGroup playMethodGroupSC = new PlayMethodGroup();
        playMethodGroupSC.setGameCategory(game.getGameCategoryId());
        playMethodGroupSC.sqlMap().getOrder().setOrderBy("sort asc");
        List<PlayMethodGroup> playMethodGroupList = playMethodGroupService.findList(playMethodGroupSC);
        if (playMethodGroupList.size() > 0) {
            model.addAttribute("playMethodGroupList", playMethodGroupList);

            PlayMethod playMethodSC = new PlayMethod();
            playMethodSC.setGroupId(playMethodGroupList.get(0).getId());
            playMethodSC.sqlMap().getOrder().setOrderBy("sort asc");
            List<PlayMethod> playMethodList = playMethodService.findList(playMethodSC);
            model.addAttribute("playMethodList", playMethodList);

            model.addAttribute("playMethod", playMethodList.get(0));

        }

        model.addAttribute("game", game);
        return "modules/lotteryweb/game/gameIndex";
    }

    /**
     * 显示玩法组
     *
     * @param groupId
     * @param model
     * @return
     */
    @RequestMapping(value = "playMethodGroup")
    public String playMethodGroup(String groupId, Model model) {
        PlayMethod playMethodSC = new PlayMethod();
        playMethodSC.setGroupId(groupId);
        List<PlayMethod> playMethodList = playMethodService.findList(playMethodSC);
        model.addAttribute("playMethodList", playMethodList);
        model.addAttribute("playMethod", playMethodList.get(0));
        return "modules/lotteryweb/game/playMethodGroup";
    }

    /**
     * 显示玩法组里的玩法页面
     *
     * @param tpl
     * @param model
     * @return
     */
    @RequestMapping(value = "tpl/{tpl}")
    public String playMethodTpl(@PathVariable("tpl") String tpl, String playMethodId, String gameId,Model model) {
        PlayMethod playMethod = playMethodService.get(playMethodId);
        Game game = gameService.get(gameId);
        model.addAttribute("playMethod", playMethod);
        model.addAttribute("game", game);
        return "modules/lotteryweb/game/gid/" + tpl;
    }


}