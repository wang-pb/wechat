package com.tkj.wechat.configuration;

import com.tkj.wechat.adminapi.annotation.support.AdminHandlerMethodArgumentResolver;
import com.tkj.wechat.userapi.annotation.suport.StudentHandlerMethodArgumentResolver;
import com.tkj.wechat.userapi.annotation.suport.TeacherHandlerMethodArgumentResolver;
import com.tkj.wechat.userapi.annotation.suport.WechatUserHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WxWebMvcConfiguration implements WebMvcConfigurer {
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new StudentHandlerMethodArgumentResolver());
        argumentResolvers.add(new WechatUserHandlerMethodArgumentResolver());
        argumentResolvers.add(new TeacherHandlerMethodArgumentResolver());
        argumentResolvers.add(new AdminHandlerMethodArgumentResolver());
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/login.html").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/templates/")
                .addResourceLocations("classpath:/dist/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
