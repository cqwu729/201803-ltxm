package com.snow.ly.blog.common.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection="s_call")
@NoArgsConstructor
@AllArgsConstructor
public class Call {

    /**
     * 联系我们
     */

    @Id
    private String id;
    private String fullName;
    private String eamil;
    private String numbere;
    private String subject;
    private String message;
    private LocalDateTime createTime;



}
