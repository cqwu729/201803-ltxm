package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.NotSeePeople;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotSeePeopleRepository extends MongoRepository<NotSeePeople,String> {


    List<NotSeePeople> findByUserIdAndAndPeopleId(String userId,String peopleId);

    List<NotSeePeople> findByUserId(String userId);




}
