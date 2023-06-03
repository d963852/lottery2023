package com.jeesite.modules.lotterycore.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.lotterycore.entity.DuocaiLotteryType;
import com.jeesite.modules.lotterycore.dao.DuocaiLotteryTypeDao;

/**
 * 多彩网彩种Service
 * @author DUKE
 * @version 2023-04-23
 */
@Service
public class DuocaiLotteryTypeService extends CrudService<DuocaiLotteryTypeDao, DuocaiLotteryType> {
	
	/**
	 * 获取单条数据
	 * @param duocaiLotteryType
	 * @return
	 */
	@Override
	public DuocaiLotteryType get(DuocaiLotteryType duocaiLotteryType) {
		return super.get(duocaiLotteryType);
	}
	
	/**
	 * 查询分页数据
	 * @param duocaiLotteryType 查询条件
	 * @param duocaiLotteryType page 分页对象
	 * @return
	 */
	@Override
	public Page<DuocaiLotteryType> findPage(DuocaiLotteryType duocaiLotteryType) {
		return super.findPage(duocaiLotteryType);
	}
	
	/**
	 * 查询列表数据
	 * @param duocaiLotteryType
	 * @return
	 */
	@Override
	public List<DuocaiLotteryType> findList(DuocaiLotteryType duocaiLotteryType) {
		return super.findList(duocaiLotteryType);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param duocaiLotteryType
	 */
	@Override
	@Transactional
	public void save(DuocaiLotteryType duocaiLotteryType) {
		super.save(duocaiLotteryType);
	}
	
	/**
	 * 更新状态
	 * @param duocaiLotteryType
	 */
	@Override
	@Transactional
	public void updateStatus(DuocaiLotteryType duocaiLotteryType) {
		super.updateStatus(duocaiLotteryType);
	}
	
	/**
	 * 删除数据
	 * @param duocaiLotteryType
	 */
	@Override
	@Transactional
	public void delete(DuocaiLotteryType duocaiLotteryType) {
		super.delete(duocaiLotteryType);
	}
	
}