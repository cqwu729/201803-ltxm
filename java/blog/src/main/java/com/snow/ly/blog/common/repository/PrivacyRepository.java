package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Privacy;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PrivacyRepository extends MongoRepository<Privacy,String> {


      List<Privacy> findByUserId(String userId);


}
