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
@Document(collection="s_photo")
@NoArgsConstructor
@AllArgsConstructor
public class Photo {

    /**照片*/
    @Id
    private String id;
    /**相册ID*/
    @Indexed
    private String albumId;
    /**图片地址*/
    private String img;







}
