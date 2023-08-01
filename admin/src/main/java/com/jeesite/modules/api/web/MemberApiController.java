/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.modules.api.web;

import cn.hutool.core.date.DateUtil;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.param.R;
import com.jeesite.modules.lotterycore.service.GameService;
import com.jeesite.modules.lotterycore.service.IssueService;
import com.jeesite.modules.lotterycore.service.LotteryWebMenuService;
import com.jeesite.modules.api.service.GameBettingWebSocketService;
import com.jeesite.modules.sys.entity.Member;
import com.jeesite.modules.sys.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 演示实例Controller
 *
 * @author ThinkGem
 * @version 2018-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/api/member")
public class MemberApiController extends BaseController {
    @Autowired
    private GameService gameService;
    @Autowired
    private LotteryWebMenuService lotteryWebMenuService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private IssueService issueService;
    @Autowired
    private GameBettingWebSocketService gameBettingWebSocketService;

    /**
     * 获取数据
     */
    @ModelAttribute
    public Game get(String id, boolean isNewRecord) {
        return gameService.get(id, isNewRecord);
    }

    /**
     * 获取会员余额
     */
    @RequestMapping(value = "getBalance/{memberId}")
    @ResponseBody
    public R getMemberBalance(@PathVariable("memberId") String memberId) {
        Member member = memberService.get(memberId);
        if (null != member) {
            return R.success().data(member.getBalance());
        } else {
            return R.failure().data(0.0d);
        }
    }

    /**
     * 获取游戏当前期号
     */
    @RequestMapping(value = "getCurrentIssueNumber/{gameCode}")
    @ResponseBody
    public R getCurrentIssueNumber(@PathVariable("gameCode") String gameCode) {
        Game game = gameService.getGameByCode(gameCode);
        Map<String, String> result = new HashMap<>();
        if (game != null) {
            result.put("currentIssueNumber", game.getCurrentIssueNumber());
            result.put("currentIssueEndTime", DateUtil.format(game.getCurrentIssueEndTime(), "yyyy-MM-dd HH:mm:ss"));
            result.put("lastIssueNumber", game.getLastIssueNumber());
            result.put("lastIssueLotteryNumber", game.getLastIssueLotteryNumber());
        }
        return R.success().data(result);
    }


}