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
@Document(collection="s_type")
@NoArgsConstructor
@AllArgsConstructor
public class Type {

    /**俱乐部 type*/
    @Id
    private String id;
    @Indexed
    private String userId;
    private String type;








}
