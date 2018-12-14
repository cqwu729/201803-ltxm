package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Login;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginRepository extends MongoRepository<Login,String> {
}
