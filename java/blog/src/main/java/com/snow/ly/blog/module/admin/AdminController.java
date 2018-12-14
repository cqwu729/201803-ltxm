package com.snow.ly.blog.module.admin;

import com.snow.ly.blog.common.pojo.Admin;
import com.snow.ly.blog.config.annotation.BlogAdmin;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@Api(description = "后台管理API")
@RequestMapping("/admin/")
public class AdminController {



    @Autowired
    private AdminService adminService;



    @GetMapping("getLoginImg")
    @ApiOperation(value = "获取登录背景图")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getLoginImg() {
        return ok(adminService.getLoginImg());
    }

    @PostMapping("addLoginImg")
    @ApiOperation(value = "添加登录背景图")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity addLoginImg(@RequestParam String img) {
        return ok(adminService.addLoginImg(img));
    }



    @GetMapping("getProtocol")
    @ApiOperation(value = "获取注册协议")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getProtocol() {
        return ok(adminService.getProtocol());
    }


    @PostMapping("deleteProtocol")
    @ApiOperation(value = "添加注册协议")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteProtocol(@RequestParam String id) {
        return ok(adminService.deleteProtocol(id));
    }

    @PostMapping("modifyProtocol")
    @ApiOperation(value = "修改注册协议")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyProtocol(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                      @RequestParam(required = false) String title,
                                      @RequestParam(required = false) String content) {
        return ok(adminService.modifyProtocol(title,content));
    }


    @PostMapping("addProtocol")
    @ApiOperation(value = "添加注册协议")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity addProtocol(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                @RequestParam String title,
                                @RequestParam String content) {
        return ok(adminService.addProtocol(title,content));
    }

    @GetMapping("getUs")
    @ApiOperation(value = "获取后台联系我们")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getUs() {
        return ok(adminService.getUs());
    }


    @PostMapping("deleteUs")
    @ApiOperation(value = "删除后台联系我们")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteUs(@RequestParam String id) {
        return ok(adminService.deleteUs(id));
    }


    @PostMapping("modifyUs")
    @ApiOperation(value = "修改后台联系我们")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyUs(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String address,
                                @RequestParam(required = false) String longitude,
                                @RequestParam(required = false) String latitude) {
        return ok(adminService.modifyUs(email,address,longitude,latitude));
    }


    @PostMapping("addUs")
    @ApiOperation(value = "添加后台联系我们")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity addUs(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                @RequestParam String email,
                                @RequestParam String address,
                                @RequestParam String longitude,
                                @RequestParam String latitude) {
        return ok(adminService.addUs(email,address,longitude,latitude));
    }


    @GetMapping("languages")
    @ApiOperation(value = "语言列表")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity languages() {
        return ok(adminService.languages());
    }

    @PostMapping("deleteLanguage")
    @ApiOperation(value = "删除语言")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteLanguage(@ApiParam(value = "语言id", required = true) @RequestParam String id) {
        return ok(adminService.deleteLanguage(id));
    }

    @PostMapping("addLanguage")
    @ApiOperation(value = "添加语言")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity addLanguage(@ApiParam(value = "语言", required = true) @RequestParam String  language,
                                      @ApiParam(value = "类型",required = true)@RequestParam Integer type) {
        return ok(adminService.addLanguage(language,type));
    }

    @PostMapping("deleteMessage")
    @ApiOperation(value = "删除系统消息",notes = "ids=12312321,3213123123,2312312")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteMessage(@ApiParam(value = "消息id", required = true) @RequestParam String ids) {
        return ok(adminService.deleteMessage(ids));
    }

    @GetMapping("getMessage")
    @ApiOperation(value = "获取系统消息")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getMessage(@ApiParam(value = "第几页", required = true) @RequestParam Integer page,
                                     @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(adminService.getMessage(page,pageSize));
    }

    @PostMapping("pushMessage")
    @ApiOperation(value = "推送系统消息")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity pushMessage(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                      @ApiParam(value = "内容", required = true) @RequestParam String content) {
        return ok(adminService.pushMessage(admin,content));
    }

    @PostMapping("deleteReview")
    @ApiOperation(value = "")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteReview(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                       @ApiParam(value = "证书ID",required = true)@RequestParam String id) {
        return ok(adminService.deleteReview(id));
    }


    @PostMapping("modifyReview")
    @ApiOperation(value = "审核证书",notes = "0 等待审核 1 通过 2 未通过")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyReview(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                       @ApiParam(value = "证书ID",required = true)@RequestParam String id,
                                       @ApiParam(value = "类型 ",required = true)@RequestParam Integer type) {
        return ok(adminService.modifyReview(id,type));
    }


