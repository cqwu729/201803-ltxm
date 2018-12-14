package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Bests;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BestsRepository extends MongoRepository<Bests,String> {

    List<Bests> findByUserId(String userId);

}
