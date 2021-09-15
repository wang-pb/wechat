package com.tkj.wechat.userapi.service;

import com.tkj.wechat.dao.ResourceMapper;
import com.tkj.wechat.domain.Resource;
import com.tkj.wechat.domain.ResourceExample;
import com.tkj.wechat.domain.WechatUser;
import com.tkj.wechat.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {

    @Value("${cos.baseurl}")
    private String baseUrl;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private WechatUserService wechatUserService;

    public Resource getById(Integer id){
        try{
            return resourceMapper.selectByPrimaryKey(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public String getUrl(String file_name){
        return baseUrl + file_name;
    }

    public Integer insertResource(Resource resource){
        try{
            resourceMapper.insert(resource);
        }catch(Exception e){
            e.printStackTrace();
            return 404;

        }
        return 0;
    }

    public Integer insertAvatar(WechatUser wechatUser, Resource resource){

        try{

            resource.setOwnerId(wechatUser.getId());
            resource.setType(StatusCode.RESOURCE_TYPE_AVATOR);
            resource.setIsDelete(Resource.NOT_DELETED);
            resourceMapper.insert(resource);
            Integer id = resource.getId();
//            System.out.println(id);

            wechatUser.setDefaultAvatarId(resource.getId());
            wechatUserService.updateWechatUser(wechatUser);
        }catch (Exception e){
            e.printStackTrace();
            return 403;
        }
        return resource.getId();
//        wechatUserService.updateWechatUser()
    }

    public Integer updateResource(Resource resource){
        try{
            return resourceMapper.updateByPrimaryKey(resource);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public Integer insertAvatar(WechatUser wechatUser,String avatarName){
        Resource resource = new Resource();
        resource.setFileName(avatarName);
        return insertAvatar(wechatUser,resource);
    }

    public Integer logicalDeleteAllIdCard(Integer userId,String idCardType){
        ResourceExample example = new ResourceExample();
        example.or().andOwnerIdEqualTo(userId).andTypeEqualTo(idCardType);
        return resourceMapper.logicalDeleteByExample(example);
    }

    public Resource insertIdCardByUserId(Integer userId,String fileName,String idCardType){
        Resource idCard = new Resource();
        logicalDeleteAllIdCard(userId,idCardType);
        idCard.setIsDelete(Resource.NOT_DELETED);
        idCard.setOwnerId(userId);
        idCard.setType(idCardType);
        idCard.setFileName(fileName);
        resourceMapper.insert(idCard);
        return idCard;
    }


}
