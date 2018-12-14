package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.PollItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PollItemRepository extends MongoRepository<PollItem,String> {

    List<PollItem> findByArticleId(String articleId);

}
