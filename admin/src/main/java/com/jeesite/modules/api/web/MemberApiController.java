/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.modules.api.web;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.jeesite.common.codec.EncodeUtils;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.api.vo.AccountChangeLogVO;
import com.jeesite.modules.api.vo.WithdrawCardVO;
import com.jeesite.modules.lotterycore.common.exception.BizError;
import com.jeesite.modules.lotterycore.entity.AccountChangeLog;
import com.jeesite.modules.lotterycore.entity.WithdrawCard;
import com.jeesite.modules.lotterycore.param.R;
import com.jeesite.modules.lotterycore.service.AccountChangeLogService;
import com.jeesite.modules.lotterycore.service.WithdrawCardService;
import com.jeesite.modules.sys.entity.Member;
import com.jeesite.modules.sys.service.MemberService;
import com.jeesite.modules.sys.utils.DictUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
@RequestMapping(value = "${adminPath}/api/member")
public class MemberApiController extends BaseController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private AccountChangeLogService accountChangeLogService;
    @Autowired
    private WithdrawCardService withdrawCardService;

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

    /**
     * 获取会员账变明细
     *
     * @return
     */
    @RequestMapping(value = "findFundLogList")
    public R findFundLogList(HttpServletRequest request, HttpServletResponse response) {
        AccountChangeLog accountChangeLogSC = new AccountChangeLog();
        accountChangeLogSC.setUserId(UserUtils.getUser().getId());
        accountChangeLogSC.setPage(new Page<>(request, response));
        Page<AccountChangeLog> page = accountChangeLogService.findPage(accountChangeLogSC);
        List<AccountChangeLogVO> voList = new ArrayList<>();
        for (AccountChangeLog entity : page.getList()) {
            AccountChangeLogVO vo = new AccountChangeLogVO();
            BeanUtil.copyProperties(entity, vo);
            voList.add(vo);
        }

        Page<AccountChangeLogVO> pageVO = new Page<>();
        pageVO.setCount(page.getCount());
        pageVO.setPageNo(page.getPageNo());
        pageVO.setPageSize(page.getPageSize());
        pageVO.setList(voList);

        return R.success().data(pageVO);
    }

    /**
     * 获取会员提款卡信息
     *
     * @return
     */
    @RequestMapping(value = "getWithdrawCard")
    public R getWithdrawCard(String widthdrawCardId) {
        widthdrawCardId = EncodeUtils.sqlFilter(widthdrawCardId);
        widthdrawCardId = EncodeUtils.xssFilter(widthdrawCardId);
        WithdrawCard withdrawCard = withdrawCardService.get(widthdrawCardId);
        WithdrawCardVO vo = new WithdrawCardVO();
        BeanUtil.copyProperties(withdrawCard, vo);
        return R.success().data(vo);
    }

    /**
     * 获取会员提款卡信息列表
     *
     * @return
     */
    @RequestMapping(value = "findWithdrawCardList")
    public R findWithdrawCardList() {
        WithdrawCard withdrawCardSC = new WithdrawCard();
        withdrawCardSC.setUserId(UserUtils.getUser().getId());
        List<WithdrawCard> withdrawCardList = withdrawCardService.findList(withdrawCardSC);
        List<WithdrawCardVO> voList = new ArrayList<>();
        for (WithdrawCard source : withdrawCardList) {
            WithdrawCardVO vo = new WithdrawCardVO();
            BeanUtil.copyProperties(source, vo);
            voList.add(vo);
        }
        return R.success().data(voList);
    }

    /**
     * 保存提现卡信息
     */
    @PostMapping(value = "saveWithdrawCard")
    @ResponseBody
    public R saveWithdrawCard(@Validated WithdrawCard withdrawCard) {
        // 判断用户提现卡是否超限
        if (withdrawCard.getIsNewRecord()) {
            WithdrawCard withdrawCardSC = new WithdrawCard();
            withdrawCardSC.setUserId(UserUtils.getUser().getId());
            List<WithdrawCard> withdrawCardList = withdrawCardService.findList(withdrawCardSC);
            if (withdrawCardList.size() >= 5) {
                return R.failure().message(BizError.每位客户最多只能有5个提现渠道.getMsg());
            }
        }

        if (StrUtil.isBlankIfStr(withdrawCard.getCardType())
                || StrUtil.isBlankIfStr(DictUtils.getDictValue("lottery_withdraw_card_type", withdrawCard.getCardType(), ""))) {
            return R.failure().message("提现渠道不存在");
        }
        if (StrUtil.isBlankIfStr(withdrawCard.getBankName())
                || StrUtil.isBlankIfStr(DictUtils.getDictValue("lottery_sys_bank_list", withdrawCard.getBankName(), ""))) {
            return R.failure().message("所属银行不存在");
        }
        if (StrUtil.isBlankIfStr(withdrawCard.getBankAccount())) {
            return R.failure().message("账号（卡号）不能为空");
        }
        if (!ReUtil.isMatch("[A-Za-z0-9]+", withdrawCard.getBankAccount())) {
            return R.failure().message("账号（卡号）只能是英文和数字");
        }
        if ("银行卡".equals(withdrawCard.getCardType())) {
            if (StrUtil.isBlankIfStr(withdrawCard.getBankAccountName())) {
                return R.failure().message("开户人不能为空");
            }
            if (!ReUtil.isMatch("[\\u4E00-\\u9FA5A-Za-z ]*", withdrawCard.getBankAccountName())) {
                return R.failure().message("开户人姓名只能是中文、英文和空格");
            }
        }

        withdrawCard.setUserId(UserUtils.getUser().getId());
        withdrawCard.setUserAccount(UserUtils.getUser().getUserName());
        withdrawCardService.save(withdrawCard);
        return R.success();
    }


}