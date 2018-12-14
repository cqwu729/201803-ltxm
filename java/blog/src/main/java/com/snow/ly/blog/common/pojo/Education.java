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
@Document(collection="s_education")
@NoArgsConstructor
@AllArgsConstructor
public class Education {

    @Id
    private String id;
    @Indexed
    private String userId;
    /**学校*/
    private String school;
    /**学位*/
    private String degree;
    private String datesAttended;
    private String description;




















}
