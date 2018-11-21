package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsVoteRecord;

public interface CmsVoteRecordDao {
    CmsVoteRecord save(CmsVoteRecord bean);

    int deleteByTopic(Integer topicId);

    CmsVoteRecord findByUserId(Integer userId, Integer topicId);

    CmsVoteRecord findByIp(String ip, Integer topicId);

    CmsVoteRecord findByCookie(String cookie, Integer topicId);
}