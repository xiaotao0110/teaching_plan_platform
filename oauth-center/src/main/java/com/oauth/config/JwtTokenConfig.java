package com.oauth.config;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JwtTokenConfig {

    /**
     * jwt访问token转换器
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory storeKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource("crtvn-jwt.jks"), "123456".toCharArray());
        converter.setKeyPair(storeKeyFactory.getKeyPair("crtvn-jwt"));
        return converter;
    }

    /**
     * jwt的token存储对象
     */
    @Bean
    public JwtTokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * token信息扩展
     */
    @Bean
    public TokenEnhancer jwtTokenEnhancer() {
        return new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                Authentication userAuthentication = authentication.getUserAuthentication();
                if (userAuthentication != null) {
                    Object principal = userAuthentication.getPrincipal();
                    Map map = new HashMap();
                    map.put("user", JSON.toJSONString(principal));
                    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
                }
                return accessToken;
            }
        };
    }
}