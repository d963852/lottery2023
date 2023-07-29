package com.jeesite.modules.lotterycore.param;

import lombok.Getter;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/4/28 22:39
 * @webSite https://github.com/coder-amiao
 * 响应结果枚举
 */
@Getter
public enum ResultCodeEnum{
    SUCCESS(true, 200, "成功"),
    FAIL(false, 400, "请求失败"),

    NOT_FOUND(false, 404, "接口不存在"),
    FORBIDDEN(false, 403, "资源拒绝访问"),
    UNAUTHORIZED(false, 401, "未认证（签名错误）"),

    INTERNAL_SERVER_ERROR(false, 500, "服务器内部错误"),


    NULL_POINT(false, 200002, "空指针异常"),
    PARAM_ERROR(false, 200001, "参数错误");

    /**
     * 响应是否成功
     */
    private Boolean success;
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String message;


    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
