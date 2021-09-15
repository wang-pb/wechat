package com.tkj.wechat;

import com.tkj.wechat.dao.AddressMapper;
import com.tkj.wechat.dao.ResourceMapper;
import com.tkj.wechat.dao.WechatUserMapper;
import com.tkj.wechat.domain.*;
import com.tkj.wechat.userapi.service.AddressService;
import com.tkj.wechat.userapi.service.PublicDisplayService;
import com.tkj.wechat.userapi.service.ResourceService;
import com.tkj.wechat.userapi.service.WechatUserService;
import com.tkj.wechat.util.StatusCode;
import net.bytebuddy.utility.RandomString;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
@MapperScan("com.tkj.wechat.dao")
class WechatApplicationTests {


    @Value(value = "${location.api-base-name}")
    private String locationApiBaseurl;

    @Value(value = "${location.api-name}")
    private String locationApiBody;

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private AddressService addressService;

    @Value(value = "${location.secret}")
    private String locationApiSecret;

    @Value(value = "${location.key}")
    private String locationApiKey;

    @Autowired
    public WechatUserMapper wechatUserMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private PublicDisplayService publicDisplayService;

    @Autowired
    private ResourceService resourceService;

    @Test
    void contextLoads() {

//        User user = userMapper.selectOneByExample(new UserExample().createCriteria().andIdEqualTo(1));
        WechatUserExample example = new WechatUserExample();
        example.or().andIdEqualTo(1);
        System.out.println(wechatUserMapper.selectOneByExample(example));
//        wechatUserMapper.insertSelective()


    }

    @Test
    void currentTest(){
        System.out.println(publicDisplayService.getCourselList());
    }

    @Value(value = "${location.api-base-name}")
    String apiBaseurl;

    @Value(value = "${location.api-name}")
    String apiBody;

    @Value(value = "${location.secret}")
    String apiSecret;

    @Value(value = "${location.key}")
    String apiKey;

    Object get_location(Map<String,String> para) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        List<String> list = new ArrayList<String>();
        for(String key :para.keySet()){
            list.add(key);
        }
        Collections.sort(list);
        System.out.println(list);
//        return null;
        String sigTmp = apiBody + "?";
        for(String key : list){
            sigTmp+=key+"="+para.get(key)+"&";
        }
        sigTmp = sigTmp.substring(0,sigTmp.length()-1);
        sigTmp += apiSecret;
        System.out.println(sigTmp);
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] digest = md5.digest(sigTmp.getBytes("utf-8"));
        String sigCode = ByteUtils.toHexString(digest);
        System.out.println(sigCode);
        String url = apiBaseurl + sigTmp.replace(apiSecret,"")+"&"+"sig="+sigCode;
        System.out.println(url);

        RestTemplate restTemplate = new RestTemplate();
        String responseEntity = restTemplate.getForEntity(url,String.class).getBody();

        System.out.println(responseEntity);


        return null;
    }

    @Autowired
    private AddressMapper addressMapper;

    @Test
    void biuildRandomInviterRelation() throws IOException, NoSuchAlgorithmException {

//        ClassPathResource classPathResource = new ClassPathResource("test_address_list.txt");
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()));
//        List<String>addressList = new ArrayList<String>();
//        String line = null;
////        LocationUtil locationUtil = addressService.ge
//
////
//        while((line = br.readLine())!=null){
//            addressList.add(line);
//        }


        Long total_user = wechatUserMapper.countByExample(new WechatUserExample());
//        Random random = new Random();
        for (int i=3;i<=total_user;++i){
//            int idx = i % addressList.size();
//            Address tmpAddress = addressService.getAddressFromAddress(addressList.get(idx));
//            tmpAddress.setUserId(i);

//            Integer father = random.nextInt(Math.toIntExact(i-1))+1;
            WechatUser wechatUser = wechatUserMapper.selectByPrimaryKey(i);
            Address address = addressService.getOneAddressByUser(i);
//            address.setId(i);
            wechatUserService.setDefaultAddress(i,address.getId());
//            wechatUser.setInviterId(father);
//            wechatUserService.updateWechatUser(wechatUser);
//            addressService.addAddress(tmpAddress,true);

        }

    }

    @Test
    void locationTest() throws IOException {

        Long total_user = wechatUserMapper.countByExample(new WechatUserExample());
        System.out.println(total_user);


        ClassPathResource classPathResource = new ClassPathResource("test_address_list.txt");

        BufferedReader br = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()));
        List<String>list = new ArrayList<String>();
        String line = null;

        while((line = br.readLine())!=null){
            list.add(line);
            System.out.println(line);
        }


    }


    @Test
    void generateRandomUsers(){
        Integer userNumber = 100;
        RandomString openIdGenerator = new RandomString(23);
        for(int i=0;i<userNumber;++i){
            WechatUser user = new WechatUser();
            user.setAddTime(LocalDateTime.now());
            user.setAvailableMoney(new BigDecimal(0.00));
            user.setFreezeMoney(new BigDecimal(0.00));
            user.setIsTeacher(StatusCode.IS_TEACHER_STUDENT);
            user.setState(0);
            user.setOpenId(openIdGenerator.nextString());
            wechatUserMapper.insertSelective(user);
            System.out.println(user);
        }

    }

}
