package com.tkj.wechat.userapi.service;

import com.tkj.wechat.dao.AddressMapper;
import com.tkj.wechat.dao.WechatUserMapper;
import com.tkj.wechat.domain.Address;
import com.tkj.wechat.domain.AddressExample;
import com.tkj.wechat.domain.WechatUser;
import com.tkj.wechat.util.LocationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AddressService {


    @Autowired
    private LocationUtil locationUtil ;


    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private WechatUserService wechatUserService;

    public Address getAddressFromAddress(String address) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Address ret =  locationUtil.getAddressFromAddress(address);
        ret.setIsDelete(Address.NOT_DELETED);
        return ret;
    }

    public Address getAddressFromCoordinate(Double lng,Double lat) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Address ret = locationUtil.getAddressFromCoordinate(lng,lat);
        ret.setIsDelete(Address.NOT_DELETED);
        return ret;
    }

    public Address getAddressById(Integer addressId){
        return addressMapper.selectByPrimaryKey(addressId);
    }


    public Integer addAddress(Integer userId,Address address,boolean isDefault){
//        Date nowDate = new Date();
        address.setIsDelete(Address.NOT_DELETED);
        address.setAddTime(LocalDateTime.now());
        address.setUpdateTime(LocalDateTime.now());
        address.setAddTime(LocalDateTime.now());
        address.setUserId(userId);

        Integer cnt = addressMapper.insert(address);
        if(cnt == 0){
            return 0;
        }
        if(isDefault){
            wechatUserService.setDefaultAddress(userId,address.getId());
        }
        return cnt;

    }


    //
    public List<Address> getAddressListByUser(Integer userId){
        AddressExample example = new AddressExample();
        example.or().andLogicalDeleted(false).andUserIdEqualTo(userId).example().orderBy("update_time desc");
        return addressMapper.selectByExample(example);
    }


    public Address getUserDefaultAddress(Integer userId){
        WechatUser wechatUser = wechatUserService.getUserById(userId);
        if(null == wechatUser ){
            return null;
        }
        if(null == wechatUser.getDefaultAddressId()){
            return null;
        }

        AddressExample example = new AddressExample();
        example.or().andLogicalDeleted(false).andUserIdEqualTo(userId).andIdEqualTo(wechatUser.getDefaultAddressId());
        Address ret = addressMapper.selectOneByExample(example);
        if(null == ret){
            wechatUserService.deleteDefaultAddress(userId);
            return null;
        }
        return ret;
    }



    //  获取默认地，如未设置默认则获取最近添加的
    public Address getOneAddressByUser(Integer userId){
        WechatUser wechatUser = wechatUserService.getUserById(userId);
        if(null == wechatUser ){
            return null;
        }
        if(null != wechatUser.getDefaultAddressId()){
            Address address = getUserDefaultAddress(userId);
            if(null != address){
                return address;
            }
        }
        AddressExample example = new AddressExample();
        example.or().andLogicalDeleted(false).andUserIdEqualTo(userId).example().orderBy("update_time desc");
        return addressMapper.selectOneByExample(example);

    }

    //将校验是否属于同一个用户
    public Integer updateAddress(Address address){
        Integer userId = address.getUserId();
        Integer addressId = address.getId();
        address.setUpdateTime(LocalDateTime.now());
        AddressExample example = new AddressExample();
        example.or().andIdEqualTo(addressId).andUserIdEqualTo(userId);
        Integer cnt = addressMapper.updateByExample(address,example);
        return cnt;

    }
    public Integer logicalDeleteAddress(Integer userId,Integer addressId){
        AddressExample example = new AddressExample();
        example.or().andUserIdEqualTo(userId).andIdEqualTo(addressId).andLogicalDeleted(false);
        return addressMapper.logicalDeleteByExample(example);

    }

}
