package com.tsu.service;

import com.tsu.entity.AlarmInfo;

/**
 * @author ZZZ
 * @create 2020/10/16/8:48
 */
public interface AlarmInfoService {

    AlarmInfo findByDeviceKey(String deviceKey);

    void update(AlarmInfo alarmInfo);

    void updateStartTime(AlarmInfo alarmInfo);
}
