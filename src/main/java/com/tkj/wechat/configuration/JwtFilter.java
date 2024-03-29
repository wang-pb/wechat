package com.tkj.wechat.configuration;
import com.alibaba.fastjson.JSONObject;
import com.tkj.wechat.util.ApiReturnUtil;
import com.tkj.wechat.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


/**
 * jwt过滤器
 *
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter implements Filter {

    /**
     * 执行登录
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        //从请求头中获取token
        final String reqToken = httpServletRequest.getHeader("token");
        StringRedisTemplate redisTemplate = SpringUtils.getBean(StringRedisTemplate.class);
        //从redis中获取token
        String token =redisTemplate.opsForValue().get("token");
        //比较两个token是否一致
        boolean equals = Objects.equals(reqToken, token);
        if (!equals) {
            return false;
        }
        JwtToken jwtToken = new JwtToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        try {
            getSubject(request, response).login(jwtToken);
            // 如果没有抛出异常则代表登入成功，返回true
            return true;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 执行登录认证
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            return executeLogin(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("JwtFilter过滤验证失败!");
            return false;
        }
    }

    /**
     * 认证失败时，自定义返回json数据
     *
     * @param request  请求
     * @param response 响应
     * @return boolean* @throws Exception 异常
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Object result = ApiReturnUtil.err(-1,"认证失败");
        Object parse = JSONObject.toJSON(result);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf8");
        response.getWriter().print(parse);
//        boolean denied = super.onAccessDenied(request, response);
//        System.out.println(denied);
        return false;
    }

    /**
     * 对跨域提供支持
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
