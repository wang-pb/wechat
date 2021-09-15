package com.tkj.wechat.adminapi.service;

import com.tkj.wechat.dao.AuthenticationMapper;
import com.tkj.wechat.dao.WechatUserMapper;
import com.tkj.wechat.domain.Authentication;
import com.tkj.wechat.domain.AuthenticationExample;
import com.tkj.wechat.domain.WechatUser;
import com.tkj.wechat.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthenticationService {

    @Autowired
    private AuthenticationMapper authenticationMapper;

    @Autowired
    private WechatUserMapper wechatUserMapper;

    public Integer approveAuthentication(Integer authId){
        AuthenticationExample example = new AuthenticationExample();
        example.or().andStateEqualTo(StatusCode.TEACHER_AUTHENTICATION_SUBMITTED).andIdEqualTo(authId);
//        Authentication authentication = authenticationMapper.selectByPrimaryKey(authId)
        Authentication authentication = authenticationMapper.selectByPrimaryKey(authId);
        authentication.setState(StatusCode.TEACHER_AUTHENTICATION_ACCEPTED);
        Integer cnt = authenticationMapper.updateByExample(authentication,example);
        if(cnt == 1){
            WechatUser wechatUser = wechatUserMapper.selectByPrimaryKey(authentication.getUserId());
            wechatUser.setIdentityNumber(authentication.getIdentityNumber());
            wechatUser.setIdentityName(authentication.getIdentityName());
            wechatUser.setState(authentication.getAuthenticationType());
            wechatUserMapper.updateByPrimaryKey(wechatUser);
        }
        return cnt;
    }

    public Integer denyAuthentication(Integer authId){
        AuthenticationExample example = new AuthenticationExample();
        example.or().andStateEqualTo(StatusCode.TEACHER_AUTHENTICATION_SUBMITTED).andIdEqualTo(authId);
        Authentication authentication = authenticationMapper.selectByPrimaryKey(authId);
        authentication.setState(StatusCode.TEACHER_AUTHENTICATION_DENY);
        Integer cnt = authenticationMapper.updateByExample(authentication,example);
        return cnt;
    }

}
