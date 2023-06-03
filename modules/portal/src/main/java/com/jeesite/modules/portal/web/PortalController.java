package com.jeesite.modules.portal.web;

import com.jeesite.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 门户主Controller
 *
 * @author tonyzhang
 * @version 2022-04-15
 */
@Controller
@RequestMapping(value = "${adminPath}/portal/main")
public class PortalController extends BaseController {

    public static final String LIST_ACTION_OWN = "own";
    public static final String LIST_ACTION_RESPONSIBLE = "responsible";
    public static final String LIST_ACTION_OVER = "over";

    /**
     * 门户主页
     */
//    @RequiresPermissions("portal:main:view")
    @RequestMapping(value = "index")
    public String index(Model model) {
        return "modules/portal/main";
    }

}