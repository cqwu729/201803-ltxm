package com.snow.ly.blog.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

import static com.snow.ly.blog.common.bean.Constants.IMG;

@Data
@Builder
@Document(collection="s_admin")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="admin对象",description="admin对象")
public class Admin {

    /**管理员 */
    @Id
    @ApiModelProperty(value = "id",hidden = true)
    private String id;
    /**账号*/
    @Indexed(unique = true)
    @ApiModelProperty(value = "账号",hidden = true)
    private String account;
    @ApiModelProperty(value = "头像",hidden = true)
    private String img=IMG;
    /**密码*/
    @ApiModelProperty(value = "密码",hidden = true)
    private String password;
    @ApiModelProperty(value = "角色",hidden = true)
    /** 0 普通 1 超级 */
    private Integer role;
    @ApiModelProperty(value = "创建时间",hidden = true)
    private LocalDateTime createTime;
    @Transient
    @ApiModelProperty(value = "token",hidden = true)
    private String token;






}
