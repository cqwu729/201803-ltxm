package com.snow.ly.blog.module.group;

import com.snow.ly.blog.common.bean.Result;
import com.snow.ly.blog.common.bean.Tips;
import com.snow.ly.blog.common.pojo.Group;
import com.snow.ly.blog.common.pojo.GroupUser;
import com.snow.ly.blog.common.pojo.User;
import com.snow.ly.blog.common.repository.GroupRepository;
import com.snow.ly.blog.common.repository.GroupUserRepository;
import com.snow.ly.blog.common.repository.MessageRepository;
import com.snow.ly.blog.common.repository.UserRepository;
import com.snow.ly.blog.common.util.StringUtils;
import com.snow.ly.blog.config.annotation.Authorization;
import com.snow.ly.blog.module.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


import static com.snow.ly.blog.common.bean.Result.fail;
import static com.snow.ly.blog.common.bean.Result.success;

@Service
public class GroupService {


    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupUserRepository groupUserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageService messageService;


    @Authorization
    public Result addGroup(String userId, String name, String img, String introduction, User user) {
       Group group=groupRepository.save(Group.builder().adminId(userId).name(name).img(img).introduction(introduction).createTime(LocalDateTime.now()).build());
        groupUserRepository.save(
                GroupUser.builder()
                 .groupId(group.getId())
                .userId(userId)
                .firstName(user.getFirstName())
                .userImg(user.getUserImg())
                .profileType(user.getProfileType())
                .build());
        return success(group);

    }

    public Result getGroups(String userId, Integer page, Integer pageSize) {
        if (page>0)page--;
        return success(groupUserRepository.findByUserId(userId,new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createTime"))).stream()
                .map(i->groupRepository.findOne(i.getGroupId()))
                .filter(i->i!=null)
                .distinct()
                .collect(Collectors.toList()));
    }

    public Result exitGroup(String userId, String groupId) {
      List<GroupUser> groupUsers=groupUserRepository.findByUserIdAndGroupId(userId,groupId);
       if (groupUsers.size()==0)return fail(Tips.GROUP_NOT.msg);
       groupUserRepository.delete(groupUsers.get(0));
       messageRepository.deleteByToUserIdAndGroupId(userId,groupId);
       return success();
    }

    public Result getGroupDetails(String id) {
        Group group=groupRepository.findOne(id);
        if (group==null) return fail(Tips.GROUP_NOT.msg);
        List<GroupUser> groupUsers=groupUserRepository.findByGroupId(id);
        group.setUsers(groupUsers.stream().map(i->{
           User u=userRepository.findOne(i.getUserId());
            if (u!=null){
                i.setFirstName(u.getFirstName());
                i.setLastName(u.getLastName());
                i.setUserImg(u.getUserImg());
            }
            return i;
        }).collect(Collectors.toList()));
        return success(group);

    }
    @Authorization
    public Result inviteFriends(String userId,String groupId, String friendsId,User user) {
        Group group=groupRepository.findOne(groupId);
        if (group==null) return fail(Tips.GROUP_NOT.msg);
        List<String> friendsIdLists = StringUtils.stringToArrayList(friendsId);
        for (String friendId : friendsIdLists){
            List<GroupUser> groupUsers = groupUserRepository.findByUserIdAndGroupId(friendId,groupId);
            if(groupUsers.size() > 0){
                return fail(Tips.GROUP_HAVE_USER.msg);
            }
        }
        List<GroupUser>groupUsers=StringUtils.stringToArrayList(friendsId).stream()
                .map(i->userRepository.findOne(i))
                .filter(i->i!=null)
                .distinct()
                .map(i->GroupUser.builder().groupId(groupId).userId(i.getUserId()).userImg(i.getUserImg()).firstName(i.getFirstName()).profileType(i.getProfileType()).build())
                .collect(Collectors.toList());
        groupUserRepository.save(groupUsers);

        //发送消息
        messageService.invite(user,groupUsers,"",1,group);

         return success();
    }


    public Result modifyGroup(String userId, String id, String name, String img, String introduction) {
        Group g=groupRepository.findOne(id);
        if (g==null)return fail(Tips.NOT_MESSAGE.msg);
        if (!userId.equals(g.getAdminId()))fail(Tips.AUTH_NOT.msg);
        if (StringUtils.isNotNull(name))g.setName(name);
        if (StringUtils.isNotNull(img))g.setImg(img);
        if (StringUtils.isNotNull(introduction))g.setIntroduction(introduction);
        return success(groupRepository.save(g));
    }
}
