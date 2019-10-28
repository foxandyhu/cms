package com.bfly.cms.vote.service;

import com.bfly.cms.vote.entity.VoteRecord;
import com.bfly.cms.vote.entity.VoteTopic;
import com.bfly.cms.vote.entity.dto.VoteSubmitDTO;
import com.bfly.core.base.service.IBaseService;

import java.util.Date;
import java.util.List;

/**
 * 问卷调查记录
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/11 22:35
 */
public interface IVoteRecordService extends IBaseService<VoteRecord, Integer> {

    /**
     * 新增问卷调查记录
     *
     * @param voteTopicId
     * @param memberId
     * @param cookie
     * @author andy_hulibo@163.com
     * @date 2019/9/11 22:36
     */
    void save(int memberId, int voteTopicId, String cookie);

    /**
     * 获得投票次数
     *
     * @param memberId
     * @param voteTopicId
     * @param date
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/11 22:37
     */
    long getRecordCount(int memberId, int voteTopicId, Date date);

    /**
     * 获得投票次数
     *
     * @param cookie
     * @param voteTopicId
     * @param date
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/11 22:48
     */
    long getRecordCount(String cookie, int voteTopicId, Date date);
}
