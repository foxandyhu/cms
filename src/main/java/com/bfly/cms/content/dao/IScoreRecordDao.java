package com.bfly.cms.content.dao;

import com.bfly.cms.content.entity.ScoreRecord;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 评分记录数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/8 17:14
 */
public interface IScoreRecordDao extends IBaseDao<ScoreRecord, Integer> {

    /**
     * 得到对应文章和评分项的评分记录
     * @param scoreItemId
     * @param articleId
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/8 17:15
     */
    @Query(value = "select record from ScoreRecord as record where articleId=:articleId and scoreItemId=:scoreItemId")
    ScoreRecord getScoreRecord(@Param("articleId") int articleId, @Param("scoreItemId") int scoreItemId);

    /**
     * 新增评分记录
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/8 16:20
     */
    @Modifying
    @Query(value = "insert into score_record(article_id,score_item_id,score_count) values(:articleId,:scoreItemId,:count)", nativeQuery = true)
    int saveScoreRecord(@Param("articleId") int articleId, @Param("scoreItemId") int scoreItemId, @Param("count") int count);

    /**
     * 修改评分项数
     *
     * @param articleId
     * @param scoreItemId
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/8 16:22
     */
    @Modifying
    @Query(value = "update score_record set score_count=score_count+1 where article_id=:articleId and score_item_id=:scoreItemId", nativeQuery = true)
    int incrementScoreRecord(@Param("articleId") int articleId, @Param("scoreItemId") int scoreItemId);
}
