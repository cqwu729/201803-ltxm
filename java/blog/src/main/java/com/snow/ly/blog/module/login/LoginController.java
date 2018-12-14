package com.snow.ly.blog.module.login;

import com.snow.ly.blog.module.admin.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@Api(description = "用户登录注册API")
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private AdminService adminService;



    @GetMapping("getLoginImg")
    @ApiOperation(value = "获取登录背景图")
    public ResponseEntity getLoginImg() {
        return ok(adminService.getLoginImg());
    }


    @GetMapping("getWxUrl")
    @ApiOperation(value = "获取微信登录地址")
    public ResponseEntity getWxUrl(@ApiParam(value = "跳转地址", required = true) @RequestParam String state) {
        return ok(loginService.getWxUrl(state));
    }

    @GetMapping("wxBackUrl")
    @ApiOperation(value = "微信登录回调地址")
    public void wxBackUrl(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) {
        loginService.wxBackUrl(request,response,attributes);
    }
    @GetMapping("wxBackUrl2")
    @ApiOperation(value = "微信登录回调地址")
    public ResponseEntity wxBackUrl2(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return ok(request.getParameter("nickname"));
    }


    @GetMapping("/getConcernedType")
    @ApiOperation(value = "获取运动类型列表")
    public ResponseEntity getConcernedType(@ApiParam(value = "语言类型")@RequestParam(required = false) Integer type,
                                           @ApiParam(value = "第几页",required = true)@RequestParam Integer page,
                                           @ApiParam(value = "多少条",required = true)@RequestParam Integer pageSize) {
        return ok(adminService.getConcernedType(type,page,pageSize));
    }





    @PostMapping("/callMe")
    @ApiOperation(value = "联系我们")
    public ResponseEntity callMe(@RequestParam String fullName,
                                 @RequestParam String eamil,
                                 @RequestParam String numbere,
                                 @RequestParam String subject,
                                 @RequestParam String message){
     return ok(loginService.callMe(fullName,eamil,numbere,subject,message));
    }

    @PostMapping("/resetPassword")
    @ApiOperation(value = "找回密码")
    public ResponseEntity resetPassword(@RequestParam String userEmailOrPhone,
                                        @RequestParam String newPassword,
                                        @RequestParam String code){
        return ok(loginService.resetPassword(userEmailOrPhone,newPassword,code));
    }

    @GetMapping("/getCode")
    @ApiOperation(value = "获取验证码",notes = "type=0:手机,1:邮箱")
    public ResponseEntity getCode(@RequestParam Integer type,
                                  @RequestParam String userEmailOrPhone){
        return ok(loginService.getCode(type,userEmailOrPhone));
    }

    @PostMapping("/register")
    @ApiOperation(value ="注册",notes = "Bloggers,Club=workFor,jobTitle,Students=sponsors,Athele=height,weight,sponsors ；dateOfBirth=2017-01-01；sports=5a7a9748abd6bc2e50cddc50,5a7a9748abd6bc2e50cddc51,5a7a9748abd6bc2e50cddc52")
    public ResponseEntity register(@RequestParam String userEmailOrPhone,
                                   @RequestParam String firsName,
                                   @RequestParam String lastName,
                                   @RequestParam String userPassword,
                                   @RequestParam Integer profileType,
                                   @RequestParam String dateOfBirth,
                                   @RequestParam Integer gender,
                                   @RequestParam String location,
                                   @RequestParam(required = false) String workFor,
                                   @RequestParam(required = false) String jobTitle,
                                   @RequestParam(required = false) String sponsors,
                                   @RequestParam(required = false) String height,
                                   @RequestParam(required = false) String weight,
                                   @RequestParam(required = false) String sports,
                                   @ApiParam(value = "验证码",required = true)@RequestParam String code){
        return ok(loginService.register(userEmailOrPhone, firsName,lastName, userPassword, profileType, dateOfBirth, gender, location, workFor, jobTitle, sponsors, height, weight,sports, code));
    }



    @PostMapping
    @ApiOperation(value ="登录")
    public ResponseEntity login(@RequestParam String userEmailOrUserPhone,
                                @RequestParam String userPassword){
        return ok(loginService.login(userEmailOrUserPhone,userPassword));
    }

    @PostMapping("/otherLoginBind")
    @ApiOperation(value ="第三方登录绑定用户数据")
    public ResponseEntity otherLoginBind(@RequestParam String userId,
                                         @RequestParam Integer profileType,
                                         @RequestParam String dateOfBirth,
                                         @RequestParam Integer gender,
                                         @RequestParam String location,
                                         @RequestParam(required = false) String workFor,
                                         @RequestParam(required = false) String jobTitle,
                                         @RequestParam(required = false) String sponsors,
                                         @RequestParam(required = false) String height,
                                         @RequestParam(required = false) String weight,
                                         @RequestParam(required = false) String sports){
        return ok(loginService.otherLoginBind(userId,profileType,dateOfBirth,gender,location,workFor,jobTitle,sponsors,height,weight,sports));
    }

    @PostMapping("/otherLogin")
    @ApiOperation(value = "第三方登录",notes = " accountType=1:Facebook;2Twitter;3:Google+;4vk;5:微信 。firstName 作为昵称")
    public ResponseEntity otherLogin(@ApiParam(value = "第三方唯一标识",required = true)@RequestParam String openId,
                                     @ApiParam(value = "第三方登录平台",required = true)@RequestParam Integer accountType,
                                     @ApiParam(value = "用户头像")@RequestParam(required = false) String userImg,
                                     @ApiParam(required = true)@RequestParam String firstName,
                                     @RequestParam(required = false) String lastName){
     return ResponseEntity.ok(loginService.otherLogin(openId,accountType,userImg,firstName,lastName));
    }






}
