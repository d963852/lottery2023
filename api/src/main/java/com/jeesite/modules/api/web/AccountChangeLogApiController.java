/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.modules.api.web;

import cn.hutool.core.bean.BeanUtil;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.api.vo.AccountChangeLogVO;
import com.jeesite.modules.lotterycore.entity.AccountChangeLog;
import com.jeesite.modules.lotterycore.param.R;
import com.jeesite.modules.lotterycore.service.AccountChangeLogService;
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
@RequestMapping(value = "${adminPath}/api/accountChangeLog")
public class AccountChangeLogApiController extends BaseController {

    @Autowired
    private AccountChangeLogService accountChangeLogService;

    /**
     * 获取会员账变明细
     *
     * @return
     */
    @RequestMapping(value = "findList")
    public R findList(HttpServletRequest request, HttpServletResponse response) {
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


}