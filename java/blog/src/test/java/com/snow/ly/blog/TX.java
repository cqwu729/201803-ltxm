package com.snow.ly.blog;

import com.alibaba.fastjson.JSON;
import com.snow.ly.blog.common.bean.Item;
import com.snow.ly.blog.common.pojo.User;
import org.junit.Test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class TX {


    @Test
    public void test15(){
        double f = 0;

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        String s=nf.format(f);
        System.out.println(s);
        Double d=Double.valueOf(s)*100;
        System.out.println(d.intValue());

    }

    @Test
    public void test14(){

        double f = 0.676;

        DecimalFormat df = new DecimalFormat("#.00");
        String s=df.format(f);
        System.out.println(s);
//        Integer x=Integer.parseInt(s)*100;
//        System.out.println(x);
    }



    @Test
    public void test13(){

        List<String> list = new ArrayList<String>();
        list.add("ab");list.add("bc");list.add("cd");
        System.out.println(list.indexOf("ab"));//0
        System.out.println(list.indexOf("bc"));//1
        System.out.println(list.indexOf("b"));//-1

        System.out.println(list.contains("ac"));//false
        System.out.println(list.contains("ab"));//true

    }

    @Test
    public void test12(){

    String str="1,2,3,5,6666";
    Arrays.asList(str).forEach(i->System.out.println(i));


    }


    @Test
    public void test11(){

        String str="{\"code\":\"%s\"}";
        System.out.println( String.format(str,"1231231"));

    }

    @Test
    public void test10(){
        String str="xx#xxxxffdfasd";
        String s=str.substring(0,1);
         System.out.println(Pattern.compile("^#$").matcher(s).matches());

    }



    @Test
    public void test09(){

//        1B+01+01+AA+AA+AA+AA+BB+BB+BB+BB=5B1 5* 177
//        27+1+1+170+170+170+170+187+187+187+187=1457
        //1B0101AAAAAAAABBBBBBBB

        List<String> strings=Arrays.asList("1B","01","01","AA","AA","AA","BB","BB","BB","BB");
//        strings.stream().forEach(i->System.out.println(i));
        System.out.println(makeChecksum("1B0101AAAAAAAABBBBBBBB"));

        List<Integer> strings1=Arrays.asList(27,1,1,170,170,170,170,187,187,187,187);
        Integer s=0;
        for (int i=0;i<strings1.size();i++){
            s=s+strings1.get(i);
        }
         System.out.println(s);
    }



    public static String makeChecksum(String data) {
        if (data == null || data.equals("")) {
            return "";
        }
        int total = 0;
        int len = data.length();
        int num = 0;
        while (num < len) {
            String s = data.substring(num, num + 2);
//            System.out.println(s);
            total += Integer.parseInt(s, 16);
            num = num + 2;
        }
        /**
         * 用256求余最大是255，即16进制的FF
         */
        int mod = total % 256;
        String hex = Integer.toHexString(mod);
        len = hex.length();
        // 如果不够校验位的长度，补0,这里用的是两位校验
        if (len < 2) {
            hex = "0" + hex;
        }
        return hex;
    }



    @Test
    public void test08(){

        Set<String> strings=new LinkedHashSet();

        Arrays.asList("1","2","3").stream().forEach(s -> strings.add(s));
        Arrays.asList("4","5","3").stream().forEach(s -> strings.add(s));
        strings.stream().forEach(s -> System.out.println(s));



    }



    @Test
    public void test07(){

        List<Item> items=Arrays.asList(
                Item.builder().content("1").image("1").build(),
                Item.builder().content("2").image("2").build(),
                Item.builder().content("3").image("3").build(),
                Item.builder().content("4").image("4").build()
        );
        String is= JSON.toJSONString(items);
        System.out.println(is);

       JSON.parseArray(is,Item.class).stream().forEach(item -> System.out.println(item));


    }




    @Test
    public void test06(){

        LocalDateTime time=LocalDateTime.now();
        System.out.println(time);
        System.out.println(time.plusDays(20));


    }




    @Test
    public void test05(){

        String sports="";
        String[] strs=sports.split(",");
        Arrays.stream(strs).forEach(s -> System.out.println(s));


    }




    @Test
    public void test04(){
        Double sum=7.0;
        Integer cd=0;

        for (int i=0;i<7;i++) {
            cd++;
            System.out.println(cd/sum);
        }


    }



    @Test
    public void test(){


        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time);
        LocalDateTime ldt = LocalDateTime.parse("2018-01-12 17:07:05", df);
        System.out.println("LocalDateTime转成String类型的时间：" + localTime);
        System.out.println("String类型的时间转成LocalDateTime：" + ldt);



    }


    @Test
    public void test02(){

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
        LocalDate ldt = LocalDate.parse("2018-01-12 ", df);
        System.out.println("String类型的时间转成LocalDate：" + ldt);
        System.out.println(LocalDate.now());
    }


    @Test
    public void test03(){

        User user=new User();
        setGender(user,2);
        System.out.println(user.getUserGender());

    }



    public void setGender(User user,Integer userGender){
        if (userGender==null)return;
        if (userGender!=0&&userGender!=1&&userGender!=2)return;
        user.setUserGender(userGender);
    }




}
