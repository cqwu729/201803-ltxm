package com.snow.ly.blog.module.message;

import com.snow.ly.blog.common.bean.Result;
import com.snow.ly.blog.common.bean.Tips;
import com.snow.ly.blog.common.pojo.*;
import com.snow.ly.blog.common.repository.*;
import com.snow.ly.blog.common.util.StringUtils;
import com.snow.ly.blog.common.util.UserUtils;
import com.snow.ly.blog.config.annotation.Authorization;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.snow.ly.blog.common.bean.Result.fail;
import static com.snow.ly.blog.common.bean.Result.success;


@Service
public class MessageService {


    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupUserRepository groupUserRepository;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private MessageDeleteRepository messageDeleteRepository;
    @Autowired
    private PrivacyRepository privacyRepository;

    @Authorization
    public Result sendGroupMessage(String userId, String groupId, String message,User user) {
        Group group=groupRepository.findOne(groupId);
        if (group==null) return fail(Tips.GROUP_NOT.msg);
       List<String>  f=groupUserRepository.findByGroupId(groupId).stream()
                .map(i->i.getUserId())
                .filter(i -> !i.equals(userId))
                .distinct()
                .collect(Collectors.toList());
        sendMessageToFriends(user,f,message,1,group);
        return success();
    }





   /**好友申请消息*/
   public Message sendFriendRequest(User user,String friendId){
     return sendMessage(
             Message.builder()
                     .userId(user.getUserId())
                     .firstName(user.getFirstName())
                     .lastName(user.getLastName())
                     .userImg(user.getUserImg())
                     .toUserId(friendId)
                     .isFlock(0)
                     .userIsDelete(0)
                     .toUserIsDelete(0)
                     .type(1)
                     .isRead(0)
                     .sendTime(LocalDateTime.now())
                     .build());
   }


   /**点赞*/
   public void like(User user ,String toUserId,String articleId){
       sendMessageToFriends(user, articleId,Arrays.asList(toUserId),user.getFirstName(),0,4);

   }

    /**评论*/
    public void comment(User user ,String toUserId,String articleId){
        sendMessageToFriends(user, articleId,Arrays.asList(toUserId),user.getFirstName(),0,5);

    }

    /**评论*/
    public void share(User user ,String toUserId,String articleId){
        sendMessageToFriends(user,articleId,Arrays.asList(toUserId),user.getFirstName(),0,6);

    }


    /**发送消息*/
    public Message sendMessage(Message message){
        return messageRepository.save(message);
    }


    public List<String> getXId(List<String> friendIds){
      return friendIds.stream()
                .filter(i->i!=null)
                .map(i->{
                    List<Privacy> p=privacyRepository.findByUserId(i);
                    if (p.size()>0&&p.get(0).getAllNotifications()==1)return i;
                    return null;
                })
                .filter(i->i!=null)
                .collect(Collectors.toList());
    }


