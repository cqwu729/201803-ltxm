package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Type;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TypeRepository extends MongoRepository<Type,String> {

    List<Type> findByUserId(String userId);


}
