package com.tkj.wechat.adminapi.service;

import com.tkj.wechat.dao.AdministratorMapper;
import com.tkj.wechat.domain.Administrator;
import com.tkj.wechat.domain.AdministratorExample;
import com.tkj.wechat.util.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class AdminUserService {

    @Autowired
    private AdministratorMapper administratorMapper;

    public Administrator verifyAdministrator(String userName, String password) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        password = new BigInteger(md5.digest(password.getBytes(StandardCharsets.UTF_8))).toString(16);
        AdministratorExample example = new AdministratorExample();
//        System.out.println(userName+"\t"+password);
        example.or().andUserNameEqualTo(userName).andPasswordEqualTo(password).andIsDeleteEqualTo(Administrator.NOT_DELETED);
        Administrator ret = null;
        try{
            ret = administratorMapper.selectOneByExample(example);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return ret;

    }

    public String generateToken(Integer userId){
        JwtHelper jwtHelper = new JwtHelper();
        return jwtHelper.createAdminToken(userId);
    }

    public Administrator getValidAdminById(Integer adminId){
        AdministratorExample example = new AdministratorExample();
        example.or().andIdEqualTo(adminId).andIsDeleteEqualTo(Administrator.NOT_DELETED);
        return administratorMapper.selectOneByExample(example);

    }

    /**
     * 根据用户名获取userid
     * @param username
     * @return
     */
    public Integer getUseridByUsername(String username) {
        return administratorMapper.selectIdByUsername(username);
    }
}
