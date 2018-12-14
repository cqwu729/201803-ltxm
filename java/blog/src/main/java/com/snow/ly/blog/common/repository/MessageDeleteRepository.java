package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.MessageDelete;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageDeleteRepository extends MongoRepository<MessageDelete,String> {



    List<MessageDelete> findByUserId(String userId);







}
