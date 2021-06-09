package com.tsu.utils;

import com.auth0.jwt.interfaces.Claim;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZZZ
 * @create 2020/9/19/15:51
 */

public class MapUtil {

    //获取请求头中的令牌
    public static Map<String, Object> getRequestToken(HttpServletRequest request, String tokenName) {
        HashMap<String, Object> var1 = new HashMap<>();
        String token = request.getHeader("token");
        var1.put(tokenName, JwtUtil.getTokenInfo(token).get("userId").asString());
        return var1;
    }
}
