package com.tkj.wechat.userapi.service;

import com.tkj.wechat.util.JwtHelper;
import com.tkj.wechat.util.StatusCode;
import org.springframework.stereotype.Service;

@Service
public class UserTokenService {
    public static String generateToken(Integer userId,Integer isTeacher){
        JwtHelper jwtHelper = new JwtHelper();
        return jwtHelper.createToken(userId,isTeacher);
    }

    public static Integer verifyStudentAndGetId(String token){
        JwtHelper jwtHelper = new JwtHelper();
        return jwtHelper.verifyTokenAndGetUserId(token, StatusCode.IS_TEACHER_TEACHER);
    }

    public static Integer verifyTeacherAndGetId(String token){
        JwtHelper jwtHelper = new JwtHelper();
        return jwtHelper.verifyTokenAndGetUserId(token,StatusCode.IS_TEACHER_STUDENT);
    }

    public static Integer verifyWechatUserAndGetId(String token){
        JwtHelper jwtHelper = new JwtHelper();
        return jwtHelper.verifyTokenAndGetUserId(token);

    }

}
