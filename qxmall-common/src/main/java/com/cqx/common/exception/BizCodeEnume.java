package com.cqx.common.exception;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/17 14:43
 */
public enum BizCodeEnume {
    UNKNOWN_EXCEPTION(10000, "系统未知异常"),
    VAILD_EXCEPTION(10001, "参数格式校验失败"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架失败");

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    BizCodeEnume(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
