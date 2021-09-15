package com.tkj.wechat.adminapi.controller;


import com.tkj.wechat.adminapi.service.AdminUserService;
import com.tkj.wechat.domain.Administrator;
import com.tkj.wechat.util.ApiReturnUtil;
import com.tkj.wechat.util.JwtUtil;
import com.tkj.wechat.util.StatusCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("admin")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${custom.jwt.expire_time}")
    private long expireTime;

    @RequestMapping("login")
    public Object login(Administrator administrator) throws NoSuchAlgorithmException {
        String userName = administrator.getUserName();
        String password = administrator.getPassword();
        String token = JwtUtil.sign(userName, password);
        redisTemplate.opsForValue().set("token",token, expireTime*2/100, TimeUnit.SECONDS);

        administrator = adminUserService.verifyAdministrator(userName,password);
        if(null == administrator){
            return ApiReturnUtil.wrongPassword();
        }
        //String token = adminUserService.generateToken(administrator.getId());
        Map<Object,Object> data = new HashMap<>();
        data.put(StatusCode.LOGIN_TOKEN_KEY,token);
        //redisTemplate.opsForValue().set(token,token, expireTime*2/100, TimeUnit.SECONDS);
        return ApiReturnUtil.ok("success");
    }

}
