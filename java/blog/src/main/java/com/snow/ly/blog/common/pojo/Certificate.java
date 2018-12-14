package com.snow.ly.blog.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

import static com.snow.ly.blog.common.bean.Constants.IMG;

@Data
@Builder
@Document(collection="s_certificate")
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {

    @Id
    private String id;
    @Indexed
    private String userId;
    private String userImg;
    private String firstName;
    private String lastName;
    /**0  Athele;1 Club ; 2 Bloggers; 3 Fans ; 4 College Students*/
    /***  运动员 俱乐部 博客 粉丝 大学生*/
    private Integer profileType=0;
    /**证书*/
    private String certificate;
    /**是否通过认证0 等待审核 1 通过 2 未通过*/
    private Integer pass;
    /**证书图片*/
    private String certificateImage;
    /**上传日期*/
    private LocalDate createDate;





}
