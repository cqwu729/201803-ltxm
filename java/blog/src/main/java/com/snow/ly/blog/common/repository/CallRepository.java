package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Call;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CallRepository extends MongoRepository<Call,String> {


}
