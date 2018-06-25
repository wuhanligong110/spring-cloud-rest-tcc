package com.miget.hxb.controller;

import com.miget.hxb.RestStatus;
import com.google.common.collect.ImmutableMap;

/**
 * @author Zhao Junjian
 */
public enum StatusCode implements RestStatus {

    OK(20000, "请求成功"),

    // 40xxx 客户端不合法的请求
    INVALID_MODEL_FIELDS(40001, "字段校验非法"),

    /**
     * 参数类型非法，常见于SpringMVC中String无法找到对应的enum而抛出的异常
     */
    INVALID_PARAMS_CONVERSION(40002, "参数类型非法"),

    // 41xxx 请求方式出错
    /**
     * http media type not supported
     */
    HTTP_MESSAGE_NOT_READABLE(41001, "HTTP消息不可读"),

    /**
     * 请求方式非法
     */
    REQUEST_METHOD_NOT_SUPPORTED(41002, "不支持的HTTP请求方法"),

    // 成功接收请求, 但是处理失败
    /**
     * Duplicate Key
     */
    DUPLICATE_KEY(42001, "操作过快, 请稍后再试"),

    /**
     * 用于登录时用户不存在的情况
     */
    USER_NOT_EXISTS(42002, "用户不存在, 请先注册"),

    /**
     * 用于下订单时的产品检查
     */
    PRODUCT_NOT_EXISTS(42003, "产品不存在"),

    /**
     * 订单不存在
     */
    ORDER_NOT_EXISTS(42004, "订单不存在"),

    /**
     * 库存不足
     */
    INSUFFICIENT_PRODUCT(42005, "库存不足"),

    /**
     * 余额不足
     */
    INSUFFICIENT_BALANCE(42006, "余额不足"),

    /**
     * 订单参数错误，订单产品Id数量小于0
     */
    NULL_PRODUCT(42007,"订单产品Id数量小于0"),

    /**
     * 订单取消增加库存失败
     */
    FAIL_CANCEL_ORDER(42008,"取消订单失败"),

    /**
     * 没有可取消的订单
     */
    WITHOUT_CANCEL_ORDER(42009, "没有可取消的订单"),

    WEIXIN_CONFIG_NULL(42010, "商家微信配置不存在"),

    WITHDRAW_TIME_ERRO(42011,"不在提现时间范围内"),

    WITHDRAW_PARAMS_ERRO(42012,"提现参数错误"),

    WITHDRAW_MAINTAIN(42013,"提现维护中"),

    USER_ACCOUNT_NOT_EXISTS(42014, "用户账户不存在"),

    // 50xxx 服务端异常
    /**
     * 用于处理未知的服务端错误
     */
    SERVER_UNKNOWN_ERROR(50001, "服务端异常, 请稍后再试"),

    /**
     * 用于远程调用时的系统出错
     */
    SERVER_IS_BUSY_NOW(50002, "系统繁忙, 请稍后再试");

    private final int code;

    private final String message;

    private static final ImmutableMap<Integer, StatusCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, StatusCode> builder = ImmutableMap.builder();
        for (StatusCode statusCode : values()) {
            builder.put(statusCode.code(), statusCode);
        }
        CACHE = builder.build();
    }

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static StatusCode valueOfCode(int code) {
        final StatusCode status = CACHE.get(code);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + code + "]");
        }
        return status;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

}
