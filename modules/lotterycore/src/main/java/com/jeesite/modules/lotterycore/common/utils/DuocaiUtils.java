package com.jeesite.modules.lotterycore.common.utils;

import cn.hutool.core.util.RandomUtil;
import com.jeesite.common.config.Global;

public class DuocaiUtils {
    public static String getUserkey() {
        String duocaiKey = Global.getProperty("duocai.key", "K2644d2471cb170,K2644d25b3c950b,K2644d2666b9264,K2644d2728dfcfa");
        String[] keys = duocaiKey.split(",");
        if (keys.length > 0) {
            return keys[RandomUtil.randomInt(0, keys.length)];
        }else{
            return "K2644d2728dfcfa";
        }
    }
}
