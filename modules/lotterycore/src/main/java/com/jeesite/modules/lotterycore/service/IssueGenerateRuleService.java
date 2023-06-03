package com.jeesite.modules.lotterycore.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.lotterycore.entity.IssueGenerateRule;
import com.jeesite.modules.lotterycore.dao.IssueGenerateRuleDao;

/**
 * 期号生成规则Service
 * @author DUKE
 * @version 2023-04-23
 */
@Service
public class IssueGenerateRuleService extends CrudService<IssueGenerateRuleDao, IssueGenerateRule> {
	
	/**
	 * 获取单条数据
	 * @param issueGenerateRule
	 * @return
	 */
	@Override
	public IssueGenerateRule get(IssueGenerateRule issueGenerateRule) {
		return super.get(issueGenerateRule);
	}
	
	/**
	 * 查询分页数据
	 * @param issueGenerateRule 查询条件
	 * @param issueGenerateRule page 分页对象
	 * @return
	 */
	@Override
	public Page<IssueGenerateRule> findPage(IssueGenerateRule issueGenerateRule) {
		return super.findPage(issueGenerateRule);
	}
	
	/**
	 * 查询列表数据
	 * @param issueGenerateRule
	 * @return
	 */
	@Override
	public List<IssueGenerateRule> findList(IssueGenerateRule issueGenerateRule) {
		return super.findList(issueGenerateRule);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param issueGenerateRule
	 */
	@Override
	@Transactional
	public void save(IssueGenerateRule issueGenerateRule) {
		super.save(issueGenerateRule);
	}
	
	/**
	 * 更新状态
	 * @param issueGenerateRule
	 */
	@Override
	@Transactional
	public void updateStatus(IssueGenerateRule issueGenerateRule) {
		super.updateStatus(issueGenerateRule);
	}
	
	/**
	 * 删除数据
	 * @param issueGenerateRule
	 */
	@Override
	@Transactional
	public void delete(IssueGenerateRule issueGenerateRule) {
		super.delete(issueGenerateRule);
	}
	
}