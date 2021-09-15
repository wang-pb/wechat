package com.tkj.wechat.userapi.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.tkj.wechat.domain.ViewUserVisableInfo;
import com.tkj.wechat.domain.WechatUser;
import com.tkj.wechat.selfdomain.request.WechatInfo;
import com.tkj.wechat.userapi.annotation.WechatUserLogin;
import com.tkj.wechat.userapi.service.*;
import com.tkj.wechat.util.ApiReturnUtil;
import com.tkj.wechat.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("wechatlogin")
@Validated
public class UserLoginController {

    @Autowired
    private WechatUserService wechatUserService;


    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UploadFileService uploadFileService;


    @PostMapping("login")
    public Object loginByWechat(@RequestBody WechatInfo wechatInfo){
        System.out.println(wechatInfo);

        String code = null;
        if (wechatInfo == null || (code = wechatInfo.getCode()) == null || null == wechatInfo.getIsTeacher()) {
//            return ApiReturnUtil.err(101,"BAD REQUET");
            return ApiReturnUtil.debug(wechatInfo);
        }


        String sessionKey = null;
        String openId = null;
        String url = wechatInfo.getAvatarUrl();
        Integer isTeacher = wechatInfo.getIsTeacher();

        try {
            WxMaService wxMaService = authService.getMxMaService();
            WxMaJscode2SessionResult result = wxMaService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();


        } catch (Exception e) {
            e.printStackTrace();

        }

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


        System.out.println(user);

        // token
        String token = userTokenService.generateToken(user.getId(),user.getIsTeacher());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);

        ViewUserVisableInfo returnUserInfo = wechatUserService.getVisableInfoById(user.getId());
        result.put("user", returnUserInfo);
        return ApiReturnUtil.ok(result);


    }

    @GetMapping("renew")
    public Object renewLoginState(@WechatUserLogin Integer userId){
        if (null == userId){
            return ApiReturnUtil.unlogin();
        }
        Map<Object,Object> data = new HashMap<>();
        WechatUser wechatUser = wechatUserService.getUserById(userId);
        data.put(StatusCode.LOGIN_TOKEN_KEY,UserTokenService.generateToken(userId,wechatUser.getIsTeacher()));

        return ApiReturnUtil.ok(data);

    }

    @GetMapping("get_user_info")
    public Object getUserInfo(@WechatUserLogin Integer userId){
        if (null == userId){
            return ApiReturnUtil.unlogin();
        }
        Map<Object,Object> data = new HashMap<>();
        ViewUserVisableInfo returnUserInfo = wechatUserService.getVisableInfoById(userId);
        data.put("user", returnUserInfo);

        return ApiReturnUtil.ok(data);
    }
}
