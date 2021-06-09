package com.tsu.service;

import com.github.pagehelper.PageInfo;
import com.tsu.entity.LoginHistory;
import com.tsu.entity.SetRelaysHistory;

import java.util.Date;

/**
 * @author ZZZ
 * @create 2020/9/29/14:42
 */

public interface SetRelaysHistoryService {
    void save(SetRelaysHistory setRelaysHistory);

    PageInfo<SetRelaysHistory> findByPageAndDateAndDeviceKey(Integer page, Integer size, Date beginTime, Date endTime, String deviceKey);

    void handlerRelay(Integer handlerNum);

}
