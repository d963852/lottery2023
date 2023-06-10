package com.jeesite.modules.lotterycore.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.lotterycore.entity.LotteryWebMenu;
import com.jeesite.modules.lotterycore.dao.LotteryWebMenuDao;

/**
 * 网站彩种菜单设置Service
 * @author tg
 * @version 2023-06-09
 */
@Service
public class LotteryWebMenuService extends CrudService<LotteryWebMenuDao, LotteryWebMenu> {
	
	/**
	 * 获取单条数据
	 * @param lotteryWebMenu
	 * @return
	 */
	@Override
	public LotteryWebMenu get(LotteryWebMenu lotteryWebMenu) {
		return super.get(lotteryWebMenu);
	}
	
	/**
	 * 查询分页数据
	 * @param lotteryWebMenu 查询条件
	 * @param lotteryWebMenu page 分页对象
	 * @return
	 */
	@Override
	public Page<LotteryWebMenu> findPage(LotteryWebMenu lotteryWebMenu) {
		return super.findPage(lotteryWebMenu);
	}
	
	/**
	 * 查询列表数据
	 * @param lotteryWebMenu
	 * @return
	 */
	@Override
	public List<LotteryWebMenu> findList(LotteryWebMenu lotteryWebMenu) {
		return super.findList(lotteryWebMenu);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param lotteryWebMenu
	 */
	@Override
	@Transactional
	public void save(LotteryWebMenu lotteryWebMenu) {
		super.save(lotteryWebMenu);
	}
	
	/**
	 * 更新状态
	 * @param lotteryWebMenu
	 */
	@Override
	@Transactional
	public void updateStatus(LotteryWebMenu lotteryWebMenu) {
		super.updateStatus(lotteryWebMenu);
	}
	
	/**
	 * 删除数据
	 * @param lotteryWebMenu
	 */
	@Override
	@Transactional
	public void delete(LotteryWebMenu lotteryWebMenu) {
		super.delete(lotteryWebMenu);
	}
	
}