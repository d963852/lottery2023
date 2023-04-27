/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.test;

import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.jeesite.modules.Application;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * 初始化数据表
 * @author ThinkGem
 * @version 2020-5-26
 */
@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
public class LotteryTest {
	
	@Test
	public void Test() throws Exception{
		String jsonArr = "[{\"issue\":\"20230426-0987\",\"opendate\":\"2023-04-26 16:27:00\",\"code\":\"9,3,6,5,1\",\"codelist\":null,\"lotterycode\":\"QIQFFC\",\"officialissue\":\"20230426-0987\"}]";
		JSONArray array = JSONUtil.parseArray(jsonArr);
		List<Dict> list = JSONUtil.toList(array, Dict.class);
		System.out.println(list.get(0).getStr("code"));
	}

}
