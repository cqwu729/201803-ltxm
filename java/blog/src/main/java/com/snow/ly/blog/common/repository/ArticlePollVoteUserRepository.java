package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.ArticlePollVoteUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArticlePollVoteUserRepository extends MongoRepository<ArticlePollVoteUser,String> {

    List<ArticlePollVoteUser> findByArticleIdAndUserId(String articleId, String userId);

    List<ArticlePollVoteUser> findByUserId(String userId);
}
