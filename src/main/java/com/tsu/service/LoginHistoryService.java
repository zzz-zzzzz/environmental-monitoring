package com.tsu.service;

import com.github.pagehelper.PageInfo;
import com.tsu.entity.LoginHistory;
import org.mybatis.spring.annotation.MapperScan;

import java.util.Date;

/**
 * @author ZZZ
 * @create 2020/9/28/19:36
 */

public interface LoginHistoryService {

    void save(LoginHistory loginHistory);


    PageInfo<LoginHistory> findByPageAndDate(Integer page, Integer size, Date beginTime, Date endTime);
}
