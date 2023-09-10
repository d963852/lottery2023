/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.modules.api.web;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.api.utils.ParamUtils;
import com.jeesite.modules.api.vo.BetOrderVO;
import com.jeesite.modules.lotterycore.common.exception.BizError;
import com.jeesite.modules.lotterycore.constants.Constant;
import com.jeesite.modules.lotterycore.entity.BetOrder;
import com.jeesite.modules.lotterycore.param.R;
import com.jeesite.modules.lotterycore.service.AccountChangeLogService;
import com.jeesite.modules.lotterycore.service.BetOrderService;
import com.jeesite.modules.sys.entity.Member;
import com.jeesite.modules.sys.service.MemberService;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "${adminPath}/api/betOrder")
public class BetOrderApiController extends BaseController {
    @Autowired
    private BetOrderService betOrderService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AccountChangeLogService accountChangeLogService;

    /**
     * 获取投注订单历史
     *
     * @return
     */
    @RequestMapping(value = "findHistoryList")
    public R findHistoryList(HttpServletRequest request, HttpServletResponse response) {
        BetOrder betOrder = new BetOrder();
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
     * 撤单
     *
     * @return
     */
    @RequestMapping(value = "cancel")
    public R cancel(String orderId) {
        // 字符串安全过滤
        if (StrUtil.isBlankIfStr(ParamUtils.securityFilter(orderId))) {
            return R.failure().message(BizError.参数异常.getMsg());
        }

        BetOrder betOrder = betOrderService.get(orderId);
        if (betOrder == null || betOrder.getIsNewRecord()) {
            return R.failure().message(BizError.投注订单不存在.getMsg());
        }
        if (!UserUtils.getUser().getId().equals(betOrder.getUserId())) {
            // 只能撤销自己的
            return R.failure().message(BizError.无权撤销投注订单.getMsg());
        }
        if (!betOrder.getBizStatus().equals(Constant.投注订单状态_未开奖)) {
            // 只能撤单未开奖的
            return R.failure().message(BizError.投注订单已开奖无法撤销.getMsg());
        }
        // 撤单
        betOrder.setBizStatus(Constant.投注订单状态_已撤单);

        // 退款
        Member currentUser = memberService.get(UserUtils.getUser().getId());
        // 开始变动余额
        double newBalance = currentUser.getBalance();//原始余额
        // 投注扣款
        double changeAmount = betOrder.getBetAmount();
        newBalance += changeAmount;
        // 记录账变日志
        accountChangeLogService.add(currentUser,
                changeAmount,
                newBalance,
                Constant.账变日志类型_入账_撤单返还投注金额,
                Constant.操作人_系统自动,
                BetOrder.class.getName(),
                betOrder.getIssueId(),
                "");

        // 客户账变完毕，保存余额
        currentUser.setBalance(NumberUtil.round(newBalance, 2).doubleValue());
        memberService.save(currentUser);

        betOrderService.save(betOrder);
        return R.success();
    }

}