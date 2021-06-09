package com.tsu.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.tsu.entity.Result;
import com.tsu.entity.StatusCode;
import com.tsu.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ZZZ
 * @create 2020/9/19/15:41
 */

//全局异常处理
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    //处理登录过期异常
    @ExceptionHandler({TokenExpiredException.class,NotLoginException.class})
    public Result handleTokenExpiredException(Exception exception) {
        log.error("处理异常" + exception.getMessage());
        return new Result(StatusCode.LOGIN_EXPIRATION, "登录过期", null);
    }

    //处理全局异常
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception exception) {
        log.error("处理异常" + exception.getMessage());
        return new Result(StatusCode.INTERNAL_ERROR, exception.getMessage(), null);
    }
}
