package com.tkj.wechat.adminapi.service;

import com.tkj.wechat.dao.ResourceMapper;
import com.tkj.wechat.dao.WechatUserMapper;
import com.tkj.wechat.domain.Resource;
import com.tkj.wechat.domain.ResourceExample;
import com.tkj.wechat.domain.WechatUser;
import com.tkj.wechat.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private WechatUserMapper wechatUserMapper;


    public Resource insertCarousel(Resource resource){
        //必填项，FILE_NAME,SEQUENCE;Id 自动生成。
        if(null == resource ||  null == resource.getFileName() || null == resource.getSequence()){
            return null;
        }
        resource.setIsDelete(Resource.NOT_DELETED);
        resource.setType(StatusCode.RESOURCE_TYPE_CAROUSEL);
        resourceMapper.insert(resource);
        return resource;
    };

    public Integer deleteCarsousel(Resource resource){
        if(null == resource || null == resource.getId()){
            return 0;
        }
        ResourceExample example = new ResourceExample();
        example.or().andIdEqualTo(resource.getId()).andTypeEqualTo(StatusCode.RESOURCE_TYPE_CAROUSEL);
        return resourceMapper.logicalDeleteByExample(example);
    }

    public Resource insertUserPoster(Resource resource){
        //必填项，FILE_NAME,SEQUENCE;Id 自动生成。
        if(null == resource ||  null == resource.getFileName() || null == resource.getSequence()||null == resource.getOwnerId()){
            return null;
        }
        Integer userID = resource.getOwnerId();
        resource.setIsDelete(Resource.NOT_DELETED);
        WechatUser wechatUser = wechatUserMapper.selectByPrimaryKey(userID);
        if(StatusCode.IS_TEACHER_TEACHER == wechatUser.getIsTeacher()){
            resource.setType(StatusCode.RESOURCE_TYPE_TEACHER_POSTER);
        }else{
            resource.setType(StatusCode.RESOURCE_TYPE_STUDENT_POSTER);
        }
        resourceMapper.insert(resource);
        return resource;
    }

    public Resource insertClassTypePoster(Resource resource){
        //必填项，FILE_NAME,SEQUENCE;Id 自动生成。
        if(null == resource ||  null == resource.getFileName() || null == resource.getSequence()||null == resource.getOwnerId()){
            return null;
        }
        Integer classTypeId = resource.getOwnerId();
        resource.setOwnerId(classTypeId);
        resource.setIsDelete(Resource.NOT_DELETED);
        resource.setType(StatusCode.RESOURCE_TYPE_CLASS_TYPE_POSTER);
        resourceMapper.insert(resource);
        return resource;
    }


    public Resource updateCarousel(Resource resource) {
        resource.setType(StatusCode.RESOURCE_TYPE_CAROUSEL);
        resourceMapper.updateByPrimaryKeySelective(resource);
        return resource;
    }

}
