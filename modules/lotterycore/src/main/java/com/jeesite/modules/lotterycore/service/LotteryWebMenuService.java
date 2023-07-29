package com.jeesite.modules.lotterycore.service;

import com.jeesite.common.cache.CacheUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.lotterycore.dao.LotteryWebMenuDao;
import com.jeesite.modules.lotterycore.entity.LotteryWebMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

	public List<LotteryWebMenu> findListFromCache(){
		List<LotteryWebMenu> menuList = CacheUtils.get("lotteryweb", "menuList");
		if (menuList == null || menuList.size() < 1) {
			LotteryWebMenu lotteryWebMenuSC = new LotteryWebMenu();
			lotteryWebMenuSC.setWebSite(Global.getProperty("website.name"));
			lotteryWebMenuSC.sqlMap().getOrder().setOrderBy("game_sort asc");
			menuList = findList(lotteryWebMenuSC);
			CacheUtils.put("lotteryweb", "menuList", menuList);
		}
		return menuList;
	}
	
}