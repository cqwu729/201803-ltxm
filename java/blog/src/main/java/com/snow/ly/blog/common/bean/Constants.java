package com.snow.ly.blog.common.bean;

/**
 * 常量
 *
 */
public class Constants {


    /**
     * 默认一页多少条
     */
    public static final Integer PAGESIZE=30;

    /**
     * 默认头像
     */
    public static final String IMG="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505214447543&di=9d0908c9b59c1885ee2f56fbaf46451b&imgtype=0&src=http%3A%2F%2Fwww.qqzhi.com%2Fuploadpic%2F2014-10-08%2F210702784.jpg";

    /***
     * 默认封面
     */
    public static final String COVER="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505214447543&di=9d0908c9b59c1885ee2f56fbaf46451b&imgtype=0&src=http%3A%2F%2Fwww.qqzhi.com%2Fuploadpic%2F2014-10-08%2F210702784.jpg";

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    public static final String CURRENT_ADMIN_ID = "CURRENT_ADMIN_ID";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 72;

    /**
     * code有效期 分
     */
    public static final int CODE_EXPIRES_MINUTE=5;


    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "authorization";


    public static final String ADMIN_AUTH = "adminAuth";

}
