package com.tkj.wechat.userapi.controller;


import com.tkj.wechat.domain.Picture;
import com.tkj.wechat.domain.Resource;
import com.tkj.wechat.domain.ViewClassTypePoster;
import com.tkj.wechat.domain.ViewUserPoster;
import com.tkj.wechat.userapi.service.PictureService;
import com.tkj.wechat.userapi.service.PublicDisplayService;
import com.tkj.wechat.userapi.service.ResourceService;
import com.tkj.wechat.util.ApiReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(("/public_display"))
public class PublicDisplayController {

    @Autowired
    private PictureService publicDisplayService;

    @Autowired
    private ResourceService resourceService;

    @Value(value = "${cos.baseUrl}")
    private String baseUrl;

    @GetMapping("get_carousel")
    public Object getCarouselControl(@RequestParam("type") String type){

        List<Picture> carouselList = publicDisplayService.getCourselList(type);

        List<Map> data = new ArrayList<Map>();
//        return null;
        for(Picture resource : carouselList){
            HashMap<String,Object> tmpMap = new HashMap<String,Object>();
            tmpMap.put("id",resource.getId());
            tmpMap.put("url",baseUrl+resource.getFileName());
            tmpMap.put("sequence",resource.getSequence());
            data.add(tmpMap);
        }

        return ApiReturnUtil.ok(data);

    }

    @GetMapping("get_teacher")
    public Object getTeacher(){

        List<ViewUserPoster> posters = publicDisplayService.getAllTeacherPoster();
        for(ViewUserPoster poster : posters){
            poster.setUrl(resourceService.getUrl(poster.getUrl()));
        }
        return ApiReturnUtil.ok(posters);

    }

    @GetMapping("get_class")
    public Object getClassTypeList(){
        List<ViewClassTypePoster> posters = publicDisplayService.getAllClassPoster();
        for(ViewClassTypePoster poster : posters){
            poster.setUrl(resourceService.getUrl(poster.getUrl()));
        }
        return ApiReturnUtil.ok(posters);
    }


}
