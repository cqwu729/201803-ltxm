package com.snow.ly.blog.common.util;

import com.alibaba.fastjson.JSON;
import com.snow.ly.blog.common.bean.SNSUserInfo;
import com.snow.ly.blog.common.bean.WxAccessToken;
import com.snow.ly.blog.common.util.okhttp.OkhttpUtils;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Component
public class WxUtils {


    private String appID="wx2f1f6a57cccd715f";
    private String appSecret="cdf1411bad02a43cc707a8d80c0225fe";

   /**获取请求地址*/
   public String getUrl(String redirectUri,String state){

       /**授权地址*/
       String url1="https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

       url1=url1.replace("APPID",appID);
       url1=url1.replace("REDIRECT_URI",encodeURl(redirectUri));
       url1=url1.replace("SCOPE","snsapi_login");
       url1=url1.replace("STATE",encodeURl(state));
       return url1;
   }

   /**微信回调处理*/
   public Map<String,String> getCode(HttpServletRequest request)  {
       Map<String,String>m=new HashMap<>();
       try {
           request.setCharacterEncoding("UTF-8");
           m.put("code",request.getParameter("code"));
           m.put("state",request.getParameter("state"));
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       }
       return m;
   }

   /**通过code获取access_token*/
   public WxAccessToken getAccessToken(String code){
       /**通过code获取access_token的接口。*/
       String url2="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

       url2=url2.replace("APPID",appID);
       url2=url2.replace("SECRET",appSecret);
       url2=url2.replace("CODE",code);
       try {
         Response response=OkhttpUtils.get(url2);
         if (response.isSuccessful()){
           return JSON.parseObject(response.body().string(),WxAccessToken.class);
         }
       } catch (IOException e) {
           e.printStackTrace();
       }
       return null;
   }
   /**获取用户个人信息*/
   public void getUserInfo(String accessToken, String openid, String state,  HttpServletRequest request, HttpServletResponse response,RedirectAttributes attributes) throws Exception {
       /**获取用户个人信息地址*/
       String url3="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
       request.setCharacterEncoding("utf-8");
       response.setCharacterEncoding("utf-8");
       url3 = url3.replace("ACCESS_TOKEN", accessToken);
       url3 = url3.replace("OPENID", openid);
       Response r = OkhttpUtils.get(url3);
       if (r.isSuccessful()) {
//           转发
//           // 设置要传递的参数
//           request.setAttribute("userInfo", JSON.parseObject(r.body().string(), SNSUserInfo.class));
//           request.getRequestDispatcher(state).forward(request, response);

           // 重定向到前端页面
           SNSUserInfo info=JSON.parseObject(r.body().string(), SNSUserInfo.class);
           String str="?openId="+info.getOpenId()+"&nickname="+encodeURl(info.getNickname())+"&sex="+info.getSex()+"&headImgUrl="+info.getHeadImgUrl();
           response.sendRedirect(state+str);

       }
   }

   public static String encodeURl(String url)  {

       try {
         return  URLEncoder.encode(url,"UTF-8");
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       }

       return null;
   }





}
