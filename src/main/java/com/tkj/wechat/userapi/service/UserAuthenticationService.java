package com.tkj.wechat.userapi.service;

import com.tkj.wechat.dao.AuthenticationMapper;
import com.tkj.wechat.domain.Authentication;
import com.tkj.wechat.domain.AuthenticationExample;
import com.tkj.wechat.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserAuthenticationService {

    @Autowired
    private AuthenticationMapper authenticationMapper;

    public Authentication getDenyAuthentiacation(Integer userId){
        AuthenticationExample example = new AuthenticationExample();
        example.or().andUserIdEqualTo(userId).andStateEqualTo(StatusCode.TEACHER_AUTHENTICATION_DENY);
        Authentication authentication = authenticationMapper.selectOneByExample(example);
        return authentication;
    }

    public Authentication StartAnAuthentiacation(Authentication authentication){
//        authentication;

        Authentication oldAuthentication = getDenyAuthentiacation(authentication.getUserId());

        authentication.setState(StatusCode.TEACHER_AUTHENTICATION_SUBMITTED);
        authentication.setAddTime(LocalDateTime.now());
        authentication.setUpdateTime(LocalDateTime.now());

        System.out.println(oldAuthentication);
        if(null == oldAuthentication){
            authenticationMapper.insert(authentication);
        }else{

            authentication.setId(oldAuthentication.getId());
            System.out.println(authentication);

            int cnt = authenticationMapper.updateByPrimaryKeyWithBLOBs(authentication);
            System.out.println(cnt);
        }
        return authentication;
    }

    public boolean noCurrentAuthentication(Integer userId){
        AuthenticationExample example = new AuthenticationExample();
        example.or().andUserIdEqualTo(userId).andStateEqualTo(StatusCode.TEACHER_AUTHENTICATION_SUBMITTED);
        Long cnt = authenticationMapper.countByExample(example);
//        System.out.println(cnt);
        return cnt == 0;
    }

    //通过条件限定，选择要更新的行，除非选中否则不更新，通过影响的行数判断是否更新成功
    public Integer recallAuthentication(Authentication authentication){
        if(null == authentication || null == authentication.getId() || null == authentication.getUserId())
            return 0;
        AuthenticationExample example = new AuthenticationExample();
        authentication.setUpdateTime(LocalDateTime.now());
        example.or().andIdEqualTo(authentication.getId()).andUserIdEqualTo(authentication.getUserId()).andStateEqualTo(StatusCode.TEACHER_AUTHENTICATION_SUBMITTED).example()
                .or().andIdEqualTo(authentication.getId()).andUserIdEqualTo(authentication.getUserId()).andStateEqualTo(StatusCode.TEACHER_AUTHENTICATION_DENY).example();
//        authentication.setState(StatusCode.TEACHER_AUTHENTICATION_SUBMITTED);
        authentication = new Authentication();
        authentication.setState(StatusCode.TEACHER_AUTHENTICATION_RECALL);
        authentication.setUpdateTime(LocalDateTime.now());
        return authenticationMapper.updateByExampleSelective(authentication,example);
    }

    public Integer updateAuthentication(Authentication authentication){
        if(null == authentication || null == authentication.getId() || null == authentication.getUserId())
            return 0;
        AuthenticationExample example = new AuthenticationExample();
        authentication.setUpdateTime(LocalDateTime.now());
        example.or().andIdEqualTo(authentication.getId()).andUserIdEqualTo(authentication.getUserId()).andStateEqualTo(StatusCode.TEACHER_AUTHENTICATION_SUBMITTED).example()
                .or().andIdEqualTo(authentication.getId()).andUserIdEqualTo(authentication.getUserId()).andStateEqualTo(StatusCode.TEACHER_AUTHENTICATION_DENY).example();
        authentication.setState(StatusCode.TEACHER_AUTHENTICATION_SUBMITTED);
        return authenticationMapper.updateByExampleSelective(authentication,example);
    }

    public List<Authentication> getAuthenticationList(Integer userId){
        AuthenticationExample example = new AuthenticationExample();
        example.or().andUserIdEqualTo(userId);
        return authenticationMapper.selectByExample(example);
    }


    public Boolean verifyTeacherIdAndAuthenticationId(Authentication authentication){
        if(null == authentication || null == authentication.getId() || null == authentication.getUserId())
            return false;
        Authentication tgt = authenticationMapper.selectByPrimaryKey(authentication.getId());
        return  (tgt.getUserId() == authentication.getUserId());
    }

}
