package com.tsu.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.tsu.entity.*;
import com.tsu.service.*;
import com.tsu.utils.JwtUtil;
import com.tsu.utils.MapUtil;
import com.tsu.utils.MyDateUtil;
import com.tsu.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ZZZ
 * @create 2020/9/19/15:41
 */
@CrossOrigin
@RestController
@RequestMapping("/app")
@Slf4j
public class AppController {


    @Qualifier("myRedisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${target.address}")
    private String targetAddress;

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private AlarmInfoService alarmInfoService;


    //测试链接
    @GetMapping("/TestConnect")
    public String testConnect() throws Exception {
        return httpClientService.doGet(targetAddress + "/app/TestConnect");
    }

    //登录
    @PostMapping("/Login")
    public Result login(@RequestBody Map<String, Object> param) throws Exception {
        //通过传递过来的验证码的uuid获取真实的验证码
        Object verificationCodeImgId = param.get("verificationCodeImgId");
        if (verificationCodeImgId != null) {
            try {
                //获取前端传递过来的验证码
                String var1 = (String) param.get("identifyCode");
                if (!var1.equalsIgnoreCase((String) redisTemplate.opsForValue().get(verificationCodeImgId.toString()))) {
                    throw new RuntimeException("验证码不正确");
                }
            } catch (Exception e) {
                //抛出异常
                throw e;
            } finally {
                //删除缓存
                redisTemplate.delete(param.get("verificationCodeImgId"));
            }
        }
        //获取登录结果
        String resultJson = httpClientService.doPost(targetAddress + "/app/Login", null, param);
        //将json转化为对象
        Result result = JSONUtil.toBean(resultJson, Result.class);
        HashMap<String, String> tokenMap = new HashMap<>();
        String token = null;
        //判断返回码
        if (result.getCode() == 1000) {
            Map<String, Object> loginData = (Map<String, Object>) result.getData();
            tokenMap.put("userId", (String) loginData.get("userId"));
            tokenMap.put("userName", (String) loginData.get("username"));
            //生成token
            token = JwtUtil.getToken(tokenMap);
        }
        return new Result(result.getCode(), result.getMessage(), token);
    }

    //获取用户设备的分组列表
    @GetMapping("/GetUserDeviceGroups")
    public Result getUserDeviceGroups(HttpServletRequest request) throws IOException, URISyntaxException {
        String resultJson = httpClientService.doGet(targetAddress + "/app/GetUserDeviceGroups", MapUtil.getRequestToken(request, "userId"), null);
        Result result = JSONUtil.toBean(resultJson, Result.class);
        List<Map> data = (List<Map>) result.getData();
        data.get(0).put("groupName", "仓库设备一");
        data.get(1).put("groupName", "仓库设备二");
        HashMap<String, Object> group3 = new HashMap<>();
        HashMap<String, Object> group4 = new HashMap<>();
        HashMap<String, Object> group5 = new HashMap<>();
        group3.put("groupId", "12344");
        group3.put("groupName", "仓库设备三");
        group4.put("groupId", "12355");
        group4.put("groupName", "仓库设备四");
        group5.put("groupId", "12366");
        group5.put("groupName", "仓库设备五");
        data.add(group3);
        data.add(group4);
        data.add(group5);
        return result;
    }

    //获取用户设备的信息和实时数据
    @GetMapping("/GetDeviceData")
    public Result getDeviceData(HttpServletRequest request, Integer groupId) throws IOException, URISyntaxException {
        if (groupId == 12344 || groupId == 12355 || groupId == 12366) {
            List<Map> deviceList = new ArrayList<>();
            HashMap<Object, Object> device = new HashMap<>();
            deviceList.add(device);
            device.put("deviceStatus", 1);
            device.put("lng", 117.043767);
            device.put("lat", 36.23092);
            return new Result(StatusCode.EXECUTE_SUCCESS, "查询成功", deviceList);
        } else {
            HashMap<String, Object> param = new HashMap<>();
            param.put("groupId", groupId);
            String resultJson = httpClientService.doGet(targetAddress + "/app/GetDeviceData", MapUtil.getRequestToken(request, "userId"), param);
            Result result = JSONUtil.toBean(resultJson, Result.class);
            if (result.getData() != null) {
                List<Map<String, Object>> deviceList = (List<Map<String, Object>>) result.getData();
                Map<String, Object> device = deviceList.get(0);
                if (126944 == groupId) {
                    device.put("lng", 117.042869);
                    device.put("lat", 36.230229);
                } else {
                    device.put("lng", 117.043767);
                    device.put("lat", 36.23092);
                }
                AlarmInfo alarmInfo = alarmInfoService.findByDeviceKey(groupId.toString());
                if (alarmInfo != null) {
                    device.put("warning", alarmInfo.getStartTime() != null);
                }
            }
            return result;
        }
    }

