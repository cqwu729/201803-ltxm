package com.snow.ly.blog.config.resolvers;



import com.snow.ly.blog.common.bean.Constants;
import com.snow.ly.blog.common.repository.UserRepository;
import com.snow.ly.blog.config.annotation.BlogUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * 增加方法注入，将含有BlogUser注解的方法参数注入当前登录用户
 *
 *
 */
@Component
public class UserMethodArgumentResolver implements HandlerMethodArgumentResolver {



    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果参数类型是String并且有BlogUser注解则支持
        if (parameter.getParameterType().isAssignableFrom(String.class) && parameter.hasParameterAnnotation(BlogUser.class))
            return true;
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
       //取出鉴权时存入的登录用户Id
        String currentUserId = (String) webRequest.getAttribute(Constants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
        if (currentUserId != null) {
            //从数据库中查询并返回
            return currentUserId;
        }
        throw new MissingServletRequestPartException(Constants.CURRENT_USER_ID);
    }
}
