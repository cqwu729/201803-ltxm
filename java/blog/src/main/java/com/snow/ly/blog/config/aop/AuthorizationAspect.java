package com.snow.ly.blog.config.aop;


import com.snow.ly.blog.common.bean.Result;
import com.snow.ly.blog.common.bean.Tips;
import com.snow.ly.blog.common.pojo.User;
import com.snow.ly.blog.common.repository.UserRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorizationAspect {


    @Autowired
    private UserRepository userRepository;


    @Pointcut(value = "@annotation(com.snow.ly.blog.config.annotation.Authorization)")
    public void aspect(){

    }


    /**
     * 在调用通知方法之前和之后运行通知。
     * @param joinPoint
     * @return
     */
    @Around(value = "aspect()")
    public Object around(ProceedingJoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        User user=userRepository.findOne(String.valueOf(args[0]));
        if (user==null)return Result.fail(Tips.USER_NOT.msg);
        args[args.length-1]=user;
        try {
          return joinPoint.proceed(args);
        } catch (Throwable e) {
            e.printStackTrace ();
        }
       return Result.fail(Tips.USER_NOT.msg);
    }



}
