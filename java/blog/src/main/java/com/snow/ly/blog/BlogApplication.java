package com.snow.ly.blog;


import com.snow.fastjson.annotation.EnableFastJson;
import com.snow.swagger.inter.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableSwagger2Doc
@EnableFastJson
public class BlogApplication {



	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
}
