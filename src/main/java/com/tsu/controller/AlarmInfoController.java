package com.tsu.controller;

import com.tsu.entity.AlarmInfo;
import com.tsu.entity.Result;
import com.tsu.entity.StatusCode;
import com.tsu.service.AlarmInfoService;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZZZ
 * @create 2020/10/16/8:44
 */

@RestController
@RequestMapping("/alarmInfo")
@CrossOrigin
public class AlarmInfoController {

    @Autowired
    private AlarmInfoService alarmInfoService;

    @GetMapping("/find")
    public Result findByDeviceKey(String deviceKey) {
        AlarmInfo alarmInfo = alarmInfoService.findByDeviceKey(deviceKey);
        return new Result(StatusCode.EXECUTE_SUCCESS, "查询成功", alarmInfo);
    }

    @PostMapping("/update")
    public Result update(@RequestBody AlarmInfo alarmInfo) {
        alarmInfoService.update(alarmInfo);
        return new Result(StatusCode.EXECUTE_SUCCESS, "修改成功", null);
    }
}
