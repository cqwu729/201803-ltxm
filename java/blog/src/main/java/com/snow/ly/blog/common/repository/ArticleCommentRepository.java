package com.snow.ly.blog.common.repository;


import com.snow.ly.blog.common.pojo.ArticleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface ArticleCommentRepository extends MongoRepository<ArticleComment,String> {


    List<ArticleComment> findByArticleId(String articleId);

    Page<ArticleComment> findByArticleId(String articleId, Pageable pageable);

}
