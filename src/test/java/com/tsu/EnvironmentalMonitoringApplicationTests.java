package com.tsu;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.json.JSONUtil;
import com.tsu.entity.MonitorHistory;
import com.tsu.entity.Result;
import com.tsu.entity.StatusCode;
import com.tsu.service.HttpClientService;
import com.tsu.service.MonitorHistoryService;
import com.tsu.utils.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class EnvironmentalMonitoringApplicationTests {

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private MonitorHistoryService monitorHistoryService;

    @Value("${target.address}")
    private String targetAddress;

    @Test
    public void test() throws IOException, URISyntaxException {
        Map<String, Object> header = new HashMap<>();
        header.put("userId", "36247");
        Map<String, Object> param = new HashMap<>();
        param.put("beginTime", "2020-9-11 00:00:00");
        param.put("endTime", "2020-10-11 00:00:00");
        param.put("deviceKey", "123128");
        param.put("nodeId", "-1");
        param.put("isAlarmData", "-1");
        String var1 = httpClientService.doGet(targetAddress + "/app/QueryHistoryList", header, param);
        Result result = JSONUtil.toBean(var1, Result.class);
        List<Map> maps = (List<Map>) result.getData();
        maps.stream().forEach(map -> {
            Double hum = (Double) map.get("Hum");
            Double tem = (Double) map.get("Tem");
            String deviceKey = (String) map.get("DeviceKey");
            Date recordTime = MyDateUtil.stringToDate((String) map.get("RecordTime"));
            MonitorHistory monitorHistory = new MonitorHistory().setDeviceKey(deviceKey).setHum(hum).setTem(tem).setDeviceName("仓库设备一").setRecordTime(recordTime);
            monitorHistoryService.save(monitorHistory);
        });
        System.out.println();
    }

}
