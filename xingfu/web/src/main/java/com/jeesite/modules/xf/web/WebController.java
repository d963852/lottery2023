/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.modules.xf.web;

import com.jeesite.common.cache.CacheUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.shiro.realm.BaseAuthorizingRealm;
import com.jeesite.common.shiro.realm.LoginInfo;
import com.jeesite.common.web.BaseController;
import com.jeesite.common.web.CookieUtils;
import com.jeesite.common.web.http.ServletUtils;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.entity.LotteryWebMenu;
import com.jeesite.modules.lotterycore.service.GameService;
import com.jeesite.modules.lotterycore.service.LotteryWebMenuService;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.DictUtils;
import com.jeesite.modules.sys.utils.PwdUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 演示实例Controller
 *
 * @author ThinkGem
 * @version 2018-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/web")
public class WebController extends BaseController {

    @Autowired
    private GameService gameService;

    @Autowired
    private LotteryWebMenuService lotteryWebMenuService;

    /**
     * 登录成功，进入管理首页
     */
    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        // 地址中如果包含JSESSIONID，则跳转一次，去掉JSESSIONID信息。
        if (StringUtils.containsIgnoreCase(request.getRequestURI(), ";JSESSIONID=")) {
            String queryString = request.getQueryString();
            queryString = queryString == null ? "" : "?" + queryString;
            ServletUtils.redirectUrl(request, response, adminPath + "/index" + queryString);
            return null;
        }

        // 验证下用户权限，以便调用doGetAuthorizationInfo方法，保存单点登录登出句柄
        Subject subject = SecurityUtils.getSubject();
        if (subject == null || !subject.isPermitted("user")) {
            if (subject != null) {
                subject.logout();
            }
            String queryString = request.getQueryString();
            queryString = queryString == null ? "" : "?" + queryString;
            ServletUtils.redirectUrl(request, response, adminPath + "/login" + queryString);
            return null;
        }

        //获取登录用户信息
        LoginInfo loginInfo = UserUtils.getLoginInfo();

        // 未加载shiro模块时会为空，直接访问则提示操作权限不足。
        if (loginInfo == null) {
            if (subject != null) {
                subject.logout();
            }
            String queryString = request.getQueryString();
            queryString = queryString == null ? "" : "?" + queryString;
            ServletUtils.redirectUrl(request, response, adminPath + "/login" + queryString);
            return null;
        }

        // 当前用户对象信息
        User user = UserUtils.get(loginInfo.getId());
        if (user == null) {
            UserUtils.getSubject().logout();
            String queryString = request.getQueryString();
            queryString = queryString == null ? "" : "?" + queryString;
            ServletUtils.redirectUrl(request, response, adminPath + "/login" + queryString);
            return null;
        }
        model.addAttribute("user", user); // 设置当前用户信息

        //获取当前会话对象
        Session session = UserUtils.getSession();

        // 是否是登录操作
        boolean isLogin = Global.TRUE.equals(session.getAttribute(BaseAuthorizingRealm.IS_LOGIN_OPER));
        if (isLogin) {
            // 获取后接着清除，防止下次获取仍然认为是登录状态
            session.removeAttribute(BaseAuthorizingRealm.IS_LOGIN_OPER);
            // 设置共享SessionId的Cookie值（第三方系统使用）
            String cookieName = Global.getProperty("session.shareSessionIdCookieName");
            if (StringUtils.isNotBlank(cookieName)) {
                CookieUtils.setCookie(response, cookieName, (String) session.getId(), "/");
            }
            // 如果登录设置了语言，则切换语言
            if (loginInfo.getParam("lang") != null) {
                Global.setLang(loginInfo.getParam("lang"), request, response);
            }
        }

        // 获取登录成功后跳转的页面
        String successUrl = request.getParameter("__url");
        if (StringUtils.isBlank(successUrl)) {
            successUrl = (String) request.getAttribute("__url");
        }
        if (StringUtils.isBlank(successUrl)) {
            successUrl = Global.getProperty("shiro.successUrl");
        }

