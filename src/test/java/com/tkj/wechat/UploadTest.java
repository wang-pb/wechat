package com.tkj.wechat;

import com.tkj.wechat.adminapi.service.CosService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class UploadTest {

    @Autowired
    CosService cosService;

    @Test
    public void testUpload() throws IOException, NoSuchAlgorithmException {
        String localFileName = "C:\\Users\\dev\\Pictures\\Saved Pictures\\camp1.jpg";
        String ret = cosService.uploadFileToPublicBucket(localFileName);
        System.out.println(ret);
        ret = cosService.uploadFileToIdentityBucket(localFileName);
        System.out.println(ret);
        ret = cosService.getIdentityUrl("-150c251c817fcb1d833557f59baabb73.jpg");
        System.out.println(ret);
        ret = cosService.getPublicUrl("-150c251c817fcb1d833557f59baabb73.jpg");
        System.out.println(ret);
    }

}
