package com.tsu.entity;

/**
 * @author ZZZ
 * @create 2020/9/19/15:41
 */

//状态码
public class StatusCode {
    //程序执行成功
    public static final Integer EXECUTE_SUCCESS = 1000;

    //程序执行失败
    public static final Integer EXECUTE_FAIL = 1001;

    //参数错误
    public static final Integer PARAM_ERROR = 1002;

    //程序内部异常
    public static final Integer INTERNAL_ERROR = 1003;

    //鉴权失败
    public static final Integer AUTHENTICATION_FAIL = 1004;

    //登录过期，未登录
    public static final Integer LOGIN_EXPIRATION = 1005;

}
