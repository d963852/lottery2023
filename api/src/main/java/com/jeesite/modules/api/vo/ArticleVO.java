package com.jeesite.modules.api.vo;

import lombok.Data;

@Data
public class ArticleVO {
    private String id;
    private String title;
    private String imgUrl;
    private String des;
    private String content;
    private String pubDate;
}
