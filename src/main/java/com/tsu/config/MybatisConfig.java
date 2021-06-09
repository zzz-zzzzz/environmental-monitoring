package com.tsu.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZZZ
 * @create 2020/9/28/20:07
 */

@Configuration
@MapperScan("com.tsu.mapper")
public class MybatisConfig {

}
