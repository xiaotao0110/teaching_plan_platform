package com.hnieu.crtvn.controller.sys;

import com.hnieu.crtvn.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@RestController
@SuppressWarnings("unchecked")
public class TokenController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${oauth.token.url}")
    private String baseUrl;

    //用户认证申请令牌，将令牌存储到redis
    @PostMapping("/applyToken")
    public Map<String, Object> login(String username, String password, String clientId, String clientSecret) {

        String url = baseUrl +
                "?grant_type=password" +
                "&username=" + username +
                "&password=" + password +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret;

        //申请令牌信息
        ResponseEntity responseEntity = restTemplate.getForEntity(url, Object.class);
        Map<String, Object> body = (Map) responseEntity.getBody();

        saveCookie((String) body.get("access_token"));

        return body;

    }

    //将令牌存储到cookie
    private void saveCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response, ".", "/", "token", token, -1, false);

    }
}
