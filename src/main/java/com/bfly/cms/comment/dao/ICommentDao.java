package com.bfly.cms.comment.dao;

import com.bfly.cms.comment.entity.Comment;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/12 11:48
 */
public interface ICommentDao extends IBaseDao<Comment, Integer> {

    /**
     * 修改评论状态
     *
     * @param status    状态
     * @param commentId 留言ID
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/2 20:53
     */
    @Modifying
    @Query("UPDATE Comment set status=:status where id=:commentId")
    int editCommentStatus(@Param("commentId") int commentId, @Param("status") int status);

    /**
     * 修改评论的推荐状态
     *
     * @param commentId 评论ID
     * @param recommend 是否推荐
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/2 20:55
     */
    @Modifying
    @Query("UPDATE Comment set recommend=:recommend where id=:commentId")
    int editCommentRecommend(@Param("commentId") int commentId, @Param("recommend") boolean recommend);
}
