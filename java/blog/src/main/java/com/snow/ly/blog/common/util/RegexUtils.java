package com.snow.ly.blog.common.util;



public class RegexUtils {



    public static final String EMAIL="^(\\w)+(.\\w+)*@(\\w)+((.\\w+)+)$";
    public static final String PHONE="^[-\\+]?[\\d]*$";
    public static final String PASSWORD="^[0-9a-zA-Z]{6,16}$";

    /**
     * 判断email
     */
    public static boolean checkEmail(String email){
        return email.matches(EMAIL);
    }


    /**
     *
     * 已数字判断 是否为手机号
     *
     */
    public static boolean checkPhone(String phone){
        return phone.matches(PHONE);
    }

    /**
     * 密码验证
     */
    public static boolean checkPassword(String password){
        return password.matches(PASSWORD);
    }














}
