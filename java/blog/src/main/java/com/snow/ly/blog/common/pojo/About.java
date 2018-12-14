package com.snow.ly.blog.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection="s_about")
@NoArgsConstructor
@AllArgsConstructor
public class About {


    /**关于我们*/
    @Id
    private String id;
    /**背景*/
    private String img;
    /**内容*/
    String content;





}
