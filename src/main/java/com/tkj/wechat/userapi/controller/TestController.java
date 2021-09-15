package com.tkj.wechat.userapi.controller;

import com.tkj.wechat.domain.ViewUserVisableInfo;
import com.tkj.wechat.domain.WechatUser;
import com.tkj.wechat.selfdomain.request.WechatInfo;
import com.tkj.wechat.userapi.annotation.WechatUserLogin;
import com.tkj.wechat.userapi.service.ResourceService;
import com.tkj.wechat.userapi.service.UploadFileService;
import com.tkj.wechat.userapi.service.UserTokenService;
import com.tkj.wechat.userapi.service.WechatUserService;
import com.tkj.wechat.util.ApiReturnUtil;
import com.tkj.wechat.util.JwtHelper;
import com.tkj.wechat.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("wechat-test-api")
@Validated
public class TestController {


    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private ResourceService resourceService;

    @GetMapping("test")
    public Object test(){
//        return ApiReturnUtil.get_return_json(0,"TEST API",null).toJSONString();
        Integer userId = 1;
        JwtHelper jwtHelper = new JwtHelper();
        String token = jwtHelper.createToken(userId, StatusCode.IS_TEACHER_STUDENT);
        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("token",token);
        return ApiReturnUtil.ok(data);
//        return jwtHelper.createToken(1, StatusCode.IS_TEACHER_STUDENT);
    }

    @PostMapping("login_without_check")
    public Object login(@RequestBody WechatInfo wechatInfo){

        String code = null;
        if (wechatInfo == null || (code = wechatInfo.getCode()) == null  ) {
            return ApiReturnUtil.err(101,"BAD REQUET");
        }

        String sessionKey = null;
        String openId = null;
        String url = wechatInfo.getAvatarUrl();
        Integer isTeacher = wechatInfo.getIsTeacher();

        //暂用code作为openid和sessionKey完成虚假登录。
        sessionKey = openId = wechatInfo.getCode();
        if (sessionKey == null || openId == null) {
            return ApiReturnUtil.err(102,"FAKE USER");
        }

        WechatUser user = null;
        if( isTeacher == StatusCode.IS_TEACHER_TEACHER){
            user = wechatUserService.getTeacherByOpenId(openId);
        }else{
            user = wechatUserService.getStudentByOpenId(openId);
        }
        if (user == null) {
//            String AvatorName = uploadFileService.downLoadFileFromUrl(url);

            user = new WechatUser();
            user.setOpenId(openId);
            user.setNickName(wechatInfo.getNickName());
            user.setGender(wechatInfo.getGender());
            user.setState(0);
            user.setIsTeacher(isTeacher);
            user.setAvailableMoney(new BigDecimal(0.00));
            user.setFreezeMoney(new BigDecimal(0.00));
            user.setLastLoginTime(LocalDateTime.now());
            user.setSessionKey(sessionKey);

            //  邀请人加载逻辑
            if (null != wechatInfo.getInviterId()){
                Integer inviterId = wechatInfo.getInviterId();
                WechatUser inviter = wechatUserService.getUserById(inviterId);
                if(null != inviter){
                    user.setInviterId(inviter.getId());
                }
            }

            user = wechatUserService.add(user);
            String avatorName = uploadFileService.downLoadFileFromUrl(url);
            resourceService.insertAvatar(user,avatorName);


        } else {
            user.setLastLoginTime(LocalDateTime.now());
            user.setSessionKey(sessionKey);
            if (wechatUserService.updateWechatUser(user) == 0) {
                return ApiReturnUtil.err(909,"update failed");
            }
        }

        // token
        String token = userTokenService.generateToken(user.getId(),user.getIsTeacher());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);

        ViewUserVisableInfo returnUserInfo = wechatUserService.getVisableInfoById(user.getId());
        result.put("user", returnUserInfo);

        return ApiReturnUtil.ok(result);


    }

    @GetMapping("get_id_test")
    public Object testLogin(@WechatUserLogin Integer userId){
        if(null == userId){
            return ApiReturnUtil.unlogin();
        }
        Map<Object,Object> data = new HashMap<>();
        data.put("id",userId);
        return ApiReturnUtil.ok(data);
    }

}
