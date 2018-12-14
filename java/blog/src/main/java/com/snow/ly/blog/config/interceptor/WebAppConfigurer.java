package com.snow.ly.blog.config.interceptor;

import com.snow.ly.blog.config.resolvers.AdminMethodArgumentResolver;
import com.snow.ly.blog.config.resolvers.UserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;


@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {


    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;
    @Autowired
    private AdminMethodArgumentResolver adminMethodArgumentResolver;
    @Autowired
    private UserMethodArgumentResolver userMethodArgumentResolver;
    @Autowired
    private AdminInterceptor adminInterceptor;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(authorizationInterceptor)
                .excludePathPatterns("/login/**")
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/getAllLanguages")
                .addPathPatterns("/home/**")
                .excludePathPatterns("/home/getComment")
                .excludePathPatterns("/home/getAdverts")
                .excludePathPatterns("/home/getAbout")
                .excludePathPatterns("/home/getPolicy")
                .excludePathPatterns("/home/getProtocol")
                .addPathPatterns("/message/**")
                .addPathPatterns("/group/**")

        ;


        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login")


                ;


    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userMethodArgumentResolver);
        argumentResolvers.add(adminMethodArgumentResolver);
    }



}
