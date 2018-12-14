package com.snow.ly.blog.common.repository;




import com.snow.ly.blog.common.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;



public interface UserRepository extends MongoRepository<User,String> {


    List<User> findByUserEmail(String userEmail);

    List<User> findByUserPhone(String userEmail);

    List<User> findByOpenIdAndAccountType(String openId,Integer accountType);

    List<User> findByUserEmailAndUserIdNotIn(String email,String userId);

    List<User> findByUserPhoneAndUserIdNotIn(String email,String userId);

    List<User> findByProfileTypeAndUserIdNotIn(Integer profileType, List<String> userIds, Pageable pageable);

    List<User> findByUserIdNotIn(List<String> userIds, Pageable pageable);

    List<User> findByUserIdInAndFirstNameLikeOrLastNameLikeOrUserEmailLikeOrUserPhoneLike(List<String> userIds,String firstName,String lastName,String userEmail,String userPhone,Pageable pageable);

    User findByUserId(String userId);

    Page<User> findByAccountStatus(Integer status,Pageable pageable);

    Page<User> findByAccountStatusIn(List<Integer> status, Pageable pageable);


    List<User> findByFirstNameLike(String firstName, Pageable pageable);


}
