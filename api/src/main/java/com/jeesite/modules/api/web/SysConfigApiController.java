/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.modules.api.web;

import cn.hutool.core.util.StrUtil;
import com.jeesite.common.config.Global;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.api.utils.ParamUtils;
import com.jeesite.modules.lotterycore.common.exception.BizError;
import com.jeesite.modules.lotterycore.param.R;
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
@RequestMapping(value = "${adminPath}/api/sysConfig")
public class SysConfigApiController extends BaseController {

    /**
     * 获取字典数据
     *
     * @return
     */
    @RequestMapping(value = "getConfig")
    public R getConfig(String configName) {
        // 字符串安全过滤
        if (StrUtil.isBlankIfStr(ParamUtils.securityFilter(configName))) {
            return R.failure().message(BizError.参数异常.getMsg());
        }
        List<String> whiteList = new ArrayList<>();
        whiteList.add("lottery.member.bonus");
        whiteList.add("lottery.member.wage");
        whiteList.add("lottery.sys.basePrice");
        whiteList.add("lottery.member.rebate");
        whiteList.add("lottery.member.odds");
        if(!whiteList.contains(configName)){
            return R.failure().message(BizError.参数异常.getMsg());
        }

        return R.success().data(Double.parseDouble(Global.getConfig(configName, "0.00")));
    }
}