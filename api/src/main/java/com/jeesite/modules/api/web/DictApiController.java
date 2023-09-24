/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.modules.api.web;

import com.jeesite.common.codec.EncodeUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.api.vo.DictVO;
import com.jeesite.modules.lotterycore.param.R;
import com.jeesite.modules.lotterycore.service.*;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.service.MemberService;
import com.jeesite.modules.sys.utils.DictUtils;
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
@RequestMapping(value = "${adminPath}/api/dict")
public class DictApiController extends BaseController {

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
     * 获取数据字典内容
     *
     * @return
     */
    @RequestMapping(value = "findList")
    public R findList(String dictType) {
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