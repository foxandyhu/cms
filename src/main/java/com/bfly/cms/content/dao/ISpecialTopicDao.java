package com.bfly.cms.content.dao;

import com.bfly.cms.content.entity.SpecialTopic;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2019/8/6 15:16
 */
public interface ISpecialTopicDao extends IBaseDao<SpecialTopic, Integer> {

    /**
     * 修改专题排序
     *
     * @param topicId 专题ID
     * @param seq     排序序号
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/4 15:24
     */
    @Modifying
    @Query("update SpecialTopic set seq=:seq where id=:topicId")
    int editSpecialTopicSeq(@Param("topicId") int topicId, @Param("seq") int seq);

    /**
     * 获得最大的排序序号
     *
     * @return 返回最大的排序序号
     * @author andy_hulibo@163.com
     * @date 2019/8/4 20:46
     */
    @Query(value = "select IFNULL(max(seq),0) as seq from special_topic", nativeQuery = true)
    int getMaxSeq();

    /**
     * 解除专题和内容的关系
     *
     * @param topicId 要解除的专题ID
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/5 21:47
     */
    @Modifying
    @Query(value = "delete from special_topic_article_ship where topic_id=:topicId", nativeQuery = true)
    int clearSpecialTopicArticleShip(@Param("topicId") int topicId);

    /**
     * 获得文章关联的专题
     *
     * @param articleId 文章ID
     * @return 专题集合
     * @author andy_hulibo@163.com
     * @date 2019/8/8 20:18
     */
    @Query(value = "SELECT id,topic_name,short_name,keywords,remark,title_img,content_img,tpl_pc,tpl_mobile,seq,is_recommend FROM special_topic as st WHERE EXISTS (select topic_id from special_topic_article_ship where article_id=:articleId and st.id=topic_id)", nativeQuery = true)
    List<SpecialTopic> getSpecialTopicForArticle(@Param("articleId") int articleId);
}
