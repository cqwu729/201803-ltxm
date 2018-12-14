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
@Document(collection="s_user_communities")
@NoArgsConstructor
@AllArgsConstructor
public class UserCommunities {

    /**
     * 用户社区
     */
    @Id
    private String id;
    private String adminId;
    /**用户*/
    @Indexed
    private String userId;
    private String communitiesTitle;
    /**图片*/
    private String communitiesImg;
    /**内容*/
    private String communitiesContent;
    private LocalDateTime createTime;








}
