package com.bfly.cms.content.dao;

import com.bfly.cms.content.entity.Article;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2019/8/1 13:45
 */
public interface IArticleDao extends IBaseDao<Article, Integer> {

    /**
     * 查询文章分页集合
     *
     * @param status    状态
     * @param type      类型
     * @param title     标题
     * @param channelId 栏目ID
     * @param pageable  分页
     * @return 文章相关信息
     * @author andy_hulibo@163.com
     * @date 2019/8/7 13:52
     */
    @Query(value = "SELECT ar.id,ar.channel_id as channleId,ch.channel_name as channelName,ar.user_id as userId,u.username as userName,ar.type_id as typeId,ar.top_level as topLevel,ar.is_recommend as recommend,ar.recommend_level as recommendLevel,ar.views,ar.`status`,ar_ext.post_date as postDate,ar_ext.title,ar.top_expired as topExpired FROM article as ar LEFT JOIN channel as ch on ar.channel_id=ch.id LEFT JOIN `user` as u on ar.user_id=u.id LEFT JOIN article_ext as ar_ext on ar.id=ar_ext.article_id where (ar.status=:status or :status is null) and (ar.type_id=:type or :type is null) and (ar.channel_id=:channelId or :channelId is null) and (ar_ext.title like CONCAT('%',:title,'%') or :title is null) order by top_level desc, id desc",
            countQuery = "SELECT count(1) FROM article as ar LEFT JOIN channel as ch on ar.channel_id=ch.id LEFT JOIN `user` as u on ar.user_id=u.id LEFT JOIN article_ext as ar_ext on ar.id=ar_ext.article_id where (ar.status=:status or :status is null) and (ar.type_id=:type or :type is null) and (ar.channel_id=:channelId or :channelId is null) and (ar_ext.title like CONCAT('%',:title,'%') or :title is null)", nativeQuery = true)
    Page<Map<String, Object>> getArticlePager(@Param("status") Integer status, @Param("type") Integer type, @Param("channelId") Integer channelId, @Param("title") String title, Pageable pageable);

    /**
     * 清除文章和会员组之间的浏览关系权限
     *
     * @param articleId 文章ID
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/7 15:23
     */
    @Modifying
    @Query(value = "delete from article_group_view where article_id=:articleId", nativeQuery = true)
    int removeArticleGroupViewShip(@Param("articleId") int articleId);

    /**
     * 清除文章的评分数据
     *
     * @param articleId 文章ID
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/7 16:05
     */
    @Modifying
    @Query(value = "delete from article_score where article_id=:articleId", nativeQuery = true)
    int removeArticleScore(@Param("articleId") int articleId);


    /**
     * 审核文章
     *
     * @param articleIds 文章ID
     * @param status     状态
     * @author andy_hulibo@163.com
     * @date 2018/12/12 11:44
     */
    @Modifying
    @Query("update Article set status=:status where id in (:articleId)")
    void verifyArticle(int status, @Param("articleId") Integer... articleIds);

    /**
     * 修改文章推荐级别
     *
     * @param recommend      是否推荐
     * @param articleIds     文章Id
     * @param recommendLevel 推荐级别
     * @author andy_hulibo@163.com
     * @date 2019/8/7 16:14
     */
    @Modifying
    @Query("update Article set recommend=:recommend,recommendLevel=:recommendLevel where id in (:articleId)")
    void recommendArticle(@Param("recommend") boolean recommend, @Param("recommendLevel") int recommendLevel, @Param("articleId") Integer... articleIds);

    /**
     * 修改文章置顶
     *
     * @param articleId 文章Id
     * @param topLevel  置顶级别
     * @param expired   置顶有效期
     * @author andy_hulibo@163.com
     * @date 2019/8/7 16:13
     */
    @Modifying
    @Query("update Article set topLevel=:topLevel,topExpired=:expired where id=:articleId")
    void editArticleTop(@Param("articleId") int articleId, @Param("topLevel") int topLevel, @Param("expired") Date expired);

    /**
     * 文章和专题关联
     *
     * @param articleId 文章ID
     * @param topicId   专题ID
     * @author andy_hulibo@163.com
     * @date 2019/8/8 16:11
     */
    @Modifying
    @Query(value = "insert into special_topic_article_ship(article_id,topic_id) values(:articleId,:topicId)", nativeQuery = true)
    void saveSpecialTopicArticleShip(@Param("articleId") int articleId, @Param("topicId") int topicId);

    /**
     * 删除文章和专题的关联
     *
     * @param articleId 文章ID
     * @param topicId   专题ID
     * @author andy_hulibo@163.com
     * @date 2019/8/8 16:12
     */
    @Modifying
    @Query(value = "delete from special_topic_article_ship where article_id=:articleId and topic_id=:topicId ", nativeQuery = true)
    void removeSpecialTopicArticleShip(@Param("articleId") int articleId, @Param("topicId") int topicId);

    /**
     * 清空文章和专题的所有关联
     *
     * @param articleId 文章ID
     * @author andy_hulibo@163.com
     * @date 2019/8/8 16:12
     */
    @Modifying
    @Query(value = "delete from special_topic_article_ship where article_id=:articleId", nativeQuery = true)
    void clearSpecialTopicArticleShip(@Param("articleId") int articleId);

    /**
     * 查询是否存在关联
     *
     * @param articleId 文章ID
     * @param topicId   专题ID
     * @return true存在关联 反之
     * @author andy_hulibo@163.com
     * @date 2019/8/8 16:53
     */
    @Query(value = "select CAST(count(1)>0 AS CHAR)as flag from special_topic_article_ship where article_id=:articleId and topic_id=:topicId ", nativeQuery = true)
    boolean isExistSpecialTopicArticleShip(@Param("articleId") int articleId, @Param("topicId") int topicId);

    /**
     * 删除文章图片集
     *
     * @param picId 图片ID
     * @author andy_hulibo@163.com
     * @date 2019/8/13 10:36
     */
    @Modifying
    @Query(value = "delete from article_picture where id=:picId", nativeQuery = true)
    void delArticlePicture(@Param("picId") int picId);

    /**
     * 删除文章附件集
     *
     * @param attachmentId 附件ID
     * @author andy_hulibo@163.com
     * @date 2019/8/13 10:37
     */
    @Modifying
    @Query(value = "delete from article_attachment where id=:attachmentId", nativeQuery = true)
    void delArticleAttachment(@Param("attachmentId") int attachmentId);
}
