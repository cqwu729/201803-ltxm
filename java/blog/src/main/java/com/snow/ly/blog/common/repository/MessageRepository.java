package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface MessageRepository extends MongoRepository<Message,String> {



    Page<Message> findByType(Integer type, Pageable pageable);

    List<Message> findByTypeAndUserIdNotIn(Integer type,List<String> deletedIds, Pageable pageable);

    List<Message> findByTypeInAndUserIdNotInAndIsRead(List<Integer> typeList,List<String> deleteIds, Integer isRead, Pageable pageable);

    List<Message> findByUserIdAndToUserId(String friendId,String userId,Pageable pageable);

    List<Message> findByGroupIdAndIsFlockAndToUserId(String groupId,Integer isFlock,String userId,Pageable pageable);

    void  deleteByToUserIdAndGroupId(String userId,String groupId);

    List<Message> findByToUserIdAndIsRead(String userId,Integer isRead,Pageable pageable);

    List<Message> findByToUserId(String userId,Pageable pageable);

    List<Message> findByToUserIdAndType(String userId,Integer type,Pageable pageable);

    List<Message> findByToUserIdAndTypeAndUserIdNotIn(String userId,Integer type,List<String> deletedIds,Pageable pageable);

    List<Message> findByToUserIdAndContentLike(String userId,String keyword,Pageable pageable);




}
