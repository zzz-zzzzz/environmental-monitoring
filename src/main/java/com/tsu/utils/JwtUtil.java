package com.tsu.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZZZ
 * @create 2020/9/21/19:00
 */

public class JwtUtil {

    private static final String signature = "token!Q@W#E$R";

    //生成token
    public static String getToken(Map<String, String> map) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);

        JWTCreator.Builder builder = JWT.create();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.withClaim(entry.getKey(), entry.getValue());
        }
        String token = builder
                .withExpiresAt(calendar.getTime()) //设置过期时间
                .sign(Algorithm.HMAC256(signature));//signature
        return token;
    }

    //验证token
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(signature)).build().verify(token);//验证对象
    }

    //获取token里面的值
    public static Map<String, Claim> getTokenInfo(String token) {
        DecodedJWT verify = verify(token);
        return verify.getClaims();
    }
}