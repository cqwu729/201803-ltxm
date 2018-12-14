package com.snow.ly.blog.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection="s_invitation")
@NoArgsConstructor
@AllArgsConstructor
public class Invitation {

    @Id
    private String id;
    @Indexed
    private String userId;
    /*图片*/
    private String img;
    /*视频*/
    private String video;
    /*文本*/
    private String text;

   /*发布时间*/
    private LocalDateTime createTime=LocalDateTime.now();

}
