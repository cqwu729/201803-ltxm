package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Playing;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlayingRepository extends MongoRepository<Playing,String> {

    List<Playing> findByUserId(String userId);



}
