package com.tkj.wechat.userapi.service;

import com.tkj.wechat.dao.ResourceMapper;
import com.tkj.wechat.dao.ViewClassTypePosterMapper;
import com.tkj.wechat.dao.ViewUserPosterMapper;
import com.tkj.wechat.domain.*;
import com.tkj.wechat.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicDisplayService implements PictureService{

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private ViewUserPosterMapper viewUserPosterMapper;

    @Autowired
    private ViewClassTypePosterMapper viewClassTypePosterMapper;

    public List<Picture> getCourselList(String type){
        //ResourceExample example = new ResourceExample();
        //example.or().andLogicalDeleted(false).andTypeEqualTo(StatusCode.RESOURCE_TYPE_CAROUSEL).example().orderBy("sequence");
        //0为轮播图，1为教师海报
        if ("1".equals(type)){
            type = StatusCode.RESOURCE_TYPE_TEACHER_POSTER;
        }else if ("0".equals(type)){
            type = StatusCode.RESOURCE_TYPE_CAROUSEL;
        }
        return resourceMapper.selectResByType(type);
    }

    public List<ViewUserPoster> getAllTeacherPoster(){

        ViewUserPosterExample example = new ViewUserPosterExample().or().andTypeEqualTo(StatusCode.RESOURCE_TYPE_TEACHER_POSTER).example().orderBy("sequence asc");
        return viewUserPosterMapper.selectByExampleWithBLOBs(example);

    }
    public List<ViewUserPoster> getAllStudentPoster(){

        ViewUserPosterExample example = new ViewUserPosterExample().or().andTypeEqualTo(StatusCode.RESOURCE_TYPE_TEACHER_POSTER).example().orderBy("sequence asc");
        return viewUserPosterMapper.selectByExampleWithBLOBs(example);

    }

    public List<ViewClassTypePoster> getAllClassPoster(){
        ViewClassTypePosterExample example  = new ViewClassTypePosterExample().orderBy("sequence asc");
        return viewClassTypePosterMapper.selectByExampleWithBLOBs(example);
    }

}
