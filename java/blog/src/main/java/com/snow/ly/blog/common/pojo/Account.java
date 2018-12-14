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
@Document(collection="s_account")
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    private String id;
    /**关联用户*/
    @Indexed
    private String xUserId;
    private String userId;
    private String userImage;
    private String userPhone;
    private String userEmail;

    private String firstName;
    private String lastName;

















}
