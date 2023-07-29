package com.jeesite.modules.lotterycore.common.utils;

import cn.hutool.http.HttpUtil;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.config.Global;

import java.util.Map;

public class TelegramUtils {
    String website = Global.getConfig("productName");
    static String chat_id = Global.getConfig("sys.telegram.bot.chatid", "");
    static String url = Global.getConfig("sys.telegram.bot.url", "");

    public static void sendMessage(String message) {
        Map<String, Object> postData = MapUtils.newHashMap();
        postData.put("chat_id", "-" + chat_id);
        postData.put("text", message);
        HttpUtil.post(url, postData);
    }
}