    /**发送消息给好友*/
    public void sendMessageToFriends(User user,List<String> friendIds,String content,Integer isFlock){
       messageRepository.save(getXId(friendIds).stream()
                .map(i->Message.builder()
                        .userId(user.getUserId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .userImg(user.getUserImg())
                        .toUserId(i)
                        .isFlock(isFlock)
                        .userIsDelete(0)
                        .toUserIsDelete(0)
                        .type(0)
                        .sendTime(LocalDateTime.now())
                        .content(content)
                        .isRead(0)
                        .build())
                .collect(Collectors.toList()));
    }

    /**发送消息给好友*/
    public void sendMessageToFriends(User user,String articleId,List<String> friendIds,String content,Integer isFlock,Integer type){


        messageRepository.save(getXId(friendIds).stream()
                .map(i->Message.builder()
                        .userId(user.getUserId())
                        .articleId(articleId)
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .userImg(user.getUserImg())
                        .toUserId(i)
                        .isFlock(isFlock)
                        .userIsDelete(0)
                        .toUserIsDelete(0)
                        .type(type)
                        .sendTime(LocalDateTime.now())
                        .content(content)
                        .isRead(0)
                        .build())
                .collect(Collectors.toList()));
    }




    /**群 发送消息给好友*/
    public void sendMessageToFriends(User user,List<String> friendIds,String content,Integer isFlock,Group group){
        messageRepository.save(getXId(friendIds).stream()
                .map(i->Message.builder()
                        .groupImg(group.getImg())
                        .userId(user.getUserId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .userImg(user.getUserImg())
                        .toUserId(i)
                        .isFlock(isFlock)
                        .groupId(group.getId())
                        .name(group.getName())
                        .users(group.getUsers())
                        .userIsDelete(0)
                        .toUserIsDelete(0)
                        .type(0)
                        .sendTime(LocalDateTime.now())
                        .content(content)
                        .isRead(0)
                        .build())
                .collect(Collectors.toList()));
    }


    /**邀请好友加入群消息*/
    public void  invite(User user,List<GroupUser> users,String content,Integer isFlock,Group group ){
        messageRepository.save(users.stream()
                .map(i->Message.builder()
                        .userId(user.getUserId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .userImg(user.getUserImg())
                        .toUserId(i.getUserId())
                        .isFlock(isFlock)
                        .groupId(group.getId())
                        .name(group.getName())
                        .users(group.getUsers())
                        .userIsDelete(0)
                        .toUserIsDelete(0)
                        .type(3)
                        .sendTime(LocalDateTime.now())
                        .content(content)
                        .isRead(0)
                        .build())
                .collect(Collectors.toList()));
    }


    /**发送系统消息*/
    public  Message pushMessage(Admin admin,String content){
        return messageRepository.save(
                Message.builder()
                        .userId(admin.getId())
                        .firstName(admin.getAccount())
                        .userImg(admin.getImg())
                        .isFlock(0)
                        .userIsDelete(0)
                        .toUserIsDelete(0)
                        .type(2)
                        .sendTime(LocalDateTime.now())
                        .content(content)
                        .isRead(0)
                        .build()
        );

    }

    /**删除消息*/
    public void deleteMessagex(String messageId,String userId){
        messageDeleteRepository.save(MessageDelete.builder().userId(userId).messageId(messageId).build());
    }


    /**去掉删除的消息*/
    public List<Message> getDeleteM(String userId, List<Message> msg){
        List<String> list=msgId(userId);
        return msg.stream()
                .filter(i->!list.contains(i.getId()))
                .collect(Collectors.toList());
    }

    public List<String> msgId(String userId){
          return messageDeleteRepository.findByUserId(userId).stream()
                  .map(i->i.getMessageId())
                  .distinct()
                  .collect(Collectors.toList());
    }


    @Authorization
    public Result sendMessageToFriends(String userId, String friendIds,String content,  User user) {
        sendMessageToFriends(user,StringUtils.stringToArrayList(friendIds),content,0);
       return success();

    }

    public Result getMessage(String userId, Integer type, Integer page, Integer pageSize) {
        if (type<0||type>2)return fail(Tips.PARAMETER.msg);
        if (page>0)page--;
        Pageable pageable=new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"sendTime"));
        List<Message> msg=new ArrayList<>();
        List<String> deletedIds = msgId(userId);
        //if (type!=2) msg=messageRepository.findByToUserIdAndType(userId,type,pageable);
        //if (type==2) msg=messageRepository.findByType(type,pageable).getContent();
        if (type != 2) msg=messageRepository.findByToUserIdAndTypeAndUserIdNotIn(userId,type,deletedIds,pageable);
        if (type == 2){
            ArrayList<Integer> typeList = new ArrayList<>();
            typeList.add(type);
            typeList.add(4);
            typeList.add(5);
            typeList.add(6);
            msg=messageRepository.findByTypeInAndUserIdNotInAndIsRead(typeList,deletedIds,0,pageable);
        }
        return success(msg);
        //return success(getDeleteM(userId,msg));
    }

    public Result getMessageDetails(String messageId) {
        Message message=messageRepository.findOne(messageId);
        if (message==null)return fail(Tips.MESSAGE_NOT.msg);
        return success(message);

    }

    public Result deleteMessage(String userId,String messageId) {
        Message message=messageRepository.findOne(messageId);
        if (message==null)return fail(Tips.MESSAGE_NOT.msg);
        deleteMessagex(messageId,userId);
        return success();

    }

    public Result dealApply(String userId, String messageId,Integer type) {
        if (type<0||type>1)return fail(Tips.PARAMETER.msg);
        Message m=messageRepository.findOne(messageId);
        if (m==null)return fail(Tips.MESSAGE_NOT.msg);
        if (m.getType()!=1)return fail(Tips.MESSAGE_NOT.msg);
        if (type==1){
            if (!userUtils.isFriend(userId,m.getUserId()))
                friendsRepository.save(
                        Friends.builder()
                                .userId(userId)
                                .friendId(m.getUserId())
                                .build());
            if (!userUtils.isFriend(m.getUserId(),userId))
                friendsRepository.save(
                        Friends.builder()
                                .userId(m.getUserId())
                                .friendId(userId)
                                .build());

        }
        messageRepository.delete(m);
       return success();
    }

    public Result getFriendMessage(String userId, String friendId,Integer page,Integer pageSize) {
        if (page>0)page--;
        List<Message>msg=messageRepository.findByUserIdAndToUserId(friendId,userId,new PageRequest(page,pageSize));
        return success(getDeleteM(userId,msg));
    }

    public Result getGroupMessage(String userId,String groupId, Integer page, Integer pageSize) {
        if (page>0)page--;
        List<Message>msg=messageRepository.findByGroupIdAndIsFlockAndToUserId(groupId,1,userId,new PageRequest(page,pageSize));
        return success(getDeleteM(userId,msg));
    }

    public Result searchMessage(String userId, String keyword,Integer page,Integer pageSize) {
        if (page>0)page--;
        List<Message>msg=messageRepository.findByToUserIdAndContentLike(userId,keyword,new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"sendTime")));
        return success(getDeleteM(userId,msg));
    }

    public Result getMessage2(String userId, Integer type,Integer page,Integer pageSize) {
        if (type<0||type>2)return fail(Tips.PARAMETER.msg);
        if (page>0)page--;
        List<Message> pm=new ArrayList<>();
        if (type!=2)pm=messageRepository.findByToUserIdAndIsRead(userId,type,new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"sendTime")));
        if (type==2)pm=messageRepository.findByToUserId(userId,new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"sendTime")));
        return success(getDeleteM(userId,pm));
    }

    public Result signMessage(String id, Integer type) {
        if (type<0||type>1)return fail(Tips.PARAMETER.msg);
        Message m=messageRepository.findOne(id);
        if (m==null)return fail(Tips.NOT_MESSAGE.msg);
        m.setIsRead(type);
        return success(messageRepository.save(m));
    }

    public Result getMyMessage(String userId, String friendId, Integer page, Integer pageSize) {
        if (page>0)page--;
        List<Message>pm=messageRepository.findByUserIdAndToUserId(userId,friendId,new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"sendTime")));
        return success(getDeleteM(userId,pm));
    }

    public Result getMessageLists(String userId, String friendId,Integer page,Integer pageSize) {
        if (page>0)page--;
        List<Message>msg=messageRepository.findByUserIdAndToUserId(friendId,userId,new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"sendTime")));
        List<Message>pm=messageRepository.findByUserIdAndToUserId(userId,friendId,new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"sendTime")));
        msg.addAll(pm);
        Collections.sort(msg);
        return success(getDeleteM(userId,msg));
    }
}