    @GetMapping("getReviewDetails")
    @ApiOperation(value = "获取证书详情")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getReviewDetails(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                           @ApiParam(value = "证书ID",required = true)@RequestParam String id) {
        return ok(adminService.getReviewDetails(id));
    }


    @GetMapping("getReview")
    @ApiOperation(value = "获取审核列表",notes = "0 等待审核 1 通过 2 未通过 3全部")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getReview(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                    @ApiParam(value = "类型",required = true)@RequestParam Integer type,
                                    @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                    @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(adminService.getReview(admin,type,page,pageSize));
    }




    @PostMapping("modifyAdminInform")
    @ApiOperation(value = "修改管理员资料")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyAdminInform(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                            @ApiParam(value = "管理员ID",required = true)@RequestParam String id,
                                            @ApiParam(value = "账号")@RequestParam(required = false)String account,
                                            @ApiParam(value = "密码")@RequestParam(required = false)String password,
                                            @ApiParam(value = "头像")@RequestParam(required = false)String img,
                                            @ApiParam(value = "角色 0 普通 1 超级")@RequestParam(required = false)Integer role) {
        return ok(adminService.modifyAdminInform(admin,id,account,password,img,role));
    }



    @PostMapping("deleteCall")
    @ApiOperation(value = "删除联系我们")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteCall(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                     @ApiParam(value = "ID",required = true)@RequestParam String ids) {
        return ok(adminService.deleteCall(ids));
    }


    @GetMapping("getCall")
    @ApiOperation(value = "获取联系我们")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getCall(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                  @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                  @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(adminService.getCall(page,pageSize));
    }


    @PostMapping("deletePolicy")
    @ApiOperation(value = "删除隐私政策")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deletePolicy(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                      @ApiParam(value = "关于我们ID",required = true)@RequestParam String ids) {
        return ok(adminService.deletePolicy(ids));
    }


    @PostMapping("modifyPolicy")
    @ApiOperation(value = "修改隐私政策")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyPolicy(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                      @ApiParam(value = "ID",required = true)@RequestParam String id,
                                      @ApiParam(value = "图片",required = true)@RequestParam String img,
                                      @ApiParam(value = "内容",required = true)@RequestParam String content) {
        return ok(adminService.modifyPolicy(id,img,content));
    }


    @GetMapping("getPolicy")
    @ApiOperation(value = "获取隐私政策")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getPolicy(@ApiParam(hidden = true) @BlogAdmin Admin admin) {
        return ok(adminService.getPolicy());
    }


    @PostMapping("addPolicy")
    @ApiOperation(value = "添加隐私政策")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity addPolicy(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                   @ApiParam(value = "图片",required = true)@RequestParam String img,
                                   @ApiParam(value = "内容",required = true)@RequestParam String content) {
        return ok(adminService.addPolicy(img,content));
    }


    @PostMapping("deleteAbout")
    @ApiOperation(value = "删除关于我们")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteAbout(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                      @ApiParam(value = "关于我们ID",required = true)@RequestParam String ids) {
        return ok(adminService.deleteAbout(ids));
    }


    @PostMapping("modifyAbout")
    @ApiOperation(value = "修改关于我们")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyAbout(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                      @ApiParam(value = "ID",required = true)@RequestParam String id,
                                      @ApiParam(value = "图片",required = true)@RequestParam String img,
                                      @ApiParam(value = "内容",required = true)@RequestParam String content) {
        return ok(adminService.modifyAbout(id,img,content));
    }


    @GetMapping("getAbout")
    @ApiOperation(value = "获取关于我们")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getAbout(@ApiParam(hidden = true) @BlogAdmin Admin admin) {
        return ok(adminService.getAbout());
    }


    @PostMapping("addAbout")
    @ApiOperation(value = "添加关于我们")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity addAbout(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                   @ApiParam(value = "图片",required = true)@RequestParam String img,
                                   @ApiParam(value = "内容",required = true)@RequestParam String content) {
        return ok(adminService.addAbout(img,content));
    }


    @PostMapping("searchGroup")
    @ApiOperation(value = "搜索社区")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity searchGroup(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                      @ApiParam(value = "关键字",required = true)@RequestParam String keyword) {
        return ok(adminService.searchGroup(keyword));
    }


    @PostMapping("deleteGroup")
    @ApiOperation(value = "删除社区",notes = "ids=11,232,23232")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteGroup(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                      @ApiParam(value = "社区ID",required = true)@RequestParam String ids) {
        return ok(adminService.deleteGroup(ids));
    }


