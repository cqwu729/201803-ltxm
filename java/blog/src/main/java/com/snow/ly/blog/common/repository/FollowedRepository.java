package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Followed;
import com.snow.ly.blog.common.pojo.Friends;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.stream.Stream;

public interface FollowedRepository extends MongoRepository<Followed,String> {


    Integer countByUserId(String userId);

    Page<Followed> findByUserId(String userId, Pageable pageable);

    Stream<Followed> findByUserId(String userId);

    List<Followed> findByUserIdAndFollowedId(String userId,String followedId);


    void deleteByUserIdAndFollowedId(String userId,String followedId);


}
