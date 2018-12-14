package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin,String>{


   Admin findByAccount(String account);











}
