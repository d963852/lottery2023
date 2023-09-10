/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.modules.api.web;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.api.utils.ParamUtils;
import com.jeesite.modules.api.vo.PlayMethodGroupVO;
import com.jeesite.modules.api.vo.PlayMethodVO;
import com.jeesite.modules.lotterycore.common.exception.BizError;
import com.jeesite.modules.lotterycore.common.exception.BizException;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.entity.PlayMethod;
import com.jeesite.modules.lotterycore.entity.PlayMethodGroup;
import com.jeesite.modules.lotterycore.param.R;
import com.jeesite.modules.lotterycore.service.*;
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
@RequestMapping(value = "${adminPath}/api/playMethod")
public class PlayMethodApiController extends BaseController {

    @Autowired
    private GameService gameService;
    @Autowired
    private PlayMethodGroupService playMethodGroupService;
    @Autowired
    private PlayMethodService playMethodService;




    /**
     * 根据游戏类型获取玩法组和玩法
     *
     * @return
     */
    @RequestMapping(value = "findListByGameCode")
    public R findListByGameCode(String gameCode) {
        // 字符串安全过滤
        if (StrUtil.isBlankIfStr(ParamUtils.securityFilter(gameCode))) {
            return R.failure().message(BizError.参数异常.getMsg());
        }

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

}