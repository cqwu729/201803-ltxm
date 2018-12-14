package com.snow.ly.blog.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection="s_advert")
@NoArgsConstructor
@AllArgsConstructor
public class Advert {

    /**广告*/

    @Id
    private String id;
    /**标题*/
    private String title;
    /**文字*/
    private String content;
    /**图片*/
    private String img;
    /**链接*/
    private String link;
    /**类型*/
    private String type;
    /**广告商*/
    private String name;
    /**费用*/
    private Double money;
    /**开始时间*/
    private LocalDateTime startTime;
    /**结束时间*/
    private LocalDateTime endTime;
    /**创建时间*/
    private LocalDateTime createTime;




}
