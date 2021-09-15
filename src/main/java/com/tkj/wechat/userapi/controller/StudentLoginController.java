package com.tkj.wechat.userapi.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.tkj.wechat.domain.WechatUser;
import com.tkj.wechat.selfdomain.request.WechatInfo;
import com.tkj.wechat.userapi.service.AuthService;
import com.tkj.wechat.userapi.service.UserTokenService;
import com.tkj.wechat.userapi.service.WechatUserService;
import com.tkj.wechat.util.ApiReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth-student")
//@Import(WechatUserService.class)
public class StudentLoginController {



    @Autowired
    private WechatUserService wechatUserService;


    @Autowired
    private UserTokenService userTokenService;
//    @Autowired
//    private JwtHelper jwtHelper = new JwtHelper();

    @Autowired
    private AuthService authService;

    /**
     * @// TODO: 2021/8/29 完整代码
     * @param wechatInfo
     * @param code
     * @return
     */
    @PostMapping("login")
    public Object loginByWechat(@RequestBody WechatInfo wechatInfo){

        String code = null;
        if (wechatInfo == null || (code = wechatInfo.getCode()) == null  ) {
            return ApiReturnUtil.err(101,"BAD REQUET");
        }

        String sessionKey = null;
        String openId = null;

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

        WechatUser user = wechatUserService.getStudentByOpenId(openId);
        if (user == null) {
            user = new WechatUser();
            user.setOpenId(openId);
//            user.setAvatar(userInfo.getAvatarUrl());
            user.setNickName(wechatInfo.getNickName());
            user.setGender(wechatInfo.getGender());
            user.setState(0);

            user.setLastLoginTime(LocalDateTime.now());
            user.setSessionKey(sessionKey);

            user = wechatUserService.add(user);

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
        result.put("user", user);
        return ApiReturnUtil.ok(result);


    }
}
