package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface GroupRepository extends MongoRepository<Group,String> {




         List<Group> findByNameLikeOrIntroductionLike(String name,String introduction);







}
