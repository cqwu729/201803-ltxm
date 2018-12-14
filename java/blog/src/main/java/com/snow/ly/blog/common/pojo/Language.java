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
@Document(collection="s_language")
@NoArgsConstructor
@AllArgsConstructor
public class Language {

    /**
     * 系统语言
     */

    @Id
    private String id;
    private String language;
    /**0 中文 1 语文 2 。。。。*/
    @Indexed(unique = true)
    private Integer type;





}
