package com.bfly.cms.vote.dao;

import com.bfly.cms.vote.entity.CmsVoteRecord;

public interface CmsVoteRecordDao {
    CmsVoteRecord save(CmsVoteRecord bean);

    int deleteByTopic(Integer topicId);

    CmsVoteRecord findByUserId(Integer userId, Integer topicId);

    CmsVoteRecord findByIp(String ip, Integer topicId);

    CmsVoteRecord findByCookie(String cookie, Integer topicId);
}