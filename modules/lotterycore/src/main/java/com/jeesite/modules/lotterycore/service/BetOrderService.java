package com.jeesite.modules.lotterycore.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.lotterycore.entity.BetOrder;
import com.jeesite.modules.lotterycore.dao.BetOrderDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 投注订单Service
 * @author thinkgem
 * @version 2023-08-26
 */
@Service
public class BetOrderService extends CrudService<BetOrderDao, BetOrder> {
	
	/**
	 * 获取单条数据
	 * @param betOrder
	 * @return
	 */
	@Override
	public BetOrder get(BetOrder betOrder) {
		return super.get(betOrder);
	}
	
	/**
	 * 查询分页数据
	 * @param betOrder 查询条件
	 * @param betOrder page 分页对象
	 * @return
	 */
	@Override
	public Page<BetOrder> findPage(BetOrder betOrder) {
		return super.findPage(betOrder);
	}
	
	/**
	 * 查询列表数据
	 * @param betOrder
	 * @return
	 */
	@Override
	public List<BetOrder> findList(BetOrder betOrder) {
		return super.findList(betOrder);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param betOrder
	 */
	@Override
	@Transactional
	public void save(BetOrder betOrder) {
		super.save(betOrder);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(betOrder, betOrder.getId(), "betOrder_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(betOrder, betOrder.getId(), "betOrder_file");
	}
	
	/**
	 * 更新状态
	 * @param betOrder
	 */
	@Override
	@Transactional
	public void updateStatus(BetOrder betOrder) {
		super.updateStatus(betOrder);
	}
	
	/**
	 * 删除数据
	 * @param betOrder
	 */
	@Override
	@Transactional
	public void delete(BetOrder betOrder) {
		super.delete(betOrder);
	}
	
}