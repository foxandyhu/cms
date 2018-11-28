package com.bfly.cms.vote.dao;

import com.bfly.cms.vote.entity.CmsVoteSubTopic;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

public interface CmsVoteSubTopicDao {
    List<CmsVoteSubTopic> findByVoteTopic(Integer voteTopicId);

    CmsVoteSubTopic findById(Integer id);

    CmsVoteSubTopic save(CmsVoteSubTopic bean);

    CmsVoteSubTopic updateByUpdater(Updater<CmsVoteSubTopic> updater);

    CmsVoteSubTopic deleteById(Integer id);
}