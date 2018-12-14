package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.ConcernedType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConcernedTypeRepository extends MongoRepository<ConcernedType,String> {



    Page<ConcernedType>findByType(Integer type, Pageable pageable);



}
