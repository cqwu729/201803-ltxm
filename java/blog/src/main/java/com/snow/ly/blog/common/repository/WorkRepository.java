package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Work;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WorkRepository extends MongoRepository<Work,String> {

    List<Work> findByUserId(String userId);


}
