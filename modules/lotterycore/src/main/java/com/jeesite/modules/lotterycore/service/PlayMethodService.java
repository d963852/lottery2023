package com.jeesite.modules.lotterycore.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.lotterycore.dao.PlayMethodDao;
import com.jeesite.modules.lotterycore.entity.PlayMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 玩法管理Service
 * @author tg
 * @version 2023-06-20
 */
@Service
public class PlayMethodService extends CrudService<PlayMethodDao, PlayMethod> {
	
	/**
	 * 获取单条数据
	 * @param playMethod
	 * @return
	 */
	@Override
	public PlayMethod get(PlayMethod playMethod) {
		return super.get(playMethod);
	}
	
	/**
	 * 查询分页数据
	 * @param playMethod 查询条件
	 * @param playMethod page 分页对象
	 * @return
	 */
	@Override
	public Page<PlayMethod> findPage(PlayMethod playMethod) {
		return super.findPage(playMethod);
	}
	
	/**
	 * 查询列表数据
	 * @param playMethod
	 * @return
	 */
	@Override
	public List<PlayMethod> findList(PlayMethod playMethod) {
		return super.findList(playMethod);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param playMethod
	 */
	@Override
	@Transactional
	public void save(PlayMethod playMethod) {
		super.save(playMethod);
	}
	
	/**
	 * 更新状态
	 * @param playMethod
	 */
	@Override
	@Transactional
	public void updateStatus(PlayMethod playMethod) {
		super.updateStatus(playMethod);
	}
	
	/**
	 * 删除数据
	 * @param playMethod
	 */
	@Override
	@Transactional
	public void delete(PlayMethod playMethod) {
		super.delete(playMethod);
	}

}