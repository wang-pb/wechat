package com.tkj.wechat.adminapi.controller;

import com.tkj.wechat.adminapi.annotation.AdminLogin;
import com.tkj.wechat.adminapi.service.AdminAuthenticationService;
import com.tkj.wechat.domain.Authentication;
import com.tkj.wechat.util.ApiReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin_auth")
public class AdminAuthController {

    @Autowired
    private AdminAuthenticationService adminAuthenticationService;

    @PostMapping("accept")
    public Object acceptAuthentication(@AdminLogin Integer adminId, @RequestBody Authentication authentication){
        if(null == adminId){
            return ApiReturnUtil.unlogin();
        }
        if(null == authentication || null == authentication.getId()){
            return ApiReturnUtil.err(500,"BAD REQUEST");
        }
        int cnt = adminAuthenticationService.approveAuthentication(authentication.getId());
        if(cnt == 0){
            return ApiReturnUtil.err(500,"NO THING TO DO");
        }
        return ApiReturnUtil.ok(cnt);
    }

    @PostMapping("deny")
    public Object denyAuthentication(@AdminLogin Integer adminId, @RequestBody Authentication authentication){
        if(null == adminId){
            return ApiReturnUtil.unlogin();
        }
        if(null == authentication || null == authentication.getId()){
            return ApiReturnUtil.err(500,"BAD REQUEST");
        }
        int cnt = adminAuthenticationService.denyAuthentication(authentication.getId());
        if(cnt == 0){
            return ApiReturnUtil.err(500,"nothing to du");
        }
        return ApiReturnUtil.ok(cnt);
    }
}
