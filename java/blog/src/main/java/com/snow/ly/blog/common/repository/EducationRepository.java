package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Education;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EducationRepository extends MongoRepository<Education,String> {


    List<Education> findByUserId(String userId);


}
