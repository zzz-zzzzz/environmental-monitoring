package com.tsu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tsu.entity.LoginHistory;
import com.tsu.entity.SetRelaysHistory;
import com.tsu.mapper.SetRelaysHistoryMapper;
import com.tsu.service.SetRelaysHistoryService;
import com.tsu.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author ZZZ
 * @create 2020/9/29/14:43
 */

@Service
public class SetRelaysHistoryServiceImpl implements SetRelaysHistoryService {
    @Autowired
    private SetRelaysHistoryMapper setRelaysHistoryMapper;

    @Override
    public void save(SetRelaysHistory setRelaysHistory) {
        setRelaysHistoryMapper.save(setRelaysHistory);
    }

    @Override
    public PageInfo<SetRelaysHistory> findByPageAndDateAndDeviceKey(Integer page, Integer size, Date beginTime, Date endTime, String deviceKey) {
        page = PageUtil.checkPageNum(page);
        size = PageUtil.checkSizeNum(size);
        PageHelper.startPage(page, size);
        List<SetRelaysHistory> setRelaysHistoryList = setRelaysHistoryMapper.findByDateAndDeviceKey(beginTime, endTime, deviceKey);
        return new PageInfo<SetRelaysHistory>(setRelaysHistoryList);
    }

    /**
     * 操作数
     * 00 01 10 11 代表 加温 减温 加湿  
     * @param handlerNum
     */
    @Override
    public void handlerRelay(Integer handlerNum) {

    }
}
