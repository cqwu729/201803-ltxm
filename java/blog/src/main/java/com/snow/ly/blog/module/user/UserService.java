package com.snow.ly.blog.module.user;

import com.snow.ly.blog.common.bean.*;
import com.snow.ly.blog.common.pojo.*;
import com.snow.ly.blog.common.repository.*;
import com.snow.ly.blog.common.util.*;
import com.snow.ly.blog.config.annotation.Authorization;
import com.snow.ly.blog.module.home.HomeService;
import com.snow.ly.blog.module.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.snow.ly.blog.common.bean.Result.fail;
import static com.snow.ly.blog.common.bean.Result.success;
import static com.snow.ly.blog.common.bean.Tips.CODE_FALSE;
import static com.snow.ly.blog.common.bean.Tips.EMAIL_ONESLF;
import static com.snow.ly.blog.common.bean.Tips.EMAIL_USER_HAD;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CodeUtils codeUtils;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private FollowersRepository followersRepository;
    @Autowired
    private FollowedRepository followedRepository;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserConcernedTypeRepository userConcernedTypeRepository;
    @Autowired
    private UserCommunitiesRepository userCommunitiesRepository;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private CertificateRepository certificateRepository;
    @Autowired
    private BestsRepository bestsRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private PrivacyRepository privacyRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private HomeService homeService;
    @Autowired
    private ArticleUtils articleUtils;
    @Autowired
    private PollItemRepository pollItemRepository;
    @Autowired
    private PlayingRepository playingRepository;
    @Autowired
    private WorkRepository workRepository;
    @Autowired
    private GroupUserRepository groupUserRepository;


    public Result followed(String userId,Integer page,Integer pageSize){
        if (page>=1)page--;
        Page<Followed> followers=followedRepository.findByUserId(userId,new PageRequest(page,pageSize));
        PageUserBean fb=new PageUserBean();
        fb.setTotalElements(followers.getTotalElements());
        fb.setTotalPages(followers.getTotalPages());
        toUserBean(fb, followers.getContent().stream().map(i->FU.builder().id(i.getId()).userId(i.getFollowedId()).build()).collect(Collectors.toList()));
        return success(fb);
    }

    public Result followers(String userId,Integer page,Integer pageSize){
        if (page>=1)page--;
        Page<Followers> followers=followersRepository.findByUserId(userId,new PageRequest(page,pageSize));
        PageUserBean fb=new PageUserBean();
        fb.setTotalElements(followers.getTotalElements());
        fb.setTotalPages(followers.getTotalPages());
        toUserBean(fb, followers.getContent().stream().map(i-> FU.builder().id(i.getId()).userId(i.getFollowerId()).build()).collect(Collectors.toList()));
        return success(fb);
    }

    public Result friends(String userId,Integer page,Integer pageSize){
        if (page>=1)page--;
        Page<Friends> friends=friendsRepository.findByUserId(userId,new PageRequest(page,pageSize));
        PageUserBean fb=new PageUserBean();
        fb.setTotalElements(friends.getTotalElements());
        fb.setTotalPages(friends.getTotalPages());
        toUserBean(fb, friends.getContent().stream().map(i-> FU.builder().id(i.getId()).userId(i.getFriendId()).build()).collect(Collectors.toList()));
        return success(fb);
    }

    public void toUserBean(PageUserBean b, List<FU> ids){

        ids.forEach(i->{
            User user=userRepository.findOne(i.getUserId());
            if (user!=null){
                b.getBeans().add(
                        UserBean.builder()
                                .id(i.getId())
                                .userId(i.getUserId())
                                .userImg(user.getUserImg())
                                .userGender(user.getUserGender())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .profileType(user.getProfileType())
                                .userSignature(user.getUserSignature())
                                .build());
            }
        });

//            ids
//                .stream()
//                .map(i->userRepository.findOne(i.getUserId()))
//                .filter(i->i!=null)
//                .forEach(i-> b.getBeans().add(
//                        UserBean.builder()
//                                .userId(i.getUserId())
//                                .userImg(i.getUserImg())
//                                .userGender(i.getUserGender())
//                                .firstName(i.getFirstName())
//                                .profileType(i.getProfileType())
//                                .userSignature(i.getUserSignature())
//                                .build())
//                );
    }



    public Result editInformation(String userId,String userImg,String firstName,String lastName,String userCover, String userBirthday,Integer userGender, String userSignature,String readingSchool){
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        if (userImg!=null){
            user.setUserImg(userImg);
            //同时修改用户所在群里成员头像
            /*List<GroupUser> groupUsers = groupUserRepository.findByUserId(user.getUserId());
            for (GroupUser groupUser : groupUsers){
                groupUser.setUserImg(userImg);
                groupUserRepository.save(groupUser);
            }*/
        }
        //修改账户头像
        //修改账户头像
        List<Account> accountList = accountRepository.findByXUserIdAndUserId(userId, userId);
        if (accountList.size() <= 0){
            return fail("失败");
        }else {
            Account account = accountList.get(0);
            account.setUserImage(user.getUserImg());
            accountRepository.save(account);
        }

        if (StringUtils.isNotNull(firstName))user.setFirstName(firstName);
        if (StringUtils.isNotNull(lastName))user.setLastName(lastName);
        if (userCover!=null)user.setUserCover(userCover);
        if (userSignature!=null)user.setUserSignature(userSignature);
        setBirthday(user,userBirthday);
        setGender(user,userGender);
        setSchool(user,readingSchool);
        userRepository.save(user);
        return success();
    }

    public void setSchool(User user,String readingSchool){
        if (StringUtils.isNull(readingSchool))return;
        user.setReadingSchool(readingSchool);
    }
    public void setGender(User user,Integer userGender){
        if (userGender==null)return;
        if (userGender!=0&&userGender!=1&&userGender!=2)return;
        user.setUserGender(userGender);
    }

    public void setBirthday(User user,String userBirthday){
        if (userBirthday == null) return;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ldt = LocalDate.parse(userBirthday, df);
        user.setUserBirthday(ldt);
    }

    public Result information(String userId){
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        //计算
        user.setFriendsCount(friendsRepository.countByUserId(userId));
        user.setFollowersCount(followersRepository.countByUserId(userId));
        user.setFollowedCount(followedRepository.countByUserId(userId));
        //运动类型
        user.setUserConecrnedType(userConcernedTypeRepository.findByUserId(userId));
        //社区
        user.setUserCommunities(userCommunitiesRepository.findByUserId(userId));
        //教育
        user.setEducations(educationRepository.findByUserId(userId));
        //技能
        user.setSkills(skillRepository.findByUserId(userId));
        //俱乐部类型
        user.setTypes(typeRepository.findByUserId(userId));
        //俱乐部证书
        user.setCertificates(certificateRepository.findByUserId(userId));
        //运动员个人记录
        user.setBests(bestsRepository.findByUserId(userId));
        //playing
        user.setPlayings(playingRepository.findByUserId(userId));
        //work
        user.setWorks(workRepository.findByUserId(userId));
        //用户数据完整性
        countCompletion(user);
        return success(user);
    }

    /**
     * 计算用户信息的完整性
     * @param user
     */
    public void countCompletion(User user){
        Double sum=7.0;
        Integer cd=1;
        /*Email*/
        if (user.getUserEmail()!=null)cd++;
        /*Phone*/
        //if (user.getUserPhone()!=null)cd++;
        /*生日*/
        if (user.getUserBirthday()!=null)cd++;
        /*性别*/
        if (user.getUserGender()!=null)cd++;
        /*学校*/
        if (user.getReadingSchool()!=null)cd++;
        /*社区*/
        if (user.getUserCommunities().size()>0)cd++;
        /*签名*/
        if (user.getUserSignature()!=null && !"".equals(user.getUserSignature().trim()))cd++;
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        String s=nf.format(cd/sum);
        Double d=Double.valueOf(s)*100;
        user.setDataCompletion(d.intValue());
    }

    public Result bindAccount(String userId,String phone,String code){
        if (!RegexUtils.checkPhone(phone))return fail(Tips.PHONE_FALSE.msg);
        if (!codeUtils.checkCode(phone,code))return fail(CODE_FALSE.msg);
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        if (phone.equals(user.getUserPhone()))return fail(Tips.PHONE_ONESLF.msg);
        if (userRepository.findByUserPhoneAndUserIdNotIn(phone,userId).size()>0)return fail(Tips.PHONE_USER_HAD.msg);
        user.setUserPhone(phone);
        userRepository.save(user);
        return success();
    }

    public Result bindEmail(String userId, String email, String code) {
        if (!RegexUtils.checkEmail(email))return fail(Tips.EMAIL_FALSE.msg);
        if (!codeUtils.checkCode(email,code))return fail(CODE_FALSE.msg);
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        if (email.equals(user.getUserEmail()))return fail(Tips.EMAIL_ONESLF.msg);
        if (userRepository.findByUserEmailAndUserIdNotIn(email,userId).size()>0)return fail(Tips.EMAIL_USER_HAD.msg);
        user.setUserEmail(email);
        userRepository.save(user);
        return success();

    }


    public Result setLanguage(String userId,String languageId){
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        Language language=languageRepository.findOne(languageId);if (language==null)return fail();
        user.setUserLanguage(String.valueOf(language.getType()));
        userRepository.save(user);
        return success(language);
    }

    public Result getAllLanguages(){
        return success(languageRepository.findAll(new Sort(Sort.Direction.ASC,"type")));
    }

    public Result changeAccount(String userId,String email,String code){
        if (!RegexUtils.checkEmail(email))return fail(Tips.EMAIL_FALSE.msg);
        //查询手机号,判断验证码
        User userO = userRepository.findByUserId(userId);
        if (userO == null){
            return fail(Tips.USER_NOT.msg);
        }
        String userPhone = userO.getUserPhone();
        if (!codeUtils.checkCode(userPhone,code))return fail(CODE_FALSE.msg);
        //if (!codeUtils.checkCode(email,code))return fail(CODE_FALSE.msg);
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        if (email.equals(user.getUserEmail()))return fail(EMAIL_ONESLF.msg);
        if (userRepository.findByUserEmailAndUserIdNotIn(email,userId).size()>0)return fail(EMAIL_USER_HAD.msg);
        user.setUserEmail(email);
        userRepository.save(user);
        return success();
    }

    public Result resetPassword(String userId,String currentPassword,String newPassword){
        Optional<User> user=Optional.ofNullable(userRepository.findOne(userId));
        if (!user.isPresent())return fail(Tips.USER_NOT.msg);
        if (!PasswordEncoderUtils.decode(currentPassword,user.get().getUserPassword())) return fail(Tips.USER_PASSWORD_FALSE.msg);
        User u=user.get();
        u.setUserPassword(PasswordEncoderUtils.encode(newPassword));
        userRepository.save(u);
        return success();

    }


    @Authorization
    public Result addFollow(String userId, String followId, User user) {
        User f = userRepository.findOne(followId);
        if (f == null) return fail(Tips.USER_NOT.msg);
        //判断是否关注
        //判断是不是粉丝
        if ((!userUtils.isFollowed(userId, followId)) && (!userUtils.isFollower(followId, userId))) {
            followersRepository.save(Followers.builder().userId(followId).followerId(userId).build());
            return success(followedRepository.save(Followed.builder().userId(userId).followedId(followId).build()));
        }
        return fail(Tips.ALREADY_FOLLOW.msg);
    }

    public Result cancelFollow(String userId,String followId) {
        followedRepository.deleteByUserIdAndFollowedId(userId,followId);
        followersRepository.deleteByUserIdAndFollowerId(followId,userId);
        return success();
    }

    @Authorization
    public Result addFriend(String userId, String friendId, User user) {
        User friends=userRepository.findOne(friendId);if (friends==null)return fail(Tips.USER_NOT.msg);
        if (userUtils.isFriend(userId,friendId))return fail(Tips.FRIEND_HAD.msg);
        //好友申请
        List<Privacy>p=privacyRepository.findByUserId(userId);
        if (p.size()>0&&p.get(0).getAllNotifications()==1){
            messageService.sendFriendRequest(user,friendId);
        }else{
            privacyRepository.save(Privacy.builder().allNotifications(1).wall(1).email(1).connections(1).email(1).userId(userId).build());
            messageService.sendFriendRequest(user,friendId);
        }
        return success();
    }

    public Result deleteArticle(String userId, String articleId) {
        if (articleRepository.findByIdAndUserId(userId, articleId).size() > 0) articleRepository.delete(articleId);
        return success();
    }

    public Result seeUserDetails(String userId) {
        return information(userId);
    }


    public Result deleteEducation(String id) {

        Education e=educationRepository.findOne(id);
        if (e==null)return fail(Tips.MESSAGE_NOT.msg);
        educationRepository.delete(id);
        return success();

    }

    @Authorization
    public Result modifyCover(String userId, String cover,User user) {
        user.setUserCover(cover);
        userRepository.save(user);
        return success();
    }
    @Authorization
    public Result modifyImage(String userId, String userImage, User user) {
        user.setUserImg(userImage);
        userRepository.save(user);
        return success();
    }
    @Authorization
    public Object modifyBirth(String userId, String dataOfBirth, User user) {
        setBirthday(user,dataOfBirth);
        userRepository.save(user);
        return success();

    }
    @Authorization
    public Result modifyGender(String userId, Integer gender, User user) {
        setGender(user,gender);
        userRepository.save(user);
        return success();
    }
    @Authorization
    public Result addConcernedType(String userId, String concernedType, User user) {

        return success(userConcernedTypeRepository.save(UserConcernedType.builder().userId(userId).concerned(concernedType).build()));


    }

    public Result deleteConcernedType(String id) {
        if (userConcernedTypeRepository.findOne(id)==null)return fail(Tips.MESSAGE_NOT.msg);
        userCommunitiesRepository.delete(id);
        return success();

    }

    public Result modifyConcernedType(String id, String concernedType) {
        UserConcernedType type=userConcernedTypeRepository.findOne(id);
        if (type==null)return fail(Tips.MESSAGE_NOT.msg);
        type.setConcerned(concernedType);
        return success(userConcernedTypeRepository.save(type));

    }
    @Authorization
    public Result modifySignature(String userId, String signature, User user) {
        user.setUserSignature(signature);
        userRepository.save(user);
        return success();
    }

    @Authorization
    public Result modifyIntroduction(String userId, String introduction, User user) {
        user.setIntroduction(introduction);
        userRepository.save(user);
        return success();
    }

    public Result addType(String userId, String type) {
        return success(typeRepository.save(Type.builder().userId(userId).type(type).build()));


    }

    public Result deleteType(String id) {
        if (typeRepository.findOne(id)==null)return fail(Tips.MESSAGE_NOT.msg);
        typeRepository.delete(id);
        return success();
    }

    @Authorization
    public Result modifyAddress(String userId, String address,User user) {
        user.setAddress(address);
        userRepository.save(user);
        return success();
    }

    public Result addCertificate(String userId, String certificate, String certificateImage) {
        User user=userRepository.findOne(userId);
        if (user==null)return fail(Tips.USER_NOT.msg);
        return success(certificateRepository.save(Certificate.builder().userId(userId).firstName(user.getFirstName()).lastName(user.getLastName()).profileType(user.getProfileType()).userImg(user.getUserImg()).certificate(certificate).pass(0).certificateImage(certificateImage).createDate(LocalDate.now()).build()));
    }

    public Result deleteCertificate(String id) {
        Certificate certificate=certificateRepository.findOne(id);
        if (certificate==null)return fail(Tips.MESSAGE_NOT.msg);
        certificateRepository.delete(id);
        return success();
    }

    public Result addBests(String userId, String best) {
        return success(bestsRepository.save(Bests.builder().best(best).userId(userId).build()));
    }

    public Result deleteBests(String id) {
        Bests bests=bestsRepository.findOne(id);
        if (bests==null)return fail(Tips.MESSAGE_NOT.msg);
        bestsRepository.delete(id);
        return success();
    }


    public Result addAlbum(String userId, String title,String cover, String description, String location, String tags, String imgs) {
        Album a = albumRepository.save(Album
                .builder()
                .userId(userId)
                .title(title)
                .cover(cover)
                .description(description)
                .location(location)
                .tags(tags)
                .createTime(LocalDateTime.now())
                .build());
        photoRepository.save(StringUtils.stringToArrayList(imgs).stream().map(i->Photo.builder().albumId(a.getId()).img(i).build()).collect(Collectors.toList()));
        return success(a);
    }

    public Result modifyAlbum(String id, String title, String cover, String description, String location, String tags, String imgs) {
        Album a=albumRepository.findOne(id);
        if (a==null)return fail(Tips.ALBUM_NOT.msg);
        if (StringUtils.isNotNull(title))a.setTitle(title);
        if (StringUtils.isNotNull(cover))a.setCover(cover);
        if (StringUtils.isNotNull(description))a.setDescription(description);
        if (StringUtils.isNotNull(location))a.setLocation(location);
        if (StringUtils.isNotNull(tags))a.setTags(tags);
        if (StringUtils.isNotNull(imgs))addPhotos(id,imgs);
        return success(albumRepository.save(a));
    }


    public Result getAlbum(String userId) {
        return success(
                albumRepository.findByUserId(userId)
                        .map(i -> {
                            i.setCount(photoRepository.findByAlbumId(i.getId()).size());
                            return i;
                        })
                        .collect(Collectors.toList()));

    }

    public Result deleteAlbum(String id) {
        if (albumRepository.findOne(id)==null)return fail(Tips.ALBUM_NOT.msg);
        albumRepository.delete(id);
        return success();
    }

    public Result addPhotos(String id, String imgs) {
        if (albumRepository.findOne(id)==null)return fail(Tips.ALBUM_NOT.msg);
        photoRepository.save(StringUtils.stringToArrayList(imgs).stream().map(i->Photo.builder().albumId(id).img(i).build()).collect(Collectors.toList()));
        return success();
    }


    public Result deletePhotos(String ids) {
        StringUtils.stringToArrayList(ids).stream().forEach(i->{
            if (photoRepository.findOne(i)!=null)photoRepository.delete(i);
        });
        return success();
    }


    public Result setPrivacy(String userId, Integer allNotifications, Integer email, Integer wall, Integer connections) {

        List<Privacy> privacies=privacyRepository.findByUserId(userId);
        Privacy p=null;
        if (privacies.size()==0){
            p=new Privacy();
            p.setUserId(userId);
            p.setAllNotifications(1);
            if (allNotifications!=null)p.setAllNotifications(allNotifications);
            p.setWall(1);
            if (wall!=null)p.setWall(wall);
            p.setEmail(1);
            if (email!=null)p.setEmail(email);
            p.setConnections(1);
            if (connections!=null)p.setConnections(connections);
        }else{
            p=privacies.get(0);
            if (allNotifications!=null)p.setAllNotifications(allNotifications);
            if (wall!=null)p.setWall(wall);
            if (email!=null)p.setEmail(email);
            if (connections!=null)p.setConnections(connections);
        }
        return success(privacyRepository.save(p));
    }

    public Result getPrivacy(String userId) {
        List<Privacy> privacies=privacyRepository.findByUserId(userId);
        if (privacies.size()>0)return success(privacies.get(0));
        return success(Privacy.builder().allNotifications(1).wall(1).email(1).connections(1).email(1).build());

    }


    public Result deleteFriends(String userId, String ids) {
        StringUtils.stringToArrayList(ids).stream()
                .forEach(i->{
                    Friends f=friendsRepository.findOne(i);
                    if (f!=null){
                        friendsRepository.delete(f);
                        List<Friends> f2=friendsRepository.findByUserIdAndFriendId(f.getFriendId(),userId);
                        f2.stream().forEach(k->{
                            Friends f3=friendsRepository.findOne(k.getId());
                            if (f3!=null)friendsRepository.delete(f3);
                        });
                    }

                });
        return success();
    }

    public Result addAccount(String userId, String userEmailOrUserPhone, String userPassword) {

        //判断是邮箱登录还是手机登录
        List<User> users = new ArrayList<>();
        if (RegexUtils.checkEmail(userEmailOrUserPhone)) users = userRepository.findByUserEmail(userEmailOrUserPhone);
        if (RegexUtils.checkPhone(userEmailOrUserPhone)) users = userRepository.findByUserPhone(userEmailOrUserPhone);
        if (users.size() <= 0) return fail(Tips.USER_NOT.msg);
        User u = users.get(0);
        if (!PasswordEncoderUtils.decode(userPassword, u.getUserPassword()))
            return fail(Tips.USER_PASSWORD_FALSE.msg);
        if (u.getAccountStatus() != 0)
            return fail(Tips.ACCOUNT_FALSE.msg);
        //验证是否已经添加此账号
        List<Account> accounts = accountRepository.findByXUserIdAndUserId(userId,u.getUserId());
        if(accounts.size() > 0){
            return fail(Tips.USER_IN_ACCOUNT.msg);
        }
        return success(
                accountRepository.save(Account.builder()
                        .xUserId(userId)
                        .userId(u.getUserId())
                        .userImage(u.getUserImg())
                        .userPhone(u.getUserPhone())
                        .userEmail(u.getUserEmail())
                        .firstName(u.getFirstName())
                        .lastName(u.getLastName())
                        .build()));
    }

    public Result getAccount(String userId) {
        return success(accountRepository.findByXUserId(userId));
    }

    public Result accountSwitch(String id) {
        Account a=accountRepository.findOne(id);
        if (a==null)return fail(Tips.MESSAGE_NOT.msg);
        User user=userRepository.findOne(a.getUserId());
        if (user==null)return fail(Tips.MESSAGE_NOT.msg);
        user.setUserToken(JWTTokenUtils.createToken(user.getUserId()));
        return success(user);
    }

    public Result deleteAccount(String ids) {
        StringUtils.stringToArrayList(ids).stream()
                .forEach(i->{
                    Account a=accountRepository.findOne(i);
                    if (a!=null){
                        accountRepository.delete(a);
                    }
                });
        return success();
    }
    @Authorization
    public Result share(String userId,Integer type,String shareTitle, String id,User user) {
        Article article=articleRepository.findOne(id);if (article==null)return fail(Tips.MESSAGE_NOT.msg);
        List<Article> articles=Arrays.asList(article);
        //        计算
        homeService.article(userId,articles);
        article=articles.get(0);
        articleUtils._1(article,3,userId);
        Article a=Article.builder()
                .userId(userId)
                .userImg(user.getUserImg())
                .firstName(user.getFirstName())
                .isToShare(1)
                .toUserId(article.getUserId())
                .toFirstName(article.getToFirstName())
                .toUserImg(article.getUserImg())
                .type(article.getType())
                .title(article.getTitle())
                .content(article.getContent())
                .images(article.getImages())
                .videos(article.getVideos())
                .visibility(type)
                .shareTitle(shareTitle)
                .views(0)
                .share(0)
                .like(0)
                .address(article.getAddress())
                .validity(article.getValidity())
                .isPoll(0)
                .isLike(0)
                .isView(0)
                .createTime(article.getCreateTime())
                .shareArticleId(article.getId())
                .build();
        Article x=articleRepository.save(a);

        List<PollItem> pollItems=new ArrayList<>();
        article.getPollItems().stream()
                .forEach(i->{
                    pollItems.add(
                            PollItem.builder()
                                    .articleId(x.getId())
                                    .content(i.getContent())
                                    .image(i.getImage())
                                    .image(i.getImage())
                                    .total(i.getTotal())
                                    .count(i.getCount())
                                    .validity(LocalDateTime.now())
                                    .build());
                });

        if (pollItems.size()>0)pollItemRepository.save(pollItems);

        //发送消息
        messageService.share(user,article.getUserId(),article.getId());

        return success();
    }

    public Result  myShare(String userId, Integer page, Integer pageSize) {
        if (page>0)page--;
        Pageable pageable=new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createTime"));
        List<Article>articles=articleRepository.findByUserIdAndIsToShare(userId,1,pageable);
        //去掉用户隐藏的文章
        articles=articleUtils.xHide(userId,articles);
        //计算
        homeService.article(userId,articles);
        return success(articles);
    }

    public Result addSkill(String userId, String content) {
        return success(skillRepository.save(Skill.builder().userId(userId).content(content).build()));

    }

    public Result deleteSkill(String userId, String id) {
        if (skillRepository.findOne(id)==null)return success();
        skillRepository.delete(id);
        return success();
    }

    public Result addPlaying(String userId, String organizationName, String sports, String position, String location, String streetAddress, String timePeriod, String description) {
        return success(playingRepository.save(
                Playing.builder()
                        .userId(userId)
                        .organizationName(organizationName)
                        .sports(sports)
                        .position(position)
                        .location(location)
                        .streetAddress(streetAddress)
                        .timePeriod(timePeriod)
                        .description(description)
                        .createTime(LocalDateTime.now())
                        .build()));
    }

    public Object modifyPlaying(String userId, String id, String organizationName, String sports, String position, String location, String streetAddress, String timePeriod, String description) {
        Playing p=playingRepository.findOne(id);
        if (p==null)return success();
        if (StringUtils.isNotNull(organizationName))p.setOrganizationName(organizationName);
        if (StringUtils.isNotNull(sports))p.setSports(sports);
        if (StringUtils.isNotNull(position))p.setPosition(position);
        if (StringUtils.isNotNull(location))p.setLocation(location);
        if (StringUtils.isNotNull(streetAddress))p.setStreetAddress(streetAddress);
        if (StringUtils.isNotNull(timePeriod))p.setTimePeriod(timePeriod);
        if (StringUtils.isNotNull(description))p.setDescription(description);
        return success(playingRepository.save(p));

    }


    public Result deletePlaying(String id) {
        if (playingRepository.findOne(id)==null)return success();
        playingRepository.delete(id);
        return success();
    }




    public Result addEducation(String userId, String school, String degree, String datesAttended, String description) {
        return success(educationRepository.save(Education.builder()
                .userId(userId)
                .school(school)
                .degree(degree)
                .datesAttended(datesAttended)
                .description(description)
                .build()));


    }

    public Result addWork(String userId, String name, String degree, String datesAttended, String description) {
        return success(workRepository.save(
                Work.builder()
                        .userId(userId)
                        .name(name)
                        .degree(degree)
                        .datesAttended(datesAttended)
                        .description(description)
                        .build()
        ));

    }

    public Result deleteWork(String id) {
        if (workRepository.findOne(id)!=null)return success();
        workRepository.delete(id);
        return success();
    }
    @Authorization
    public Result modifyWH(String userId, String w, String h,User user) {

        if (StringUtils.isNotNull(w))user.setWeight(w);
        if (StringUtils.isNotNull(h))user.setHeight(h);
        return success(userRepository.save(user));

    }
    @Authorization
    public Result addConecrnedType(String userId, String name, User user) {

        return success(userConcernedTypeRepository.save(UserConcernedType.builder().userId(userId).concerned(name).build()));

    }

    public Result deleteConecrnedType(String id) {
        UserConcernedType type=userConcernedTypeRepository.findOne(id);
        if (type==null)return success();
        userConcernedTypeRepository.delete(type);
        return success();
    }
    @Authorization
    public Result addCommunities(String userId, String communitiesTitle, String communitiesImg, String communitiesContent,User user) {
        UserCommunities uc=UserCommunities.builder()
                .userId(userId)
                .communitiesTitle(communitiesTitle)
                .build();
        if (StringUtils.isNotNull(communitiesImg))uc.setCommunitiesImg(communitiesImg);
        if (StringUtils.isNotNull(communitiesContent))uc.setCommunitiesImg(communitiesContent);
        //保存社区数据至用户
        return success(userCommunitiesRepository.save(uc));
    }

    public Result deleteCommunities(String id) {

        if (userCommunitiesRepository.findOne(id)==null)return success();
        userCommunitiesRepository.delete(id);
        return success();
    }

    public Result modifyCommunities(String id, String communitiesTitle, String communitiesImg, String communitiesContent) {
        UserCommunities uc=userCommunitiesRepository.findOne(id);
        if (uc==null)return fail(Tips.MESSAGE_NOT.msg);
        if (StringUtils.isNotNull(communitiesTitle))uc.setCommunitiesTitle(communitiesTitle);
        if (StringUtils.isNotNull(communitiesImg))uc.setCommunitiesImg(communitiesImg);
        if (StringUtils.isNotNull(communitiesContent))uc.setCommunitiesContent(communitiesContent);
        return success(userCommunitiesRepository.save(uc));
    }

    public Result albumDetails(String id,Integer page,Integer pageSize) {
        if (page>0)page--;
        Album album=albumRepository.findOne(id);
        if (album==null)return fail(Tips.ALBUM_NOT.msg);
        Page<Photo>photos=photoRepository.findByAlbumId(id,new PageRequest(page,pageSize));
        album.setPhotos(photos);
        album.setCount(Integer.parseInt(String.valueOf(photos.getTotalElements())));
        return success(album);

    }
    public Result getArticle(String userId, Integer type, Integer page, Integer pageSize) {
        if (type<0||type>2)return fail(Tips.PARAMETER.msg);
        if (type==0) return homeService.my(userId,page,pageSize);
        if (type==1) return homeService.myA(userId,page,pageSize);
        if (type==2) return homeService.index(userId,page,pageSize);
        return success();
    }
    @Authorization
    public Result likeSKill(String userId, String id, User user) {
        Skill skill=skillRepository.findOne(id);
        if (skill==null)return fail(Tips.NOT_MESSAGE.msg);
        skill.setUserName(user.getFirstName());
        skill.setUserImage(user.getUserImg());
        return success(skillRepository.save(skill));
    }


}
