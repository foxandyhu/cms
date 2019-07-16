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
     * @author andy_hulibo@163.com
     * @date 2019/7/15 21:03
     */
    @Modifying
    @Query("delete from ScoreItem where group.id=:groupId")
    int removeScoreItems(@Param("groupId") int groupId);
}
