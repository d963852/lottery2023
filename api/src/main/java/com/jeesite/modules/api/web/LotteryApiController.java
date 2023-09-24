/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.modules.api.web;

import cn.hutool.core.bean.BeanUtil;
import com.jeesite.common.codec.EncodeUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.api.vo.*;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.lotterycore.common.exception.BizError;
import com.jeesite.modules.lotterycore.common.exception.BizException;
import com.jeesite.modules.lotterycore.constants.Constant;
import com.jeesite.modules.lotterycore.entity.*;
import com.jeesite.modules.lotterycore.param.R;
import com.jeesite.modules.lotterycore.service.*;
import com.jeesite.modules.lotterycore.vo.IssueVO;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.service.MemberService;
import com.jeesite.modules.sys.utils.DictUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private BetOrderService betOrderService;


    /**
     * 投注页面
     */
    @RequestMapping(value = "betting")
    public String betting(String gameId, Model model) {
        // 字符串安全过滤
        gameId = EncodeUtils.xssFilter(gameId);
        gameId = EncodeUtils.sqlFilter(gameId);

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
        // 字符串安全过滤
        gameGroup = EncodeUtils.xssFilter(gameGroup);
        gameGroup = EncodeUtils.sqlFilter(gameGroup);
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
        // 字符串安全过滤
        gameCode = EncodeUtils.xssFilter(gameCode);
        gameCode = EncodeUtils.sqlFilter(gameCode);

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
        // 字符串安全过滤
        gameCode = EncodeUtils.xssFilter(gameCode);
        gameCode = EncodeUtils.sqlFilter(gameCode);

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

    /**
     * 获取下注历史
     *
     * @return
     */
    @RequestMapping(value = "findBetHistoryList")
    public R findBetHistoryList(BetOrder betOrder, HttpServletRequest request, HttpServletResponse response) {
        betOrder.setPage(new Page<>(request, response));
        betOrder.setUserId(UserUtils.getUser().getId());
        Page<BetOrder> page = betOrderService.findPage(betOrder);
        List<BetOrderVO> betOrderVOList = new ArrayList<>();
        for (BetOrder order : page.getList()) {
            BetOrderVO vo = new BetOrderVO();
            BeanUtil.copyProperties(order, vo);
            betOrderVOList.add(vo);
        }

        Page<BetOrderVO> pageVO = new Page<>();
        pageVO.setCount(page.getCount());
        pageVO.setPageNo(page.getPageNo());
        pageVO.setPageSize(page.getPageSize());
        pageVO.setList(betOrderVOList);

        return R.success().data(pageVO);
    }

    /**
     * 获取最近30期开奖历史
     *
     * @return
     */
    @RequestMapping(value = "findIssueHistoryList")
    public R findIssueHistoryList(String gameCode) {
        // 字符串安全过滤
        gameCode = EncodeUtils.xssFilter(gameCode);
        gameCode = EncodeUtils.sqlFilter(gameCode);

        Game game = gameService.validGameByGameCode(gameCode);
        if (game == null || game.getIsNewRecord()) {
            return R.failure().message(BizError.游戏不存在.getMsg());
        }
        Issue issueSC = new Issue();
        issueSC.setGameCode(gameCode);
        issueSC.setStateDrawed();//查找所有非未开奖的状态
        issueSC.setPage(new Page(1, 30)); // 查前30条数据
        List<Issue> issueList = issueService.findList(issueSC);
        List<IssueVO> issueVOList = new ArrayList<>();
        for (Issue issue : issueList) {
            IssueVO vo = new IssueVO();
            BeanUtil.copyProperties(issue, vo);
            issueVOList.add(vo);
        }
        return R.success().data(issueVOList);
    }


    /**
     * 获取下注历史
     *
     * @return
     */
    @RequestMapping(value = "cancelBetOrder")
    public R cancelBetOrder(String orderId) {
        // 字符串安全过滤
        orderId = EncodeUtils.xssFilter(orderId);
        orderId = EncodeUtils.sqlFilter(orderId);
        BetOrder betOrder = betOrderService.get(orderId);
        if (betOrder == null || betOrder.getIsNewRecord()) {
            return R.failure().message(BizError.投注订单不存在.getMsg());
        }
        if (UserUtils.getUser().getId() != betOrder.getUserId()) {
            // 只能撤销自己的
            return R.failure().message(BizError.无权撤销投注订单.getMsg());
        }
        if (!betOrder.getBizStatus().equals(Constant.投注订单状态_未开奖)) {
            // 只能撤单未开奖的
            return R.failure().message(BizError.投注订单已开奖无法撤销.getMsg());
        }
        // 撤单
        betOrder.setBizStatus(Constant.投注订单状态_已撤单);
        betOrderService.save(betOrder);
        return R.success();
    }

    /**
     * 获取下注历史
     *
     * @return
     */
    @RequestMapping(value = "findDictList")
    public R findDictList(String dictType) {
        // 字符串安全过滤
        dictType = EncodeUtils.xssFilter(dictType);
        dictType = EncodeUtils.sqlFilter(dictType);
        // 仅允许指定的字典类型
        List<String> allowDictTypeList = new ArrayList<>();
        allowDictTypeList.add("lottery_withdraw_card_type");//提现卡类型
        allowDictTypeList.add("lottery_sys_bank_list");//银行卡列表
        if(!allowDictTypeList.contains(dictType)){
            return R.failure().message("不允许查询的字典类型");
        }

        List<DictData> dictList = DictUtils.getDictList(dictType);
        List<DictVO> dictVOList = new ArrayList<>();
        for (DictData source: dictList) {
            DictVO vo = new DictVO();
            vo.setLabel(source.getDictLabel());
            vo.setValue(source.getDictValue());
            dictVOList.add(vo);
        }
        return R.success().data(dictVOList);
    }


}