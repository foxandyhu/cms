package com.bfly.cms.message.service;

import com.bfly.cms.message.entity.Comment;
import com.bfly.core.base.service.IBaseService;
import com.bfly.core.enums.CommentStatus;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

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

    /**
     * 统计当天评论总数和总评论数
     * @return Map
     * @author andy_hulibo@163.com
     * @date 2019/8/14 19:47
     */
    Map<String, BigInteger> getTodayAndTotalComment();

    /**
     * 获得最新的前几条评论
     *
     * @param limit 返回最大条数
     * @return Map
     * @author andy_hulibo@163.com
     * @date 2019/8/15 12:21
     */
    List<Map<String, Object>> getLatestComment(int limit);
}
