package com.snow.ly.blog.config.interceptor;




import com.alibaba.fastjson.JSON;
import com.snow.ly.blog.common.bean.Result;
import com.snow.ly.blog.common.util.JWTTokenUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static com.snow.ly.blog.common.bean.Constants.AUTHORIZATION;
import static com.snow.ly.blog.common.bean.Constants.CURRENT_USER_ID;
import static com.snow.ly.blog.common.bean.Result.over;

/**
 * 自定义拦截器，判断此次请求是否有权限
 *
 *
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {




    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token=request.getHeader(AUTHORIZATION);
        if (Optional.ofNullable(token).isPresent()&&JWTTokenUtils.validateToken(token)&&JWTTokenUtils.getUserId(token)!=null){
            //如果token验证成功，将token对应的用户id存在request中，便于之后注入
            request.setAttribute(CURRENT_USER_ID, JWTTokenUtils.getUserId(token));
            return true;
        }
        //如果验证token失败，返回错误信息
        response(response);
        return false;
    }



    /**
     * 返回错误信息
     */
    public void response(HttpServletResponse response){
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out= null;
        try {
            out = response.getWriter();
            out.write(JSON.toJSONString(over()));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            out.close();
        }

    }


}
