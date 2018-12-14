package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.UserConcernedType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserConcernedTypeRepository extends MongoRepository<UserConcernedType,String> {


    List<UserConcernedType> findByUserId(String userId);

}
