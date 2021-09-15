package com.tkj.wechat.adminapi.controller;

import com.tkj.wechat.adminapi.annotation.AdminLogin;
import com.tkj.wechat.adminapi.service.AdminResourceService;
import com.tkj.wechat.adminapi.service.AdminUserService;
import com.tkj.wechat.domain.Administrator;
import com.tkj.wechat.domain.Resource;
import com.tkj.wechat.util.ApiReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("admin_display")
public class AdminPublicDisplayController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AdminResourceService adminResourceService;
//    private ResourceService resourceService;

    @PostMapping("insert_carousel")
    public Object insertCarsousel(@AdminLogin Integer userId, @RequestBody Resource resource){
        Administrator administrator = adminUserService.getValidAdminById(userId);
        if(null == administrator){
            return ApiReturnUtil.unlogin();
        }

        Resource carousel = adminResourceService.insertCarousel(resource);
        return ApiReturnUtil.ok(carousel);
    }

    @PostMapping("update_carousel")
    public Object updateCarsousel(@AdminLogin Integer userId, @RequestBody Resource resource){
        Resource carousel = adminResourceService.updateCarousel(resource);
        return ApiReturnUtil.ok(carousel);
    }

    @DeleteMapping("del_carousel/{id}")
    public Object delCarsousel(@AdminLogin Integer userId, @PathVariable Integer id){
        Resource re = new Resource();
        re.setId(id);
        adminResourceService.deleteCarsousel(re);
        return ApiReturnUtil.ok(re);
    }

    @PostMapping("insertUserPoster")
    public Object insertUserPoster(@AdminLogin Integer userId, @RequestBody Resource poster){
        Administrator administrator = adminUserService.getValidAdminById(userId);
        if(null == administrator){
            return ApiReturnUtil.unlogin();
        }
        poster = adminResourceService.insertUserPoster(poster);
        return ApiReturnUtil.ok(poster);
    }

    @PostMapping("insertClassTypePoster")
    public Object insertClassTypePoster(@AdminLogin Integer adminId, @RequestBody Resource poster){
        Administrator administrator = adminUserService.getValidAdminById(adminId);
        if(null == administrator){
            return ApiReturnUtil.unlogin();
        }
        poster = adminResourceService.insertClassTypePoster(poster);
        return poster;
    }

}
