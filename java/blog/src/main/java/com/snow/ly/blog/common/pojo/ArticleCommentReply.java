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
@Document(collection="s_article_comment_reply")
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCommentReply {



    @Id
    private String id;
    @Indexed
    private String commentId;

    private String userId;
    private String username;
    private String userImage;

    private String replyUserId;
    private String replyUsername;
    private String replyUserImage;

    private String content;
    private String image;
    private LocalDateTime createTime;




}
