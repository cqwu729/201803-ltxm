package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Advert;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AdvertRepository extends MongoRepository<Advert,String> {


    List<Advert> findByStartTimeBeforeAndEndTimeAfter(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    List<Advert> findByStartTimeBefore(LocalDateTime time);




}
