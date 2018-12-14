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
@Document(collection="s_not_see_people")
@NoArgsConstructor
@AllArgsConstructor
public class NotSeePeople {


    @Id
    private String id;
    @Indexed
    private String userId;
    private String peopleId;














}
