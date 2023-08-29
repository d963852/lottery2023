package com.jeesite.modules.lotterycore.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.lotterycore.dao.AccountChangeLogDao;
import com.jeesite.modules.lotterycore.entity.AccountChangeLog;
import com.jeesite.modules.sys.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 账变日志Service
 *
 * @author thinkgem
 * @version 2023-08-26
 */
@Service
public class AccountChangeLogService extends CrudService<AccountChangeLogDao, AccountChangeLog> {

    /**
     * 获取单条数据
     *
     * @param accountChangeLog
     * @return
     */
    @Override
    public AccountChangeLog get(AccountChangeLog accountChangeLog) {
        return super.get(accountChangeLog);
    }

    /**
     * 查询分页数据
     *
     * @param accountChangeLog 查询条件
     * @param accountChangeLog page 分页对象
     * @return
     */
    @Override
    public Page<AccountChangeLog> findPage(AccountChangeLog accountChangeLog) {
        return super.findPage(accountChangeLog);
    }

    /**
     * 查询列表数据
     *
     * @param accountChangeLog
     * @return
     */
    @Override
    public List<AccountChangeLog> findList(AccountChangeLog accountChangeLog) {
        return super.findList(accountChangeLog);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param accountChangeLog
     */
    @Override
    @Transactional
    public void save(AccountChangeLog accountChangeLog) {
        super.save(accountChangeLog);
        // 保存上传图片
        FileUploadUtils.saveFileUpload(accountChangeLog, accountChangeLog.getId(), "accountChangeLog_image");
        // 保存上传附件
        FileUploadUtils.saveFileUpload(accountChangeLog, accountChangeLog.getId(), "accountChangeLog_file");
    }

    /**
     * 更新状态
     *
     * @param accountChangeLog
     */
    @Override
    @Transactional
    public void updateStatus(AccountChangeLog accountChangeLog) {
        super.updateStatus(accountChangeLog);
    }

    /**
     * 删除数据
     *
     * @param accountChangeLog
     */
    @Override
    @Transactional
    public void delete(AccountChangeLog accountChangeLog) {
        super.delete(accountChangeLog);
    }

    /**
     * 添加账变记录
     *
     * @param member        这个账户账变记录的来源账户
     * @param amount        账变钱数，增加正，减少负
     * @param changeType    账变记录操作类型
     * @param ActionAccount 操作人
     * @param bizType       关联业务类型
     * @param bizId         关联业务id
     */
    @Transactional
    public void add(Member member, double amount, double newBalance ,String changeType, String ActionAccount, String bizType, String bizId) {
//        double newBalance = member.getBalance() + amount; 新的余额只能传过来，否则需要一步一保存
        AccountChangeLog accountChangeLog = new AccountChangeLog();
        accountChangeLog.setUserId(member.getId());
        accountChangeLog.setUserAccount(member.getMemName());
        accountChangeLog.setChangeAmount(NumberUtil.round(amount, 2).doubleValue());
        accountChangeLog.setChangeTime(DateUtil.date());
        accountChangeLog.setChangeType(changeType);
        accountChangeLog.setBalance(NumberUtil.round(newBalance, 2).doubleValue());
        accountChangeLog.setActionUserAccount(ActionAccount);
        accountChangeLog.setActionTime(DateUtil.date());
        accountChangeLog.setBizType(bizType);
        accountChangeLog.setBizId(bizId);
        save(accountChangeLog);
    }

}