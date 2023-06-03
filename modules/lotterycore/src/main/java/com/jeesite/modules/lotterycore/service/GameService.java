package com.jeesite.modules.lotterycore.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.dao.GameDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 游戏Service
 * @author DUKE
 * @version 2023-04-22
 */
@Service
public class GameService extends CrudService<GameDao, Game> {
	
	/**
	 * 获取单条数据
	 * @param game
	 * @return
	 */
	@Override
	public Game get(Game game) {
		return super.get(game);
	}
	
	/**
	 * 查询分页数据
	 * @param game 查询条件
	 * @param game page 分页对象
	 * @return
	 */
	@Override
	public Page<Game> findPage(Game game) {
		return super.findPage(game);
	}
	
	/**
	 * 查询列表数据
	 * @param game
	 * @return
	 */
	@Override
	public List<Game> findList(Game game) {
		return super.findList(game);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param game
	 */
	@Override
	@Transactional
	public void save(Game game) {
		super.save(game);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(game, game.getId(), "game_image");
	}
	
	/**
	 * 更新状态
	 * @param game
	 */
	@Override
	@Transactional
	public void updateStatus(Game game) {
		super.updateStatus(game);
	}
	
	/**
	 * 删除数据
	 * @param game
	 */
	@Override
	@Transactional
	public void delete(Game game) {
		super.delete(game);
	}
	
}