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
@Document(collection="message_delete")
@NoArgsConstructor
@AllArgsConstructor
public class MessageDelete {


    @Id
    private String id;
    @Indexed
    private String userId;
    private String messageId;











}
