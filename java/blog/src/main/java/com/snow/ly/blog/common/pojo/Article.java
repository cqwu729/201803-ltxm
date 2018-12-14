package com.snow.ly.blog.common.pojo;

import com.snow.ly.blog.common.bean.UserBean;
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
@Document(collection="s_article")
@NoArgsConstructor
@AllArgsConstructor
public class Article {


    @Id
    private String id;
    @Indexed
    private String userId;
    private String userImg;
    private String firstName;
    private String lastName;

    /**类型 0 帖子 1 投票 2 视频 3 活动 4 系统的活动*/
    private Integer type;
    /**标题*/
    private String title;
    /**内容*/
    private String content;
    /**图片*/
    private List<String> images=new ArrayList<>();
    /**视频*/
    private List<String> videos=new ArrayList<>();
    /**可见性 0 只有自己可见 1 好友，粉丝  2 所用人可见*/
    private Integer visibility=2;
    /**阅览数*/
    private Integer views=0;
    /**分享数*/
    private Integer share=0;
    /**喜欢数*/
    private Integer like=0;
    /**地址*/
    private String address;
    /**有效期*/
    private LocalDateTime validity;
    /**投票图片*/
    private String image;
    /**投票选项*/
    private List<PollItem> pollItems=new ArrayList<>();
    /**点赞用户*/
    private List<UserBean> isLikeUsers=new ArrayList<>();
    /**评论用户*/
    private List<ArticleComment> commentAndReply=new ArrayList<>();

    /**是否来自分享 0 不是 1 是*/
    private Integer isToShare;
    /**分享标题*/
    private String shareTitle;

    /**来自用户*/
    private String toUserId;
    private String toUserImg;
    private String toFirstName;


    /**是否投票 0 未投票 1投票*/
    private Integer isPoll=0;
    /**是否点赞 0 未 1已*/
    private Integer isLike=0;
    /**是否阅读 0 未 1 已*/
    private Integer isView=0;
    /**是否关注 0 未 1已*/
    private Integer isFollowed=0;
    /**是不是 后台发布 0 不是 1 是 */
    private Integer isSys=0;

    /**创建时间*/
    private LocalDateTime createTime;

    /**
     * 分享的文章ID
     */
    private String shareArticleId;










}
