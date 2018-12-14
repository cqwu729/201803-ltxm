package com.snow.ly.blog.test;

import com.snow.ly.blog.common.pojo.*;
import com.snow.ly.blog.common.repository.*;
import com.snow.ly.blog.config.annotation.BlogUser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static com.snow.ly.blog.common.util.PasswordEncoderUtils.encode;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/test")
public class TestController {


    @Autowired
    UserRepository userRepository;
    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    FriendsRepository friendsRepository;
    @Autowired
    private ConcernedTypeRepository concernedTypeRepository;
    @Autowired
    private AdminRepository adminRepository;


    @PostMapping("addAdmin")
    @ApiOperation(value = "添加管理员")
    public ResponseEntity addAdmin(@ApiParam(value = "账号", required = true) @RequestParam String account,
                                   @ApiParam(value = "密码",required = true)@RequestParam String password) {
        Admin admin=adminRepository.save(Admin.builder().account(account).role(1).password(encode(password)).createTime(LocalDateTime.now()).build());

        return ok(admin);
    }

    @PostMapping("/addConcernedType")
    public ResponseEntity addConcernedType(@RequestParam String concerned,
                                           @RequestParam String img){
        return ok(concernedTypeRepository.save(ConcernedType.builder().concerned(concerned).img(img).build()));
    }

    @PostMapping("/addFriends")
    public ResponseEntity addFriends(@RequestParam String userId,
                                     @RequestParam String friendId){
        return ok(friendsRepository.save(Friends.builder().friendId(friendId).userId(userId).build()));
    }

    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestParam String name,
                                  @RequestParam String password){
       User u=User.builder().lastName(name).userPassword(password).build();

        return ok(userRepository.save(u));
    }

    @PostMapping("/addLanguages")
    @ApiOperation(value = "添加语言")
    public ResponseEntity addLanguages(@RequestParam String language){
        Language l=Language.builder().language(language).build();
        return ok(languageRepository.save(l));
    }

    @GetMapping("/allUsers")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = false, dataType = "string", paramType = "header"),
    })
    public ResponseEntity allUsers(){
        return ok(userRepository.findAll());
    }

    @DeleteMapping("/deleteUser")
    @ApiOperation(value = "",notes = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1YTc3MDE1NzczNzg3YzJiZDgxNTNkZGMiLCJ1c2VySWQiOiI1YTc3MDE1NzczNzg3YzJiZDgxNTNkZGMiLCJleHAiOjE1MTc5ODI3MDR9.peCeAZmEZSjoFunv1uval5OeL2S1_YquDOLbhb1twH8wzNjqiIEQv9J9oWpo-kcjuL3naMDf12A7kK2tKA4v9g")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity deleteUser(@ApiParam(hidden =true) @BlogUser String userId){
        System.out.println(userId);
//        userRepository.deleteAll();
       return ok("xxxxxxxxxxx");
    }

    @Autowired
    private RedisTemplate<String, String> template;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @GetMapping("/test")
    public ResponseEntity test(){

//设置缓存，建议每个键都设置过期时间
        redisTemplate.opsForValue().set("test", "test", 5, TimeUnit.SECONDS);
//获取缓存值
        String value = redisTemplate.opsForValue().get("test");

        System.out.println(value);

        return ok("xxxxxxxxxxxxxxxxd");
    }


}
