package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.GroupUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GroupUserRepository extends MongoRepository<GroupUser,String> {



    List<GroupUser> findByUserIdAndGroupId(String userId,String groupId);


    List<GroupUser> findByUserId(String userId, Pageable pageable);

    List<GroupUser> findByGroupId(String groupId);
    List<GroupUser> findByUserId(String userId);






}
