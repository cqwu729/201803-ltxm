package com.snow.ly.blog.module.user;

import com.snow.ly.blog.config.annotation.BlogUser;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@Api(description = "用户 API")
@RequestMapping("/user/")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("likeSKill")
    @ApiOperation(value = "点赞技能")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity likeSKill(@ApiParam(hidden = true)@BlogUser String userId,
                                    @ApiParam(value = "技能ID", required = true) @RequestParam String id) {
        return ok(userService.likeSKill(userId,id,null));
    }

    @GetMapping("getArticle")
    @ApiOperation(value = "获取文章",notes = "type=0 自己的 1 关注的和好友的 2 所有的 ")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getArticle(@ApiParam(hidden = true)@BlogUser String userId,
                                     @ApiParam(value = "类型", required = true) @RequestParam Integer type,
                                     @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                     @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(userService.getArticle(userId,type,page,pageSize));
    }
    @GetMapping("getOtherArticle")
    @ApiOperation(value = "获取他人的文章",notes = "type=0 自己的 1 关注的和好友的 2 所有的 ")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getOtherArticle(@ApiParam(value = "其他用户ID",required = true)@RequestParam String userId,
                                     @ApiParam(value = "类型", required = true) @RequestParam Integer type,
                                     @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                     @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(userService.getArticle(userId,type,page,pageSize));
    }


    @PostMapping("modifyWH")
    @ApiOperation(value = "修改体重身高")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyWH(@ApiParam(hidden = true)@BlogUser String userId,
                                   @ApiParam(value = "体重") @RequestParam(required = false) String w,
                                   @ApiParam(value = "身高")@RequestParam(required = false)String h) {
        return ok(userService.modifyWH(userId,w,h,null));
    }

    @GetMapping("myShare")
    @ApiOperation(value = "我的分享列表")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity myShare(@ApiParam(hidden = true)@BlogUser String userId,
                                  @ApiParam(value = "第几页", required = true) @RequestParam Integer page,
                                  @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(userService.myShare(userId,page,pageSize));
    }


    @PostMapping("share")
    @ApiOperation(value = "分享")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity share(@ApiParam(hidden = true)@BlogUser String userId,
                                @ApiParam(value = "类型 0 只有自己可见 1 好友，粉丝  2 所用人可见",required =true)@RequestParam Integer type,
                                @ApiParam(value = "分享标题")@RequestParam(required = false) String shareTitle,
                                @ApiParam(value = "文章id", required = true) @RequestParam String id) {
        return ok(userService.share(userId,type,shareTitle,id,null));
    }


    @PostMapping("deleteAccount")
    @ApiOperation(value = "删除账号",notes = "ids=1232,2312,231")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteAccount(@ApiParam(value = "账号id", required = true) @RequestParam String ids) {
        return ok(userService.deleteAccount(ids));
    }

    @PostMapping("accountSwitch")
    @ApiOperation(value = "账号切换")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity accountSwitch(@ApiParam(value = "账号id", required = true) @RequestParam String id) {
        return ok(userService.accountSwitch(id));
    }


    @GetMapping("getAccount")
    @ApiOperation(value = "获取账号列表")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getAccount(@ApiParam(hidden = true)@BlogUser String userId) {
        return ok(userService.getAccount(userId));
    }

    @PostMapping("addAccount")
    @ApiOperation(value = "添加账号")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity addAccount(@ApiParam(hidden = true)@BlogUser String userId,
                                     @RequestParam String userEmailOrUserPhone,
                                     @RequestParam String userPassword) {
        return ok(userService.addAccount(userId,userEmailOrUserPhone,userPassword));
    }

    @PostMapping("deleteFriends")
    @ApiOperation(value = "删除好友",notes = "ids=111,222,23232")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteFriends(@ApiParam(hidden = true)@BlogUser String userId,
                                        @ApiParam(value = "好友id", required = true) @RequestParam String ids) {
        return ok(userService.deleteFriends(userId,ids));
    }

    @GetMapping("getPrivacy")
    @ApiOperation(value = "用户用户隐私设置")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getPrivacy(@ApiParam(hidden = true)@BlogUser String userId) {
        return ok(userService.getPrivacy(userId));
    }

    @PostMapping("setPrivacy")
    @ApiOperation(value = "设置隐私")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity setPrivacy(@ApiParam(hidden = true)@BlogUser String userId,
                                     @RequestParam(required = false) Integer allNotifications,
                                     @RequestParam(required = false) Integer email,
                                     @RequestParam(required = false) Integer wall,
                                     @RequestParam(required = false) Integer connections) {
        return ok( userService.setPrivacy(userId,allNotifications,email,wall,connections));
    }


    @PostMapping("deletePhotos")
    @ApiOperation(value = "删除图片",notes = "ids=1,2,3")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deletePhotos(@ApiParam(value = "图片ID",required = true)@RequestParam String ids){
         return ok(userService.deletePhotos(ids));
    }

    @PostMapping("addPhotos")
    @ApiOperation(value = "添加图片",notes = "imgs=11111,2222222,33333333")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity addPhotos(@ApiParam(value = "相册ID",required = true)@RequestParam String id,
                                    @ApiParam(value = "图片",required = true)@RequestParam String imgs){
         return ok(userService.addPhotos(id,imgs));
    }

    @GetMapping("albumDetails")
    @ApiOperation(value = "相册详情")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity albumDetails(@ApiParam(value = "相册id", required = true) @RequestParam String id,
                                       @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                       @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(userService.albumDetails(id,page,pageSize));
    }


    @PostMapping("deleteAlbum")
    @ApiOperation(value = "删除相册")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteAlbum(@ApiParam(value = "相册ID",required = true)@RequestParam String id){
        return ok(userService.deleteAlbum(id));
    }

    @GetMapping("getAlbum")
    @ApiOperation(value = "获取相册列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity getAlbum(@ApiParam(hidden = true) @BlogUser String userId){
        return ok(userService.getAlbum(userId));
    }



    @GetMapping("getOtherAlbum")
    @ApiOperation(value = "获取他人相册列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity getOtherAlbum(@ApiParam(value = "其他用ID",required = true) @RequestParam String userId){
        return ok(userService.getAlbum(userId));
    }


    @PostMapping("modifyAlbum")
    @ApiOperation(value = "修改相册")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyAlbum(@ApiParam(value = "相册ID", required = true) @RequestParam String id,
                            @ApiParam(value = "标题")@RequestParam(required = false) String title,
                            @ApiParam(value = "封面")@RequestParam(required = false) String cover,
                            @ApiParam(value = "描述")@RequestParam(required = false) String description,
                            @ApiParam(value = "地址")@RequestParam(required = false) String location,
                            @ApiParam(value = "tags")@RequestParam(required = false) String tags,
                            @ApiParam(value = "图片")@RequestParam(required = false) String imgs) {
        return ok(userService.modifyAlbum(id,title,cover,description,location,tags,imgs));
    }

    @PostMapping("addAlbum")
    @ApiOperation(value = "添加相册",notes = "imgs=111,22,333")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity addAlbum(@ApiParam(hidden = true) @BlogUser String userId,
                                   @ApiParam(value = "标题",required = true)@RequestParam String title,
                                   @ApiParam(value = "封面",required = true)@RequestParam String cover,
                                   @ApiParam(value = "描述",required = true)@RequestParam String description,
                                   @ApiParam(value = "地址",required = true)@RequestParam String location,
                                   @ApiParam(value = "tags",required = true)@RequestParam String tags,
                                   @ApiParam(value = "图片",required = true)@RequestParam String imgs){
       return ok(userService.addAlbum(userId,title,cover,description,location,tags,imgs));
    }



    @PostMapping(value = "deleteBests")
    @ApiOperation(value = "运动员-删除个人记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity deleteBests(@ApiParam(value = "记录ID", required = true) @RequestParam String id) {
        return ok(userService.deleteBests(id));
    }



    @PostMapping(value = "addBests")
    @ApiOperation(value = "运动员-添加个人记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity addBests(@ApiParam(hidden = true) @BlogUser String userId,
                                         @ApiParam(value = "内容", required = true) @RequestParam String best) {
        return ok(userService.addBests(userId,best));
    }


    @PostMapping(value = "deleteCertificate")
    @ApiOperation(value = "俱乐部-删除证书")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity deleteCertificate(@ApiParam(value = "证书ID", required = true) @RequestParam String id) {
        return ok(userService.deleteCertificate(id));
    }

    @PostMapping(value = "addCertificate")
    @ApiOperation(value = "俱乐部-添加证书")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity addCertificate(@ApiParam(hidden = true) @BlogUser String userId,
                                         @ApiParam(value = "证书名", required = true) @RequestParam String certificate,
                                         @ApiParam(value = "证书图片",required = true)@RequestParam String certificateImage) {
        return ok(userService.addCertificate(userId, certificate,certificateImage));
    }

    @PostMapping(value = "modifyAddress")
    @ApiOperation(value = "俱乐部-修改地址")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity modifyAddress(@ApiParam(hidden = true) @BlogUser String userId,
                                  @ApiParam(value = "内容", required = true) @RequestParam String address) {
        return ok(userService.modifyAddress(userId, address,null));
    }

    @PostMapping(value = "deleteType")
    @ApiOperation(value = "俱乐部-删除TYPE")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity addType(@ApiParam(value = "TYPE_ID", required = true) @RequestParam String id) {
        return ok(userService.deleteType(id));
    }

    @PostMapping(value = "addType")
    @ApiOperation(value = "俱乐部-添加TYPE")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity addType(@ApiParam(hidden = true) @BlogUser String userId,
                                  @ApiParam(value = "内容", required = true) @RequestParam String type) {
        return ok(userService.addType(userId, type));
    }

    @PostMapping(value = "modifyIntroduction")
    @ApiOperation(value = "俱乐部-修改简介")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity modifyIntroduction(@ApiParam(hidden = true)@BlogUser String userId,
                                          @ApiParam(value = "简介",required = true)@RequestParam String introduction){
        return ok(userService.modifyIntroduction(userId,introduction,null));
    }
    @PostMapping(value = "modifySignature")
    @ApiOperation(value = "修改签名")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity modifySignature(@ApiParam(hidden = true)@BlogUser String userId,
                                          @ApiParam(value = "签名",required = true)@RequestParam String signature){
        return ok(userService.modifySignature(userId,signature,null));
    }



    @PostMapping(value = "deleteConcernedType")
    @ApiOperation(value = "删除运动类型")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity deleteConcernedType(@ApiParam(value = "运动类型ID",required = true)@RequestParam String id){
        return ok(userService.deleteConcernedType(id));
    }


    @PostMapping(value = "addConcernedType")
    @ApiOperation(value = "添加运动类型")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity addConcernedType(@ApiParam(hidden = true)@BlogUser String userId,
                                           @ApiParam(value = "运动类型",required = true)@RequestParam String ConcernedType){
        return ok(userService.addConcernedType(userId,ConcernedType,null));
    }

    @PostMapping("modifyConcernedType")
    @ApiOperation(value = "修改运动类型")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyConcernedType(@ApiParam(value = "运动类型ID", required = true) @RequestParam String id,
                                              @ApiParam(value = "运动类型",required = true)@RequestParam String concernedType) {
        return ok(userService.modifyConcernedType(id,concernedType));
    }


    @PostMapping(value = "modifyGender")
    @ApiOperation(value = "修改性别")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity modifyGender(@ApiParam(hidden = true)@BlogUser String userId,
                                      @ApiParam(value = "性别",required = true)@RequestParam Integer gender){
        return ok(userService.modifyGender(userId,gender,null));
    }


    @PostMapping(value = "modifyBirth")
    @ApiOperation(value = "修改生日")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity modifyBirth(@ApiParam(hidden = true)@BlogUser String userId,
                                      @ApiParam(value = "生日",required = true)@RequestParam String dataOfBirth){
        return ok(userService.modifyBirth(userId,dataOfBirth,null));
    }


    @PostMapping(value = "modifyImage")
    @ApiOperation(value = "修改用户头像")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity modifyImage(@ApiParam(hidden = true)@BlogUser String userId,
                                      @ApiParam(value = "头像图片",required = true)@RequestParam String userImage){
        return ok(userService.modifyImage(userId,userImage,null));
    }

    @PostMapping(value = "modifyCover")
    @ApiOperation(value = "修改背景图片")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity modifyCover(@ApiParam(hidden = true)@BlogUser String userId,
                                      @ApiParam(value = "背景图片",required = true)@RequestParam String cover){
        return ok(userService.modifyCover(userId,cover,null));
    }
    @PostMapping(value = "deleteEducation")
    @ApiOperation(value = "博主-运动员-删除教育经历")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity deleteEducation(@ApiParam(value = "教育经历ID",required = true)@RequestParam String id){
        return ok(userService.deleteEducation(id));
    }

    @PostMapping(value = "addEducation")
    @ApiOperation(value = "博主-运动员-添加教育经历")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity addEducation(@ApiParam(hidden = true)@BlogUser String userId,
                                       @ApiParam(value = "学校",required = true)@RequestParam String school,
                                       @ApiParam(value = "学位",required = true)@RequestParam String degree,
                                       @RequestParam String datesAttended,
                                       @RequestParam String description){
           return ok(userService.addEducation(userId,school,degree,datesAttended,description));
    }

    @PostMapping("addWork")
    @ApiOperation(value = "博主-运动员-添加 work")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity addWork(@ApiParam(hidden = true) @BlogUser String userId,
                                  @RequestParam String name,
                                  @RequestParam String degree,
                                  @RequestParam String datesAttended,
                                  @RequestParam String description) {
        return ok(userService.addWork(userId,name,degree,datesAttended,description));
    }

    @PostMapping("deleteWork")
    @ApiOperation(value = "博主-运动员-删除work")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteWork(@RequestParam String id) {
        return ok(userService.deleteWork(id));
    }


    @PostMapping("addSkill")
    @ApiOperation(value = "博主-运动员-添加技能")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity addSkill(@ApiParam(hidden = true)@BlogUser String userId,
                                   @ApiParam(value = "内容", required = true) @RequestParam String content) {
        return ok(userService.addSkill(userId,content));
    }

    @PostMapping("deleteSkill")
    @ApiOperation(value = "博主-运动员-删除技能")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteSkill(@ApiParam(hidden = true)@BlogUser String userId,
                                      @ApiParam(value = "技能id", required = true) @RequestParam String id) {
        return ok(userService.deleteSkill(userId,id));
    }

    @PostMapping("deletePlaying")
    @ApiOperation(value = "博主-运动员-删除playing history ")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deletePlaying(@RequestParam String id) {
        return ok(userService.deletePlaying(id));
    }


    @PostMapping("modifyPlaying")
    @ApiOperation(value = "博主-运动员-修改playing history ")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyPlaying(@ApiParam(hidden = true)@BlogUser String userId,
                                     @RequestParam String id,
                                     @RequestParam(required = false) String organizationName,
                                     @RequestParam(required = false) String sports,
                                     @RequestParam(required = false) String position,
                                     @RequestParam(required = false) String location,
                                     @RequestParam(required = false) String streetAddress,
                                     @RequestParam(required = false) String timePeriod,
                                     @RequestParam(required = false) String description) {
        return ok(userService.modifyPlaying(userId,id,organizationName,sports,position,location,streetAddress,timePeriod,description));
    }


    @PostMapping("addPlaying")
    @ApiOperation(value = "博主-运动员-添加playing history ")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity addPlaying(@ApiParam(hidden = true)@BlogUser String userId,
                                     @RequestParam String organizationName,
                                     @RequestParam String sports,
                                     @RequestParam String position,
                                     @RequestParam String location,
                                     @RequestParam String streetAddress,
                                     @RequestParam String timePeriod,
                                     @RequestParam String description) {
        return ok(userService.addPlaying(userId,organizationName,sports,position,location,streetAddress,timePeriod,description));
    }

    @GetMapping("seeUserDetails")
    @ApiOperation(value = "查看用户详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity seeUserDetails(@ApiParam(value = "其他用户ID",required = true)@RequestParam String userId){
        return ok(userService.seeUserDetails(userId));
    }


    @PostMapping("deleteArticle")
    @ApiOperation(value = "删除文章")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity deleteArticle(@ApiParam(hidden = true)@BlogUser String userId,
                                        @ApiParam(value = "文章ID",required = true)@RequestParam String articleId){
        return ok(userService.deleteArticle(userId,articleId));
    }


    @PostMapping("addFriend")
    @ApiOperation(value = "添加好友")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity addFriend(@ApiParam(hidden = true)@BlogUser String userId,
                                    @ApiParam(value = "好友ID",required = true)@RequestParam String friendId){
        return ok(userService.addFriend(userId,friendId,null));

    }

    @PostMapping("cancelFollow")
    @ApiOperation(value = "取消关注")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity cancelFollow(@ApiParam(hidden = true)@BlogUser String userId,
                                       @ApiParam(value = "关注者ID",required = true)@RequestParam String followId){
        return ok(userService.cancelFollow(userId,followId));
    }

    @PostMapping("addFollow")
    @ApiOperation(value = "添加关")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity addFollow(@ApiParam(hidden = true)@BlogUser String userId,
                                    @ApiParam(value = "关注者ID",required = true)@RequestParam String followId){
        return ok(userService.addFollow(userId,followId,null));

    }

    @GetMapping("followed")
    @ApiOperation(value = "关注者列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity followed(@ApiParam(hidden = true)@BlogUser String userId,
                                    @RequestParam Integer page,
                                    @RequestParam Integer pageSize){
        return ok(userService.followed(userId,page,pageSize));
    }

    @GetMapping("followers")
    @ApiOperation(value = "粉丝列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity followers(@ApiParam(hidden = true)@BlogUser String userId,
                                  @RequestParam Integer page,
                                  @RequestParam Integer pageSize){
        return ok(userService.followers(userId,page,pageSize));
    }
    @GetMapping("friends")
    @ApiOperation(value = "好友列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity friends(@ApiParam(hidden = true)@BlogUser String userId,
                                  @RequestParam Integer page,
                                  @RequestParam Integer pageSize){
     return ok(userService.friends(userId,page,pageSize));
    }






    @PostMapping("addCommunities")
    @ApiOperation(value = "添加社区")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity addCommunities(@ApiParam(hidden = true)@BlogUser String userId,
                                         @ApiParam(value = "标题", required = true) @RequestParam String communitiesTitle,
                                         @ApiParam(value = "图片")@RequestParam(required = false) String communitiesImg,
                                         @ApiParam(value = "内容")@RequestParam(required = false) String communitiesContent) {
        return ok(userService.addCommunities(userId,communitiesTitle,communitiesImg,communitiesContent,null));
    }

    @PostMapping("deleteCommunities")
    @ApiOperation(value = "删除社区")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteCommunities(@ApiParam(value = "社区id", required = true) @RequestParam String id) {
        return ok(userService.deleteCommunities(id));
    }

    @PostMapping("modifyCommunities")
    @ApiOperation(value = "修改社区")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyCommunities(  @ApiParam(value = "社区Id",required = true)@RequestParam String id,
                                              @ApiParam(value = "标题") @RequestParam(required = false) String communitiesTitle,
                                              @ApiParam(value = "图片")@RequestParam(required = false) String communitiesImg,
                                              @ApiParam(value = "内容")@RequestParam(required = false) String communitiesContent){
        return ok(userService.modifyCommunities(id,communitiesTitle,communitiesImg,communitiesContent));
    }

    @PostMapping("editInformation")
    @ApiOperation(value = "修改个人信息",notes = "userBirthday=2018-2-24;userGender=0 保密 1 男 2 女;")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity editInformation(@ApiParam(hidden = true)@BlogUser String userId,
                                          @ApiParam(value = "用户头像")@RequestParam(required = false) String userImg,
                                          @ApiParam(value = "firstName")@RequestParam(required = false) String firstName,
                                          @ApiParam(value = "lastName")@RequestParam(required = false) String lastName,
                                          @ApiParam(value = "封面")@RequestParam(required = false) String userCover,
                                          @ApiParam(value = "生日")@RequestParam(required = false) String userBirthday,
                                          @ApiParam(value = "性别")@RequestParam(required = false) Integer userGender,
                                          @ApiParam(value = "签名")@RequestParam(required = false) String userSignature,
                                          @ApiParam(value = "就读学校")@RequestParam(required = false)String readingSchool){
      return ok(userService.editInformation(userId,userImg,firstName,lastName,userCover,userBirthday,userGender,userSignature,readingSchool));
    }



    @GetMapping("information")
    @ApiOperation(value = "个人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity information(@ApiParam(hidden = true)@BlogUser String userId){
        return ok(userService.information(userId));
    }

    @PostMapping("bindAccount")
    @ApiOperation(value = "绑定手机号")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity bindAccount(@ApiParam(hidden = true)@BlogUser String userId,
                                      @ApiParam(value = "手机号",required = true)@RequestParam String phone,
                                      @ApiParam(value = "验证码",required = true)@RequestParam String code){
        return ok(userService.bindAccount(userId,phone,code));
    }

    @PostMapping("bindEmail")
    @ApiOperation(value = "绑定邮箱")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity bindEmail(@ApiParam(hidden = true)@BlogUser String userId,
                                      @ApiParam(value = "邮箱",required = true)@RequestParam String email,
                                      @ApiParam(value = "验证码",required = true)@RequestParam String code){
        return ok(userService.bindEmail(userId,email,code));
    }



    @PostMapping("setLanguage")
    @ApiOperation(value ="设置语言")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity setLanguage(@ApiParam(hidden = true)@BlogUser String userId,
                                      @ApiParam(value = "语言ID",required = true)@RequestParam String languageId){
        return ok(userService.setLanguage(userId,languageId));
    }

    @GetMapping("getAllLanguages")
    @ApiOperation(value = "获取所用语言")
    public ResponseEntity getAllLanguages(){
        return ok(userService.getAllLanguages());
    }

    @PostMapping("changeAccount")
    @ApiOperation(value = "修改账号邮箱")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity changeAccount(@ApiParam(hidden = true)@BlogUser String userId,
                                        @ApiParam(value = "邮箱",required = true)@RequestParam String email,
                                        @ApiParam(value = "验证码",required = true)@RequestParam String code){
         return ok(userService.changeAccount(userId,email,code));
    }

    @PostMapping("resetPassword")
    @ApiOperation(value = "修改密码")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity resetPassword(@ApiParam(hidden = true)@BlogUser String userId,
                                        @ApiParam(value = "现在的密码",required = true)@RequestParam String currentPassword,
                                        @ApiParam(value = "新密码",required = true)@RequestParam String newPassword){
          return ok(userService.resetPassword(userId,currentPassword,newPassword));
    }




}
