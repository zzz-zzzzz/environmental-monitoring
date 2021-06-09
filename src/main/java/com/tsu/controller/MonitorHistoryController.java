package com.tsu.controller;

import com.github.pagehelper.PageInfo;
import com.tsu.entity.MonitorHistory;
import com.tsu.entity.Result;
import com.tsu.entity.StatusCode;
import com.tsu.service.MonitorHistoryService;
import com.tsu.utils.MyDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * @author ZZZ
 * @create 2020/10/16/19:04
 */

@RestController
@CrossOrigin
@RequestMapping("/monitorHistory")
public class MonitorHistoryController {

    @Autowired
    private MonitorHistoryService monitorHistoryService;

    //查询历史分页
    @PostMapping("/findByPage/{page}/{size}")
    public Result findByPage(@PathVariable Integer page, @PathVariable Integer size, @RequestBody Map<String, Object> param) {
        Date beginTime = MyDateUtil.stringToDate(param.get("beginTime").toString());
        Date endTime = MyDateUtil.stringToDate(param.get("endTime").toString());
        PageInfo<MonitorHistory> pageInfo = monitorHistoryService.findByPage(page, size, beginTime, endTime, (String) param.get("deviceKey"));
        return new Result(StatusCode.EXECUTE_SUCCESS, "查询成功", pageInfo);
    }


    //查询指定设备的最近14天记录
    @GetMapping("/findLastFourteenDays")
    public Result findLastFourteenDays(String deviceKey) {
        Map<String, Object> map = monitorHistoryService.queryLastFourteenDays(deviceKey);
        return new Result(StatusCode.EXECUTE_SUCCESS, "查询成功", map);
    }

    //查询指定设备的最近五小时的记录
    @GetMapping("/findLastFiveHours")
    public Result findLastFiveHours(String deviceKey) throws ParseException {
        Map<String, Object> map=monitorHistoryService.findLastFiveHours(deviceKey);
        return new Result(StatusCode.EXECUTE_SUCCESS, "查询成功", map);
    }
}
