package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AccountRepository extends MongoRepository<Account,String> {


    List<Account> findByXUserId(String xUserId);

    List<Account> findByXUserIdAndUserId(String xUserId,String userId);

    List<Account> findByUserId(String userId);

}
