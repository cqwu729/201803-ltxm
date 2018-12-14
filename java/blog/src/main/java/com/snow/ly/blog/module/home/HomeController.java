package com.snow.ly.blog.module.home;

import com.snow.ly.blog.config.annotation.BlogUser;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@Api(description = "主页API")
@RequestMapping("/home/")
public class HomeController {


    @Autowired
    private HomeService homeService;

    @GetMapping("getProtocol")
    @ApiOperation(value = "获取注册协议")
    public ResponseEntity getProtocol() {
        return ok(homeService.getProtocol());
    }

    @GetMapping("getUs")
    @ApiOperation(value = "获取后台联系我们")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getUs() {
        return ok(homeService.getUs());
    }



    @GetMapping("getSysArticle")
    @ApiOperation(value = "获取系统发布的文章",notes = "type= 0 帖子 1 投票 2 视频 3 活动 4 所有")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity getSysArticle(@ApiParam(value = "类型",required = true)@RequestParam Integer type,
                                        @ApiParam(value = "第几页", required = true) @RequestParam Integer page,
                                        @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(homeService.getSysArticle(type,page,pageSize));
    }

    @PostMapping("searchArticle")
    @ApiOperation(value = "搜索文章")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity searchArticle(@ApiParam(hidden = true) @BlogUser String userId,
                                        @ApiParam(value = "关键字", required = true) @RequestParam String keyword,
                                        @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                        @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(homeService.searchArticle(userId,keyword,page,pageSize));
    }

    @PostMapping("addHide")
    @ApiOperation(value = "隐藏文章")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity addHide(@ApiParam(hidden = true) @BlogUser String userId,
                                  @ApiParam(value = "文章id", required = true) @RequestParam String id) {
        return ok(homeService.addHide(userId,id));
    }


    @GetMapping("getPolicy")
    @ApiOperation(value = "获取隐私政策")
    public ResponseEntity getPolicy() {
        return ok(homeService.getPolicy());
    }


    @GetMapping("getAbout")
    @ApiOperation(value = "获取关于我们")
    public ResponseEntity getAbout() {
        return ok(homeService.getAbout());
    }

    @GetMapping("getAdverts")
    @ApiOperation(value = "获取广告列表")
    public ResponseEntity getAdverts(@ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                     @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(homeService.getAdverts(page,pageSize));
    }



    @PostMapping("invitation")
    @ApiOperation(value = "好友邀请")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    public ResponseEntity invitation(@ApiParam(hidden = true) @BlogUser String userId,
                                     @ApiParam(value = "好友邮箱",required = true)@RequestParam String email,
                                     @ApiParam(value = "邀请内容",required = true)@RequestParam String message) {
          return ok(homeService.invitation(userId,email,message));
    }



    @GetMapping("getComment")
    @ApiOperation(value = "获取文章评论")
    public ResponseEntity getComment(@ApiParam(value = "文章ID", required = true) @RequestParam String id,
                                     @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                     @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(homeService.getComment(id,page,pageSize));
    }

    @PostMapping("notSeePeople")
    @ApiOperation(value = "不查看此推荐人")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity notSeePeople(@ApiParam(hidden = true)@BlogUser String userId,
                                       @ApiParam(value = "用户ID",required = true)@RequestParam String peopleId){
        return ok(homeService.notSeePeople(userId,peopleId));
    }


    @PostMapping("search")
    @ApiOperation(value = "搜索好友")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity search(@ApiParam(hidden = true)@BlogUser String userId,
                                 @ApiParam(value = "关键字",required = true)@RequestParam String keyword,
                                 @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                 @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize){
        return ok(homeService.search(userId,keyword,page,pageSize));
    }


    @PostMapping("articleComment")
    @ApiOperation(value = "评论")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity articleComment(@ApiParam(hidden = true)@BlogUser String userId,
                                         @RequestParam String  articleId,
                                         @RequestParam(required = false) String content,
                                         @RequestParam(required = false) String image){
        return ok(homeService.articleComment(userId,articleId,content,image));
    }



    @PostMapping("commentReply")
    @ApiOperation(value = "回复")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity commentReply(@ApiParam(hidden = true)@BlogUser String userId,
                                         @RequestParam String  commentId,
                                         @RequestParam String replyUserId,
                                         @RequestParam(required = false) String content,
                                         @RequestParam(required = false) String image){
        return ok(homeService.commentReply(userId,commentId,replyUserId,content,image));
    }


    @PostMapping("unlike")
    @ApiOperation(value = "取消点赞")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity unlike(@ApiParam(hidden = true)@BlogUser String userId,
                               @RequestParam String  articleId){
        return ok(homeService.unlike(userId,articleId));
    }

    @PostMapping("like")
    @ApiOperation(value = "点赞")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity like(@ApiParam(hidden = true)@BlogUser String userId,
                               @RequestParam String  articleId){
        return ok(homeService.like(userId,articleId));
    }