    //获取设备的继电器状态
    @GetMapping("/GetRelays")
    public Result getRelays(HttpServletRequest request, Integer deviceKey) throws IOException, URISyntaxException {
        if (deviceKey == 12344 || deviceKey == 12355 || deviceKey == 12366) {
            List<Map<String, Object>> relays = Arrays.asList(0, 1, 2, 3).stream().map(item -> {
                Map<String, Object> relay = new HashMap<>();
                relay.put("relayName", item == 0 ? "加温" : item == 1 ? "制冷" : item == 2 ? "加湿" : "除湿");
                relay.put("deviceKey", deviceKey);
                relay.put("relayID", item);
                relay.put("deviceStatus", 0);
                relay.put("status", 1);
                return relay;
            }).collect(Collectors.toList());
            return new Result(StatusCode.EXECUTE_SUCCESS, "查询成功", relays);
        } else {
            HashMap<String, Object> param = new HashMap<>();
            param.put("deviceKey", deviceKey);
            String resultJson = httpClientService.doGet(targetAddress + "/app/GetRelays", MapUtil.getRequestToken(request, "userId"), param);
            Result result = JSONUtil.toBean(resultJson, Result.class);
            List<Map> data = (List<Map>) result.getData();
            List<Map> newData = data.stream().filter(map -> {
                int relayID = (int) map.get("relayID");
                if (relayID < 4) {
                    if (relayID == 0) {
                        map.put("relayName", "加温");
                    }
                    if (relayID == 1) {
                        map.put("relayName", "制冷");
                    }
                    if (relayID == 2) {
                        map.put("relayName", "加湿");
                    }
                    if (relayID == 3) {
                        map.put("relayName", "除湿");
                    }
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
            result.setData(newData);
            return result;
        }
    }

    //操作继电器状态
    @PostMapping("/SetRelays")
    public String setRelays(HttpServletRequest request, @RequestBody Map<String, Object> param) throws IOException {
        return httpClientService.doPost(targetAddress + "/app/SetRelays", MapUtil.getRequestToken(request, "userId"), param);
    }

    //返回base64的验证码
    @GetMapping("/getVerificationCode")
    public Result getVerificationCode() throws IOException {
        //产生验证码图片的。图片的宽是68，高是40，验证码的长度是4，干扰线的条数是20
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(70, 40, 4, 20);
        //获取验证码图片中的字符串
        String verificationCode = lineCaptcha.getCode();
        //将验证码放到redis中
        String uuid = IdUtil.simpleUUID();
        redisTemplate.opsForValue().set(uuid, verificationCode);

        HashMap<String, Object> map = new HashMap<>();
        map.put("verificationCodeImg", lineCaptcha.getImageBase64Data());
        map.put("verificationCodeImgId", uuid);
        return new Result(StatusCode.EXECUTE_SUCCESS, "获取成功", map);
    }

    //刷新时删除之前在redis中的验证码
    @GetMapping("/clearVerificationCode")
    public Result clearVerificationCode(@RequestParam String verificationCodeId) {
        if (verificationCodeId != null && redisTemplate.opsForValue().get(verificationCodeId) != null) {
            redisTemplate.delete(verificationCodeId);
            return new Result(StatusCode.EXECUTE_SUCCESS, "删除成功", null);
        }
        return new Result(StatusCode.EXECUTE_FAIL, "删除失败", null);
    }


    //获取设备信息
    @GetMapping("/getDeviceSituation")
    public Result getDeviceSituation(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("offline", 3);
        result.put("normal", 1);
        result.put("warning", 1);
        return new Result(StatusCode.EXECUTE_SUCCESS, "查询成功", result);
    }


}
