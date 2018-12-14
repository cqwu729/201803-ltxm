package com.snow.ly.blog.common.util;

import com.snow.ly.blog.common.bean.UserBean;
import com.snow.ly.blog.common.pojo.*;
import com.snow.ly.blog.common.repository.*;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;



@Component
public class ArticleUtils {


    @Autowired
    private ArticlePollVoteUserRepository articlePollVoteUserRepository;
    @Autowired
    private ArticleIsLikeUserRepository articleIsLikeUserRepository;
    @Autowired
    private ArticleIsViewUserRepository articleIsViewUserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PollItemRepository pollItemRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleCommentRepository articleCommentRepository;
    @Autowired
    private ArticleCommentReplyRepository articleCommentReplyRepository;
    @Autowired
    private HideRepository hideRepository;

    //去掉隐藏的文章
    public List<Article> xHide(String userId,List<Article> list){
        List<String> ids=getHide(userId);
        return list.stream()
                .filter(i->!ids.contains(i.getId()))
                .collect(Collectors.toList());
    }

    //取消隐藏
    public void cHide(String id){
        if (hideRepository.findOne(id)==null)return;
        hideRepository.delete(id);
    }

    //隐藏文章
    public void addHide(String userId,String articleId){
        if (articleRepository.findOne(articleId)==null)return;
        hideRepository.save(
                Hide.builder()
                        .userId(userId)
                        .articleId(articleId)
                        .build());
    }
    //获取用户隐藏文章id
    public List<String> getHide(String userId){
       return hideRepository.findByUserId(userId)
                .map(i->i.getArticleId())
                .distinct()
                .collect(Collectors.toList());
    }
    //获取文章评论
    public void getCommentAndReply(Article a){
        a.setCommentAndReply(articleCommentRepository.findByArticleId(a.getId()).stream()
                .map(i->{
                    i.setCommentReplies(articleCommentReplyRepository.findByCommentId(i.getId()));
                    return i;
                }).collect(Collectors.toList()));
    }

    public List<ArticleComment> getCommentAndReply(String id,Integer page,Integer pageSize){
        if (page>0)page--;
        Page<ArticleComment> a=articleCommentRepository.findByArticleId(id,new PageRequest(page,pageSize));
        return a.getContent().stream()
                .map(i->{
                    i.setCommentReplies(articleCommentReplyRepository.findByCommentId(i.getId()));
                    i.setTotal(a.getTotalElements());
                    i.setTotalPage(a.getTotalPages());
                    return i;
                }).collect(Collectors.toList());
    }


    //取消点赞
    public void _1_2(Article article,String userId){
        if (!checkIsLike(userId,article))return;
        Integer likeCount=article.getLike();
        if (likeCount<=0)return;
        likeCount--;
        article.setLike(likeCount);
        articleRepository.save(article);
        articleIsLikeUserRepository.removeByArticleIdAndUserId(article.getId(),userId);
    }

    //文章数据加1
    public void _1(Article article,Integer type,String userId){
//        type =1:view 2: like 3：share
        switch (type){
            case 1:
                article.setViews(article.getViews()+1);
                articleIsViewUserRepository.save(ArticleIsViewUser.builder().articleId(article.getId()).userId(userId).build());
                break;
            case 2:
                article.setViews(article.getViews()+1);
                article.setLike(article.getLike()+1);
                articleIsViewUserRepository.save(ArticleIsViewUser.builder().articleId(article.getId()).userId(userId).build());
                articleIsLikeUserRepository.save(ArticleIsLikeUser.builder().articleId(article.getId()).userId(userId).build());
                break;
            case 3:
                article.setViews(article.getViews()+1);
                article.setShare(article.getShare()+1);
                default:
                    break;
        }
        articleRepository.save(article);
    }

    //计算投票
    public List<PollItem> calculatePoll(String articleId){
        Integer total=0;
        List<PollItem> list=pollItemRepository.findByArticleId(articleId);
        for (PollItem i:list) total = total + i.getCount();
        Double finalTotal = Double.valueOf(total);
       return list.stream()
                .map(i->{
                    if (finalTotal>0)
                    i.setPercentage(Double.valueOf(i.getCount()/finalTotal));
                    i.setTotal(finalTotal);
                    return i;
                })
                .collect(Collectors.toList());
    }

    //获取article点赞用户
    public List<UserBean> getArticleIsLike(String articleId){
     return  articleIsLikeUserRepository.findByArticleId(articleId)
                .map(i->{
                    User user=userRepository.findOne(i.getUserId());
                    if (user==null)return null;
                    return   UserBean.builder()
                            .userId(i.getUserId())
                            .firstName(user.getFirstName())
                            .userGender(user.getUserGender())
                            .userImg(user.getUserImg())
                            .userSignature(user.getUserSignature())
                            .profileType(user.getProfileType())
                            .build();
                })
                .filter(i->i!=null)
                .collect(Collectors.toList());
    }



    //判断用户是否阅读
    public boolean checkIsView(String userId,Article article){
        if (articleIsViewUserRepository.findByArticleIdAndUserId(article.getId(),userId).size()>0)return true;
        return false;
    }

    //判断用户是否点赞
    public boolean checkIsLike(String userId,Article article){
        if (articleIsLikeUserRepository.findByArticleIdAndUserId(article.getId(),userId).size()>0)return true;
        return false;
    }

    //判断用户是否投票
    public boolean checkIsPoll(String userId,Article article){
        if (articlePollVoteUserRepository.findByArticleIdAndUserId(article.getId(),userId).size()>0)return true;
        return false;
    }



}
