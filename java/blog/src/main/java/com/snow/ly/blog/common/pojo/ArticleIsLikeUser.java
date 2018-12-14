package com.snow.ly.blog.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection="s_article_is_like_user")
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(def = "{articleId:1,userId:1}")
public class ArticleIsLikeUser {

    /***
     * 用户点赞
     */

    @Id
    private String id;
    @Indexed
    private String articleId;
    private String userId;






}
