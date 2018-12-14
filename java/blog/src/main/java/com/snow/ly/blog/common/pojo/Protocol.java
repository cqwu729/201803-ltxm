package com.snow.ly.blog.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection="s_protocol")
@NoArgsConstructor
@AllArgsConstructor
public class Protocol {


    /**注册协议*/
    @Id
    private String id;
    private String title;
    private String content;









}
