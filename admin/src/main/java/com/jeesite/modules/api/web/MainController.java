package com.jeesite.modules.api.web;

import com.jeesite.common.web.BaseController;
import com.jeesite.modules.lotterycore.service.GameService;
import com.jeesite.modules.lotterycore.service.LotteryWebMenuService;
import com.jeesite.modules.sys.entity.Member;
import com.jeesite.modules.sys.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 乐透会员管理Controller
 *
 * @author tg
 * @version 2023-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/main/")
public class MainController extends BaseController {
    @Autowired
    private GameService gameService;
    @Autowired
    private LotteryWebMenuService lotteryWebMenuService;

    /**
     * 管理主页
     */
    @RequestMapping(value = "index")
    public String index(Member member, Model model) {
        model.addAttribute("menuList", lotteryWebMenuService.findListFromCache());
        model.addAttribute("gameList", gameService.findListFromCache());
        model.addAttribute("menuGroupList", DictUtils.getDictList("lottery_menu_group"));
        model.addAttribute("member", member);
        return "modules/lotteryweb/index";
    }

}