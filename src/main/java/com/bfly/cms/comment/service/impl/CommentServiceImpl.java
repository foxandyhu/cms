package com.bfly.cms.comment.service.impl;

import com.bfly.cms.comment.dao.ICommentDao;
import com.bfly.cms.comment.entity.Comment;
import com.bfly.cms.comment.entity.CommentExt;
import com.bfly.cms.comment.service.ICommentService;
import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.cms.user.entity.User;
import com.bfly.cms.user.service.IUserService;
import com.bfly.core.context.ContextUtil;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.context.IpThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/12 11:37
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class CommentServiceImpl extends BaseServiceImpl<Comment, Integer> implements ICommentService {

    @Autowired
    private ICommentDao commentDao;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyComment(int status, int... commentId) {
        Assert.notNull(commentId, "评论信息不存在!");
        for (int id : commentId) {
            Comment comment = get(id);
            Assert.notNull(comment, "评论信息不存在!");
            comment.setStatus(status);
            commentDao.save(comment);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recommendComment(int commentId, boolean recommend) {
        Comment comment = get(commentId);
        Assert.notNull(comment, "评论信息不存在!");
        Assert.isTrue(comment.getStatus() != Comment.WAIT_CHECK, "该评论尚未审核不能推荐!");
        Assert.isTrue(comment.getStatus() != Comment.UNPASSED, "审核不通过的评论不能推荐!");
        comment.setRecommend(recommend);
        commentDao.save(comment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replyComment(int commentId, String content, Member member) {
        Comment comment = get(commentId);
        Assert.notNull(comment, "评论信息不存在!");
        Assert.isTrue(comment.getStatus() != Comment.WAIT_CHECK, "该评论尚未审核不能回复!");
        Assert.isTrue(comment.getStatus() != Comment.UNPASSED, "审核不通过的评论不能回复!");
        comment.setReplyCount(comment.getReplyCount() + 1);

        Comment replyComment = new Comment();
        replyComment.setParent(comment);
        replyComment.setCreateTime(new Date());

        CommentExt replyExt = new CommentExt();
        replyExt.setText(content);
        replyExt.setIp(IpThreadLocal.get());
        replyComment.setCommentExt(replyExt);
        replyComment.setStatus(Comment.WAIT_CHECK);
        comment.setPostMember(memberService.get(member.getId()));
        commentDao.save(comment);
        commentDao.save(replyComment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replyComment(int commentId, String content, User user) {
        Comment comment = get(commentId);
        Assert.notNull(comment, "评论信息不存在!");
        Assert.isTrue(comment.getStatus() != Comment.WAIT_CHECK, "该评论尚未审核不能回复!");
        Assert.isTrue(comment.getStatus() != Comment.UNPASSED, "审核不通过的评论不能回复!");
        comment.setReplyCount(comment.getReplyCount() + 1);

        Comment replyComment = new Comment();
        replyComment.setParent(comment);
        replyComment.setCreateTime(new Date());

        CommentExt replyExt = new CommentExt();
        replyExt.setText(content);
        replyExt.setIp(IpThreadLocal.get());
        replyComment.setCommentExt(replyExt);
        replyComment.setStatus(Comment.PASSED);
        comment.setPostUser(userService.get(user.getId()));
        commentDao.save(comment);
        commentDao.save(replyComment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(int commentId, String content) {
        Comment comment = get(commentId);
        Assert.notNull(comment, "评论信息不存在!");

        comment.getCommentExt().setText(content);
        commentDao.save(comment);
    }
}
