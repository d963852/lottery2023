/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.modules.api.web;

import com.jeesite.common.web.BaseController;
import com.jeesite.modules.lotterycore.param.R;
import com.jeesite.modules.sys.entity.Member;
import com.jeesite.modules.sys.service.MemberService;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private MemberService memberService;

    /**
     * 获取会员余额
     */
    @RequestMapping(value = "getBalance")
    @ResponseBody
    public R getMemberBalance() {
        Member currentUser = memberService.get(UserUtils.getUser().getId());
        if (null != currentUser) {
            return R.success().data(currentUser.getBalance());
        } else {
            return R.failure();
        }
    }

    /**
     * 获取会员投注返点
     */
    @RequestMapping(value = "getRebate")
    @ResponseBody
    public R getRebate() {
        Member currentUser = memberService.get(UserUtils.getUser().getId());
        if (null != currentUser) {
            return R.success().data(currentUser.getRebate());
        } else {
            return R.failure();
        }
    }

}