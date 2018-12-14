package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.ArticleIsViewUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArticleIsViewUserRepository extends MongoRepository<ArticleIsViewUser,String> {


   List<ArticleIsViewUser> findByArticleIdAndUserId(String articleId, String userId);


}
