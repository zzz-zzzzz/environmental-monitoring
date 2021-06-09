package com.tsu.service;

import cn.hutool.json.JSONUtil;
import com.tsu.entity.AlarmInfo;
import com.tsu.entity.MonitorHistory;
import com.tsu.entity.Result;
import com.tsu.entity.StatusCode;
import com.tsu.utils.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZZZ
 * @create 2020/10/9/19:17
 */

@Service
@Slf4j
public class ScheduledService {

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private MonitorHistoryService monitorHistoryService;

    @Autowired
    private AlarmInfoService alarmInfoService;

    @Value("${target.address}")
    private String targetAddress;


    @Scheduled(cron = "0,30 * * * * *")
    public void scheduledTask() throws IOException, URISyntaxException {
        log.info("*****执行定时任务");
        HashMap<String, Object> param = new HashMap<>();
        param.put("groupId", "");
        HashMap<String, Object> header = new HashMap<>();
        header.put("userId", 36247);
        String resultJson = httpClientService.doGet(targetAddress + "/app/GetDeviceData", header, param);
        Result result = JSONUtil.toBean(resultJson, Result.class);
        if (result.getCode() != 1000) {
            return;
        }
        List<Map> data = (List<Map>) result.getData();
        data.stream().forEach(map -> {
            Integer deviceStatus = (Integer) map.get("deviceStatus");
            if (deviceStatus == 2) {
                String deviceKey = (String) map.get("deviceKey");
                List<Map> realTimeData = (List<Map>) map.get("realTimeData");
                Double tem = Double.parseDouble((String) realTimeData.get(0).get("dataValue"));
                Double hum = Double.parseDouble((String) realTimeData.get(1).get("dataValue"));
                //检查当前设备是否温度湿度，不满足其阈值
                this.checkTemAndHum(tem, hum, deviceKey);
                String deviceName = null;
                if ("123128".equals(deviceKey)) {
                    deviceName = "仓库设备一";
                }
                if ("126944".equals(deviceKey)) {
                    deviceName = "仓库设备二";
                }
                MonitorHistory monitorHistory = new MonitorHistory().
                        setDeviceKey(deviceKey).
                        setTem(tem).
                        setHum(hum).
                        setRecordTime(new Date()).
                        setDeviceName(deviceName);
                monitorHistoryService.save(monitorHistory);
            }
        });

    }

    /**
     * @param tem
     * @param hum
     * @param deviceKey
     * @Author ZK
     */
    //检查温度是否超出范围
    public void checkTemAndHum(Double tem, Double hum, String deviceKey) {
        AlarmInfo alarmInfo = alarmInfoService.findByDeviceKey(deviceKey);
        if (alarmInfo.getFlag() == 0) {
            return;
        }
        boolean returnFlag = false;
        //0：恒温   1：报警
        if (alarmInfo.getAlarmType() == 0) {
            //恒温报警
            if (alarmInfo.getTemMin() != tem || alarmInfo.getHumMin() != hum) {
                //温度或湿度改变
                returnFlag = true;
            }
        } else if (alarmInfo.getAlarmType() == 1) {
            //超过阈值报警
            if (tem > alarmInfo.getTemMax() || tem < alarmInfo.getTemMin() || hum > alarmInfo.getHumMax() || hum < alarmInfo.getHumMin()) {
                returnFlag = true;
            }
        }
        if (returnFlag) {
            Date now = new Date();
            if (alarmInfo.getStartTime() == null) {
                alarmInfo.setStartTime(now);
                alarmInfoService.updateStartTime(alarmInfo);
            } else {
                if (now.getTime() - alarmInfo.getStartTime().getTime() < 3600000) {
                    return;
                }
            }
            log.warn("***** 警告，设备：" + deviceKey + " 温度为" + tem + " 湿度为" + hum + " 超出温度湿度预设值");
            alarmInfoService.updateStartTime(alarmInfo.setStartTime(now));
            // TODO: 2020/10/16 发送短信
            // TODO: 2020/10/27 操作继电器
        } else {
            //温度正常
            alarmInfoService.updateStartTime(alarmInfo.setStartTime(null));
        }

    }
}
