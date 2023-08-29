/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.modules.api.web;

import cn.hutool.core.bean.BeanUtil;
import com.jeesite.common.config.Global;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.api.vo.GameVO;
import com.jeesite.modules.api.vo.PlayMethodGroupVO;
import com.jeesite.modules.api.vo.PlayMethodVO;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.lotterycore.common.exception.BizException;
import com.jeesite.modules.lotterycore.constants.Constant;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.entity.Issue;
import com.jeesite.modules.lotterycore.entity.PlayMethod;
import com.jeesite.modules.lotterycore.entity.PlayMethodGroup;
import com.jeesite.modules.lotterycore.param.R;
import com.jeesite.modules.lotterycore.service.*;
import com.jeesite.modules.sys.service.MemberService;
import com.jeesite.modules.sys.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示实例Controller
 *
 * @author ThinkGem
 * @version 2018-03-24
 */
@RestController
@RequestMapping(value = "${adminPath}/api/lottery")
public class LotteryApiController extends BaseController {

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
    @Autowired
    private MemberService memberService;

    @Autowired
    private BetService betService;


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
     * 获取游戏列表
     *
     * @param gameGroup
     * @return
     */
    @RequestMapping(value = "findGameList")
    public R findGameList(String gameGroup, Model model) {
        Game gameSC = new Game();
        List<GameVO> gameVoList = new ArrayList<>();
        if ("hot".equals(gameGroup)) {
            //热门
            gameSC.setHotGameFlag(Global.YES);
            List<Game> gameList = gameService.findList(gameSC);
            for (Game game : gameList) {
                GameVO gameVo = new GameVO();
                BeanUtil.copyProperties(game, gameVo);
                // 游戏图片
                List<FileUpload> gameImageList = FileUploadUtils.findFileUpload(game.getId(), "game_image");
                if (gameImageList.size() > 0) {
                    gameVo.setImgUrl(gameImageList.get(0).getFileUrl());
                }
                gameVoList.add(gameVo);
            }
        } else if ("fav".equals(gameGroup)) {
            //TODO 最爱

        } else {
            // 啥类型都不是
            return R.failure();
        }
        return R.success().data(gameVoList);
    }

    /**
     * 获取游戏详细信息
     *
     * @return
     */
    @RequestMapping(value = "getGameInfo")
    public R getGameInfo(String gameCode) {
        try {
            Game game = gameService.validGameByGameCode(gameCode);
            GameVO gameVo = new GameVO();
            BeanUtil.copyProperties(game, gameVo);
            // 游戏图片
            List<FileUpload> gameImageList = FileUploadUtils.findFileUpload(game.getId(), "game_image");
            if (gameImageList.size() > 0) {
                gameVo.setImgUrl(gameImageList.get(0).getFileUrl());
            }
            return R.success().data(gameVo);
        } catch (BizException bizException) {
            return R.failure().message(bizException.getMessage());
        }
    }

    /**
     * 根据游戏类型获取玩法组和玩法
     *
     * @return
     */
    @RequestMapping(value = "findPlayMethodList")
    public R findPlayMethodList(String gameCode) {
        List<PlayMethodGroupVO> playMethodGroupVoList = new ArrayList<>();
        try {
            Game game = gameService.validGameByGameCode(gameCode);
            String gameCategory = game.getGameCategory();
            // 根据游戏分类获取对应的playMethodGroup
            PlayMethodGroup playMethodGroupSC = new PlayMethodGroup();
            playMethodGroupSC.setGameCategory(gameCategory);
            List<PlayMethodGroup> playMethodGroupList = playMethodGroupService.findList(playMethodGroupSC);
            for (PlayMethodGroup playMethodGroup : playMethodGroupList) {
                PlayMethodGroupVO playMethodGroupVo = new PlayMethodGroupVO();
                BeanUtil.copyProperties(playMethodGroup, playMethodGroupVo);
                // 获取分组下的playMethod
                PlayMethod playMethodSC = new PlayMethod();
                playMethodSC.setGroupId(playMethodGroup.getId());
                List<PlayMethod> playMethodList = playMethodService.findList(playMethodSC);
                List<PlayMethodVO> playMethodVoList = new ArrayList<>();
                for (PlayMethod playMethod : playMethodList) {
                    PlayMethodVO playMethodVo = new PlayMethodVO();
                    BeanUtil.copyProperties(playMethod, playMethodVo);
                    playMethodVoList.add(playMethodVo);
                }
                playMethodGroupVo.setPlayMethodList(playMethodVoList);
                playMethodGroupVoList.add(playMethodGroupVo);
            }
            return R.success().data(playMethodGroupVoList);
        } catch (BizException bizException) {
            return R.failure().message(bizException.getMessage());
        }
    }

    /**
     * 获取彩票基准销售单价
     *
     * @return
     */
    @RequestMapping(value = "getBasePrice")
    public R getBasePrice() {
        return R.success().data(Constant.系统_单注销售单价);
    }

    /**
     * 获取系统投注返点上限
     *
     * @return
     */
    @RequestMapping(value = "getSysMaxRebate")
    public R getSysMaxRebate() {
        return R.success().data(Constant.系统_会员_返点上限);
    }

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