package com.snow.ly.blog.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection="s_play")
@NoArgsConstructor
@AllArgsConstructor
public class Playing {

    /**运动员 大学生 玩历史*/
    @Id
    private String id;
    @Indexed
    private String userId;
    private String organizationName;
    private String sports;
    private String position;
    private String location;
    private String streetAddress;
    private String timePeriod;
    /**0 不行 1 是*/
    private Integer currently;
    private String description;
    private LocalDateTime createTime;










}
