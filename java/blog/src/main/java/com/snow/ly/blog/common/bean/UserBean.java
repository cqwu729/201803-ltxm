package com.snow.ly.blog.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBean {

    private String id;
    private String userId;
    private String userImg;
    private String firstName;
    private String lastName;
    private Integer profileType;
    private Integer userGender;
    private String userSignature;







}
