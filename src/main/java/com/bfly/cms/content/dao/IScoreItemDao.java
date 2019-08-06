package com.bfly.cms.content.dao;

import com.bfly.cms.content.entity.ScoreItem;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/17 13:54
 */
public interface IScoreItemDao extends IBaseDao<ScoreItem, Integer> {

    /**
     * 根据评分组删除评分项
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/15 21:03
     */
    @Modifying
    @Query("delete from ScoreItem where group.id=:groupId")
    int removeScoreItems(@Param("groupId") int groupId);

    /**
     * 修改评分项排序
     *
     * @param itemId 评分项ID
     * @param seq    排序序号
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/4 15:24
     */
    @Modifying
    @Query("update ScoreItem set seq=:seq where id=:itemId")
    int editScoreItemSeq(@Param("itemId") int itemId, @Param("seq") int seq);

    /**
     * 获得最大的排序序号
     * @param groupId 分组id
     * @return 返回最大的排序序号
     * @author andy_hulibo@163.com
     * @date 2019/8/4 20:46
     */
    @Query(value = "select IFNULL(max(seq),0) as seq from score_item where group_id=:groupId", nativeQuery = true)
    int getMaxSeq(@Param("groupId")int groupId);
}
