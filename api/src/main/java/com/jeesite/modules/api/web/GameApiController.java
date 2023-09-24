/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.modules.api.web;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.jeesite.common.config.Global;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.api.utils.ParamUtils;
import com.jeesite.modules.api.vo.GameVO;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.lotterycore.common.exception.BizError;
import com.jeesite.modules.lotterycore.common.exception.BizException;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.param.R;
import com.jeesite.modules.lotterycore.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "${adminPath}/api/game")
public class GameApiController extends BaseController {

    @Autowired
    private GameService gameService;

    /**
     * 获取游戏列表
     *
     * @param gameType
     * @return
     */
    @RequestMapping(value = "findListByType")
    public R findListByType(String gameType) {
        // 字符串安全过滤
        if (StrUtil.isBlankIfStr(ParamUtils.securityFilter(gameType))) {
            return R.failure().message(BizError.参数异常.getMsg());
        }

        Game gameSC = new Game();
        List<GameVO> gameVoList = new ArrayList<>();
        if ("hot".equals(gameType)) {
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
        } else if ("fav".equals(gameType)) {
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
    @RequestMapping(value = "getByCode")
    public R getByCode(String gameCode) {
        // 字符串安全过滤
        if (StrUtil.isBlankIfStr(ParamUtils.securityFilter(gameCode))) {
            return R.failure().message(BizError.参数异常.getMsg());
        }

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

}