package com.bfly.cms.comment.service;

import com.bfly.cms.comment.entity.Comment;
import com.bfly.cms.member.entity.Member;
import com.bfly.cms.user.entity.User;
import com.bfly.core.base.service.IBaseService;

/**
 * 评论管理业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/12 11:35
 */
public interface ICommentService extends IBaseService<Comment, Integer> {

    /**
     * 审核帖子
     *
     * @param commentId 帖子ID
     * @param status    状态
     * @author andy_hulibo@163.com
     * @date 2018/12/12 11:44
     */
    void verifyComment(int status, int... commentId);

    /**
     * 修改帖子是否推荐
     *
     * @param commentId 帖子ID
     * @param recommend 是否推荐
     * @author andy_hulibo@163.com
     * @date 2018/12/12 11:46
     */
    void recommendComment(int commentId, boolean recommend);

    /**
     * 评论回复
     *
     * @param commentId 评论ID
     * @param content   评论内容
     * @param member 回复者ID
     * @author andy_hulibo@163.com
     * @date 2018/12/12 13:40
     */
    void replyComment(int commentId, String content, Member member);

    /**
     * 评论回复
     *
     * @param commentId 评论ID
     * @param content   评论内容
     * @param user 回复者ID
     * @author andy_hulibo@163.com
     * @date 2018/12/12 13:40
     */
    void replyComment(int commentId, String content, User user);

    /**
     * 修改评论
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:21
     */
    void edit(int commentId, String content);
}
