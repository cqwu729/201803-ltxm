package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.ArticleCommentReply;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArticleCommentReplyRepository extends MongoRepository<ArticleCommentReply,String> {

    List<ArticleCommentReply> findByCommentId(String commentId);

}
