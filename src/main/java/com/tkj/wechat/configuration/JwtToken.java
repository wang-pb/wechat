package com.tkj.wechat.configuration;
import org.apache.shiro.authc.AuthenticationToken;
/**
 * @ClassName JwtToken
 * @Description TODO
 * @Author 幸运的大树
 * @Date 2021/9/3 15:04
 * @Version 1.0
 *
 * 实现shiro的AuthenticationToken接口的类JwtToken
 **/


public class JwtToken implements AuthenticationToken{

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
