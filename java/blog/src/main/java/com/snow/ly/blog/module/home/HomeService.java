package com.snow.ly.blog.module.home;

import com.alibaba.fastjson.JSON;
import com.snow.ly.blog.common.bean.*;
import com.snow.ly.blog.common.pojo.*;
import com.snow.ly.blog.common.repository.*;
import com.snow.ly.blog.common.util.*;
import com.snow.ly.blog.config.annotation.Authorization;
import com.snow.ly.blog.module.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


import static com.snow.ly.blog.common.bean.Result.fail;
import static com.snow.ly.blog.common.bean.Result.success;


@Service
public class HomeService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private PollItemRepository pollItemRepository;
    @Autowired
    private ArticlePollVoteUserRepository articlePollVoteUserRepository;
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private ArticleUtils articleUtils;
    @Autowired
    private ArticleCommentRepository articleCommentRepository;
    @Autowired
    private ArticleCommentReplyRepository articleCommentReplyRepository;
    @Autowired
    private NotSeePeopleRepository notSeePeopleRepository;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private AdvertRepository advertRepository;
    @Autowired
    private AboutRepository aboutRepository;
    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UsRepository usRepository;
    @Autowired
    private ProtocolRepository protocolRepository;

    public Result commentReply(String userId, String commentId, String replyUserId, String content, String image) {
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        User reply=userRepository.findOne(replyUserId);if (reply == null) return fail(Tips.USER_NOT.msg);
        ArticleComment comment=articleCommentRepository.findOne(commentId);if (comment==null)return fail(Tips.COMMENT_NOT.msg);
        if (StringUtils.isNull(content)&&StringUtils.isNull(image))return fail(Tips.PARAMETER.msg);
        ArticleCommentReply commentReply=ArticleCommentReply
                .builder()
                .commentId(commentId)
                .userId(userId)
                .username(user.getFirstName())
                .userImage(user.getUserImg())
                .replyUserId(replyUserId)
                .replyUsername(reply.getFirstName())
                .replyUserImage(reply.getUserImg())
                .createTime(LocalDateTime.now())
                .build();
        if (StringUtils.isNotNull(content))commentReply.setContent(content);
        if (StringUtils.isNotNull(image))commentReply.setImage(image);
        return success(articleCommentReplyRepository.save(commentReply));
    }

    public Result articleComment(String userId, String articleId,  String content, String image) {
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        Article article=articleRepository.findOne(articleId);if (article==null)return fail(Tips.MESSAGE_NOT.msg);
        if (StringUtils.isNull(content)&&StringUtils.isNull(image))return fail(Tips.PARAMETER.msg);
        ArticleComment comment=ArticleComment
                .builder()
                .articleId(articleId)
                .userId(userId)
                .username(user.getFirstName()+user.getLastName())
                .userImage(user.getUserImg())
                .createTime(LocalDateTime.now())
                .build();
        if (StringUtils.isNotNull(content))comment.setContent(content);
        if (StringUtils.isNotNull(image))comment.setImage(image);
        articleUtils._1(article,1,userId);
        //发送消息
        messageService.comment(user,article.getUserId(),article.getId());
        return success(articleCommentRepository.save(comment));
    }

    public Result unlike(String userId, String articleId) {
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        Article article=articleRepository.findOne(articleId);if (article==null)return fail(Tips.MESSAGE_NOT.msg);
        articleUtils._1_2(article,userId);
        return success();
    }

    public Result like(String userId, String articleId) {
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        Article article=articleRepository.findOne(articleId);if (article==null)return fail(Tips.MESSAGE_NOT.msg);
        if (articleUtils.checkIsLike(userId,article))return success();
        articleUtils._1(article,2,userId);
        //发送消息
        if(!userId.equals(article.getUserId())){
            messageService.like(user,article.getUserId(),article.getId());
        }
        return success();
    }

    public Result getArticleDetail(String userId,String articleId){
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        Article article=articleRepository.findOne(articleId);if (article==null)return fail(Tips.MESSAGE_NOT.msg);
        List<Article>articles=Arrays.asList(article);
//        计算
        article(userId,articles);
        Article x=articles.get(0);
        articleUtils._1(x,1,userId);
        return success(articles);
    }


    public Result getActivity(String userId, Integer page, Integer pageSize) {
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        if (page>0)page--;
        Pageable pageable=new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createTime"));
        List<Article> articles=articleRepository.findByTypeInAndUserIdIn(Arrays.asList(3,4),getUserIds(userId),pageable);
        //去掉用户隐藏的文章
        articles=articleUtils.xHide(userId,articles);
        //计算
        article(userId,articles);
        return success(articles);
    }

    public Result getPoll(String userId,Integer type,Integer page,Integer pageSize){
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        if (page>0)page--;
        Pageable pageable=new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createTime"));
        List<Article>articlePage=switchPoll(userId,type,pageable);
        //去掉用户隐藏的文章
        articlePage=articleUtils.xHide(userId,articlePage);
        List<Article>articles=new ArrayList<>();
        //可见性判断
        checkValidity(userId,articlePage,articles);
        //计算
        article(userId,articles);
       return success(articles);
    }

    public Result getVideos(String userId, Integer type, Integer page, Integer pageSize) {
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        if (page>0)page--;
        Pageable pageable=new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createTime"));
        List<Article> articles=switchVideo(userId,type,pageable);
        //去掉用户隐藏的文章
        articles=articleUtils.xHide(userId,articles);
        //计算
        article(userId,articles);
        return success(articles);
    }
    //获取视频分类
    public List<Article> switchVideo(String userId,Integer type,Pageable pageable){
        List<Article> articles=new ArrayList<>();
        switch (type){
            case 0://所有
                articles=articleRepository.findByTypeAndUserIdIn(2,getUserIds(userId),pageable);
                break;
            case 1://我发布的
                articles=articleRepository.findByUserIdAndType(userId,2,pageable);
                break;
            default:
                articles=articleRepository.findByTypeAndUserIdIn(2,getUserIds(userId),pageable);
                break;
        }
     return articles;
    }



    //计算
    public void article(String userId,List<Article> articles){
        articles.stream().forEach(i->{
            if (articleUtils.checkIsLike(userId,i)){
                i.setIsLike(1);
            }else {
                i.setIsLike(0);
            }
            if (articleUtils.checkIsView(userId,i))i.setIsView(1);
            //点赞用户
            i.setIsLikeUsers(articleUtils.getArticleIsLike(i.getId()));
            //获取文章评论
            articleUtils.getCommentAndReply(i);
            if (i.getType()==1){//投票
                //判断是否投票
                if (articleUtils.checkIsPoll(userId,i))i.setIsPoll(1);
                //获取投票项
                i.setPollItems(articleUtils.calculatePoll(i.getId()));
            }

            //判断是否关注
            if (userUtils.isFollowed(userId,i.getUserId())){
              i.setIsFollowed(1);
            }else{
                i.setIsFollowed(0);
            }

        });
    }

    //获取投票分类
    public List<Article> switchPoll(String userId,Integer type,Pageable pageable){
        List<Article> articles=new ArrayList<>();
        switch (type){
            case 0://所有
                 articles=articleRepository.findByTypeAndUserIdIn(1,getUserIds(userId),pageable);
                break;
            case 1://我发布的
                articles=articleRepository.findByUserIdAndType(userId,1,pageable);
                break;
            case 2://我参与的
                articles=articleRepository.findByTypeAndIdIn(1, articlePollVoteUserRepository.findByUserId(userId).stream().map(i->i.getArticleId()).collect(Collectors.toList()), pageable);
                break;
            case 3://过期的
                articles=articleRepository.findByTypeAndValidityBeforeAndUserIdIn(1,LocalDateTime.now(),getUserIds(userId),pageable);
                break;
            case 4://未过期的
                articles=articleRepository.findByTypeAndValidityAfterAndUserIdIn(1,LocalDateTime.now(),getUserIds(userId),pageable);
                break;
            default: //所有
                articles=articleRepository.findByTypeAndUserIdIn(1,getUserIds(userId),pageable);
                break;
        }
      return articles;
    }



    //可见性判断
    public void  checkValidity(String userId,List<Article> list,List<Article> articles){
        list.stream().forEach(i->{

            switch (i.getVisibility()){
                case 0://自己
                    if (userId.equals(i.getUserId()))articles.add(i);
                    break;
                case 1://好友
                    if (userId.equals(i.getUserId())) articles.add(i);
                    else if (checkFriend(i.getUserId(),userId))articles.add(i);
                    break;
                 default://所有
                      articles.add(i);
                     break;
            }
        });
    }

    //判断是不是好友
    public boolean checkFriend(String userId,String friendId){
        if (friendsRepository.findByUserIdAndFriendId(userId,friendId).size()>0)return true;
        return false;
    }

    //获取相关用户id
    public List<String> getUserIds(String userId){
        return userUtils.distinctUserIds(userUtils.getFollowed(userId),userUtils.getFriends(userId),Arrays.asList(userId));
    }




    public Result vote(String userId,String pollId,String pollItemId){
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        Article article=articleRepository.findByIdAndType(pollId,1);
        if (article==null)return fail(Tips.POLL_NOT.msg);
        if (checkVoteTime(article.getValidity()))return fail(Tips.POLL_OVERDUE.msg);
        if (checkVote(userId,pollId))return fail(Tips.VOTE_HAD.msg);
        PollItem item=pollItemRepository.findOne(pollItemId);
        if (item==null)return fail(Tips.POLL_ITEM_NOT.msg);
        item.setCount(item.getCount()+1);
        pollItemRepository.save(item);
        articlePollVoteUserRepository.save(ArticlePollVoteUser.builder().articleId(pollId).userId(userId).build());
        return success();
    }

    //判断是否过期
    public boolean checkVoteTime(LocalDateTime time){
        if (LocalDateTime.now().isAfter(time))return true;
        return false;
    }


    //判断用户是否投票
    public boolean checkVote(String userId,String pollId){
        if (articlePollVoteUserRepository.findByArticleIdAndUserId(pollId,userId).size()>0)return true;
        return false;
    }


    public Result poll(String userId,String title,String image,String items,Integer validity,Integer visibility,String address){
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        Article a=Article.builder().userId(userId).userImg(user.getUserImg()).firstName(user.getFirstName()).lastName(user.getLastName()).type(1).title(title).createTime(LocalDateTime.now()).build();
        if (StringUtils.isNotNull(address))a.setAddress(address);
        if (StringUtils.isNotNull(image))a.setImage(image);
        if (visibility!=null&&visibility>=0&&visibility<=2){
            a.setVisibility(visibility);
        }else{
            a.setVisibility(2);
        }
        //设置有效期
        setValidity(a,validity);
        a=articleRepository.save(a);
        //设置投票选项
        setPollItem(a,items);
        return success(a);
    }

    public void setPollItem(Article a,String items){
        pollItemRepository.save(JSON.parseArray(items,Item.class).stream()
                .map(item -> PollItem.builder().articleId(a.getId()).content(item.getContent()).image(item.getImage()).validity(a.getValidity()).build())
                .collect(Collectors.toList()));

    }

    public void setValidity(Article a,Integer validity){
        if (a==null||validity==null)return;
        a.setValidity( LocalDateTime.now().plusDays(validity));
    }

    public Result video(String userId,String content,String image,String video,Integer visibility,String address){
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        Article a=Article.builder().userId(userId).userImg(user.getUserImg()).firstName(user.getFirstName()).lastName(user.getLastName()).type(2).content(content).images(Arrays.asList(image)).videos(Arrays.asList(video)).createTime(LocalDateTime.now()).build();
        if (visibility!=null&&visibility>=0&&visibility<=2)a.setVisibility(visibility);
        if (StringUtils.isNotNull(address))a.setAddress(address);
        return success(articleRepository.save(a));
    }

    public Result post(String userId,String content,String images,Integer visibility,String address){
        User user=userRepository.findOne(userId);if (user==null)return fail(Tips.USER_NOT.msg);
        Article a=Article.builder().userId(userId).userImg(user.getUserImg()).firstName(user.getFirstName()).lastName(user.getLastName()).content(content).images(StringUtils.stringToArrayList(images)).createTime(LocalDateTime.now()).build();
        if (StringUtils.isPost(content))a.setType(3);
        else a.setType(0);
        if (visibility!=null&&visibility>=0&&visibility<=2)a.setVisibility(visibility);
        if (StringUtils.isNotNull(address))a.setAddress(address);
        return success(articleRepository.save(a));
    }





    @Authorization
    public Result getPeople(String userId, String existUserId, Integer page, Integer pageSize,User user) {
        if (page>=1)page--;
        List<String> existUserList = new ArrayList<>();
        existUserList.add(userId);
        if (existUserId != null && !"".equals(existUserId)){
            String[] str = existUserId.split(",");
            for (String s : str){
                existUserList.add(s);
            }
        }

        List<User> list = userRepository.findByUserIdNotIn(existUserList, new PageRequest(page, pageSize));

        //List<User> list = userRepository.findByProfileTypeAndUserIdNotIn(
        //        Double.valueOf(Math.random() * 5).intValue(),
        //        userUtils.distinctUserIds(userUtils.getFriends(userId), userUtils.getFollowed(userId), userUtils.notSeePeopleList(userId), Arrays.asList(userId)),
        //        new PageRequest(page, pageSize));

        return success(list
                .stream()
                .map(i->
                        UserBean.builder()
                                .userId(i.getUserId())
                                .userImg(i.getUserImg())
                                .userGender(i.getUserGender())
                                .firstName(i.getFirstName())
                                .lastName(i.getLastName())
                                .profileType(i.getProfileType())
                                .userSignature(i.getUserSignature())
                                .build())
                .collect(Collectors.toList()));

    }


    public Result index(String userId, Integer page, Integer pageSize) {
        if (page>=1)page--;
        Pageable pageable=new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createTime"));
        //获取系统的
//        List<Article> list=articleRepository.findByUserIdIn(getUserIds(userId),pageable);
        List<Article> list=articleRepository.findByUserIdInOrVisibility(getUserIds(userId),2,pageable);
        //去掉用户隐藏的文章
        list=articleUtils.xHide(userId,list);
        List<Article> articles=new ArrayList<>();
        //可见性判断
        checkValidity(userId,list,articles);
        //计算
        article(userId,articles);
        return success(articles);
    }

    /**获取自己的*/
    public Result my(String userId,Integer page,Integer pageSize){
        if (page>=1)page--;
        Pageable pageable=new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createTime"));
        List<Article> list=articleRepository.findByUserId(userId,pageable);
        //去掉用户隐藏的文章
        list=articleUtils.xHide(userId,list);
        List<Article> articles=new ArrayList<>();
        //可见性判断
        checkValidity(userId,list,articles);
        //计算
        article(userId,articles);
        return success(articles);

    }

    /**关注的和好友的*/
    public Result myA(String userId,Integer page,Integer pageSize){
        if (page>=1)page--;
        Pageable pageable=new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createTime"));
        List<Article> list=articleRepository.findByUserIdIn(userUtils.distinctUserIds(userUtils.getFollowed(userId),userUtils.getFriends(userId)),pageable);
        //去掉用户隐藏的文章
        list=articleUtils.xHide(userId,list);
        List<Article> articles=new ArrayList<>();
        //可见性判断
        checkValidity(userId,list,articles);
        //计算
        article(userId,articles);
        return success(articles);
    }




    public Result search(String userId,String keyword,Integer page,Integer pageSize) {
        if (page>0)page--;
//        Pattern pattern = Pattern.compile("^.*"+keyword+".*$", Pattern.CASE_INSENSITIVE);
//        List<User> users=mongoTemplate.find(new Query(Criteria.where("userId").in(userUtils.getFriends(userId)).orOperator(Criteria.where("firstName").regex(pattern),Criteria.where("lastName").regex(pattern),Criteria.where("userEmail").regex(pattern),Criteria.where("userPhone").regex(pattern))),User.class);
//        Pattern pattern = Pattern.compile("^.*"+keyword, Pattern.CASE_INSENSITIVE);
//        List<User> users=mongoTemplate.find(new Query(Criteria.where("userId").in(userUtils.getFriends(userId)).orOperator(Criteria.where("firstName").regex(pattern))),User.class);
//        PagingUtils<User> p=new PagingUtils<>();
//        return success(
//                new PageImpl<UserBean>(
//                        p.page(users,page,pageSize)
//                                .stream()
//                                .map(i->UserBean.builder()
//                                        .userId(i.getUserId())
//                                        .userImg(i.getUserImg())
//                                        .userGender(i.getUserGender())
//                                        .firstName(i.getFirstName())
//                                        .profileType(i.getProfileType())
//                                        .userSignature(i.getUserSignature())
//                                        .build())
//                                .collect(Collectors.toList()),new PageRequest(page,pageSize),users.size()));

        List<User> users=userRepository.findByFirstNameLike(keyword,new PageRequest(page,pageSize));
        PagingUtils<User> p=new PagingUtils<>();
        return success(
                new PageImpl<SearchUser>(
                        p.page(users,page,pageSize)
                                .stream()
                                .map(i->{
                                    SearchUser su=SearchUser.builder()
                                            .userId(i.getUserId())
                                            .userImg(i.getUserImg())
                                            .userGender(i.getUserGender())
                                            .firstName(i.getFirstName())
                                            .lastName(i.getLastName())
                                            .profileType(i.getProfileType())
                                            .userSignature(i.getUserSignature())
                                            .isFriend(0)
                                            .build();
                                  if (userUtils.isFriend(userId,i.getUserId()))su.setIsFriend(1);
                                    return su;
                                })
                                .collect(Collectors.toList()),new PageRequest(page,pageSize),users.size()));


    }


    public Result notSeePeople(String userId, String peopleId) {
        User people=userRepository.findOne(peopleId);
        if (people==null)return fail(Tips.USER_NOT.msg);
        if (notSeePeopleRepository.findByUserIdAndAndPeopleId(userId,peopleId).size()>0) return success();
        notSeePeopleRepository.save(NotSeePeople.builder().userId(userId).peopleId(peopleId).build());
        return success();



    }

    public Result getComment(String id,Integer page,Integer pageSize) {
        return success(articleUtils.getCommentAndReply(id,page,pageSize));

    }

    public Result invitation(String userId, String email, String message) {
           EmailUtils.invitation(email,message);
           return success();

    }


    public Result getAdverts(Integer page, Integer pageSize) {
        if (page>0)page--;
        LocalDateTime now=LocalDateTime.now();
       return success(advertRepository.findByStartTimeBeforeAndEndTimeAfter(now,now,new PageRequest(page,pageSize)));

    }

    public Result getAbout() {
        List<About> a=aboutRepository.findAll();
        if (a.size()==0)return fail();
        return success(a.get(a.size()-1));
    }

    public Result getPolicy() {
        List<Policy> a=policyRepository.findAll();
        if (a.size()==0)return fail();
        return success(a.get(0));
    }

    public Result addHide(String userId, String id) {
        articleUtils.addHide(userId,id);
        return success();

    }

    public Result searchArticle(String userId, String keyword, Integer page, Integer pageSize) {
        if (page>0)page--;
        Pageable pageable=new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createTime"));
        List<Article> list=articleRepository.findByUserIdInAndTitleLike(getUserIds(userId),keyword,pageable);
        //去掉用户隐藏的文章
        list=articleUtils.xHide(userId,list);
        List<Article> articles=new ArrayList<>();
        //可见性判断
        checkValidity(userId,list,articles);
        //计算
        article(userId,articles);
        return success(articles);

    }

    public Result getSysArticle(Integer type,Integer page, Integer pageSize) {
        if (type<0||type>4)return fail(Tips.PARAMETER.msg);
        if (page>0)page--;
        if (type==4)return success(articleRepository.findByIsSys(1,new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createTime"))));
        if (type!=4)return success(articleRepository.findByIsSysAndType(1,type,new PageRequest(page,pageSize,new Sort(Sort.Direction.DESC,"createTime"))));
        return success();
    }

    public Result getUs() {
        return success(usRepository.findAll());
    }

    public Result getProtocol() {
        return success(protocolRepository.findAll());
    }
}