    @PostMapping("modifyGroup")
    @ApiOperation(value = "修改社团")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyGroup(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                      @ApiParam(value = "社团ID",required = true)@RequestParam String id,
                                      @ApiParam(value = "群名称")@RequestParam(required = false) String name,
                                      @ApiParam(value = "群头像")@RequestParam(required = false) String img,
                                      @ApiParam(value = "群简介")@RequestParam(required = false) String introduction) {
        return ok(adminService.modifyGroup(id,name,img,introduction));
    }



    @PostMapping("addGroup")
    @ApiOperation(value = "添加社团")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity addGroup(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                   @ApiParam(value = "群名称",required = true)@RequestParam String name,
                                   @ApiParam(value = "群头像",required = true)@RequestParam String img,
                                   @ApiParam(value = "群简介",required = true)@RequestParam String introduction) {
        return ok(adminService.addGroup(admin,name,img,introduction));
    }




    @GetMapping("getGroupDetails")
    @ApiOperation(value = "获取社团详情")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getGroupDetails(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                          @ApiParam(value = "社团ID",required = true)@RequestParam String id) {
        return ok(adminService.getGroupDetails(id));
    }


    @GetMapping("getGroup")
    @ApiOperation(value = "获取社团列表")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getGroup(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                   @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                   @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(adminService.getGroup(page,pageSize));
    }



    @PostMapping("deleteConcernedType")
    @ApiOperation(value = "删除运动类型",notes = "ids=1,2,3,4")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteConcernedType(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                              @ApiParam(value = "运动类型ID",required = true)@RequestParam String ids) {
        return ok(adminService.deleteConcernedType(ids));
    }


    @GetMapping("getConcernedType")
    @ApiOperation(value = "获取运动类型列表")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getConcernedType(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                           @ApiParam(value = "语言类型")@RequestParam(required = false) Integer type,
                                           @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                           @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(adminService.getConcernedType(type,page,pageSize));
    }


    @PostMapping("modifyConcernedType")
    @ApiOperation(value = "修改运动类型")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyConcernedType(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                              @ApiParam(value = "运动类型ID",required = true)@RequestParam String id,
                                              @ApiParam(value = "语言类型")@RequestParam(required = false) Integer type,
                                              @ApiParam(value = "类型名")@RequestParam(required = false) String concerned,
                                              @ApiParam(value = "图片")@RequestParam(required = false) String img) {
        return ok(adminService.modifyConcernedType(admin,id,type,concerned,img));
    }


    @PostMapping("addConcernedType")
    @ApiOperation(value = "添加运动类型")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity addConcernedType(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                           @ApiParam(value = "语言类型",required = true)@RequestParam Integer type,
                                           @ApiParam(value = "类型名",required = true)@RequestParam String concerned,
                                           @ApiParam(value = "图片",required = true)@RequestParam String img) {
        return ok(adminService.addConcernedType(type,concerned,img));
    }


    @PostMapping("deleteArticle")
    @ApiOperation(value = "删除文章",notes = "ids=11,2,32323")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteArticle(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                        @ApiParam(value = "文章ID",required = true)@RequestParam String ids) {
        return ok(adminService.deleteArticle(ids));
    }


    @PostMapping("poll")
    @ApiOperation(value = "发布投票",notes = "validity 单位为天 items=[{\"content\":\"1\",\"image\":\"1\"},{\"content\":\"2\",\"image\":\"2\"},{\"content\":\"3\",\"image\":\"3\"},{\"content\":\"4\",\"image\":\"4\"}]")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity poll(@ApiParam(hidden = true)@BlogAdmin Admin admin,
                               @ApiParam(value = "标题",required =true)@RequestParam String title,
                               @ApiParam(value = "选择项",required = true)@RequestParam String items,
                               @ApiParam(value = "有效期",required = true)@RequestParam Integer validity,
                               @ApiParam(value = "可见性")@RequestParam(required = false)Integer visibility,
                               @ApiParam(value = "地址")@RequestParam(required = false)String address){
        return ok(adminService.poll(admin,title,items,validity,visibility,address));
    }


    @PostMapping("modifyPoll")
    @ApiOperation(value = "修改投票", notes = "validity 单位为天 items=[{\"content\":\"1\",\"image\":\"1\"},{\"content\":\"2\",\"image\":\"2\"},{\"content\":\"3\",\"image\":\"3\"},{\"content\":\"4\",\"image\":\"4\"}]")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyPoll(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                     @ApiParam(value = "文章ID", required = true) @RequestParam String id,
                                     @ApiParam(value = "标题") @RequestParam(required = false) String title,
                                     @ApiParam(value = "选择项") @RequestParam(required = false) String items,
                                     @ApiParam(value = "有效期") @RequestParam(required = false) Integer validity,
                                     @ApiParam(value = "可见性") @RequestParam(required = false) Integer visibility,
                                     @ApiParam(value = "地址") @RequestParam(required = false) String address) {
        return ok(adminService.modifyPoll(admin, id, title, items, validity, visibility, address));
    }


