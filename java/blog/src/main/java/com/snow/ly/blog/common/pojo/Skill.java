package com.snow.ly.blog.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection="s_skill")
@NoArgsConstructor
@AllArgsConstructor
public class Skill {


    /**技能*/
    @Id
    private String id;
    @Indexed
    private String userId;
    /**内容*/
    private String content;
    /**点赞 用户名*/
    private String userName;
    /**点赞 用户头像*/
    private String userImage;











}
