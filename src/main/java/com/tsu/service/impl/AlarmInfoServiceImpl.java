package com.tsu.service.impl;

import com.tsu.entity.AlarmInfo;
import com.tsu.mapper.AlarmInfoMapper;
import com.tsu.service.AlarmInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author ZZZ
 * @create 2020/10/16/8:49
 */

@Service
public class AlarmInfoServiceImpl implements AlarmInfoService {
    @Autowired
    private AlarmInfoMapper alarmInfoMapper;


    @Override
    public void updateStartTime(AlarmInfo alarmInfo) {
        alarmInfoMapper.updateStartTime(alarmInfo);
    }


    @Override
    public AlarmInfo findByDeviceKey(String deviceKey) {
        return alarmInfoMapper.findByDeviceKey(deviceKey);
    }

    @Transactional
    @Override
    public void update(AlarmInfo alarmInfo) {
        alarmInfoMapper.update(alarmInfo);
    }


}
