package com.bfly.cms.message.dao;

import com.bfly.cms.message.entity.Comment;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;
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
     * @param articleId 内容ID
     * @param pageable  分页信息 JPA会自动识别 不需要在sql中写分页语句
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2019/8/4 17:15
     */
    @Query(value = "SELECT cmt.id,cmt.article_id as articleId,cmt.member_user_name as memberUserName,cmt.user_name as userName,cmt.post_date as postDate,cmt.is_recommend as recommend,cmt.`status`,cmt_ext.ip,cmt_ext.area,cmt_ext.text,cnt_ext.title,cnt.type_id as typeId FROM `comment` AS cmt LEFT JOIN comment_ext AS cmt_ext ON cmt.id=cmt_ext.comment_id LEFT JOIN article AS cnt ON cmt.article_id=cnt.id LEFT JOIN article_ext AS cnt_ext ON cnt.id=cnt_ext.article_id where (cmt.status=:status or :status is null) and (cmt.is_recommend=:recommend or :recommend is null) and (cmt.article_id=:articleId or :articleId is null) ORDER BY cmt.post_date desc", nativeQuery = true,
            countQuery = "SELECT count(1) FROM `comment` AS cmt LEFT JOIN comment_ext AS cmt_ext ON cmt.id=cmt_ext.comment_id LEFT JOIN article AS cnt ON cmt.article_id=cnt.id LEFT JOIN article_ext AS cnt_ext ON cnt.id=cnt_ext.article_id where (cmt.status=:status or :status is null) and (cmt.is_recommend=:recommend or :recommend is null) and (cmt.article_id=:articleId or :articleId is null)")
    Page<Map<String, Object>> getCommentPage(@Param("status") Integer status, @Param("recommend") Boolean recommend, @Param("articleId") Integer articleId, Pageable pageable);

    /**
     * 统计当天评论总数和总评论数
     *
     * @return Map today 今日评论数 total总评论数
     * @author andy_hulibo@163.com
     * @date 2019/8/14 19:47
     */
    @Query(value = "select count(1) as total,temp.today FROM `comment`,(select count(1) as today from `comment` as cmm where date_format(post_date, '%Y-%m-%d')=date_format(NOW(), '%Y-%m-%d')) as temp", nativeQuery = true)
    Map<String, BigInteger> getTodayAndTotalComment();

    /**
     * 获得最新的前几条评论
     * @param status
     * @param limit 返回最大条数
     * @return Map
     * @author andy_hulibo@163.com
     * @date 2019/8/15 12:21
     */
    @Query(value = "select c.article_id as articleId,ch.channel_path as channelPath,m_ext.face,c.member_user_name as userName,c.post_date as postDate,c_ext.ip,c_ext.text,c_ext.area,c.`status` from `comment` as c LEFT JOIN comment_ext as c_ext on c.id=c_ext.comment_id LEFT JOIN member as m on m.user_name=c.member_user_name LEFT JOIN member_ext as m_ext on m.id=m_ext.member_id LEFT JOIN article as ar on c.article_id=ar.id LEFT JOIN channel as ch on ar.channel_id=ch.id WHERE (c.`status`=:status or :status is null) and c.member_user_name is not NULL order by c.post_date DESC LIMIT 0,:limit", nativeQuery = true)
    List<Map<String, Object>> getLatestComment(@Param("limit") int limit,@Param("status")Integer status);

    /**
     * 顶评论
     * @author andy_hulibo@163.com
     * @date 2019/9/6 18:18
     */
    @Modifying
    @Query("update Comment set ups=ups+1 where id=:commendId")
    int upComment(@Param("commendId") int commendId);

    /**
     * 踩评论
     * @author andy_hulibo@163.com
     * @date 2019/9/6 18:18
     */
    @Modifying
    @Query("update Comment set downs=downs+1 where id=:commendId")
    int downComment(@Param("commendId") int commendId);
}
