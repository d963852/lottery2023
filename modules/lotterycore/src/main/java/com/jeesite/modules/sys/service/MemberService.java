package com.jeesite.modules.sys.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.TreeService;
import com.jeesite.modules.lotterycore.service.AccountChangeLogService;
import com.jeesite.modules.sys.dao.MemberDao;
import com.jeesite.modules.sys.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 乐透会员管理Service
 *
 * @author tg
 * @version 2023-06-10
 */
@Service
public class MemberService extends TreeService<MemberDao, Member> {

    @Autowired
    private AccountChangeLogService accountChangeLogService;

    /**
     * 获取单条数据
     *
     * @param member
     * @return
     */
    @Override
    public Member get(Member member) {
        return super.get(member);
    }

    /**
     * 查询分页数据
     *
     * @param member 查询条件
     * @param member page 分页对象
     * @return
     */
    @Override
    public Page<Member> findPage(Member member) {
        return super.findPage(member);
    }

    /**
     * 查询列表数据
     *
     * @param member
     * @return
     */
    @Override
    public List<Member> findList(Member member) {
        return super.findList(member);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param member
     */
    @Override
    @Transactional
    public void save(Member member) {
        super.save(member);
    }

    /**
     * 更新状态
     *
     * @param member
     */
    @Override
    @Transactional
    public void updateStatus(Member member) {
        super.updateStatus(member);
    }

    /**
     * 删除数据
     *
     * @param member
     */
    @Override
    @Transactional
    public void delete(Member member) {
        super.delete(member);
    }

    public Double getBalanceByMemberId(String memId) {
        Member member = get(memId);
        return member.getBalance();
    }

}