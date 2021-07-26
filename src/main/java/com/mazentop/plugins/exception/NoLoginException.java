package com.mazentop.plugins.exception;

import org.springframework.http.HttpStatus;

/**
 * 无权限访问
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2019/8/20 10:55
 */
public class NoLoginException extends RuntimeException {
    // ~ Constructors
    // ===================================================================================================

    /**
     * Constructs an <code>NoLoginException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public NoLoginException(String msg) {
        super(msg);
    }


    public NoLoginException() {
        this(HttpStatus.FORBIDDEN.getReasonPhrase());
    }

    /**
     * Constructs an <code>NoLoginException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public NoLoginException(String msg, Throwable t) {
        super(msg, t);
    }
}