package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SkillRepository extends MongoRepository<Skill,String> {


    List<Skill> findByUserId(String userId);


}