    @GetMapping("getArticleDetail")
    @ApiOperation(value = "获取详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity getArticleDetail(@ApiParam(hidden = true)@BlogUser String userId,
                                           @RequestParam String  articleId){
        return ok(homeService.getArticleDetail(userId,articleId));
    }

    @GetMapping("getPoll")
    @ApiOperation(value = "获取投票列表",notes = "type=0 所用,1 我发布的，2，我参与的，3，过期的，4，未过期的")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity getPoll(@ApiParam(hidden = true)@BlogUser String userId,
                                  @ApiParam(value = "类型",required = true)@RequestParam Integer type,
                                  @RequestParam Integer page,
                                  @RequestParam Integer pageSize){
      return ok(homeService.getPoll(userId,type,page,pageSize));
    }

     @GetMapping("getVideos")
     @ApiOperation(value = "获取视频列表",notes = "type=0 所用,1 我发布的")
     @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
     public ResponseEntity getVideos(@ApiParam(hidden = true)@BlogUser String userId,
                                   @ApiParam(value = "类型",required = true)@RequestParam Integer type,
                                   @RequestParam Integer page,
                                   @RequestParam Integer pageSize){
         return ok(homeService.getVideos(userId,type,page,pageSize));
     }

    @GetMapping("getActivity")
    @ApiOperation(value = "获取话题活动列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity getActivity(@ApiParam(hidden = true)@BlogUser String userId,
                                    @RequestParam Integer page,
                                    @RequestParam Integer pageSize){
        return ok(homeService.getActivity(userId,page,pageSize));
    }

    @GetMapping("getPeople")
    @ApiOperation(value = "获取推荐人列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity getPeople(@ApiParam(hidden = true)@BlogUser String userId,
                                      @ApiParam(value = "已推荐的用户ID,以','(逗号)分割") @RequestParam(required = false) String existUserId,
                                      @RequestParam Integer page,
                                      @RequestParam Integer pageSize){
        return ok(homeService.getPeople(userId,existUserId,page,pageSize,null));
    }

    @GetMapping("index")
    @ApiOperation(value = "获取文章列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity index(@ApiParam(hidden = true)@BlogUser String userId,
                                    @RequestParam Integer page,
                                    @RequestParam Integer pageSize){
        return ok(homeService.index(userId,page,pageSize));
    }



    @PostMapping("vote")
    @ApiOperation(value = "投票")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity vote(@ApiParam(hidden = true)@BlogUser String userId,
                               @ApiParam(value = "投票id",required = true)@RequestParam String pollId,
                               @ApiParam(value = "投票选项id",required = true)@RequestParam String pollItemId){
        return ok(homeService.vote(userId,pollId,pollItemId));
    }

    @PostMapping("poll")
    @ApiOperation(value = "发布投票",notes = "validity 单位为天 items=[{\"content\":\"1\",\"image\":\"1\"},{\"content\":\"2\",\"image\":\"2\"},{\"content\":\"3\",\"image\":\"3\"},{\"content\":\"4\",\"image\":\"4\"}]")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity poll(@ApiParam(hidden = true)@BlogUser String userId,
                               @ApiParam(value = "标题",required =true)@RequestParam String title,
                               @ApiParam(value = "投票图片")@RequestParam(required = false) String image,
                               @ApiParam(value = "选择项",required = true)@RequestParam String items,
                               @ApiParam(value = "有效期",required = true)@RequestParam Integer validity,
                               @ApiParam(value = "可见性")@RequestParam(required = false)Integer visibility,
                               @ApiParam(value = "地址")@RequestParam(required = false)String address){
       return ok(homeService.poll(userId,title,image,items,validity,visibility,address));
    }
    @PostMapping("video")
    @ApiOperation(value = "发布视频")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity video(@ApiParam(hidden = true)@BlogUser String userId,
                                @ApiParam(value = "内容",required = true)@RequestParam String content,
                                @ApiParam(value = "封面图片",required = true)@RequestParam String image,
                                @ApiParam(value = "视频",required = true)@RequestParam String video,
                                @ApiParam(value = "可见性")@RequestParam(required = false)Integer visibility,
                                @ApiParam(value = "地址")@RequestParam(required = false)String address){
       return ok(homeService.video(userId,content,image,video,visibility,address));
    }

    @PostMapping("post")
    @ApiOperation(value = "发布说说",notes = "'#'活动话题 images=1,2,3;visibility 可见性 0 只有自己可见 1 好友，粉丝  2 所用人可见")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity post(@ApiParam(hidden = true)@BlogUser String userId,
                               @ApiParam(value = "内容",required = true)@RequestParam String content,
                               @ApiParam(value = "图片")@RequestParam(required = false)String images,
                               @ApiParam(value = "可见性")@RequestParam(required = false)Integer visibility,
                               @ApiParam(value = "地址")@RequestParam(required = false)String address){
        return ok(homeService.post(userId,content,images,visibility,address));
    }








}
