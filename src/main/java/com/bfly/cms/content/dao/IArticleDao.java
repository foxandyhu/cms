package com.bfly.cms.content.dao;

import com.bfly.cms.content.entity.Article;
import com.bfly.cms.content.entity.dto.ArticleLuceneDTO;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2019/8/1 13:45
 */
public interface IArticleDao extends IBaseDao<Article, Integer> {

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

    /**
     * 统计今天发布和总数的文章
     *
     * @return Map today 今日发布数 total 发布总数
     * @author andy_hulibo@163.com
     * @date 2019/8/14 19:26
     */
    @Query(value = "select count(1) as total,temp.today FROM article,(select count(1) as today from article_ext as ar_ext INNER JOIN article as ar on ar.id=ar_ext.article_id  where date_format(post_date, '%Y-%m-%d')=date_format(NOW(), '%Y-%m-%d') AND ar.status!=0) as temp where status!=0", nativeQuery = true)
    Map<String, BigInteger> getTodayAndTotalArticle();

    /**
     * 点击率头几名的文章信息
     *
     * @param limit
     * @return Map
     * @author andy_hulibo@163.com
     * @date 2019/8/15 12:46
     */
    @Query(value = "select a.id,a_ext.title,a.views from article as a LEFT JOIN article_ext as a_ext on a.id=a_ext.article_id WHERE a.`status`=2 order by views desc limit 0,:limit", nativeQuery = true)
    List<Map<String, Object>> getClickTopArticle(@Param("limit") int limit);

    /**
     * 评论数头几名的文章信息
     *
     * @param limit
     * @return Map
     * @author andy_hulibo@163.com
     * @date 2019/8/15 12:47
     */
    @Query(value = "select a.id,a_ext.title,a.comments from article as a LEFT JOIN article_ext as a_ext on a.id=a_ext.article_id WHERE a.`status`=2 order by comments desc limit 0,:limit", nativeQuery = true)
    List<Map<String, Object>> getCommentsTopArticle(@Param("limit") int limit);

    /**
     * 下一篇文章
     *
     * @param currentArticleId 当前文章ID
     * @param channelId        栏目ID
     * @param status           状态
     * @return Map 部分字段
     * @author andy_hulibo@163.com
     * @date 2019/9/4 22:54
     */
    @Query(value = "select ar.id,title,ch.channel_path as path,title_img as titleImg from article as ar INNER JOIN article_ext on ar.id=article_id INNER JOIN channel as ch ON ar.channel_id=ch.id where ar.id>:currentArticleId and (ar.status=:status or :status is null) and (ar.channel_id=:channelId or :channelId is null) limit 0,1", nativeQuery = true)
    Map<String, Object> getNext(@Param("currentArticleId") int currentArticleId, @Param("channelId") Integer channelId, @Param("status") Integer status);

    /**
     * 上一篇文章
     *
     * @param currentArticleId 当前文章ID
     * @param channelId        栏目ID
     * @param status           状态
     * @return Map 部分字段
     * @author andy_hulibo@163.com
     * @date 2019/9/4 22:54
     */
    @Query(value = "select ar.id,title,ch.channel_path as path,title_img as titleImg from article as ar INNER JOIN article_ext on ar.id=article_id INNER JOIN channel as ch ON ar.channel_id=ch.id where ar.id<:currentArticleId and (ar.status=:status or :status is null) and (ar.channel_id=:channelId or :channelId is null) order by ar.id desc limit 0,1", nativeQuery = true)
    Map<String, Object> getPrev(int currentArticleId, @Param("channelId") Integer channelId, @Param("status") Integer status);

    /**
     * 文章评论数增长
     *
     * @param articleId 文章ID
     * @param setup     增长数
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/6 16:44
     */
    @Modifying
    @Query("update Article set comments=comments+ :setup where id=:articleId")
    int editArticleComments(@Param("articleId") int articleId, @Param("setup") int setup);

    /**
     * 修改文章的浏览量
     *
     * @param articleId 文章ID
     * @param setup     增长数
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/6 17:45
     */
    @Modifying
    @Query("update Article set views=views+ :setup where id=:articleId")
    int editArticleViews(@Param("articleId") int articleId, @Param("setup") int setup);

    /**
     * 修改文章文件下载量
     *
     * @param articleId 文章ID
     * @param setup     增长数
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/7 18:36
     */
    @Modifying
    @Query("update Article set downloads=downloads+ :setup where id=:articleId")
    int editArticleDownloads(@Param("articleId") int articleId, @Param("setup") int setup);

    /**
     * 修改文章评分
     *
     * @param articleId   文章ID
     * @param scoreItemId 评分项ID
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/7 18:37
     */
    @Modifying
    @Query(value = "update article set scores=scores+(select score from score_item WHERE id=:scoreItemId) WHERE id=:articleId", nativeQuery = true)
    int editArticleScores(@Param("articleId") int articleId, @Param("scoreItemId") int scoreItemId);

    /**
     * 踩文章
     *
     * @param articleId 文章Id
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/6 18:18
     */
    @Modifying
    @Query("update Article set downs=downs+1 where id=:articleId")
    int editArticleDowns(@Param("articleId") int articleId);

    /**
     * 顶文章
     *
     * @param articleId 文章Id
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/6 18:18
     */
    @Modifying
    @Query("update Article set ups=ups+1 where id=:articleId")
    int editArticleUps(@Param("articleId") int articleId);

    /**
     * 清空置顶过期的文章把置顶级别设置为0
     *
     * @param date 日期
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/14 11:22
     */
    @Modifying
    @Query("update Article set topLevel=0,topExpired=null where topExpired<:date and topExpired is not null")
    int resetArticleTopLevelForExpired(@Param("date") Date date);

    /**
     * 自动发布文章
     *
     * @param passingStatus
     * @param passStatus
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/14 13:23
     */
    @Modifying
    @Query(value = "UPDATE article set `status`=:passStatus WHERE exists(select article_id from article_ext WHERE `status`=:passingStatus and post_date<now())", nativeQuery = true)
    int autoPublishArticle(@Param("passStatus") int passStatus, @Param("passingStatus") int passingStatus);

    /**
     * 得到ArticleLuceneDTO
     *
     * @param limit
     * @param from
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/10/28 12:09
     */
    @Query(value = "select ar.id,ar.status,ch.channel_path as channelPath,ar_ext.title,ar_ext.post_date as postDate,ar_ext.title_img as titleImg,ar_ext.summary,ar_txt.txt from article as ar LEFT JOIN article_ext as ar_ext on ar.id=ar_ext.article_id LEFT JOIN article_txt as ar_txt on ar.id=ar_txt.article_id LEFT JOIN channel as ch on ar.channel_id=ch.id limit :from,:limit", nativeQuery = true)
    List<Map<String,Object>> getArticleLuceneDTO(@Param("from") int from, @Param("limit") int limit);
}
