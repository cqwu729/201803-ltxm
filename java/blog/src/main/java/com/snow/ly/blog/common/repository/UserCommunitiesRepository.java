package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.UserCommunities;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserCommunitiesRepository extends MongoRepository<UserCommunities,String> {


    List<UserCommunities> findByUserId(String userId);

}
