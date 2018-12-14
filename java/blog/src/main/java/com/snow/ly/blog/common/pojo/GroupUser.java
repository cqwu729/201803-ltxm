package com.snow.ly.blog.common.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

import static com.snow.ly.blog.common.bean.Constants.IMG;

@Data
@Builder
@Document(collection="s_group_user")
@NoArgsConstructor
@AllArgsConstructor
public class GroupUser {


    /**群 用户*/


    private String id;
    /**群ID*/
    @Indexed
    private String groupId;
    /**用户id*/
    @Indexed
    private String userId;
    private String userImg;
    private String firstName;
    private String lastName;
    /**0  Athele;1 Club ; 2 Bloggers; 3 Fans ; 4 College Students*/
    /***  运动员 俱乐部 博客 粉丝 大学生*/
    private Integer profileType=0;








}
