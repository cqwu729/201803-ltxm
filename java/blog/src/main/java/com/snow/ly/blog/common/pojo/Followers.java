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
@Document(collection="s_followers")
@NoArgsConstructor
@AllArgsConstructor
public class Followers {


    /**
     * 粉丝
     */

    @Id
    private String id;
    @Indexed
    private String userId;
    private String followerId;



}
