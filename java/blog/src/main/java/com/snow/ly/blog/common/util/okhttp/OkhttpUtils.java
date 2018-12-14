package com.snow.ly.blog.common.util.okhttp;

import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Map;



public class OkhttpUtils {

    private  static OkHttpClient client;

    static {
        client = new OkHttpClient.Builder()
                .sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier((hostname, session) -> true)
                .build();
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }

   /**get请求*/
   public static Response get(String url) throws IOException {
       Request request=new Request.Builder()
               .url(url)
               .build();
       return client.newCall(request).execute();

   }


   /**post*/
   public static Response post(String url, Map<String,String> params) throws IOException {
       FormBody.Builder b=new FormBody.Builder();
       params.forEach((k,v)->b.add(k,v));
       RequestBody body =b.build();
       Request request = new Request.Builder()
               .url(url)
               .post(body)
               .build();
       return client.newCall(request).execute();
   }


   public static void main(String[]args) throws IOException {


       Response response=get("https://blog.csdn.net/qq_29882585/article/details/52234524");
       System.out.println(response.body().string());


   }


}
