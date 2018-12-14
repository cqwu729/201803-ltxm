package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Language;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LanguageRepository extends MongoRepository<Language,String> {


    Language findByType(Integer type);



}
