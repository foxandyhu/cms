package com.bfly.cms.message.dao;

import com.bfly.cms.message.entity.Comment;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

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


    /**
     * 查询评论分页数据
     *
     * @param recommend 是否推荐
     * @param status    状态
     * @param contentId 内容ID
     * @param pageable  分页信息 JPA会自动识别 不需要在sql中写分页语句
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2019/8/4 17:15
     */
    @Query(value = "SELECT cmt.id,cmt.content_id as contentId,cmt.member_user_name as memberUserName,cmt.user_name as userName,cmt.post_date as postDate,cmt.is_recommend as recommend,cmt.`status`,cmt_ext.ip,cmt_ext.area,cmt_ext.text,cnt_ext.title,cnt_type.`name` as typeName FROM `comment` AS cmt LEFT JOIN comment_ext AS cmt_ext ON cmt.id=cmt_ext.comment_id LEFT JOIN content AS cnt ON cmt.content_id=cnt.id LEFT JOIN content_ext AS cnt_ext ON cnt.id=cnt_ext.content_id LEFT JOIN content_type AS cnt_type ON cnt.type_id=cnt_type.id where (cmt.status=:status or :status is null) and (cmt.is_recommend=:recommend or :recommend is null) and (cmt.content_id=:contentId or :contentId is null) ORDER BY cmt.post_date desc", nativeQuery = true,
            countQuery = "SELECT count(1) FROM `comment` AS cmt LEFT JOIN comment_ext AS cmt_ext ON cmt.id=cmt_ext.comment_id LEFT JOIN content AS cnt ON cmt.content_id=cnt.id LEFT JOIN content_ext AS cnt_ext ON cnt.id=cnt_ext.content_id LEFT JOIN content_type AS cnt_type ON cnt.type_id=cnt_type.id where (cmt.status=:status or :status is null) and (cmt.is_recommend=:recommend or :recommend is null) and (cmt.content_id=:contentId or :contentId is null)")
    Page<Map<String, Object>> getCommentPage(@Param("status") Integer status, @Param("recommend") Boolean recommend, @Param("contentId") Integer contentId, Pageable pageable);
}
