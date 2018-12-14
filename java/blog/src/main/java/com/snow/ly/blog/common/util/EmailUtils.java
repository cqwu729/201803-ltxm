package com.snow.ly.blog.common.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class EmailUtils {


    static final String accessKey = "LTAIPz1NdtkRqVG4";
    static final String accessSecret = "j36hYlkVwJP3txIB6dtcpdRQn1MOg0";

    private static final String homeAddress="mydojo@e.s2018s.com";
    private static final String nickname="myDojo";
    private static final String tags="验证码";
    private static final String tags2="好友邀请";
    private static final String theme="验证码";
    private static final String theme2="好友邀请";
    private static final String content="尊敬的用户，欢迎使用myDojo，您的验证码为:%s 请妥善保管。如果不是本人操作，请忽略本条消息。";

    private static IClientProfile profile;
    private static IAcsClient client;
    private static SingleSendMailRequest request;
    static  {
        // 如果是除杭州region外的其它region（如新加坡、澳洲Region），需要将下面的"cn-hangzhou"替换为"ap-southeast-1"、或"ap-southeast-2"。
        profile = DefaultProfile.getProfile("cn-hangzhou", accessKey, accessSecret);
        // 如果是除杭州region外的其它region（如新加坡region）， 需要做如下处理
        //try {
        //DefaultProfile.addEndpoint("dm.ap-southeast-1.aliyuncs.com", "ap-southeast-1", "Dm",  "dm.ap-southeast-1.aliyuncs.com");
        //} catch (ClientException e) {
        //e.printStackTrace();
        //}
        client = new DefaultAcsClient(profile);
        request = new SingleSendMailRequest();
    }



    /**好友邀请*/
    public static void invitation(String email,String message){

        request.setAccountName(homeAddress);//控制台创建的发信地址
        request.setFromAlias(nickname);//发信人昵称
        request.setAddressType(1);
        request.setTagName(tags2);//控制台创建的标签
        request.setReplyToAddress(true);
        request.setToAddress(email);//目标地址
        request.setSubject(theme2);//邮件主题
        request.setHtmlBody(message);//邮件正文
        try {
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }



    /**发送邮箱注册验证码*/
    public static void  sendEmail(String email,String num){

        request.setAccountName(homeAddress);//控制台创建的发信地址
        request.setFromAlias(nickname);//发信人昵称
        request.setAddressType(1);
        request.setTagName(tags);//控制台创建的标签
        request.setReplyToAddress(true);
        request.setToAddress(email);//目标地址
        request.setSubject(theme);//邮件主题
        request.setHtmlBody(String.format(content,num));//邮件正文
        try {
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }


    }






    public static void main(String[]args){


        sendEmail("1277615541@qq.com","1232456");

//       sendEmail("xiaoyiqaz2@hotmail.com","123456");
    }







}
