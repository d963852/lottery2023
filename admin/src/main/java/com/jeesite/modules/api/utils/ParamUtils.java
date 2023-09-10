package com.jeesite.modules.api.utils;

import com.jeesite.common.codec.EncodeUtils;

public class ParamUtils {
    public static String securityFilter(String param) {
        param = EncodeUtils.sqlFilter(param);
        param = EncodeUtils.xssFilter(param);
        return param;
    }


    public static void main(String[] args) {
        String test = ParamUtils.securityFilter(null);
        System.out.println(test);
    }

}