package com.snow.ly.blog.module.group;

import com.snow.ly.blog.config.annotation.BlogUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@Api(description = "群API")
@RequestMapping("/group/")
public class GroupController {

    @Autowired
    private GroupService groupService;


    @PostMapping("modifyGroup")
    @ApiOperation(value = "修改社团")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyGroup(@ApiParam(hidden = true) @BlogUser String userId,
                                      @ApiParam(value = "社团ID",required = true)@RequestParam String id,
                                      @ApiParam(value = "群名称")@RequestParam(required = false) String name,
                                      @ApiParam(value = "群头像")@RequestParam(required = false) String img,
                                      @ApiParam(value = "群简介")@RequestParam(required = false) String introduction) {
        return ok(groupService.modifyGroup(userId,id,name,img,introduction));
    }



    @PostMapping("inviteFriends")
    @ApiOperation(value = "邀请好友加入群",notes = "friendsId=111,2323,3434")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity inviteFriends(@ApiParam(hidden = true) @BlogUser String userId,
                                        @ApiParam(value = "群ID",required = true)@RequestParam String groupId,
                                        @ApiParam(value = "好友IDs",required = true)@RequestParam String friendsId) {
        return ok(groupService.inviteFriends(userId,groupId,friendsId,null));
    }

    @GetMapping("getGroupDetails")
    @ApiOperation(value = "获取群详情")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getGroupDetails(@ApiParam(value = "群ID", required = true) @RequestParam String id) {
        return ok(groupService.getGroupDetails(id));
    }

    @PostMapping("exitGroup")
    @ApiOperation(value = "退出该群")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity exitGroup(@ApiParam(hidden = true) @BlogUser String userId,
                                    @ApiParam(value = "群ID",required = true)@RequestParam String groupId) {
        return ok(groupService.exitGroup(userId,groupId));
    }

    @GetMapping("getGroups")
    @ApiOperation(value = "获取群列表")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getGroups(@ApiParam(hidden = true) @BlogUser String userId,
                                    @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                    @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(groupService.getGroups(userId,page,pageSize));
    }

    @PostMapping("addGroup")
    @ApiOperation(value = "添加群")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity addGroup(@ApiParam(hidden = true) @BlogUser String userId,
                                   @ApiParam(value = "群名称",required = true)@RequestParam String name,
                                   @ApiParam(value = "群头像",required = true)@RequestParam String img,
                                   @ApiParam(value = "群简介",required = true)@RequestParam String introduction) {
        return ok(groupService.addGroup(userId,name,img,introduction,null));
    }



}
