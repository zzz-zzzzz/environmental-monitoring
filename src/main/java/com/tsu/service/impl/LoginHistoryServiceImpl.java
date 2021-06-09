package com.tsu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tsu.entity.LoginHistory;
import com.tsu.mapper.LoginHistoryMapper;
import com.tsu.service.LoginHistoryService;
import com.tsu.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author ZZZ
 * @create 2020/9/28/19:37
 */

@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {
    @Autowired
    private LoginHistoryMapper loginHistoryMapper;

    @Override
    @Transactional
    public void save(LoginHistory loginHistory) {
        loginHistoryMapper.save(loginHistory);
    }

    @Override
    public PageInfo<LoginHistory> findByPageAndDate(Integer page, Integer size, Date beginTime, Date endTime) {
        page = PageUtil.checkPageNum(page);
        size = PageUtil.checkSizeNum(size);
        PageHelper.startPage(page, size);
        List<LoginHistory> loginHistoryList = loginHistoryMapper.findByDate(beginTime, endTime);
        return new PageInfo<LoginHistory>(loginHistoryList);
    }

}
