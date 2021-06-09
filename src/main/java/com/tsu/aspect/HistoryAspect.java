package com.tsu.aspect;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tsu.entity.LoginHistory;
import com.tsu.entity.Result;
import com.tsu.entity.SetRelaysHistory;
import com.tsu.service.LoginHistoryService;
import com.tsu.service.SetRelaysHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author ZZZ
 * @create 2020/9/28/15:46
 */

/**
 * 切面记录登录和操作继电器
 */
@Component
@Aspect
@Slf4j
public class HistoryAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginHistoryService loginHistoryService;

    @Autowired
    private SetRelaysHistoryService setRelaysHistoryService;

    @Pointcut("execution(* com.tsu.controller.AppController.login(..))")
    private void pt1() {

    }

    @Pointcut("execution(* com.tsu.controller.AppController.setRelays(..))")
    private void pt2() {

    }

    //login的切面
    @Around("pt1()")
    public Object login(ProceedingJoinPoint pjd) throws Throwable {
        Object returnValue = null;
        LoginHistory loginHistory = new LoginHistory();
        try {
            Object[] args = pjd.getArgs();
            Map<String, Object> param = (Map<String, Object>) args[0];
            loginHistory.setLoginDate(new Date()).setLoginIp(request.getRemoteAddr()).setLoginName((String) param.get("loginName"));
            returnValue = pjd.proceed(args);
            Result result = (Result) returnValue;
            loginHistory.setLoginResult(result.getMessage());
        } catch (Throwable throwable) {
            Exception e = (Exception) throwable;
            loginHistory.setLoginResult(e.getMessage());
            throw throwable;
        } finally {
            loginHistoryService.save(loginHistory);
        }
        return returnValue;
    }


    @Around("pt2()")
    public Object setRelays(ProceedingJoinPoint pjd) throws Throwable {
        Object returnValue = null;
        try {
            Object[] args = pjd.getArgs();
            returnValue = pjd.proceed(args);
            Map<String, Object> param = (Map<String, Object>) args[1];
            SetRelaysHistory setRelaysHistory = new SetRelaysHistory();
            Integer relayId = (Integer) param.get("relayID");
            setRelaysHistory.
                    setDeviceName((String) param.get("deviceName")).
                    setRelayId(relayId).
                    setSetDate(new Date()).
                    setDeviceKey((String) param.get("deviceKey")).
                    setRelayStatus((Integer) param.get("opt"));
            setRelaysHistoryService.save(setRelaysHistory);
        } catch (Throwable throwable) {
            throw throwable;
        } finally {

        }
        return returnValue;
    }


}
