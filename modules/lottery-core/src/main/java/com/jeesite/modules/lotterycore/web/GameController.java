package com.jeesite.modules.lotterycore.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.service.GameService;

/**
 * 游戏Controller
 * @author DUKE
 * @version 2023-04-22
 */
@Controller
@RequestMapping(value = "${adminPath}/lotterycore/game")
public class GameController extends BaseController {

	@Autowired
	private GameService gameService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Game get(String id, boolean isNewRecord) {
		return gameService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("lotterycore:game:view")
	@RequestMapping(value = {"list", ""})
	public String list(Game game, Model model) {
		model.addAttribute("game", game);
		return "modules/lotterycore/gameList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("lotterycore:game:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Game> listData(Game game, HttpServletRequest request, HttpServletResponse response) {
		game.setPage(new Page<>(request, response));
		Page<Game> page = gameService.findPage(game);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("lotterycore:game:view")
	@RequestMapping(value = "form")
	public String form(Game game, Model model) {
		model.addAttribute("game", game);
		return "modules/lotterycore/gameForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("lotterycore:game:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Game game) {
		gameService.save(game);
		return renderResult(Global.TRUE, text("保存游戏成功！"));
	}
	
	/**
	 * 停用数据
	 */
	@RequiresPermissions("lotterycore:game:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(Game game) {
		game.setStatus(Game.STATUS_DISABLE);
		gameService.updateStatus(game);
		return renderResult(Global.TRUE, text("停用游戏成功"));
	}
	
	/**
	 * 启用数据
	 */
	@RequiresPermissions("lotterycore:game:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(Game game) {
		game.setStatus(Game.STATUS_NORMAL);
		gameService.updateStatus(game);
		return renderResult(Global.TRUE, text("启用游戏成功"));
	}
	
}