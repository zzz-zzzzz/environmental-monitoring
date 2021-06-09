package com.tsu.exception;

/**
 * @author ZZZ
 * @create 2020/9/19/15:41
 */

public class NotLoginException extends RuntimeException {

    public NotLoginException() {
        this("未登录请先登录");
    }

    public NotLoginException(String message) {
        super(message);
    }
}
