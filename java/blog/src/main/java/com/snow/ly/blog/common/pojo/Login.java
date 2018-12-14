package com.snow.ly.blog.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection="s_login")
@NoArgsConstructor
@AllArgsConstructor
public class Login {


    /**登录背景图*/
    @Id
    private String id;
    /**背景*/
    private String img;






}
