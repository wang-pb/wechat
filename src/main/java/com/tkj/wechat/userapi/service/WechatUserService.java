package com.tkj.wechat.userapi.service;

import com.tkj.wechat.dao.ViewUserVisableInfoMapper;
import com.tkj.wechat.dao.WechatUserMapper;
import com.tkj.wechat.domain.*;
import com.tkj.wechat.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WechatUserService {

    @Autowired
    private WechatUserMapper wechatUserMapper;

    @Autowired
    private ViewUserVisableInfoMapper viewUserVisableInfoMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ResourceService resourceService;

    public WechatUser add(WechatUser wechatUser){
        wechatUserMapper.insert(wechatUser);
        return wechatUser;
    }

//    public Integer updateById(WechatUser wechatUser){
//        return wechatUserMapper.updateByPrimaryKey(wechatUser);
//    }

    public Integer updateWechatUser(WechatUser wechatUser){
        try{
            wechatUser.setUpdateTime(LocalDateTime.now());
            return wechatUserMapper.updateByPrimaryKey(wechatUser);

        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public WechatUser getUserById(Integer id){
        try{
            return wechatUserMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public Long getUserNumber(){
        try{
            return wechatUserMapper.countByExample(new WechatUserExample());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public WechatUser getTeacherByOpenId(String openId){
        WechatUserExample example = new WechatUserExample();
        example.or().andIsTeacherEqualTo(StatusCode.IS_TEACHER_TEACHER).andOpenIdEqualTo(openId);
        return wechatUserMapper.selectOneByExample(example);
    }

    public WechatUser getStudentByOpenId(String openId){
        WechatUserExample example = new WechatUserExample();
        example.or().andIsTeacherEqualTo(StatusCode.IS_TEACHER_STUDENT).andOpenIdEqualTo(openId);
        return wechatUserMapper.selectOneByExample(example);
    }


    public ViewUserVisableInfo getVisableInfoById(Integer id){
        ViewUserVisableInfoExample example = new ViewUserVisableInfoExample();
        example.or().andIdEqualTo(id).example();
        ViewUserVisableInfo ret = viewUserVisableInfoMapper.selectOneByExample(example);
        ret.setAvatarUrl(resourceService.getUrl(ret.getAvatarUrl()));
        return ret;

    }

    public Integer setDefaultAddress(Integer userId,Integer addressId){

        Address address = addressService.getAddressById(addressId);
        if(null == address || userId != address.getUserId()){
            return 0;
        }
        WechatUser wechatUser = new WechatUser();
        wechatUser.setDefaultAddressId(addressId);
        wechatUser.setId(userId);
        return wechatUserMapper.updateByPrimaryKeySelective(wechatUser);

    }

    public Integer deleteDefaultAddress(Integer userId){
        WechatUser wechatUser = wechatUserMapper.selectByPrimaryKey(userId);
        if(null == wechatUser){
            return 0;
        }
//        wechatUser.setId(userId);
        wechatUser.setDefaultAddressId(null);
        return wechatUserMapper.updateByPrimaryKey(wechatUser);
    }





}
