package com.tsu.config;

import com.tsu.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

/**
 * @author ZZZ
 * @create 2020/9/19/15:41
 */

@Configuration
public class WebMvcConfigurerImpl implements WebMvcConfigurer {

    //添加拦截器到容器中。并设置其拦截路径，和不拦截路径
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/**").
                excludePathPatterns("/app/Login").
                excludePathPatterns("/app/clearVerificationCode").
                excludePathPatterns("/app/getVerificationCode");
    }
}
