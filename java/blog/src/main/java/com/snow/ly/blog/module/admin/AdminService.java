package com.snow.ly.blog.module.admin;

import com.snow.ly.blog.common.bean.Cub;
import com.snow.ly.blog.common.bean.Result;
import com.snow.ly.blog.common.bean.Tips;
import com.snow.ly.blog.common.pojo.*;
import com.snow.ly.blog.common.repository.*;
import com.snow.ly.blog.common.util.ArticleUtils;
import com.snow.ly.blog.common.util.StringUtils;
import com.snow.ly.blog.module.home.HomeService;
import com.snow.ly.blog.module.message.MessageService;
import com.snow.ly.blog.module.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.snow.ly.blog.common.bean.Result.fail;
import static com.snow.ly.blog.common.bean.Result.success;
import static com.snow.ly.blog.common.util.JWTTokenUtils.createToken;
import static com.snow.ly.blog.common.util.PasswordEncoderUtils.decode;
import static com.snow.ly.blog.common.util.PasswordEncoderUtils.encode;
import static com.snow.ly.blog.common.util.StringUtils.isNotNull;
import static com.snow.ly.blog.common.util.StringUtils.stringToArrayList;
import static com.snow.ly.blog.common.util.StringUtils.stringToLocalDateTime;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AdvertRepository advertRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleUtils articleUtils;
    @Autowired
    private HomeService homeService;
    @Autowired
    private ConcernedTypeRepository concernedTypeRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupUserRepository groupUserRepository;
    @Autowired
    private AboutRepository aboutRepository;
    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private CallRepository callRepository;
    @Autowired
    private CertificateRepository certificateRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private UsRepository usRepository;
    @Autowired
    private ProtocolRepository protocolRepository;
    @Autowired
    private LoginRepository loginRepository;



    public Result login(String account, String password) {
        Admin admin=adminRepository.findByAccount(account);
        if (admin==null)return fail(Tips.ADMIN_NOT.msg);
        if (!decode(password,admin.getPassword()))return fail(Tips.USER_PASSWORD_FALSE.msg);
        admin.setToken(createToken(admin.getId()));
        return success(admin);
    }

    public Result adminInformation(Admin admin) {

        return success(admin);

    }

    public Result modifyPassword(Admin admin, String oldPassword, String newPassword) {
        if (!decode(oldPassword,admin.getPassword()))return fail(Tips.USER_PASSWORD_F.msg);
        admin.setPassword(encode(newPassword));
        return success(adminRepository.save(admin));

    }

    public Result addAdmin(Admin admin,String account, String password) {
        if (admin.getRole()!=1)return fail(Tips.AUTH_NOT.msg);
        Admin a=adminRepository.findByAccount(account);
        if (a!=null)return fail(Tips.ADMIN_ACOUNT_HAD.msg);
        return success(adminRepository.save(Admin.builder().account(account).role(0).password(encode(password)).createTime(LocalDateTime.now()).build()));

    }

    public Result getAdmins(Integer page, Integer pageSize) {
        if (page>0)page--;
        return success(adminRepository.findAll(new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createTime"))));
    }

    public Result deleteAdmin(Admin admin, String adminIds) {
        if (admin.getRole()!=1)return fail(Tips.AUTH_NOT.msg);
        StringUtils.stringToArrayList(adminIds).stream()
                .forEach(i->{
                    Admin a=adminRepository.findOne(i);
                    //if (a!=null&&a.getRole()!=1)adminRepository.delete(a);
                    if (a!=null)adminRepository.delete(a); //超级管理员可以删除
                });

        return success();
    }

    public Result getUsers(Integer status,Integer page, Integer pageSize) {
        if (status<0 ||status>2)return fail(Tips.PARAMETER.msg);
        if (page>0)page--;
        if (status==2)return success(userRepository.findByAccountStatusIn(Arrays.asList(0,1),new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createTime"))));
        return success(userRepository.findByAccountStatus(status,new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createTime"))));
    }

    public Result operateUser(Integer type, String userIds) {
        if (type<1||type>3)return fail(Tips.PARAMETER.msg);
        StringUtils.stringToArrayList(userIds).stream()
                .forEach(i->{
                    User u=userRepository.findOne(i);
                    if (u!=null){
                        if (type!=3){
                            u.setAccountStatus(type);
                        }else {
                            if (u.getAccountStatus()!=2){
                                u.setAccountStatus(0);
                            }
                        }
                        userRepository.save(u);
                    }

                });

        return success();
    }

    public Result getUserDetails(String userId) {

        return userService.seeUserDetails(userId);

    }

    public Result getAdverts(Integer page, Integer pageSize) {
        if (page>0)page--;
        return success(advertRepository.findAll(new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createTime"))));
    }

    public Result publishAdvert(String title, String content, String img, String link, String type, String name, Double money, String startTime, String endTime) {
      Advert a=Advert.builder()
                .title(title)
                .content(content)
                .img(img)
                .type(type)
                .name(name)
                .money(money)
                .startTime(stringToLocalDateTime(startTime))
                .endTime(stringToLocalDateTime(endTime))
                 .createTime(LocalDateTime.now())
                .build();
       if (isNotNull(link))a.setLink(link);
       return success(advertRepository.save(a));
    }

    public Result modifyAdvert(String id, String title, String content, String img, String link, String type, String name, Double money, String startTime, String endTime) {
      Advert a=advertRepository.findOne(id);
      if (a==null)return fail(Tips.ADVERT_NOT.msg);
      if (isNotNull(title))a.setTitle(title);
      if (isNotNull(content))a.setContent(content);
      if (isNotNull(img))a.setImg(img);
      if (isNotNull(link))a.setLink(link);
      if (isNotNull(title))a.setType(type);
      if (isNotNull(name))a.setName(name);
      if (money!=null)a.setMoney(money);
      if (isNotNull(startTime))a.setStartTime(stringToLocalDateTime(startTime));
      if (isNotNull(endTime))a.setEndTime(stringToLocalDateTime(endTime));
      return success(advertRepository.save(a));
    }

    public Result deleteAdvert(String ids) {
        stringToArrayList(ids).stream()
                .forEach(i->{
                    Advert a=advertRepository.findOne(i);
                    if (a!=null)advertRepository.delete(a);
                });

        return success();
    }

    public Result getArticles(Integer page, Integer pageSize) {
        if (page>0)page--;
        Page<Article> articles=articleRepository.findAll(new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createTime")));
        articles.getContent().stream()
                .forEach(i->{
                    //点赞用户
                    i.setIsLikeUsers(articleUtils.getArticleIsLike(i.getId()));
                    //获取文章评论
                    articleUtils.getCommentAndReply(i);
                    if (i.getType()==1){//投票
                        //获取投票项
                        i.setPollItems(articleUtils.calculatePoll(i.getId()));
                    }

                });
        return success(articles);
    }

    public Result getArticleDetails(String id) {
        Article a=articleRepository.findOne(id);
        if (a==null)return fail(Tips.NOT_MESSAGE.msg);
        //点赞用户
        a.setIsLikeUsers(articleUtils.getArticleIsLike(a.getId()));
        //获取文章评论
        articleUtils.getCommentAndReply(a);
        if (a.getType()==1){//投票
            //获取投票项
            a.setPollItems(articleUtils.calculatePoll(a.getId()));
        }
        return success(a);
    }

    public Result modifyPoll(Admin admin, String id, String title, String items, Integer validity, Integer visibility, String address) {
        Article a = articleRepository.findOne(id);
        if (a == null) return fail(Tips.NOT_MESSAGE.msg);
        if (StringUtils.isNotNull(title)) a.setTitle(title);
        if (StringUtils.isNotNull(address)) a.setAddress(address);
        if (visibility != null && visibility >= 0 && visibility <= 2) {
            a.setVisibility(visibility);
        } else {
            a.setVisibility(2);
        }
        if (validity != null) {
            //设置有效期
            homeService.setValidity(a, validity);
        }
        if (StringUtils.isNotNull(items)) {
            //设置投票选项
            homeService.setPollItem(a, items);
        }
        a=articleRepository.save(a);
        return success(getArticleDetails(a.getId()));
    }

    public Result poll(Admin admin, String title, String items, Integer validity, Integer visibility, String address) {

        Article a=Article.builder().userId(admin.getId()).userImg(admin.getImg()).firstName(admin.getAccount()).type(1).title(title).createTime(LocalDateTime.now()).build();
        if (StringUtils.isNotNull(address))a.setAddress(address);
        if (visibility!=null&&visibility>=0&&visibility<=2){
            a.setVisibility(visibility);
        }else{
            a.setVisibility(2);
        }
        a.setIsSys(1);
        //设置有效期
        homeService.setValidity(a,validity);
        a=articleRepository.save(a);
        //设置投票选项
        homeService.setPollItem(a,items);

        return success(getArticleDetails(a.getId()));






    }

    public Result video(Admin admin, String content, String image, String video, Integer visibility, String address) {

        Article a=Article.builder().userId(admin.getId()).isSys(1).userImg(admin.getImg()).firstName(admin.getAccount()).type(2).content(content).images(Arrays.asList(image)).videos(Arrays.asList(video)).createTime(LocalDateTime.now()).build();
        if (visibility!=null&&visibility>=0&&visibility<=2)a.setVisibility(visibility);
        if (StringUtils.isNotNull(address))a.setAddress(address);
        return success(articleRepository.save(a));



    }


    public Result modifyVideo(Admin admin, String id, String content, String image, String video, Integer visibility, String address) {
        Article a = articleRepository.findOne(id);
        if (a == null) return fail(Tips.NOT_MESSAGE.msg);
        if (StringUtils.isNotNull(content)) a.setContent(content);
        if (StringUtils.isNotNull(image)) a.setImages(Arrays.asList(image));
        if (StringUtils.isNotNull(video)) a.setVideos(Arrays.asList(video));
        if (visibility != null && visibility >= 0 && visibility <= 2) a.setVisibility(visibility);
        if (StringUtils.isNotNull(address)) a.setAddress(address);
        return success(articleRepository.save(a));
    }


    public Result post(Admin admin, String content, String images, Integer visibility, String address) {
        Article a=Article.builder().userId(admin.getId()).isSys(1).userImg(admin.getImg()).firstName(admin.getAccount()).content(content).images(StringUtils.stringToArrayList(images)).createTime(LocalDateTime.now()).build();
        if (StringUtils.isPost(content))
            a.setType(0);
        else a.setType(3);
        if (visibility!=null&&visibility>=0&&visibility<=2)a.setVisibility(visibility);
        if (StringUtils.isNotNull(address))a.setAddress(address);
        return success(articleRepository.save(a));

    }

    public Result modifyPost(Admin admin, String id, String content, String images, Integer visibility, String address) {
        Article a = articleRepository.findOne(id);
        if (a == null) return fail(Tips.NOT_MESSAGE.msg);
        if (StringUtils.isNotNull(content)) a.setContent(content);
        if (StringUtils.isNotNull(images))a.setImages(StringUtils.stringToArrayList(images));
        if (visibility!=null&&visibility>=0&&visibility<=2)a.setVisibility(visibility);
        if (StringUtils.isNotNull(address))a.setAddress(address);
        return success(articleRepository.save(a));
    }



    public Result deleteArticle(String ids) {
        List<String> strings=StringUtils.stringToArrayList(ids);
        strings.forEach(i->{
            if (articleRepository.findOne(i)!=null){
                articleRepository.delete(i);
            }
        });

        return success();
    }


    public Result addConcernedType(Integer type,String concerned, String img) {
      return success(concernedTypeRepository.save(ConcernedType.builder().type(type).concerned(concerned).img(img).build()));

    }

    public Result modifyConcernedType(Admin admin, String id,Integer type, String concerned, String img) {

        ConcernedType t=concernedTypeRepository.findOne(id);
        if (t==null)return fail(Tips.NOT_MESSAGE.msg);
        if (StringUtils.isNotNull(concerned))t.setConcerned(concerned);
        if (StringUtils.isNotNull(img))t.setImg(img);
        if (type!=null)t.setType(type);
        return success(concernedTypeRepository.save(t));

    }


    public Result getConcernedType(Integer type,Integer page, Integer pageSize) {
        if (page>0)page--;
        if (type==null) return success(concernedTypeRepository.findAll(new PageRequest(page,pageSize)));
        return success(concernedTypeRepository.findByType(type,new PageRequest(page,pageSize)));
    }

    public Result deleteConcernedType(String ids) {
        StringUtils.stringToArrayList(ids)
                .stream()
                .forEach(i->{
                    if (concernedTypeRepository.findOne(i)!=null){
                        concernedTypeRepository.delete(i);
                    }
        });
        return success();
    }

    public Result getGroup(Integer page, Integer pageSize) {
        if (page>0)page--;
        Page<Group> groupPage = groupRepository.findAll(new PageRequest(page,pageSize));
        for (Group group : groupPage.getContent()){
            List<GroupUser> groupUsers = groupUserRepository.findByGroupId(group.getId());
            group.setUsers(groupUsers);
        }
        return success(groupPage);

    }

    public Result getGroupDetails(String id) {

        Group g=groupRepository.findOne(id);
        if (g==null)return fail(Tips.NOT_MESSAGE.msg);
        g.setUsers(groupUserRepository.findByGroupId(id));
        return success(g);
    }

    public Result addGroup(Admin admin, String name, String img, String introduction) {

       return success(groupRepository.save(Group.builder().name(name).img(img).introduction(introduction).createTime(LocalDateTime.now()).adminId(admin.getId()).build()));


    }

    public Result modifyGroup(String id, String name, String img, String introduction) {
        Group g=groupRepository.findOne(id);
        if (g==null)return fail(Tips.NOT_MESSAGE.msg);
        if (StringUtils.isNotNull(name))g.setName(name);
        if (StringUtils.isNotNull(img))g.setImg(img);
        if (StringUtils.isNotNull(introduction))g.setIntroduction(introduction);
        return success(groupRepository.save(g));

    }

    public Result deleteGroup(String ids) {
        StringUtils.stringToArrayList(ids).stream()
                .forEach(i->{
                    if (groupRepository.findOne(i)!=null)
                        groupRepository.delete(i);
                });
        return success();
    }

    public Result searchGroup(String keyword) {

        return success(groupRepository.findByNameLikeOrIntroductionLike(keyword,keyword));

    }

    public Result addAbout(String img, String content) {
        return success(aboutRepository.save(About.builder().img(img).content(content).build()));

    }

    public Result getPolicy() {
        return success(policyRepository.findAll());
    }

    public Result modifyPolicy(String id, String img, String content) {
        Policy a=policyRepository.findOne(id);
        if (a==null)return fail(Tips.NOT_MESSAGE.msg);
        if (StringUtils.isNotNull(img))a.setImg(img);
        if (StringUtils.isNotNull(content))a.setContent(content);
        return success(policyRepository.save(a));
    }

    public Result deletePolicy(String ids) {
        StringUtils.stringToArrayList(ids).stream()
                .forEach(i->{
                    if (policyRepository.findOne(i)!=null)
                        policyRepository.delete(i);
                });
        return success();
    }


    public Result addPolicy(String img, String content) {
        return success(policyRepository.save(Policy.builder().img(img).content(content).build()));

    }

    public Result getAbout() {
        return success(aboutRepository.findAll());
    }

    public Result modifyAbout(String id, String img, String content) {
        About a=aboutRepository.findOne(id);
        if (a==null)return fail(Tips.NOT_MESSAGE.msg);
        if (StringUtils.isNotNull(img))a.setImg(img);
        if (StringUtils.isNotNull(content))a.setContent(content);
        return success(aboutRepository.save(a));
    }

    public Result deleteAbout(String ids) {
        StringUtils.stringToArrayList(ids).stream()
                .forEach(i->{
                    if (aboutRepository.findOne(i)!=null)
                        aboutRepository.delete(i);
                });
        return success();
    }

    public Result getCall(Integer page, Integer pageSize) {

        if (page>0)page--;
        return success(callRepository.findAll(new PageRequest(page,pageSize)));



    }

    public Result deleteCall(String ids) {

        StringUtils.stringToArrayList(ids)
                .stream()
                .forEach(i->{
                    if (callRepository.findOne(i)!=null)
                        callRepository.delete(i);
                });
        return success();
    }

    public Result modifyAdminInform(Admin admin, String id, String account, String password, String img, Integer role) {

        Admin a=adminRepository.findOne(id);
        if (a==null)return fail(Tips.ADMIN_NOT.msg);
        if (isNotNull(img))a.setImg(img);
        if ((role!=null&&role<0)||(role!=null&&role>1))return fail(Tips.PARAMETER.msg);
        if (role!=null&&admin.getRole()!=1)return fail(Tips.AUTH_NOT.msg);
        if (role!=null&&admin.getRole()==1)a.setRole(role);
        if (isNotNull(account)&&admin.getRole()!=1)return fail(Tips.AUTH_NOT.msg);
        if (isNotNull(password)&&admin.getRole()!=1)return fail(Tips.AUTH_NOT.msg);
        if (isNotNull(password)&&admin.getRole()==1)a.setPassword(encode(password));
        if (isNotNull(account)&&admin.getRole()==1){
            if (adminRepository.findByAccount(account)!=null){
                return fail(Tips.ADMIN_ACOUNT_HAD.msg);
            }else{
                a.setAccount(account);
            }
        }

        return success(adminRepository.save(a));

    }

    public Result getReview(Admin admin,Integer type, Integer page, Integer pageSize) {
        if (page>0)page--;
        if (type<0||type>3)return fail(Tips.PARAMETER.msg);
        if (type>=0&&type<=2)
        return success(certificateRepository.findByPass(type,new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createDate"))));
        if (type==3)
        return success(certificateRepository.findAll(new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createDate"))));
        return success();
    }


    public Result getReviewDetails(String id) {
        Certificate c=certificateRepository.findOne(id);
        if (c==null)return fail(Tips.MESSAGE_NOT.msg);
        User u=userRepository.findOne(c.getId());
        if (u==null)return fail(Tips.USER_NOT.msg);
         return success(
                 Cub.builder()
                 .id(c.getId())
                 .certificate(c.getCertificate())
                 .isTrue(c.getPass())
                 .certificateImage(c.getCertificateImage())
                 .createDate(c.getCreateDate())
                 .userId(u.getUserId())
                 .accountStatus(u.getAccountStatus())
                 .userImg(u.getUserImg())
                 .firstName(u.getFirstName())
                 .lastName(u.getLastName())
                 .profileType(u.getProfileType())
                 .userGender(u.getUserGender())
                 .userEmail(u.getUserEmail())
                 .userPhone(u.getUserPhone())
                 .build());

    }

    public Result modifyReview(String id, Integer type) {
        if (type<0||type>2)return fail(Tips.PARAMETER.msg);
        Certificate c=certificateRepository.findOne(id);
        if (c==null)return fail(Tips.MESSAGE_NOT.msg);
        c.setPass(type);
        return success(certificateRepository.save(c));

    }

    public Result deleteReview(String id) {

        Certificate c=certificateRepository.findOne(id);
        if (c==null)return fail(Tips.MESSAGE_NOT.msg);
        certificateRepository.delete(c);
        return success();


    }

    public Result pushMessage(Admin admin, String content) {

        return success(messageService.pushMessage(admin,content));


    }

    public Result getMessage(Integer page, Integer pageSize) {
        if (page>0)page--;
        return success(messageRepository.findByType(2,new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"sendTime"))));
    }

    public Result deleteMessage(String ids) {

        StringUtils.stringToArrayList(ids).stream()
                .forEach(i->{
                    Message m=messageRepository.findOne(i);
                    if (m!=null){
                        messageRepository.delete(m);
                    }
                });
        return success();
    }

    public Result addLanguage(String language, Integer type) {
        Language l=languageRepository.findByType(type);
        if (l!=null)return fail(Tips.LANGUAGE_HAD.msg);
        return success(languageRepository.save(Language.builder().language(language).type(type).build()));


    }

    public Result deleteLanguage(String id) {
        Language l=languageRepository.findOne(id);
        if (l!=null)languageRepository.delete(id);
        return success();
    }

    public Result languages() {
        return success(languageRepository.findAll(new Sort(Sort.Direction.ASC,"type")));
    }


    public Result addUs(String email, String address, String longitude, String latitude) {
        List<Us> us=usRepository.findAll();
        if (us.size()>0){
            Us u=us.get(0);
            u.setEmail(email);
            u.setAddress(address);
            u.setLongitude(longitude);
            u.setLatitude(latitude);
            return success(usRepository.save(u));
        }else {
            return success(usRepository.save(Us.builder().email(email).address(address).longitude(longitude).latitude(latitude).build()));
        }
    }

    public Result modifyUs(String email, String address, String longitude, String latitude) {
        List<Us> us=usRepository.findAll();
        if (us.size()==0)return fail();
        Us u=us.get(0);
        if (StringUtils.isNotNull(email))
        u.setEmail(email);
        if (StringUtils.isNotNull(address))
        u.setAddress(address);
        if (StringUtils.isNotNull(longitude))
        u.setLongitude(longitude);
        if (StringUtils.isNotNull(latitude))
        u.setLatitude(latitude);
        return success(usRepository.save(u));
        
        
    }

    public Result deleteUs(String id) {
        Us us=usRepository.findOne(id);
        if (us!=null)usRepository.delete(id);
        return success();
    }

    public Result getUs() {
        return success(usRepository.findAll());
    }

    public Result addProtocol(String title, String content) {
        List<Protocol> ps=protocolRepository.findAll();
        if (ps.size()==0)return success(protocolRepository.save(Protocol.builder().title(title).content(content).build()));
        Protocol p=ps.get(0);
        if (StringUtils.isNotNull(title))
        p.setTitle(title);
        if (StringUtils.isNotNull(content))
        p.setContent(content);
        return success(protocolRepository.save(p));
    }

    public Result modifyProtocol(String title, String content) {
        List<Protocol> ps=protocolRepository.findAll();
        if (ps.size()==0)return fail();
        Protocol p=ps.get(0);
        p.setTitle(title);
        p.setContent(content);
        return success(protocolRepository.save(p));
    }

    public Result deleteProtocol(String id) {
       if (protocolRepository.findOne(id)!=null)protocolRepository.delete(id);
       return success();

    }

    public Result getProtocol() {
        return success(protocolRepository.findAll());
    }


    public Result addLoginImg(String img) {
        List<Login>list=loginRepository.findAll();
        if (list.size()==0)return success(loginRepository.save(Login.builder().img(img).build()));
        Login l=list.get(0);
        l.setImg(img);
        return success(loginRepository.save(l));
    }

    public Result getLoginImg() {
        return success(loginRepository.findAll());
    }
}
