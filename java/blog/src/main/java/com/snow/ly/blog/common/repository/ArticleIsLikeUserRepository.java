package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.ArticleIsLikeUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.stream.Stream;

public interface ArticleIsLikeUserRepository extends MongoRepository<ArticleIsLikeUser,String> {

    List<ArticleIsLikeUser>  findByArticleIdAndUserId(String articleId, String userId);

    Stream<ArticleIsLikeUser> findByArticleId(String articleId);

    void removeByArticleIdAndUserId(String articleId,String userId);

}
