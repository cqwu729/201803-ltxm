package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends MongoRepository<Article,String> {



    Article findByIdAndType(String id,Integer type);

    List<Article> findByIdAndUserId(String id,String userId);

    List<Article> findByUserIdAndType(String userId, Integer type, Pageable pageable);

    List<Article> findByTypeAndUserIdIn(Integer type, List<String> userIds, Pageable pageable);

    List<Article> findByUserIdIn(List<String> userIds, Pageable pageable);

    List<Article> findByUserIdInOrVisibility(List<String> userIds,Integer visibility, Pageable pageable);

    List<Article> findByTypeAndIdIn(Integer type, List<String> articleId, Pageable pageable);

    List<Article> findByTypeInAndUserIdIn(List<Integer> types, List<String> articleId, Pageable pageable);

    List<Article> findByTypeAndValidityBeforeAndUserIdIn(Integer type, LocalDateTime now,List<String> userIds,Pageable pageable);

    List<Article> findByTypeAndValidityAfterAndUserIdIn(Integer type, LocalDateTime now,List<String> userIds,Pageable pageable);

    List<Article> findByUserIdAndIsToShare(String userId,Integer isToShare,Pageable pageable);

    List<Article>findByUserId(String userId,Pageable pageable);


    List<Article>findByUserIdInAndTitleLike(List<String>userIds,String title,Pageable pageable);

    Page<Article>findByIsSys(Integer isSys,Pageable pageable);

    Page<Article>findByIsSysAndType(Integer isSys,Integer type,Pageable pageable);

}
