package com.snow.ly.blog.module.login;

import com.snow.ly.blog.common.bean.Result;
import com.snow.ly.blog.common.bean.Tips;
import com.snow.ly.blog.common.bean.WxAccessToken;
import com.snow.ly.blog.common.pojo.*;
import com.snow.ly.blog.common.repository.*;
import com.snow.ly.blog.common.util.*;
import com.snow.ly.blog.module.user.UserService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;

import static com.snow.ly.blog.common.bean.Result.fail;
import static com.snow.ly.blog.common.bean.Result.success;
import static com.snow.ly.blog.common.bean.Tips.CODE_FALSE;
import static com.snow.ly.blog.common.bean.Tips.EMAIL_PHONE_FALSE;
import static com.snow.ly.blog.common.util.StringUtils.isNotNull;

@Service
public class LoginService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CodeUtils codeUtils;
    @Autowired
    private CallRepository callRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserConcernedTypeRepository userConcernedTypeRepository;
    @Autowired
    private ConcernedTypeRepository concernedTypeRepository;
    @Autowired
    private PrivacyRepository privacyRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private WxUtils wxUtils;

    public Result getAllConcernedType(){
        return success(concernedTypeRepository.findAll());
    }

    public Result callMe(String fullName,String eamil, String numbere,String subject,String message){
         callRepository.save(Call.builder()
                 .fullName(fullName)
                 .eamil(eamil)
                 .numbere(numbere)
                 .subject(subject)
                 .message(message)
                 .createTime(LocalDateTime.now())
                 .build()
         );
        return success();
    }

    public Result resetPassword(String userEmailOrPhone,String newPassword,String code){
        if (!(RegexUtils.checkEmail(userEmailOrPhone))&&!(RegexUtils.checkPhone(userEmailOrPhone))) return fail(EMAIL_PHONE_FALSE.msg);
        if (!codeUtils.checkCode(userEmailOrPhone,code))return fail(CODE_FALSE.msg);
        List<User>users=new ArrayList<>();
        if (RegexUtils.checkEmail(userEmailOrPhone))users=userRepository.findByUserEmail(userEmailOrPhone);
        if (RegexUtils.checkPhone(userEmailOrPhone))users=userRepository.findByUserPhone(userEmailOrPhone);
        if (users.size()<=0)return fail(Tips.USER_NOT.msg);
        User user=users.get(0);
        user.setUserPassword(PasswordEncoderUtils.encode(newPassword));
        return success(userRepository.save(user));
    }

    public Result getCode(Integer type,String userEmailOrPhone){
        if (!(RegexUtils.checkEmail(userEmailOrPhone))&&!(RegexUtils.checkPhone(userEmailOrPhone))) return fail(EMAIL_PHONE_FALSE.msg);
        String code=codeUtils.setCode(userEmailOrPhone,10*60L);
        //邮箱验证码
        if (type==1) EmailUtils.sendEmail(userEmailOrPhone,code);
        //短信验证码
        if (type==0) CodeUtils.sendSms(userEmailOrPhone,code);
        return success(code);
    }

    public Result otherLoginBind(String userId,Integer profileType, String dateOfBirth,Integer gender, String location, String workFor, String jobTitle,String sponsors, String height,String weight,String sports){
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        if (user.getIsBind()==1)return fail(Tips.USER_HAD_BIND.msg);
        Result result=toRegister(user,profileType, dateOfBirth, gender, location, workFor, jobTitle, sponsors, height, weight);
        if (result.getCode()==0) return result;
        user.setIsBind(1);
        //设置用户运动类型
        setSports(sports,user.getUserId());
        return success(userRepository.save(user));
    }

    public Result otherLogin(String openId,Integer accountType,String userImg,String firstName,String lastName){
        List<User> users=userRepository.findByOpenIdAndAccountType(openId,accountType);
        User user=null;
        if (users.size()>0){
            user=users.get(0);
//            if (StringUtils.isNotNull(userImg))
//            user.setUserImg(userImg);
            user.setFirstName(firstName);
            if (user.getAccountStatus()!=0)
                return fail(Tips.ACCOUNT_FALSE.msg);
        }else{
            user= User.builder()
                    .openId(openId)
                    .isBind(0)
                    .accountType(accountType)
                    .userImg(userImg)
                    .accountStatus(0)
                    .firstName(firstName)
                    .createTime(new Date())
                    .build();
        }
        if (isNotNull(lastName))user.setLastName(lastName);
        user=userRepository.save(user);
        user.setUserToken(JWTTokenUtils.createToken(user.getUserId()));
        return success(user);
    }
    public Result login(String userEmailOrUserPhone, String userPassword) {
        //判断是邮箱登录还是手机登录
        List<User> users = new ArrayList<>();
        if (RegexUtils.checkEmail(userEmailOrUserPhone)) users = userRepository.findByUserEmail(userEmailOrUserPhone);
        if (RegexUtils.checkPhone(userEmailOrUserPhone)) users = userRepository.findByUserPhone(userEmailOrUserPhone);
        if (users.size() <= 0) return fail(Tips.USER_NOT.msg);
        User user = users.get(0);
        if (!PasswordEncoderUtils.decode(userPassword, user.getUserPassword()))
            return fail(Tips.USER_PASSWORD_FALSE.msg);
        if (user.getAccountStatus() != 0)
            return fail(Tips.ACCOUNT_FALSE.msg);
        user.setUserToken(JWTTokenUtils.createToken(user.getUserId()));
        return success(user);
    }
    public Result register(String userEmailOrPhone, String firsName,String lastName, String userPassword,Integer profileType, String dateOfBirth,Integer gender, String location, String workFor, String jobTitle,String sponsors, String height,String weight, String sports,String code) {
        if (!(RegexUtils.checkEmail(userEmailOrPhone))&&!(RegexUtils.checkPhone(userEmailOrPhone))) return fail(EMAIL_PHONE_FALSE.msg);
        if (!codeUtils.checkCode(userEmailOrPhone,code))return fail(CODE_FALSE.msg);
        List<User>users=new ArrayList<>();
        if (RegexUtils.checkEmail(userEmailOrPhone))users=userRepository.findByUserEmail(userEmailOrPhone);
        if (RegexUtils.checkPhone(userEmailOrPhone))users=userRepository.findByUserPhone(userEmailOrPhone);
        if (users.size()>0)return fail(Tips.USER_EMAIL_HAD.msg);
        User u=new User();
        u.setFirstName(firsName);
        u.setLastName(lastName);
        u.setIsBind(1);
        u.setUserPassword(PasswordEncoderUtils.encode(userPassword));
        u.setCreateTime(new Date());
        //初始化用户头像
        u.setUserImg("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505214447543&di=9d0908c9b59c1885ee2f56fbaf46451b&imgtype=0&src=http%3A%2F%2Fwww.qqzhi.com%2Fuploadpic%2F2014-10-08%2F210702784.jpg");
        if (RegexUtils.checkEmail(userEmailOrPhone))u.setUserEmail(userEmailOrPhone);
        if (RegexUtils.checkPhone(userEmailOrPhone))u.setUserPhone(userEmailOrPhone);
        Result result=toRegister(u,profileType, dateOfBirth, gender, location, workFor, jobTitle, sponsors, height, weight);
        if (result.getCode()==0) return result;
        //账号状态
        u.setAccountStatus(0);
        u=userRepository.save(u);
        //初始化账号表
        accountRepository.save(Account.builder()
                .xUserId(u.getUserId())
                .userId(u.getUserId())
                .userImage(u.getUserImg())
                .userPhone(u.getUserPhone())
                .userEmail(u.getUserEmail())
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .build());
        //设置用户运动类型
        setSports(sports,u.getUserId());
        u.setUserToken(JWTTokenUtils.createToken(u.getUserId()));

        //初始化隐私设置
        List<Privacy> privacies=privacyRepository.findByUserId(u.getUserId());
        if (privacies.size() == 0){
            privacyRepository.save(Privacy.builder().allNotifications(1).wall(1).email(1).connections(1).email(1).userId(u.getUserId()).build());
        }
        return success(u);
    }

    public void setSports(String sports,String userId){
        if (sports==null)return;
        Arrays.stream(sports.split(","))
                .map(i -> concernedTypeRepository.findOne(i))
                .filter(i ->i!= null)
                .forEach(i ->userConcernedTypeRepository.save( UserConcernedType.builder().userId(userId).concernedId(i.getId()).concerned(i.getConcerned()).img(i.getImg()).build()));

    }

    public Result toRegister(User u,Integer profileType, String dateOfBirth,Integer gender, String location, String workFor, String jobTitle,String sponsors, String height,String weight){
        userService.setGender(u,gender);
        userService.setBirthday(u,dateOfBirth);
        u.setLocation(location);
        u.setIsBind(1);
        switch (profileType){
            case 0:
                if (height==null||weight==null||sponsors==null) return fail("height,weight,sponsors 不能为空");
                u.setHeight(height);
                u.setWeight(weight);
                u.setSponsors(sponsors);
                break;
            case 1:
                if (workFor==null||jobTitle==null) return fail("workFor,jobTitle不能为空");
                u.setWorkFor(workFor);
                u.setJobTitle(jobTitle);
                break;
            case 2:
                if (workFor==null||jobTitle==null) return fail("workFor,jobTitle不能为空");
                u.setWorkFor(workFor);
                u.setJobTitle(jobTitle);
                break;
            case 3:
                break;
            case 4:
                if (sponsors==null) return fail("sponsors不能为空");
                u.setSponsors(sponsors);
                break;
            default:
                return fail("profileType参数错误0-4");
        }
        u.setProfileType(profileType);

        /**初始化 用户隐私表*/
        privacyRepository.save(Privacy.builder().allNotifications(1).email(1).wall(1).connections(1).build());


        return success();
    }


    public Result getWxUrl(String state) {

        return success(wxUtils.getUrl("http://39.104.60.12:8080/login/wxBackUrl",state));


    }

    public void wxBackUrl(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) {
        Map<String, String> map = wxUtils.getCode(request);
        if (map == null) return;
        String code = map.get("code");
        String state = map.get("state");
        WxAccessToken accessToken = wxUtils.getAccessToken(code);
        if (accessToken == null) return;
        try {
            wxUtils.getUserInfo(accessToken.getAccess_token(), accessToken.getOpenid(), state, request, response, attributes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
