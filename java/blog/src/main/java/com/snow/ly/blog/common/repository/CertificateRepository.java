package com.snow.ly.blog.common.repository;

import com.snow.ly.blog.common.pojo.Certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CertificateRepository extends MongoRepository<Certificate,String> {

    List<Certificate> findByUserId(String userId);

    Page<Certificate> findByPass(Integer pass, Pageable pageable);


}
