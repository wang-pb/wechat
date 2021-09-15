package com.tkj.wechat.util;

import com.alibaba.fastjson.JSONObject;
import com.tkj.wechat.domain.Address;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


@Component
public class LocationUtil {

    @Value(value = "${location.api-base-name}")
    private String locationApiBaseurl;

    @Value(value = "${location.api-name}")
    private String locationApiBody;

    @Value(value = "${location.secret}")
    private String locationApiSecret;

    @Value(value = "${location.key}")
    private String locationApiKey;


//    public LocationUtil(String locationApiBaseurl, String locationApiBody, String locationApiSecret, String locationApiKey) {
//        this.locationApiBaseurl = locationApiBaseurl;
//        this.locationApiBody = locationApiBody;
//        this.locationApiSecret = locationApiSecret;
//        this.locationApiKey = locationApiKey;
//    }
    public Address queryLocation(HashMap<String,String> para) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        para.put("key", locationApiKey);
        List<String> list = new ArrayList<String>();

        for(String key :para.keySet()){
            list.add(key);
        }
        Collections.sort(list);
//        System.out.println(para);
//        return null;
        String sigTmp = locationApiBody + "?";
        for(String key : list){
            sigTmp+=key+"="+para.get(key)+"&";
        }
        sigTmp = sigTmp.substring(0,sigTmp.length()-1);
        sigTmp += locationApiSecret;
//        System.out.println(sigTmp);
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] digest = md5.digest(sigTmp.getBytes("utf-8"));
        String sigCode = ByteUtils.toHexString(digest);
        String url = locationApiBaseurl + sigTmp.replace(locationApiSecret,"")+"&"+"sig="+sigCode;

        RestTemplate restTemplate = new RestTemplate();
        String responseEntity = restTemplate.getForEntity(url,String.class).getBody();

        JSONObject jsonObject =  JSONObject.parseObject(responseEntity);
        if(!jsonObject.get("status").equals(0)){
            return null;
        }
        Address address = new Address();
        address.setLng(jsonObject.getJSONObject("result").getJSONObject("location").getDouble("lng"));
        address.setLat(jsonObject.getJSONObject("result").getJSONObject("location").getDouble("lat"));

        String address_title = null;

        JSONObject address_components = null;

        if(para.containsKey("address")) {
            address_components = jsonObject.getJSONObject("result").getJSONObject("address_components");
            address_title = (String) jsonObject.getJSONObject("result").get("title");

        }else{
            address_components = jsonObject.getJSONObject("result").getJSONObject("address_component");
            address_title = (String) jsonObject.getJSONObject("result").get("address");

        }

        address.setProvince(address_components.getString("province"));
        address.setCity(address_components.getString("city"));
        address.setArea(address_components.getString("district"));
        address.setAddressDetail(address.getProvince()+address.getCity()+address.getArea()+address_title);

        return address;


//        return jsonObject;

    }

    public Address getAddressFromCoordinate(Double lng, Double lat) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        HashMap<String,String> para = new HashMap<String,String>();
        para.put("location",(lat.toString()+","+lng.toString()));
        return queryLocation(para);
    }

    public Address getAddressFromAddress(String location) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        HashMap<String,String> para = new HashMap<String,String>();
        para.put("address", location);
        return queryLocation(para);
    }


}
