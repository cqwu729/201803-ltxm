package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Followers;
import com.snow.ly.blog.common.pojo.Friends;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FollowersRepository extends MongoRepository<Followers,String> {


    Integer countByUserId(String userId);

    Page<Followers> findByUserId(String userId, Pageable pageable);

    List<Followers> findByUserIdAndAndFollowerId(String userId,String followerId);

    void deleteByUserIdAndFollowerId(String userId,String followerId);

}
