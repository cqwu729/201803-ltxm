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
@Document(collection="s_hide")
@NoArgsConstructor
@AllArgsConstructor
public class Hide {

    /**隐藏文章*/

    @Id
    private String id;
    /**用户id*/
    @Indexed
    private String userId;
    /**文章id*/
    private String articleId;





















}
