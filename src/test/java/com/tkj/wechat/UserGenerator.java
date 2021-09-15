package com.tkj.wechat;

import cn.binarywang.tools.generator.ChineseIDCardNumberGenerator;
import cn.binarywang.tools.generator.ChineseNameGenerator;
import cn.binarywang.tools.generator.base.GenericGenerator;
import com.tkj.wechat.domain.WechatUser;
import com.tkj.wechat.userapi.service.WechatUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserGenerator {

    @Autowired
    private WechatUserService wechatUserService;

    private GenericGenerator chineseIDCardNumberGenerator = ChineseIDCardNumberGenerator.getInstance();
    private GenericGenerator nameGeneratoe = ChineseNameGenerator.getInstance();

    @Test
    public void generateIdentity(){
       Integer userLimit = Math.toIntExact(wechatUserService.getUserNumber());
       for(int i=1;i<userLimit;++i){
           WechatUser wechatUser = wechatUserService.getUserById(i);
           wechatUser.setIdentityNumber(chineseIDCardNumberGenerator.generate());
           wechatUser.setIdentityName(nameGeneratoe.generate());
           wechatUserService.updateWechatUser(wechatUser);
       }


    }
}
