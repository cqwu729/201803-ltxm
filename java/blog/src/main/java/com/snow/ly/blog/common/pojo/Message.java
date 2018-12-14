package com.snow.ly.blog.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@Document(collection="s_message")
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Comparable {


    @Id
    private String id;
    @Indexed
    private String userId;
    private String userImg;
    private String firstName;
    private String lastName;
    /**用户是否删除 0 未 1 删*/
    private Integer userIsDelete;
    /**发送给谁*/
    @Indexed
    private String toUserId;
    /**接受用户是否删除*/
    private Integer toUserIsDelete;
    /**是不是对消息 0 普通消息 1 群消息*/
    private Integer isFlock=0;
    /**群id*/
    private String groupId;
    /**名称*/
    private String name;
    /**用户*/
    private List<GroupUser> users=new ArrayList<>();

    /**消息类型 0 好友消息 1好友申请消息 2 系统消息 3 邀请进群 4 点赞 5 评论 6 分享*/
    private Integer type;
    /**文章id*/
    private String articleId;
    /**消息内容*/
    private String content;
    /**发送时间*/
    private LocalDateTime sendTime;
    /**是否已读 0 未 1 已*/
    private Integer isRead;
    /**群头像*/
    private String groupImg;

    @Override
    public int compareTo(Object o) {
        Message sdto = (Message)o;

        LocalDateTime time = sdto.getSendTime();
        // note: enum-type's comparation depend on types' list order of enum method
        // so, if compared property is enum-type ,then its comparationfollow ObjEnum.objType order


        return this.sendTime.compareTo(time);
    }
}
