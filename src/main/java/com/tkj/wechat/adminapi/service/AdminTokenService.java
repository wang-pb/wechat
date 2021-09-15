package com.tkj.wechat.adminapi.service;

import com.tkj.wechat.util.JwtHelper;
import org.springframework.stereotype.Service;

@Service
public class AdminTokenService {

    public static Integer verifyAdminUserId(String token){
        JwtHelper jwtHelper = new JwtHelper();
        return jwtHelper.verifyAdminTokenAndGetUserId(token);
    }

}
