package com.snow.ly.blog.common.bean;

public enum  Tips {


    FAIL(0,"失败"),
    SUCCESS(1,"成功"),
    DISABLED_TOEK(2,"token过期"),
    USER_EMAIL_HAD("该账号已注册"),
    USER_PASSWORD_FALSE("用户名或密码错误"),
    USER_PASSWORD_F("旧密码错误"),
    USER_NOT("用户信息不存在"),
    CODE_FALSE("验证码错误"),
    EMAIL_PHONE_FALSE("请输入正确的邮箱或手机号"),
    EMAIL_FALSE("请输入正确的邮箱"),
    PHONE_FALSE("请输入正确的手机号"),
    EMAIL_USER_HAD("该邮箱已经绑定了其他用户"),
    EMAIL_ONESLF("你已经绑定过了该邮箱"),
    LANGUAGE_FALE("语言信息不存在"),
    PHONE_USER_HAD("该手机已绑定其他用户"),
    PHONE_ONESLF("你已经绑定过了该手机"),
    USER_HAD_BIND("用户已经绑定过了基本信息"),
    POLL_NOT("投票信息不存在"),
    POLL_ITEM_NOT("投票项信息不存在"),
    VOTE_HAD("已经投过票了"),
    POLL_OVERDUE("投票已过期"),
    MESSAGE_NOT("查询信息存在"),
    LIEK_HAD("已点赞"),
    PARAMETER("参数错误"),
    COMMENT_NOT("评论信息不存在"),
    FOLLOWED_NOT("FOLLOWED信息不存在"),
    FRIEND_HAD("已经是好友了，无需重复添加"),
    MESSAGE_SYS("系统消息请联系管理员"),
    ALBUM_NOT("相册信息不存在"),
    GROUP_NOT("群信息不存在"),
    ADMIN_NOT("管理员信息不存在"),
    ADMIN_ACOUNT_HAD("管理员账号信息已存在"),
    AUTH_NOT("没有权限"),
    ACCOUNT_FALSE("该账号已被管理员禁用或删除，请联系管理员"),
    ADVERT_NOT("广告信息不存在"),
    NOT_MESSAGE("所查询的信息不存在"),
    LANGUAGE_HAD("语言类型已存在"),
    GROUP_HAVE_USER("该用户已加入该群"),
    USER_IN_ACCOUNT("该用户已添加"),
    ALREADY_FOLLOW("该用户已关注"),



    ;


    public Integer code;
    public String msg;


    Tips(String msg) {
        this.msg = msg;
    }

    Tips(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
