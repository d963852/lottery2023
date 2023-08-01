/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.modules.api.web;

import cn.hutool.core.date.DateUtil;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.api.vo.ArticleVO;
import com.jeesite.modules.cms.entity.Article;
import com.jeesite.modules.cms.entity.Category;
import com.jeesite.modules.cms.service.ArticleService;
import com.jeesite.modules.lotterycore.param.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示实例Controller
 *
 * @author ThinkGem
 * @version 2018-03-24
 */
@RestController
@RequestMapping(value = "${adminPath}/api/lottery")
public class LotteryApiController extends BaseController {

    @Autowired
    private ArticleService articleService;

    /**
     * 获取彩票大厅轮播文章图片
     *
     * @return
     */
    @RequestMapping(value = "getIndexPictureList")
    public R getIndexPictureList() {
        Article articleSC = new Article();
        Category categorySC = new Category();
        categorySC.setCategoryCode("A1000");
        articleSC.setCategory(categorySC);
        List<Article> articleList = articleService.findList(articleSC);
        List<ArticleVO> articleVOList = new ArrayList<>();
        for (Article article : articleList) {
            ArticleVO articleVO = new ArticleVO();
            articleVO.setTitle(article.getTitle());
            articleVO.setId(article.getId());
            articleVO.setImgUrl(article.getImage());
            articleVOList.add(articleVO);
        }

        return R.success().data(articleVOList);
    }

    /**
     * 获取通知公告标题
     *
     * @return
     */
    @RequestMapping(value = "getNoticeTitleList")
    public R getNoticeTitleList() {
        List<ArticleVO> articleVOList = new ArrayList<>();

        Article articleSC = new Article();
        Category categorySC = new Category();
        categorySC.setCategoryCode("A1002");
        articleSC.setCategory(categorySC);
        // 获取文章内容
        Page<Article> page = new Page<>(1, 5, -1);
        articleSC.setPage(page);
        page = articleService.findPage(articleSC);
        if (page.getList().size() > 0) {
            List<Article> articleList = page.getList();
            for (Article article : articleList) {
                ArticleVO articleVO = new ArticleVO();
                articleVO.setTitle(DateUtil.format(article.getCreateDate(), "yyyy-MM-dd") + " " + article.getTitle());
                articleVOList.add(articleVO);
            }
        }
        return R.success().data(articleVOList);
    }


}