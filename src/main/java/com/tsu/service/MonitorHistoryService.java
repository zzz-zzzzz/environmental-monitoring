package com.tsu.service;

import com.github.pagehelper.PageInfo;
import com.tsu.entity.MonitorHistory;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ZZZ
 * @create 2020/10/9/20:00
 */
public interface MonitorHistoryService {
    void save(MonitorHistory monitorHistory);

    PageInfo<MonitorHistory> findByPage(Integer page, Integer size, Date beginTime, Date endTime, String deviceKey);

    Map<String,Object> queryLastFourteenDays(String deviceKey);

    Map<String, Object> findLastFiveHours(String deviceKey) throws ParseException;
}
