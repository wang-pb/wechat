package com.tkj.wechat.userapi.controller;

import com.tkj.wechat.domain.Resource;
import com.tkj.wechat.userapi.annotation.TeacherLogin;
import com.tkj.wechat.userapi.service.ResourceService;
import com.tkj.wechat.util.ApiReturnUtil;
import com.tkj.wechat.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;


    @PostMapping("upload_id_card_front")
    public Object uploadIdCardFront(@TeacherLogin Integer teacherId, @RequestBody Resource uploadResource){
        if (null == teacherId){
            return ApiReturnUtil.unlogin();
        }
        String idCardFileName = uploadResource.getFileName();
        Resource idCard = resourceService.insertIdCardByUserId(teacherId,idCardFileName,StatusCode.RESOURCE_TYPE_IDENTITY_FRONT);
        return  idCard;
    }

    @PostMapping("upload_id_card_reverse")
    public Object uploadIdCardReverse(@TeacherLogin Integer teacherId, @RequestBody Resource resource){
        if (null == teacherId){
            return ApiReturnUtil.unlogin();
        }
        String idCardFileName = resource.getFileName();
        Resource idCard = resourceService.insertIdCardByUserId(teacherId,idCardFileName,StatusCode.RESOURCE_TYPE_IDENTITY_REVERSE);
        return  idCard;
    }


//    @PostMapping("upload_")

}
