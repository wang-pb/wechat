package com.tkj.wechat.util;

public class StatusCode {
    //用户身份，是学生
    public static final Integer IS_TEACHER_STUDENT = 0;
    //用户身份，是老师
    public static final Integer IS_TEACHER_TEACHER = 1;

    public static final Integer IS_COLLAGE_STUDENT = 1;
    public static final Integer IS_PROFESSIONAL_TEACHER = 2;


    public static final String LOGIN_TOKEN_KEY = "token";

    public static final Integer ERROR_UNLOGIN = 501;
    public static final Integer ERROR_NOTHING_TO_DO = 888;

    public static final Integer WRONG_ACCOUNT_OR_PASSWORD = 909;
    //轮播图
    public static final String RESOURCE_TYPE_CAROUSEL = "RESOURCE_TYPE_CAROUSEL";

    public static final String RESOURCE_TYPE_AVATOR = "RESOURCE_TYPE_AVATOR";

    public static final String RESOURCE_TYPE_TEACHER_POSTER = "RESOURCE_TYPE_TEACHER_POSTER";

    public static final String RESOURCE_TYPE_STUDENT_POSTER = "RESOURCE_TYPE_STUDENT_POSTER";

    public static final String RESOURCE_TYPE_CLASS_TYPE_POSTER = "RESOURCE_TYPE_CLASS_TYPE_POSTER";

    //身份证正面
    public static final String RESOURCE_TYPE_IDENTITY_FRONT = "RESOURCE_TYPE_IDENTITY_FRONT";
    //身份证反面
    public static final String RESOURCE_TYPE_IDENTITY_REVERSE = "RESOURCE_TYPE_IDENTITY_REVERSE";

    public static final Integer ERROR_USER_ID_NOT_MUTCH_ERROR = 601;

    public static final Integer TEACHER_AUTHENTICATION_SUBMITTED = 100;
    public static final Integer TEACHER_AUTHENTICATION_ACCEPTED = 200;
    public static final Integer TEACHER_AUTHENTICATION_DENY = 102;
    public static final Integer TEACHER_AUTHENTICATION_RECALL = 103;



    public static final Integer TEACHER_APPLICATION_SUBMITTED = 100;
    public static final Integer TEACHER_APPLICATION_DENY = 101;
    public static final Integer TEACHER_APPLICATION_RECALLED_BEFORE_ACCEPTED = 102;
    public static final Integer TEACHER_APPLICATION_ACCEPTED = 200;
    public static final Integer TEACHER_APPLICATION_RECALLED_AFTER_ACCEPTED = 201;

    public static final Integer SUPER_ADMINISTRATOR_LEVEL = 0;
    public static final Integer COMMON_MANAGER_LEVEL = 1;


}
