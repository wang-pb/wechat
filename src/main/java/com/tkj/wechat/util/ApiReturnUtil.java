package com.tkj.wechat.util;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiReturnUtil {


    public static Object ok(Object data){
        Map<String,Object> objectMap = new HashMap<String,Object>();
        objectMap.put("state",0);
        objectMap.put("msg","执行成功");
        objectMap.put("data",data);
        return objectMap;
    }

    public static Object err(Integer err_number,String msg){
        Map<String,Object> objectMap = new HashMap<String,Object>();
        objectMap.put("state",err_number);
        objectMap.put("msg",msg);
        return objectMap;
    }
    public static Object debug(Object data){
        Map<String,Object> objectMap = new HashMap<String,Object>();
        objectMap.put("state",110);
        objectMap.put("msg","DEBUG INFOMATION");
        objectMap.put("data",data);
        return objectMap;
    }
    public static Object nothingToDo() { return err(StatusCode.ERROR_NOTHING_TO_DO,"操作未生效");}
    public static Object unlogin(){
        return err(StatusCode.ERROR_UNLOGIN,"您未登录");
    }
    public static Object wrongPassword(){return err(StatusCode.WRONG_ACCOUNT_OR_PASSWORD,"用户名或密码错误");}

}
