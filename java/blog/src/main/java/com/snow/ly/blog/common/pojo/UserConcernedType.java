package com.snow.ly.blog.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection="s_user_concerned_type")
@NoArgsConstructor
@AllArgsConstructor
public class UserConcernedType {

    /**用户运动类型*/

    @Id
    private String id;
    private String userId;
    private String concernedId;
    private String concerned;
    private String img;





}
