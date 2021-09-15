package com.tkj.wechat.adminapi.service;

import com.tkj.wechat.dao.ClassTypeMapper;
import com.tkj.wechat.domain.ClassType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminClassTypeService {

    @Autowired
    private ClassTypeMapper classTypeMapper;

    public ClassType insertClassType(ClassType classType){
        classType.setIsDelete(ClassType.NOT_DELETED);
        classTypeMapper.insert(classType);
        return classType;
    }

}
