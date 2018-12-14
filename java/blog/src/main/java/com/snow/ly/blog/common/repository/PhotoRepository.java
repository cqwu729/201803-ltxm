package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PhotoRepository extends MongoRepository<Photo,String> {


    List<Photo> findByAlbumId(String albumId);

    Page<Photo> findByAlbumId(String albumId, Pageable pageable);

}
