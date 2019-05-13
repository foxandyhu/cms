package com.bfly.cms.vote.dao;

import com.bfly.cms.vote.entity.VoteTopic;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Query;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/12 15:05
 */
public interface IVoteTopicDao extends IBaseDao<VoteTopic, Integer> {

    /**
     * 清除默认问卷主题
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 15:36
     */
    @Query("update VoteTopic topic set topic.def=false where topic.def=true")
    void clearDefVoteTopic();
}
