package com.tsu.controller;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.tsu.entity.Result;
import com.tsu.entity.SetRelaysHistory;
import com.tsu.entity.StatusCode;
import com.tsu.service.SetRelaysHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * @author ZZZ
 * @create 2020/10/16/19:08
 */

@CrossOrigin
@RestController
@RequestMapping("/setRelaysHistory")
@Slf4j
public class SetRelaysHistoryController {

    @Autowired
    private SetRelaysHistoryService setRelaysHistoryService;

    //查询继电器修改的记录
    @PostMapping("/findByPage/{page}/{size}")
    public Result findByPage(@PathVariable Integer page, @PathVariable Integer size, @RequestBody Map<String, Object> param) {
        Date beginTime = DateUtil.parse((String) param.get("beginTime"));
        Date endTime = DateUtil.parse((String) param.get("endTime"));
        String deviceKey = (String) param.get("deviceKey");
        PageInfo<SetRelaysHistory> pageInfo = setRelaysHistoryService.findByPageAndDateAndDeviceKey(page, size, beginTime, endTime, deviceKey);
        return new Result(StatusCode.EXECUTE_SUCCESS, "查询成功", pageInfo);
    }

}
