package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Hide;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.stream.Stream;

public interface HideRepository extends MongoRepository<Hide,String> {





    Stream<Hide> findByUserId(String userId);












}
