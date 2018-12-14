package com.snow.ly.blog;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class sample {


    private static final String accessKey = "LTAI3uAA6QnyG2nt";
    private static final String accessSecret = "HYJz0xhagfDJ6C1qPbKLhnmPrRmCBy";

   public static void main(String[]args) throws ClientException {




       // 如果是除杭州region外的其它region（如新加坡、澳洲Region），需要将下面的"cn-hangzhou"替换为"ap-southeast-1"、或"ap-southeast-2"。
       IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey, accessSecret);
       // 如果是除杭州region外的其它region（如新加坡region）， 需要做如下处理
       //try {
       //DefaultProfile.addEndpoint("dm.ap-southeast-1.aliyuncs.com", "ap-southeast-1", "Dm",  "dm.ap-southeast-1.aliyuncs.com");
       //} catch (ClientException e) {
       //e.printStackTrace();
       //}
       IAcsClient client = new DefaultAcsClient(profile);
       SingleSendMailRequest request = new SingleSendMailRequest();

//       request.setVersion("2017-06-22");// 如果是除杭州region外的其它region（如新加坡region）,必须指定为2017-06-22
//       request.setAccountName("1277615541@s2018s.xyz");//控制台创建的发信地址
//       request.setFromAlias("发信人昵称");//发信人昵称
//       request.setAddressType(1);
//       request.setTagName("test");//控制台创建的标签
//       request.setReplyToAddress(true);
//       request.setToAddress("1277615541@qq.com");//目标地址
//       request.setSubject("测试。。。。");//邮件主题
//       request.setHtmlBody("好，很好，非常好。。。。");//邮件正文
//       SingleSendMailResponse httpResponse = client.getAcsResponse(request);


       request.setAccountName("1277615541@s2018s.xyz");//控制台创建的发信地址
       request.setFromAlias("发信人昵称");//发信人昵称
       request.setAddressType(1);
       request.setTagName("test");//控制台创建的标签
       request.setReplyToAddress(true);
       request.setToAddress("xiaoyiqaz2@hotmail.com");//目标地址
       request.setSubject("Best Pricing Available Until July 31st");//邮件主题
       request.setHtmlBody("日期: 2017/4/21 6:12 (GMT)\n" +
               "如果是你本人，则你的帐户安全，可以忽略此电子邮件。\n" +
               "如果不确定是否是你本人，则某个恶意用户可能已经知道了你的密码。请查看你最近的活动，我们将帮助你进行纠正操作。xxxxx");//邮件正文
       SingleSendMailResponse httpResponse = client.getAcsResponse(request);




       System.out.println(httpResponse.getRequestId());



   }








}
