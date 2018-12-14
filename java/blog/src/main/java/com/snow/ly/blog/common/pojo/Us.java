package com.snow.ly.blog.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection="s_us")
@NoArgsConstructor
@AllArgsConstructor
public class Us {

    /**后台联系我们*/
    @Id
    private String id;
    private String email;
    private String address;
    private String longitude;
    private String latitude;





}
