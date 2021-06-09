package com.tsu.controller;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.tsu.entity.LoginHistory;
import com.tsu.entity.Result;
import com.tsu.entity.StatusCode;
import com.tsu.service.LoginHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * @author ZZZ
 * @create 2020/10/16/18:58
 */

@RestController
@CrossOrigin
@RequestMapping("/loginHistory")
public class LoginHistoryController {

    @Autowired
    private LoginHistoryService loginHistoryService;

    //查询登陆的记录
    @PostMapping("/findByPage/{page}/{size}")
    public Result findByPage(@PathVariable Integer page, @PathVariable Integer size, @RequestBody Map<String, Object> param) {
        Date beginTime = DateUtil.parse((String) param.get("beginTime"));
        Date endTime = DateUtil.parse((String) param.get("endTime"));
        PageInfo<LoginHistory> pageInfo = loginHistoryService.findByPageAndDate(page, size, beginTime, endTime);
        return new Result(StatusCode.EXECUTE_SUCCESS, "查询成功", pageInfo);
    }
}
