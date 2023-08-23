package com.jeesite.modules.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class PlayMethodGroupVO {
    private String id;
    private String groupName;		// 分组名称
    private Long sort;		// 排序
    private String positioned;		// 是否不定位
    private String android;		// 移动端是否可用
    List<PlayMethodVO> playMethodList;

}
