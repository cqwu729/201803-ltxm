package com.snow.ly.blog.module.message;

import com.snow.ly.blog.config.annotation.BlogUser;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@Api(description = "消息API")
@RequestMapping("/message/")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("getMyMessage")
    @ApiOperation(value = "获取自己发送给好友的消息")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getMyMessage(@ApiParam(hidden = true) @BlogUser String userId,
                                       @ApiParam(value = "好友ID", required = true) @RequestParam String friendId,
                                       @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                       @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(messageService.getMyMessage(userId,friendId,page,pageSize));
    }

    @PostMapping("signMessage")
    @ApiOperation(value = "标记消息")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity signMessage(@ApiParam(value = "消息ID", required = true) @RequestParam String id,
                                      @ApiParam(value = "类型 0 未读 1 已读 ", required = true) @RequestParam Integer type) {
        return ok(messageService.signMessage(id,type));
    }

    @GetMapping("getMessage")
    @ApiOperation(value = "获取消息")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getMessage(@ApiParam(hidden = true) @BlogUser String userId,
                                     @ApiParam(value = "类型 0 未读 1 已读 2 所有", required = true) @RequestParam Integer type,
                                     @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                     @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(messageService.getMessage2(userId,type,page,pageSize));
    }

    @PostMapping("searchMessage")
    @ApiOperation(value = "搜索消息")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity searchMessage(@ApiParam(hidden = true) @BlogUser String userId,
                                        @ApiParam(value = "关键字", required = true) @RequestParam String keyword,
                                        @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                        @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(messageService.searchMessage(userId,keyword,page,pageSize));
    }


    @GetMapping("getGroupMessage")
    @ApiOperation(value = "获取组消息")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getGroupMessage(@ApiParam(hidden = true) @BlogUser String userId,
                                          @ApiParam(value = "组ID",required = true)@RequestParam String groupId,
                                          @ApiParam(value = "第几页", required = true) @RequestParam Integer page,
                                          @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(messageService.getGroupMessage(userId,groupId,page,pageSize));
    }


    @GetMapping("getFriendMessage")
    @ApiOperation(value = "获取好友消息")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getFriendMessage(@ApiParam(hidden = true) @BlogUser String userId,
                                           @ApiParam(value = "好友id", required = true) @RequestParam String friendId,
                                           @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                           @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(messageService.getFriendMessage(userId,friendId,page,pageSize));
    }

    @GetMapping("getMessageLists")
    @ApiOperation(value = "获取聊天消息列表")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getMessageLists(@ApiParam(hidden = true) @BlogUser String userId,
                                           @ApiParam(value = "好友id", required = true) @RequestParam String friendId,
                                           @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                           @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(messageService.getMessageLists(userId,friendId,page,pageSize));
    }



    @PostMapping("sendGroupMessage")
    @ApiOperation(value = "发送群消息")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity sendGroupMessage(@ApiParam(hidden = true) @BlogUser String userId,
                                           @ApiParam(value = "群ID",required = true)@RequestParam String groupId,
                                           @ApiParam(value = "消息内容",required = true)@RequestParam String message) {
        return ok(messageService.sendGroupMessage(userId,groupId,message,null));
    }


    @PostMapping("deleteMessage")
    @ApiOperation(value = "删除消息")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity deleteMessage(@ApiParam(hidden = true)@BlogUser String userId,
                                        @ApiParam(value = "消息ID",required = true)@RequestParam String messageId){
        return ok(messageService.deleteMessage(userId,messageId));
    }


    @GetMapping("getMessageDetails")
    @ApiOperation(value = "消息详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity getMessageDetails(@ApiParam(value = "消息ID",required = true)@RequestParam String messageId){
        return ok(messageService.getMessageDetails(messageId));
    }
    @GetMapping("getMessages")
    @ApiOperation(value = "获取消息列表",notes = "type= 0 好友消息 1好友申请消息 2 系统消息 ")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity getMessages(@ApiParam(hidden = true)@BlogUser String userId,
                                      @ApiParam(value = "消息类型",required = true)@RequestParam Integer type,
                                      @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                      @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize){
        return ok(messageService.getMessage(userId,type,page,pageSize));
    }


    @PostMapping("dealApply")
    @ApiOperation(value = "处理好友申请",notes = "type=0 拒绝 1同意")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity dealApply(@ApiParam(hidden = true)@BlogUser String userId,
                                    @ApiParam(value = "消息ID",required = true)@RequestParam String messageId,
                                    @ApiParam(value = "类型",required = true)@RequestParam Integer type) {
        return ok(messageService.dealApply(userId,messageId,type));
    }


    @PostMapping("sendMessageToFriends")
    @ApiOperation(value = "发送消息给好友",notes = "friendIds=23123123213,23232323123,3454353534")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity sendMessageToFriends(@ApiParam(hidden = true)@BlogUser String userId,
                                               @ApiParam(value = "好友ID",required = true)@RequestParam String friendIds,
                                               @ApiParam(value = "发送内容",required = true)@RequestParam String content){
        return ok(messageService.sendMessageToFriends(userId,friendIds,content,null));
    }











}
