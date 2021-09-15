package com.tkj.wechat;

import com.tkj.wechat.dao.AdministratorMapper;
import com.tkj.wechat.dao.ClassTypeMapper;
import com.tkj.wechat.dao.WechatUserMapper;
import com.tkj.wechat.domain.*;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class ClassTestSequence {

    @Autowired
    WechatUserMapper wechatUserMapper;

    @Autowired
    AdministratorMapper administratorMapper;

    @Autowired
    ClassTypeMapper classTypeMapper;


    Integer testTeacherId = 64;
    Integer testAdminId = 1;

    public List<ClassType> getPermittedClassTypeList(Integer userId){
        return classTypeMapper.selectByExample(new ClassTypeExample());
    }

    public void err(String info){
        System.out.println(info);
    }

    public List<TeacherTime> generateRandomTeacherTime(int class_number){
        LocalDateTime now = LocalDateTime.now();
//        now.plusDays(1);
        List<TeacherTime> times = new ArrayList<>();
        for(int i=0;i<class_number;++i){
            TeacherTime t = new TeacherTime();
            t.setStartTime(now.plusDays(i+1));
            t.setEndTime(t.getStartTime().plusHours(1));
            times.add(t);
        }

        return times;
    }
    @Test
    public void classTest(){
        WechatUser teacher = wechatUserMapper.selectByPrimaryKey(testTeacherId);
        Administrator admin = administratorMapper.selectByPrimaryKey(testAdminId);

        List<ClassType> permittedClassTypeList = getPermittedClassTypeList(teacher.getId());
        if(null == permittedClassTypeList || permittedClassTypeList.isEmpty() || teacher.getState() == 0){
            err("ERROR");
            return ;
        }

        ClassType selectedClassType = permittedClassTypeList.get(0);



    }


}
