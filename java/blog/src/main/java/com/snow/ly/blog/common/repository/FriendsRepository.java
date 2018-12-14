package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Friends;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.stream.Stream;

public interface FriendsRepository extends MongoRepository<Friends,String> {


    Integer countByUserId(String userId);

    Page<Friends> findByUserId(String userId, Pageable pageable);

    Stream<Friends> findByUserId(String userId);

    List<Friends> findByUserIdAndFriendId(String userId,String friendId);



}
