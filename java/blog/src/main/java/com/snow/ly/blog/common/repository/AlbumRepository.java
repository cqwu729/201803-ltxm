package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Album;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.stream.Stream;

public interface AlbumRepository extends MongoRepository<Album,String> {



    Stream<Album> findByUserId(String userId);




}
