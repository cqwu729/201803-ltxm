package com.snow.ly.blog.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtils {


    /**string》》localDateTime*/
    public  static LocalDateTime stringToLocalDateTime(String str,String regx){
        if (regx==null) regx="yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter df = DateTimeFormatter.ofPattern(regx);
        return LocalDateTime.parse(str,df);

    }


    public  static LocalDateTime stringToLocalDateTime(String str){
        return stringToLocalDateTime(str,null);
    }



    /**
     * 判断是说说还是话题
     *
     */
    public static boolean isPost(String str){
        if (str==null)return false;
        String s=str.substring(0,1);
        return Pattern.compile("^#$").matcher(s).matches();
    }

    /**
     * 将字符串转为数组
     * str=1,2,3,4
     */
    public static List<String> stringToArrayList(String str){
        if (isNull(str))return new ArrayList<>();
        return Arrays.stream(str.split(",")).collect(Collectors.toList());
    }




    /**
     * 判断字符串是否为空
     */
    public  static boolean isNull(String str){
        if (null==str||"".equals(str)||"null".equals(str))return true;
        return false;
    }

    public static  boolean isNotNull(String str){
        if (null!=str&&!"".equals(str)&&!"null".equals(str))return true;
        return false;
    }


}
