package com.tkj.wechat.userapi.annotation.suport;

import com.tkj.wechat.userapi.annotation.StudentLogin;
import com.tkj.wechat.userapi.annotation.WechatUserLogin;
import com.tkj.wechat.userapi.service.UserTokenService;
import com.tkj.wechat.util.StatusCode;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class StudentHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

//    public static final String LOGIN_TOKEN_KEY = "token";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Integer.class) && parameter.hasParameterAnnotation(StudentLogin.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest request, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String token = request.getHeader(StatusCode.LOGIN_TOKEN_KEY);
        if (token == null || token.isEmpty()) {
            return null;
        }
        return UserTokenService.verifyWechatUserAndGetId(token);
    }
}
