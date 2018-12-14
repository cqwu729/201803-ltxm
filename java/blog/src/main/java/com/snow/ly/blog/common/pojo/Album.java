package com.snow.ly.blog.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection="s_album")
@NoArgsConstructor
@AllArgsConstructor
public class Album {


    /**相册*/
    @Id
    private String id;
    @Indexed
    private String userId;
    /**标题*/
    private String title;
    /**封面*/
    private String cover;
    /**描述*/
    private String description;
    /**地址*/
    private String location;
    /**tags*/
    private String tags;
    /***/
    /**数量*/
    @Transient
    private Integer count;
    private LocalDateTime createTime;

    @Transient
    private Page<Photo> photos;





}
