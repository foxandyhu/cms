package com.bfly.cms.comment.service;

import com.bfly.cms.comment.entity.Comment;
import com.bfly.core.base.service.IBaseService;
import com.bfly.core.enums.CommentStatus;

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
    void verifyComment(CommentStatus status, Integer... commentId);

    /**
     * 修改帖子是否推荐
     *
     * @param commentId 帖子ID
     * @param recommend 是否推荐
     * @author andy_hulibo@163.com
     * @date 2018/12/12 11:46
     */
    void recommendComment(int commentId, boolean recommend);
}
