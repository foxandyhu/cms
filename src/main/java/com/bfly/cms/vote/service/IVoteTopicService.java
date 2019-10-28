package com.bfly.cms.vote.service;

import com.bfly.cms.vote.entity.VoteTopic;
import com.bfly.cms.vote.entity.dto.VoteSubmitDTO;
import com.bfly.core.base.service.IBaseService;

import java.util.List;

/**
 * 问卷调查业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/12 15:04
 */
public interface IVoteTopicService extends IBaseService<VoteTopic, Integer> {

    /**
     * 设置问卷主题是否启用
     *
     * @param voteTopicId 问卷主题ID
     * @param enable      是否启用
     * @author andy_hulibo@163.com
     * @date 2018/12/12 15:29
     */
    void setEnableVoteTopic(int voteTopicId, boolean enable);

    /**
     * 得到问答自主题的回复数量
     *
     * @param voteSubTopicId
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/11 13:11
     */
    long getVoteReplyCount(int voteSubTopicId);

    /**
     * 投票
     *
     * @param dtos
     * @param voteTopicId
     * @param cookie
     * @author andy_hulibo@163.com
     * @date 2019/9/11 20:22
     */
    void voteSubmit(int voteTopicId, List<VoteSubmitDTO> dtos, String cookie);
}