        // 登录操作如果是Ajax操作，直接返回登录信息字符串。
        if (ServletUtils.isAjaxRequest(request)) {
            model.addAttribute("result", Global.TRUE);
            // 如果是登录，则返回登录成功信息，否则返回获取成功信息
            if (isLogin) {
                model.addAttribute("message", text("sys.login.success"));
            } else {
                model.addAttribute("message", text("sys.login.getInfo"));
            }
            model.addAttribute("sessionid", (String) session.getId());
            if (!StringUtils.contains(successUrl, "://")) {
                successUrl = request.getContextPath() + successUrl;
            }
            model.addAttribute("__url", successUrl); // 告诉浏览器登录后跳转的页面
            return ServletUtils.renderObject(response, model);
        }
        // 如果是登录操作，则跳转到登录成功页
        else if (isLogin) {
            return REDIRECT + successUrl;
        }

        // 是否允许刷新主页，如果已登录，再次访问主页，则退出原账号。
        if (!Global.getConfigToBoolean("shiro.isAllowRefreshIndex", "true")) {
            String logined = CookieUtils.getCookie(request, "LOGINED");
            if (StringUtils.isBlank(logined) || "false".equals(logined)) {
                CookieUtils.setCookie(response, "LOGINED", "true");
            } else if (StringUtils.equals(logined, "true")) {
                UserUtils.getSubject().logout();
                CookieUtils.setCookie(response, "LOGINED", "false");
                String queryString = request.getQueryString();
                queryString = queryString == null ? "" : "?" + queryString;
                return REDIRECT + adminPath + "/login" + queryString;
            }
        }

        // 初始密码策略和密码修改策略验证（0：关闭；1：提醒用户；2：强制修改初始或旧密码）
        String passwordModifyUrl = PwdUtils.passwordModifyValid(user, model);
        if (passwordModifyUrl != null) {
            try {
                request.getRequestDispatcher(passwordModifyUrl).forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        // 非无类型用户，自动根据用户类型设置默认菜单的归属系统（个性化示例）
        //if (!User.USER_TYPE_NONE.equals(user.getUserType())){
        //	session.setAttribute("sysCode", user.getUserType());
        //	UserUtils.removeCache(UserUtils.CACHE_AUTH_INFO+"_"+session.getId());
        //}

        // 登录切换角色身份（个性化示例）
        //String roleCode = "dept";
        //session.setAttribute("roleCode", roleCode);
        //UserUtils.removeCache(UserUtils.CACHE_AUTH_INFO+"_"+session.getId());

        List<Game> gameList = CacheUtils.get("xingfu", "gameList");
        if (gameList == null || gameList.size() < 1) {
            Game gameSC = new Game();
            gameSC.sqlMap().getOrder().setOrderBy("order_no asc");
            gameList = gameService.findList(gameSC);
            CacheUtils.put("xingfu", "gameList", gameList);
        }
        model.addAttribute("gameList",gameList);

        List<LotteryWebMenu> menuList = CacheUtils.get("xingfu", "menuList");
        if (menuList == null || menuList.size() < 1) {
            LotteryWebMenu lotteryWebMenuSC = new LotteryWebMenu();
            lotteryWebMenuSC.setWebSite("xingfu");
            lotteryWebMenuSC.sqlMap().getOrder().setOrderBy("game_sort asc");
            menuList = lotteryWebMenuService.findList(lotteryWebMenuSC);
            CacheUtils.put("xingfu", "menuList", menuList);
        }
        model.addAttribute("menuList",menuList);

        model.addAttribute("menuGroupList", DictUtils.getDictList("lottery_menu_group"));

        // 返回指定用户类型的首页视图
        String userType = user.getUserType();
        if (User.USER_TYPE_NONE.equals(userType)) {
            userType = User.USER_TYPE_EMPLOYEE;
        }
        String view = UserUtils.getUserTypeValue(userType, "indexView");
        if (StringUtils.isNotBlank(view)) {
            return view;
        }

        // 返回主页面视图
        return "modules/xingfu/sysIndex";
    }

}