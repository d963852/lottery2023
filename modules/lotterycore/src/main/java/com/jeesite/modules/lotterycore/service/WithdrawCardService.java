package com.jeesite.modules.lotterycore.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.lotterycore.entity.WithdrawCard;
import com.jeesite.modules.lotterycore.dao.WithdrawCardDao;

/**
 * 提现信息Service
 * @author thinkgem
 * @version 2023-09-01
 */
@Service
public class WithdrawCardService extends CrudService<WithdrawCardDao, WithdrawCard> {
	
	/**
	 * 获取单条数据
	 * @param withdrawCard
	 * @return
	 */
	@Override
	public WithdrawCard get(WithdrawCard withdrawCard) {
		return super.get(withdrawCard);
	}
	
	/**
	 * 查询分页数据
	 * @param withdrawCard 查询条件
	 * @param withdrawCard page 分页对象
	 * @return
	 */
	@Override
	public Page<WithdrawCard> findPage(WithdrawCard withdrawCard) {
		return super.findPage(withdrawCard);
	}
	
	/**
	 * 查询列表数据
	 * @param withdrawCard
	 * @return
	 */
	@Override
	public List<WithdrawCard> findList(WithdrawCard withdrawCard) {
		return super.findList(withdrawCard);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param withdrawCard
	 */
	@Override
	@Transactional
	public void save(WithdrawCard withdrawCard) {
		super.save(withdrawCard);
	}
	
	/**
	 * 更新状态
	 * @param withdrawCard
	 */
	@Override
	@Transactional
	public void updateStatus(WithdrawCard withdrawCard) {
		super.updateStatus(withdrawCard);
	}
	
	/**
	 * 删除数据
	 * @param withdrawCard
	 */
	@Override
	@Transactional
	public void delete(WithdrawCard withdrawCard) {
		super.delete(withdrawCard);
	}
	
}