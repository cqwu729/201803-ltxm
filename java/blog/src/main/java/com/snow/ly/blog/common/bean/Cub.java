package com.snow.ly.blog.common.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cub {




    private String id;
    /**证书*/
    private String certificate;
    /**是否通过认证0 等待审核 1 通过 2 未通过*/
    private Integer isTrue;
    /**证书图片*/
    private String certificateImage;
    /**上传日期*/
    private LocalDate createDate;

    private String userId;
    /**账号状态 0 正常 1 禁用 2 删除*/
    private Integer accountStatus;
    private String userImg;
    private String firstName;
    private String lastName;
    /**0  Athele;1 Club ; 2 Bloggers; 3 Fans ; 4 College Students*/
    /***  运动员 俱乐部 博客 粉丝 大学生*/
    private Integer profileType;
    /**0 保密 1 男 2 女*/
    private Integer userGender;
    /**语言*/
    private String userEmail;
    private String userPhone;





}