    @PostMapping("video")
    @ApiOperation(value = "发布视频")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity video(@ApiParam(hidden = true)@BlogAdmin Admin admin,
                                @ApiParam(value = "内容",required = true)@RequestParam String content,
                                @ApiParam(value = "封面图片",required = true)@RequestParam String image,
                                @ApiParam(value = "视频",required = true)@RequestParam String video,
                                @ApiParam(value = "可见性")@RequestParam(required = false)Integer visibility,
                                @ApiParam(value = "地址")@RequestParam(required = false)String address){
        return ok(adminService.video(admin,content,image,video,visibility,address));
    }


    @PostMapping("modifyVideo")
    @ApiOperation(value = "修改视频")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyVideo(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                      @ApiParam(value = "文章ID", required = true) @RequestParam String id,
                                      @ApiParam(value = "内容") @RequestParam(required = false) String content,
                                      @ApiParam(value = "封面图片") @RequestParam(required = false) String image,
                                      @ApiParam(value = "视频") @RequestParam(required = false) String video,
                                      @ApiParam(value = "可见性") @RequestParam(required = false) Integer visibility,
                                      @ApiParam(value = "地址") @RequestParam(required = false) String address) {
        return ok(adminService.modifyVideo(admin, id, content, image, video, visibility, address));
    }



    @PostMapping("post")
    @ApiOperation(value = "发布说说",notes = "'#'活动话题 images=1,2,3;visibility 可见性 0 只有自己可见 1 好友，粉丝  2 所用人可见")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity post(@ApiParam(hidden = true)@BlogAdmin Admin admin,
                               @ApiParam(value = "内容",required = true)@RequestParam String content,
                               @ApiParam(value = "图片")@RequestParam(required = false)String images,
                               @ApiParam(value = "可见性")@RequestParam(required = false)Integer visibility,
                               @ApiParam(value = "地址")@RequestParam(required = false)String address){
        return ok(adminService.post(admin,content,images,visibility,address));
    }


    @PostMapping("modifyPost")
    @ApiOperation(value = "修改说说说说", notes = "'#'活动话题 images=1,2,3;visibility 可见性 0 只有自己可见 1 好友，粉丝  2 所用人可见")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyPost(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                     @ApiParam(value = "文章ID", required = true) @RequestParam String id,
                                     @ApiParam(value = "内容") @RequestParam(required = false) String content,
                                     @ApiParam(value = "图片") @RequestParam(required = false) String images,
                                     @ApiParam(value = "可见性") @RequestParam(required = false) Integer visibility,
                                     @ApiParam(value = "地址") @RequestParam(required = false) String address) {
        return ok(adminService.modifyPost(admin, id,content, images, visibility, address));
    }



    @GetMapping("getArticleDetails")
    @ApiOperation(value = "获取文章详情")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getArticleDetails(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                            @ApiParam(value = "文章ID",required = true)@RequestParam String id) {
        return ok(adminService.getArticleDetails(id));
    }


    @GetMapping("getArticles")
    @ApiOperation(value = "获取文章列表")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getArticles(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                      @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                      @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(adminService.getArticles(page,pageSize));
    }



    @PostMapping("deleteAdvert")
    @ApiOperation(value = "删除广告",notes = "ids=1111,312312,313131")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteAdvert(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                       @ApiParam(value = "广告ID",required = true) @RequestParam String ids) {
        return ok(adminService.deleteAdvert(ids));
    }



    @PostMapping("modifyAdvert")
    @ApiOperation(value = "修改广告")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyAdvert(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                       @ApiParam(value ="广告ID",required = true) @RequestParam String id,
                                       @ApiParam(value = "标题")@RequestParam(required = false) String title,
                                       @ApiParam(value = "文字")@RequestParam(required = false) String content,
                                       @ApiParam(value = "图片")@RequestParam(required = false) String img,
                                       @ApiParam(value = "链接")@RequestParam(required = false) String link,
                                       @ApiParam(value = "类型")@RequestParam(required = false) String type,
                                       @ApiParam(value = "广告商")@RequestParam(required = false)String name,
                                       @ApiParam(value = "费用")@RequestParam(required = false) Double money,
                                       @ApiParam(value = "开始时间")@RequestParam(required = false) String startTime,
                                       @ApiParam(value = "结束时间")@RequestParam(required = false) String endTime) {
        return ok(adminService.modifyAdvert(id,title,content,img,link,type,name,money,startTime,endTime));
    }


