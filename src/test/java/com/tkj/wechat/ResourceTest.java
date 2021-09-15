package com.tkj.wechat;

import com.alibaba.fastjson.JSONObject;
import com.tkj.wechat.domain.Resource;
import com.tkj.wechat.domain.WechatUser;
import com.tkj.wechat.selfdomain.request.WechatInfo;
import com.tkj.wechat.userapi.service.ResourceService;
import com.tkj.wechat.userapi.service.WechatUserService;
import com.tkj.wechat.util.LocalFileUtil;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@SpringBootTest
@MapperScan("com.tkj.wechat.dao")
public class ResourceTest {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private WechatUserService wechatUserService;


    @Test
    public void TestInsert(){
        Resource avator = new Resource();
        avator.setFileName("983c317b8dfd2632635835d984e18a8999fe2c12");
        WechatUser wechatUser = wechatUserService.getUserById(23);
        resourceService.insertAvatar(wechatUser,avator);

    }

    @Test
    public void giveFakeUsersAvator() throws IOException {
        List<String> hashList = LocalFileUtil.readlinesFromFile("head_hash.txt");
        int idx = 0;
        Long totalUser = wechatUserService.getUserNumber();
        for(int i=0;i<totalUser;++i){
            WechatUser wechatUser = wechatUserService.getUserById(i);
            idx = i % hashList.size();
            Resource avator = new Resource();
            avator.setFileName(hashList.get(idx));
            Integer state = resourceService.insertAvatar(wechatUser,avator);
//            System.out.println(state);
        }

    }

    @Test
    public void getAnUserInfo(){
        WechatUser wechatUser = wechatUserService.getUserById(23);
        WechatInfo wechatInfo = new WechatInfo();
        wechatInfo.setCity("aa");
        wechatInfo.setGender(wechatUser.getGender());
        wechatInfo.setNickName(wechatInfo.getNickName());
        Resource resource = resourceService.getById(566);
        wechatInfo.setAvatarUrl(resourceService.getUrl(resource.getFileName()));
        System.out.println(JSONObject.toJSON(wechatInfo));
    }

    public void test(){
        SortedSet<Integer> s = new TreeSet<Integer>();
        ;
    }
}
