package com.jeesite.modules.lotterycore.param;

import lombok.Data;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/4/28 22:47
 * @webSite https://github.com/coder-amiao
 * 统一响应结果处理  使用链式编程 返回类本身
 */
@Data
public class R {

    private Boolean success;

    private Integer code;

    private String message;

    /**
     * 接口请求时间戳
     */
    private Long timestamp;

    private Object data;


    private R setSuccess(Boolean success) {
        this.success = success;
        return this;
    }


    private R setMessage(String message) {
        this.message = message;
        return this;
    }

    private R setData(Object data) {
        this.data = data;
        return this;
    }

    private R setCode(Integer code) {
        this.code = code;
        return this;
    }

    private R() {
    }

    private R(Long timestamp) {
        this.timestamp = timestamp;
    }


    /**
     * 通用返回成功
     *
     * @return
     */
    public static R success() {
        return new R(System.currentTimeMillis())
                .setSuccess(ResultCodeEnum.SUCCESS.getSuccess())
                .setCode(ResultCodeEnum.SUCCESS.getCode())
                .setMessage(ResultCodeEnum.SUCCESS.getMessage());

    }

    /**
     * 通用返回失败
     *
     * @return
     */
    public static R failure() {
        return new R(System.currentTimeMillis())
                .setSuccess(ResultCodeEnum.FAIL.getSuccess())
                .setCode(ResultCodeEnum.FAIL.getCode())
                .setMessage(ResultCodeEnum.FAIL.getMessage());

    }

    /**
     * 设置结果，形参为结果枚举
     *
     * @param result
     * @return
     */
    public static R setResult(ResultCodeEnum result) {
        return new R(System.currentTimeMillis())
                .setSuccess(result.getSuccess())
                .setCode(result.getCode())
                .setMessage(result.getMessage());

    }


    // 自定义返回数据
    public R data(Object data) {
        return this.setData(data);

    }


    // 自定义状态信息
    public R message(String message) {
        return this.setMessage(message);

    }

    // 自定义状态码
    public R code(Integer code) {
        return this.setCode(code);

    }

    // 自定义返回结果
    public R success(Boolean success) {
        return this.setSuccess(success);

    }

}
