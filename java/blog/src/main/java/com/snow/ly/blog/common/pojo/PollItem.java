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
@Document(collection="s_poll_item")
@NoArgsConstructor
@AllArgsConstructor
public class PollItem {


    @Id
    private String id;
    @Indexed
    private String articleId;
    private String content;
    private String image;
    /**总的投票数*/
    private Double total=0.0;
    private Integer count=0;
    private Double percentage=0.0;
    private LocalDateTime validity=LocalDateTime.now();



}
