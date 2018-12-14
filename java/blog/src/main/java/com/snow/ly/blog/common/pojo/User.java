package com.snow.ly.blog.common.pojo;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.snow.ly.blog.common.bean.Constants.COVER;
import static com.snow.ly.blog.common.bean.Constants.IMG;


@Data
@Builder
@Document(collection="s_user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * 用户
     */

    @Id
    private String userId;
    @Indexed
    private String openId;
    /**用户是否绑定数据 0未绑定 1已绑定*/
    private Integer isBind=0;
    /**0 注册用户 1 Facebook 2 Twitter 3 Google+ 4 vk 5 wx*/
    private Integer accountType=0;
    /**账号状态 0 正常 1 禁用 2 删除*/
    private Integer accountStatus=0;
    private String userImg;
    private String firstName;
    private String lastName;
    /**0  Athele;1 Club ; 2 Bloggers; 3 Fans ; 4 College Students*/
    /***  运动员 俱乐部 博客 粉丝 大学生*/
    private Integer profileType=0;
    /**0 保密 1 男 2 女*/
    private Integer userGender=0;
    /**语言*/
    private String userLanguage="3";

    @Indexed
    private String userEmail;
    @Indexed
    private String userPhone;
    private String userPassword;

    private LocalDate userBirthday=LocalDate.now();

    private String location;
    private String workFor;
    private String jobTitle;
    private String sponsors;
    private String height;
    private String weight;

    /***/
    private List<Bests> bests=new ArrayList<>();
    /**俱乐部简介*/
    private String introduction;
    /**俱乐部类型*/
    private List<Type> types=new ArrayList<>();
    /**俱乐部地址*/
    private String address;
    /**俱乐部证书*/
    private List<Certificate> certificates=new ArrayList<>();
    /**技能*/
    private List<Skill> skills=new ArrayList<>();
    /**教育*/
    private List<Education> educations=new ArrayList<>();
    /**playing history*/
    private List<Playing>playings=new ArrayList<>();
    /**work*/
    private List<Work>works=new ArrayList<>();

    /**所在学校*/
    private String readingSchool;
    /**运动类型*/
    private List<UserConcernedType> userConecrnedType=new ArrayList<>();
    /**用户社区*/
    private List<UserCommunities> userCommunities=new ArrayList<>();
    /**签名*/
    private String userSignature;
    /**封面*/
    private String userCover;


    @JSONField(format = "yyyy-HH-dd mm:hh:ss")
    private Date createTime;
    @Transient
    private String userToken;
    /**好友个数*/
    @Transient
    private Integer friendsCount=0;
    /**追随者个数*/
    @Transient
    private Integer followersCount=0;
    /**关注个数*/
    @Transient
    private Integer followedCount=0;
    /**个人资料的完整性*/
    @Transient
    private Integer DataCompletion=0;




}
