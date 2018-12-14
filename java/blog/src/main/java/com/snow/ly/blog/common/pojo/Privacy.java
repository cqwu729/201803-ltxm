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
@Document(collection="s_privacy")
@NoArgsConstructor
@AllArgsConstructor
public class Privacy {

    /**
     * 用户隐私设置
     */
    @Id
    private String id;
    @Indexed
    private String userId;
    /**0关闭 1 开启*/
    /**所用通知*/
    private Integer allNotifications;
    /**邮件*/
    private Integer email;
    /**相册*/
    private Integer wall;
    /**好友请求*/
    private Integer connections;










}
