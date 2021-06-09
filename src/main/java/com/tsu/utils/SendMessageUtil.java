package com.tsu.utils;

import com.tsu.service.HttpClientService;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ZZZ
 * @create 2020/10/14/15:14
 */
public class SendMessageUtil {

    public static String sendMessage(String content, String mobile) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        //云市场分配的密钥Id
        String secretId = "AKID7I9yiOdXs66UJ9IcDX2aMBWY2RGK570BfdbL";
        //云市场分配的密钥Key
        String secretKey = "54j9oZpSXif76ukM9JJ1Ji3TMqCWfM3rITJbiqKf";
        String source = "market";
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String datetime = sdf.format(cd.getTime());
        String auth = calcAuthorization(source, secretId, secretKey, datetime);
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("X-Source", source);
        headerMap.put("X-Date", datetime);
        headerMap.put("Authorization", auth);
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("content", content);
        contentMap.put("mobile", mobile);
        HttpClientService httpClientService = SpringUtil.getBean("httpClientService", HttpClientService.class);
        String url = "https://service-4lwag7c4-1253285064.gz.apigw.tencentcs.com/release/qxt_yzm_sms/?content=" + content + "&mobile=" + mobile;
        System.out.println(url);
        return httpClientService.doPost(url, headerMap, contentMap);
    }

    public static String calcAuthorization(String source, String secretId, String secretKey, String datetime)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String signStr = "x-date: " + datetime + "\n" + "x-source: " + source;
        Mac mac = Mac.getInstance("HmacSHA1");
        Key sKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(sKey);
        byte[] hash = mac.doFinal(signStr.getBytes("UTF-8"));
        String sig = new BASE64Encoder().encode(hash);
        String auth = "hmac id=\"" + secretId + "\", algorithm=\"hmac-sha1\", headers=\"x-date x-source\", signature=\"" + sig + "\"";
        return auth;
    }
}
