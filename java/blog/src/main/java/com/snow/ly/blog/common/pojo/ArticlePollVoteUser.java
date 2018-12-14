package com.snow.ly.blog.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection="s_article_poll_vote_user")
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(def = "{articleId:1,userId:1}")
public class ArticlePollVoteUser {

    /***
     * 用户投票
     */

    @Id
    private String id;
    private String articleId;
    private String userId;






}