    @PostMapping("publishAdvert")
    @ApiOperation(value = "发布广告",notes="startTime,endTime= 2018-01-10 16:02:00")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity publishAdvert(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                        @ApiParam(value = "标题",required = true)@RequestParam String title,
                                        @ApiParam(value = "文字",required = true)@RequestParam String content,
                                        @ApiParam(value = "图片",required = true)@RequestParam String img,
                                        @ApiParam(value = "链接")@RequestParam(required = false) String link,
                                        @ApiParam(value = "类型",required = true)@RequestParam String type,
                                        @ApiParam(value = "广告商",required = true)@RequestParam String name,
                                        @ApiParam(value = "费用",required = true)@RequestParam Double money,
                                        @ApiParam(value = "开始时间",required = true)@RequestParam String startTime,
                                        @ApiParam(value = "结束时间",required = true)@RequestParam String endTime) {
        return ok(adminService.publishAdvert(title,content,img,link,type,name,money,startTime,endTime));
    }


    @GetMapping("getAdverts")
    @ApiOperation(value = "获取广告列表")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getAdverts(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                     @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                     @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(adminService.getAdverts(page,pageSize));
    }



    @GetMapping("getUserDetails")
    @ApiOperation(value = "获取用户详情")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getUserDetails(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                         @ApiParam(value = "用户ID",required = true)@RequestParam String userId) {
        return ok(adminService.getUserDetails(userId));
    }


    @PostMapping("operateUser")
    @ApiOperation(value = "操作用户",notes = "type=1 禁用 2 删除 3 恢复禁用; userIds=12323,23123,12313")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity operateUser(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                      @ApiParam(value = "类型",required = true)@RequestParam Integer type,
                                      @ApiParam(value = "用户ID",required = true)@RequestParam String userIds) {
        return ok(adminService.operateUser(type,userIds));
    }



    @GetMapping("getUsers")
    @ApiOperation(value = "获取用户列表",notes = "status=0 正常 1 禁用 2所用")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getUsers(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                   @ApiParam(value = "账号状态",required = true)@RequestParam Integer status,
                                   @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                   @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(adminService.getUsers(status,page,pageSize));
    }


    @PostMapping("deleteAdmin")
    @ApiOperation(value = "删除管理员",notes = "adminIds=1232,1232,111")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity deleteAdmin(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                             @ApiParam(value = "管理员ID",required = true)@RequestParam String adminIds) {
        return ok(adminService.deleteAdmin(admin,adminIds));
    }


    @GetMapping("getAdmins")
    @ApiOperation(value = "获取管理员列表")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getAdmins(@ApiParam(hidden = true) @BlogAdmin Admin admin,
                                    @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                    @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(adminService.getAdmins(page,pageSize));
    }



    @PostMapping("addAdmin")
    @ApiOperation(value = "添加管理员")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity addAdmin(@ApiParam(hidden = true)@BlogAdmin Admin admin,
                                   @ApiParam(value = "账号",required = true)@RequestParam String account,
                                   @ApiParam(value = "密码",required = true)@RequestParam String password) {
        return ok(adminService.addAdmin(admin,account,password));
    }


    @PostMapping("modifyPassword")
    @ApiOperation(value = "修改管理员密码")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity modifyPassword(@ApiParam(hidden = true)@BlogAdmin Admin admin,
                                         @ApiParam(value = "原始密码",required = true)@RequestParam String oldPassword,
                                         @ApiParam(value = "新密码",required = true)@RequestParam String newPassword) {
        return ok(adminService.modifyPassword(admin,oldPassword,newPassword));
    }

    @GetMapping("information")
    @ApiOperation(value = "管理员个人信息")
    @ApiImplicitParam(name = "adminAuth", value = "adminAuth", required = true, dataType = "string", paramType = "header")
    public ResponseEntity adminInformation(@ApiParam(hidden = true)@BlogAdmin Admin admin) {
        return ok(adminService.adminInformation(admin));
    }

    @PostMapping("login")
    @ApiOperation(value = "登录")
    public ResponseEntity login(@ApiParam(value = "账号",required = true)@RequestParam String account,
                                @ApiParam(value = "密码",required = true)@RequestParam String password) {
        return ok(adminService.login(account,password));
    }









}
