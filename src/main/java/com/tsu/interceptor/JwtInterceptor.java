package com.tsu.interceptor;

import com.tsu.exception.NotLoginException;
import com.tsu.utils.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author ZZZ
 * @create 2020/9/21/19:03
 */

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果是OPTIONS请求，直接返回
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        String token = request.getHeader("token");
        if (token == null) {
            throw new NotLoginException();
        }
        try {
            JwtUtil.verify(token);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
}
