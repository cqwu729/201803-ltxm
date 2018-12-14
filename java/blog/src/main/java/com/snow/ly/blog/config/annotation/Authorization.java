package com.snow.ly.blog.config.annotation;

import java.lang.annotation.*;


@Target({ElementType.PARAMETER, ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authorization {

    String description()  default "";

}
