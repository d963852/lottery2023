/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.modules.api.web;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.jeesite.common.codec.EncodeUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.api.vo.WithdrawCardVO;
import com.jeesite.modules.lotterycore.common.exception.BizError;
import com.jeesite.modules.lotterycore.entity.WithdrawCard;
import com.jeesite.modules.lotterycore.param.R;
import com.jeesite.modules.lotterycore.service.AccountChangeLogService;
import com.jeesite.modules.lotterycore.service.WithdrawCardService;
import com.jeesite.modules.sys.service.MemberService;
import com.jeesite.modules.sys.utils.DictUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
@RequestMapping(value = "${adminPath}/api/withdrawCard")
public class WithdrawCardApiController extends BaseController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private AccountChangeLogService accountChangeLogService;
    @Autowired
    private WithdrawCardService withdrawCardService;


    /**
     * 获取会员提款卡信息
     *
     * @return
     */
    @RequestMapping(value = "get")
    public R get(String id) {
        id = EncodeUtils.sqlFilter(id);
        id = EncodeUtils.xssFilter(id);
        WithdrawCard withdrawCard = withdrawCardService.get(id);
        if (!UserUtils.getUser().getId().equals(withdrawCard.getUserId())) {
            return R.failure().message("只能查看自己的提现信息");
        }
        WithdrawCardVO vo = new WithdrawCardVO();
        BeanUtil.copyProperties(withdrawCard, vo);
        return R.success().data(vo);
    }

    /**
     * 获取会员提款卡信息列表
     *
     * @return
     */
    @RequestMapping(value = "findList")
    public R findList() {
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
    @PostMapping(value = "save")
    @ResponseBody
    public R save(@Validated WithdrawCard withdrawCard) {
        // 判断用户提现卡是否超限
        if (withdrawCard.getIsNewRecord()) {
            WithdrawCard withdrawCardSC = new WithdrawCard();
            withdrawCardSC.setUserId(UserUtils.getUser().getId());
            List<WithdrawCard> withdrawCardList = withdrawCardService.findList(withdrawCardSC);
            if (withdrawCardList.size() >= 5) {
                return R.failure().message(BizError.每位客户最多只能有5个提现渠道.getMsg());
            }
        }

        if (StrUtil.isBlankIfStr(withdrawCard.getBankAccount())) {
            return R.failure().message("账号（卡号）不能为空");
        }

        if ("银行卡".equals(withdrawCard.getCardType())) {
            if (!ReUtil.isMatch("[0-9]+", withdrawCard.getBankAccount())) {
                return R.failure().message("银行卡号只能是数字");
            }
            if (StrUtil.isBlankIfStr(withdrawCard.getBankName())) {
                return R.failure().message("所属银行不能为空");
            }
            if (StrUtil.isBlankIfStr(DictUtils.getDictValue("lottery_sys_bank_list", withdrawCard.getBankName(), ""))) {
                return R.failure().message("所属银行不存在");
            }
            if (StrUtil.isBlankIfStr(withdrawCard.getBankAccountName())) {
                return R.failure().message("开户人不能为空");
            }
            if (!ReUtil.isMatch("^[\\u4E00-\\u9FA5A-Za-z ]*$", withdrawCard.getBankAccountName())) {
                return R.failure().message("开户人姓名只能是中文、英文和空格");
            }
        } else if ("USDT-TRC20".equals(withdrawCard.getCardType())) {
            if (!ReUtil.isMatch("^[A-Za-z0-9]+$", withdrawCard.getBankAccount())) {
                return R.failure().message("USDT-TRC20地址只能包含字母和数字");
            }
        } else if ("支付宝".equals(withdrawCard.getCardType())) {
            if (!ReUtil.isMatch("^(?:1[3-9]\\d{9}|[a-zA-Z\\d._-]*\\@[a-zA-Z\\d.-]{1,10}\\.[a-zA-Z\\d]{1,20})$", withdrawCard.getBankAccount())) {
                return R.failure().message("支付宝账号只能是手机号或者邮箱");
            }
        } else if ("微信".equals(withdrawCard.getCardType())) {
            if (!ReUtil.isMatch("^[a-zA-Z]([-_a-zA-Z0-9]{5,19})+$", withdrawCard.getBankAccount())) {
                return R.failure().message("微信账号只能包含数字、字母、减号和下划线");
            }
        }

        withdrawCard.setUserId(UserUtils.getUser().getId());
        withdrawCard.setUserAccount(UserUtils.getUser().getUserName());
        withdrawCardService.save(withdrawCard);
        return R.success();
    }

    /**
     * 获取会员提款卡信息列表
     *
     * @return
     */
    @RequestMapping(value = "delete")
    public R delete(String id) {
        id = EncodeUtils.sqlFilter(id);
        id = EncodeUtils.xssFilter(id);
        WithdrawCard withdrawCard = withdrawCardService.get(id);
        if (!UserUtils.getUser().getId().equals(withdrawCard.getUserId())) {
            return R.failure().message("无权删除此项信息");
        }
        withdrawCardService.delete(withdrawCard);
        return R.success();
    }


}