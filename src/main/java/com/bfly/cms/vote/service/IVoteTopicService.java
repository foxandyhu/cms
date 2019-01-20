package com.bfly.cms.vote.service;

import com.bfly.cms.vote.entity.VoteTopic;
import com.bfly.core.base.service.IBaseService;

/**
 * 问卷调查业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/12 15:04
 */
public interface IVoteTopicService extends IBaseService<VoteTopic, Integer> {

    /**
     * 设置默认的问卷主题
     *
     * @param voteTopicId 问卷主题ID
     * @author andy_hulibo@163.com
     * @date 2018/12/12 15:28
     */
    void setDefaultVoteTopic(int voteTopicId);

    /**
     * 设置问卷主题是否启用
     *
     * @param voteTopicId 问卷主题ID
     * @param enable      是否启用
     * @author andy_hulibo@163.com
     * @date 2018/12/12 15:29
     */
    void setEnableVoteTopic(int voteTopicId, boolean enable);
}
