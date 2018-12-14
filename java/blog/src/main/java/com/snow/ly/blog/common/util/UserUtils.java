package com.snow.ly.blog.common.util;

import com.snow.ly.blog.common.repository.FollowedRepository;
import com.snow.ly.blog.common.repository.FollowersRepository;
import com.snow.ly.blog.common.repository.FriendsRepository;
import com.snow.ly.blog.common.repository.NotSeePeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserUtils {


    @Autowired
    private FollowersRepository followersRepository;
    @Autowired
    private FollowedRepository  followedRepository;
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private NotSeePeopleRepository notSeePeopleRepository;


    //判断是否是好友
    public boolean isFriend(String userId,String friendId){
        if (friendsRepository.findByUserIdAndFriendId(userId,friendId).size()>0)return true;
        return false;
    }


    //判断是不是粉丝
    public boolean isFollower(String userId,String followerId){
        if (followersRepository.findByUserIdAndAndFollowerId(userId,followerId).size()>0)return true;
        return false;
    }

    //判断是否关注
    public boolean isFollowed(String userId,String followedId){
        if (followedRepository.findByUserIdAndFollowedId(userId,followedId).size()>0)return true;
        return false;
    }

    //获取不查看人推荐列表
    public List<String> notSeePeopleList(String userId){
       return notSeePeopleRepository.findByUserId(userId).stream()
                .map(i->i.getPeopleId())
                .collect(Collectors.toList());
    }

    //用户ID去重
    public  List<String>  distinctUserIds(List<String>...lists){
    return Arrays.stream(lists)
                .flatMap(i->i.stream())
                .distinct()
                .collect(Collectors.toList());
    }


    //关注
    public List<String> getFollowed(String userId){
        return followedRepository.findByUserId(userId).map(i->i.getFollowedId()).collect(Collectors.toList());
    }

    //获取好友
    public List<String> getFriends(String userId){
        return friendsRepository.findByUserId(userId).map(i->i.getFriendId()).collect(Collectors.toList());
    }







}
