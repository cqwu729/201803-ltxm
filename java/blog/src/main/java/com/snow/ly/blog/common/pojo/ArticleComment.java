package com.snow.ly.blog.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Document(collection="s_article_comment")
@NoArgsConstructor
@AllArgsConstructor
public class ArticleComment {



    @Id
    private String id;
    @Indexed
    private String articleId;
    private String userId;
    private String username;
    private String userImage;

    private String content;
    private String image;

    private List<ArticleCommentReply> commentReplies=new ArrayList<>();

    private LocalDateTime createTime;

    @Transient
    private long total;
    @Transient
    private long totalPage;





}
