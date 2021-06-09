package com.tsu.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tsu.entity.MonitorHistory;
import com.tsu.mapper.MonitorHistoryMapper;
import com.tsu.service.MonitorHistoryService;
import com.tsu.utils.MyDateUtil;
import com.tsu.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;

/**
 * @author ZZZ
 * @create 2020/10/9/20:01
 */

@Service
@Slf4j
public class MonitorHistoryServiceImpl implements MonitorHistoryService {
    @Autowired
    private MonitorHistoryMapper monitorHistoryMapper;

    @Qualifier("myRedisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional
    @Override
    public void save(MonitorHistory monitorHistory) {
        monitorHistoryMapper.save(monitorHistory);
    }

    @Override
    public PageInfo<MonitorHistory> findByPage(Integer page, Integer size, Date beginTime, Date endTime, String deviceKey) {
        page = PageUtil.checkPageNum(page);
        size = PageUtil.checkSizeNum(size);
        PageHelper.startPage(page, size);
        List<MonitorHistory> monitorHistoryList = monitorHistoryMapper.findByRecordTimeAndDeviceKey(beginTime, endTime, deviceKey);
        return new PageInfo<MonitorHistory>(monitorHistoryList);
    }

    @Override
    public Map<String, Object> queryLastFourteenDays(String deviceKey) {
        //查询缓存
        String salt = DateUtil.format(new Date(), "yyyy-MM-dd");
        String cache = (String) redisTemplate.opsForValue().get(deviceKey + salt);
        if (cache != null) {
            return JSONUtil.toBean(cache, Map.class);
        }
        //查询数据库
        Map<String, Object> map = new HashMap<>();
        List<Double> temList = new ArrayList<>();
        List<Double> humList = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            //获取第i天的起始时间
            Date beginTime = DateUtil.beginOfDay(new Date(System.currentTimeMillis() - i * 86400000)).toJdkDate();
            //获取第i天的结束时间
            Date endTime = new Date(beginTime.getTime() + 86399000);
            MonitorHistory monitorHistory = monitorHistoryMapper.queryByTime(beginTime, endTime, deviceKey);
            if (monitorHistory == null) {
                temList.add(22.0);
                humList.add(55.6);
            } else {
                temList.add(monitorHistory.getTem());
                humList.add(monitorHistory.getHum());
            }
        }
        map.put("tem", temList);
        map.put("hum", humList);
        //放到缓存
        redisTemplate.opsForValue().set(deviceKey + salt, JSONUtil.parse(map).toString());
        return map;
    }

    @Override
    public Map<String, Object> findLastFiveHours(String deviceKey) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        List<Double> temList = new ArrayList<>();
        List<Double> humList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Date beginTime = MyDateUtil.getBeforeHourTime(i);
            Date endTime = new Date(beginTime.getTime() + 3599999);
            MonitorHistory monitorHistory = monitorHistoryMapper.queryByTime(beginTime, endTime, deviceKey);
            if (monitorHistory == null) {
                temList.add(22.0);
                humList.add(55.6);
            } else {
                temList.add(monitorHistory.getTem());
                humList.add(monitorHistory.getHum());
            }
            map.put("tem", temList);
            map.put("hum", humList);
        }
        return map;
    }
}
