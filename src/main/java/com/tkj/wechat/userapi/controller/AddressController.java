package com.tkj.wechat.userapi.controller;

import com.tkj.wechat.domain.Address;
import com.tkj.wechat.userapi.annotation.WechatUserLogin;
import com.tkj.wechat.userapi.service.AddressService;
import com.tkj.wechat.userapi.service.WechatUserService;
import com.tkj.wechat.util.ApiReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;
    @Autowired
    private WechatUserService wechatUserService;

    @GetMapping("get_address_list")
    public Object getAddressList(@WechatUserLogin Integer userId){
        if(null == userId){
            return ApiReturnUtil.unlogin();
        }
        List<Address> ret = addressService.getAddressListByUser(userId);
        return ApiReturnUtil.ok(ret);
    }

    @PostMapping("update_address")
    public Object updateAddress(@WechatUserLogin Integer userId,@RequestBody Address address){
        if(null == userId){
            return ApiReturnUtil.unlogin();
        }
        if(null == address.getArea() || null == address.getCity() || null == address.getProvince()
        || null == address.getId() || null == address.getAddressDetail() || null == address.getLat() || null == address.getLng()){
            return ApiReturnUtil.debug(address);
        }
        address.setUserId(userId);
        Integer cnt = addressService.updateAddress(address);
        if(cnt == 0){
            return ApiReturnUtil.err(400,"NOTHING TO DO");
        }
        return ApiReturnUtil.ok(address);
    }

    @PostMapping("set_default_address")
    public  Object setDefaultAddress(@WechatUserLogin Integer userId,@RequestBody Address address){
        if(null == userId){
            return ApiReturnUtil.unlogin();
        }
        if(null == address.getId()){
            return ApiReturnUtil.debug(address);
        }
        Integer cnt = wechatUserService.setDefaultAddress(userId,address.getId());
        if(cnt == 0){
            return ApiReturnUtil.nothingToDo();
        }
        return ApiReturnUtil.ok(cnt);
    }

    @GetMapping("get_default_address")
    public Object getDefaultAddress(@WechatUserLogin Integer userId){
        if (null == userId) {
            return ApiReturnUtil.unlogin();
        }
        Address address =  addressService.getUserDefaultAddress(userId);
        return ApiReturnUtil.ok(address);
    }


    @PostMapping("add_address")
    public Object addAddress(@WechatUserLogin Integer userId, @RequestBody Address address){
        if(null == userId){
            return ApiReturnUtil.unlogin();
        }
        if(null == address.getArea() || null == address.getCity() || null == address.getProvince()
                || null == address.getAddressDetail() || null == address.getLat() || null == address.getLng()){
            return ApiReturnUtil.debug(address);
        }
        addressService.addAddress(userId,address,false);
        return ApiReturnUtil.ok(address);
    }

    @PostMapping("delete_address")
    public Object deleteAddress(@WechatUserLogin Integer userId, @RequestBody Address address){
        if (null == userId) {
            return ApiReturnUtil.unlogin();
        }
        if(null == address.getId()){
            return ApiReturnUtil.debug(address);
        }
        Integer cnt = addressService.logicalDeleteAddress(userId,address.getId());
        if(cnt == 0 ){
            return ApiReturnUtil.nothingToDo();
        }
        return ApiReturnUtil.ok(cnt);
    }

    @PostMapping("add_default_address")
    public Object addDefaultAddress(@WechatUserLogin Integer userId, @RequestBody Address address){
        if (null == userId) {
            return ApiReturnUtil.unlogin();
        }
        if(null == address.getArea() || null == address.getCity() || null == address.getProvince()
                || null == address.getAddressDetail() || null == address.getLat() || null == address.getLng()){
            return ApiReturnUtil.debug(address);
        }
        addressService.addAddress(userId,address,true);
        return ApiReturnUtil.ok(address);
    }


}
