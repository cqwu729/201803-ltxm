package com.snow.ly.blog.common.util;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class CodeUtils {

    @Autowired
    private StringRedisTemplate redisTemplate;



    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIPz1NdtkRqVG4";
    static final String accessKeySecret = "j36hYlkVwJP3txIB6dtcpdRQn1MOg0";
    //必填:短信签名-可在短信控制台中找到
    static final String signName="myDojo道场";
    //必填:短信模板-可在短信控制台中找到
    static final String templateCode="SMS_135710004";
    static IClientProfile profile;
    static IAcsClient acsClient;
    static {

        profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            e.printStackTrace();
        }
       acsClient = new DefaultAcsClient(profile);
    }

    /**
     * 验证码
     * @param phone
     * @param code
     */

    public static void sendSms(String phone,String code){
        String str="{\"code\":\"%s\"}";
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(String.format(str,code));
        //hint 此处可能会抛出异常，注意catch
        try {
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            System.out.println(sendSmsResponse.getMessage());
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }





    /**
     * 设置验证
     */
    public String setCode(String emailOrPhone,Long time){
        String code=createCode(6);
//        redisTemplate.opsForValue().set(emailOrPhone,code,time, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(emailOrPhone,code);
        return code;
    }

    /**
     * 获取验证码
     */
    public String getCode(String emailOrPhone){
        return redisTemplate.opsForValue().get(emailOrPhone);
    }

    /**
     * 验证验证码是否正确
     */
    public Boolean checkCode(String emailOrPhone,String code){
        String rCode=getCode(emailOrPhone);
        if (StringUtils.isNull(rCode))return false;
        if (rCode.equals(code)){
            redisTemplate.delete(emailOrPhone);
            return true;
        }
        return false;
    }

    /**
     * 生产验证码
     */
    public String createCode(Integer strLength){

        Random rm = new Random();
        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);
        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);

    }





}
