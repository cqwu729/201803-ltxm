package com.snow.ly.blog.common.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Document(collection="s_group")
@NoArgsConstructor
@AllArgsConstructor
public class Group {


    /**用户群*/


    private String id;
    /**名称*/
    private String name;
    /**管理员*/
    private String adminId;
    /**用户*/
    private List<GroupUser> users=new ArrayList<>();
    /**头像*/
    private String img;
    /**简介*/
    private String introduction;

    private LocalDateTime createTime;







}
