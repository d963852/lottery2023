/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 */
package com.jeesite.test;

import com.jeesite.common.tests.BaseSpringContextTests;
import com.jeesite.modules.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest(classes = Application.class)
public class WebSpringTest extends BaseSpringContextTests {

//    @Test
//    public void testTg(){
//        TelegramUtils.sendMessage("测试测试");
//    }

}
