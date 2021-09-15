package com.tkj.wechat.userapi.controller;

import com.tkj.wechat.domain.Authentication;
import com.tkj.wechat.userapi.annotation.TeacherLogin;
import com.tkj.wechat.userapi.service.UserAuthenticationService;
import com.tkj.wechat.util.ApiReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("authen")
public class AuthenticationController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @PostMapping("submit_authentication")
    public Object submitAuthentication(@TeacherLogin Integer teacherId, @RequestBody Authentication authentication){
        if(null == teacherId){
            return ApiReturnUtil.unlogin();
        }
        if(null == authentication || null == authentication.getAuthenticationType()
        || null == authentication.getIdentityName() || null == authentication.getIdentityName()
        || null == authentication.getIdentityCardFrontId() || null == authentication.getIdentityCardReverseId()
        || null == authentication.getEducationCode()  || null == authentication.getRecord()){
            return ApiReturnUtil.err(400,"NOT COMPLETE");
//            return
        }

        if(!userAuthenticationService.noCurrentAuthentication(teacherId)){
            return ApiReturnUtil.err(400,"有审核正在进行");
        }
        authentication.setUserId(teacherId);
        authentication = userAuthenticationService.StartAnAuthentiacation(authentication);
        return ApiReturnUtil.ok(authentication);
    }

    @PostMapping("recall_authentication")
    public Object recallAuthentication(@TeacherLogin Integer teacherId, @RequestBody Authentication authentication){
        if(null == teacherId){
            return ApiReturnUtil.unlogin();
        }
        if(null == authentication || null == authentication.getId()){
            return ApiReturnUtil.err(405,"BAD_REQUEST");
        }
        authentication.setUserId(teacherId);
        Integer lines = userAuthenticationService.recallAuthentication(authentication);
        if(lines == 0){
            return ApiReturnUtil.err(0,"nothing to do");
        }
        return ApiReturnUtil.ok(authentication);
    }

    @PostMapping("update_authentication")
    public Object updateAuthentication(@TeacherLogin Integer teacherId,@RequestBody Authentication authentication){
        if(null == teacherId){
            return ApiReturnUtil.unlogin();
        }
        if(null == authentication || null == authentication.getId()){
            return ApiReturnUtil.err(405,"BAD_REQUEST");
        }
        authentication.setUserId(teacherId);
        Integer cnt = userAuthenticationService.updateAuthentication(authentication);
        if(cnt != 1){
            return ApiReturnUtil.err(555,cnt.toString() + "lines effected");
        }
        return ApiReturnUtil.ok(authentication);

    }

    @GetMapping("get_authentication_list")
    public Object getAuthenticationList(@TeacherLogin Integer teacherId){
        if(null == teacherId){
            return ApiReturnUtil.unlogin();
        }
        List<Authentication> ret = userAuthenticationService.getAuthenticationList(teacherId);
        return ApiReturnUtil.ok(ret);

    }

}
