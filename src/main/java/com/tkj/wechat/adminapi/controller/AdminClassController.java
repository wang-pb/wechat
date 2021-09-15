package com.tkj.wechat.adminapi.controller;

import com.tkj.wechat.adminapi.annotation.AdminLogin;
import com.tkj.wechat.adminapi.service.AdminClassTypeService;
import com.tkj.wechat.adminapi.service.AdminUserService;
import com.tkj.wechat.domain.Administrator;
import com.tkj.wechat.domain.ClassType;
import com.tkj.wechat.util.ApiReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin_class")
public class AdminClassController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AdminClassTypeService classTypeService;

    @PostMapping("insert_class_type")
    public Object insertClassType(@AdminLogin Integer adminId, @RequestBody ClassType classType){
        Administrator administrator = adminUserService.getValidAdminById(adminId);
        if(null == administrator){
            return ApiReturnUtil.unlogin();
        }
        classTypeService.insertClassType(classType);
        return ApiReturnUtil.ok(classType);
    }

}
