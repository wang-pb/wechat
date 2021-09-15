package com.tkj.wechat.userapi.controller;

import com.tkj.wechat.util.ApiReturnUtil;
import com.tkj.wechat.util.JwtUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * shiro控制器
 *
 */
@RestController
@RequestMapping("/shiro")
public class ShiroController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${custom.jwt.expire_time}")
    private long expireTime;

    @RequestMapping("/getToken")
    public Object getToken(){
        String token = JwtUtil.sign("wpb", "123");
        redisTemplate.opsForValue().set(token,token, expireTime*2/100, TimeUnit.SECONDS);
        return ApiReturnUtil.ok(token);
    }

    @RequiresPermissions("user:admin")
    @RequestMapping("/test")
    public Object test(){
        System.out.println("进入测试，只有带有令牌才可以进入该方法");
        return ApiReturnUtil.ok("访问接口成功");
    }
}
