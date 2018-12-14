package com.snow.ly.blog.config.interceptor;


import com.snow.ly.blog.common.util.JWTTokenUtils;
import com.snow.ly.blog.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.snow.ly.blog.common.bean.Constants.ADMIN_AUTH;
import static com.snow.ly.blog.common.bean.Constants.AUTHORIZATION;
import static com.snow.ly.blog.common.bean.Constants.CURRENT_ADMIN_ID;


/**
 * 自定义拦截器，判断此次请求是否有权限
 *
 *
 */
@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;


    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token=request.getHeader(ADMIN_AUTH);
        if (StringUtils.isNotNull(token)&&JWTTokenUtils.validateToken(token)&&JWTTokenUtils.getUserId(token)!=null){
            //如果token验证成功，将token对应的用户id存在request中，便于之后注入
            request.setAttribute(CURRENT_ADMIN_ID, JWTTokenUtils.getUserId(token));
            return true;
        }
        //如果验证token失败，返回错误信息
        authorizationInterceptor.response(response);
        return false;
    }




}
