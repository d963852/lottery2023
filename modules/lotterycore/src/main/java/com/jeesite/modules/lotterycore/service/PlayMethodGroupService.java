package com.jeesite.modules.lotterycore.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.lotterycore.dao.PlayMethodGroupDao;
import com.jeesite.modules.lotterycore.entity.PlayMethodGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 玩法分组Service
 * @author tg
 * @version 2023-06-20
 */
@Service
public class PlayMethodGroupService extends CrudService<PlayMethodGroupDao, PlayMethodGroup> {
	
	/**
	 * 获取单条数据
	 * @param playMethodGroup
	 * @return
	 */
	@Override
	public PlayMethodGroup get(PlayMethodGroup playMethodGroup) {
		return super.get(playMethodGroup);
	}
	
	/**
	 * 查询分页数据
	 * @param playMethodGroup 查询条件
	 * @param playMethodGroup page 分页对象
	 * @return
	 */
	@Override
	public Page<PlayMethodGroup> findPage(PlayMethodGroup playMethodGroup) {
		return super.findPage(playMethodGroup);
	}
	
	/**
	 * 查询列表数据
	 * @param playMethodGroup
	 * @return
	 */
	@Override
	public List<PlayMethodGroup> findList(PlayMethodGroup playMethodGroup) {
		return super.findList(playMethodGroup);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param playMethodGroup
	 */
	@Override
	@Transactional
	public void save(PlayMethodGroup playMethodGroup) {
		super.save(playMethodGroup);
	}
	
	/**
	 * 更新状态
	 * @param playMethodGroup
	 */
	@Override
	@Transactional
	public void updateStatus(PlayMethodGroup playMethodGroup) {
		super.updateStatus(playMethodGroup);
	}
	
	/**
	 * 删除数据
	 * @param playMethodGroup
	 */
	@Override
	@Transactional
	public void delete(PlayMethodGroup playMethodGroup) {
		super.delete(playMethodGroup);
	}
	
}