package com.cqx.qxmall.member.exception;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/5/5 16:27
 */
public class PhoneExistException extends RuntimeException{
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public PhoneExistException() {
        super("手机号存在");
    }
}
