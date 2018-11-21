package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsVoteSubTopic;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

public interface CmsVoteSubTopicDao {
    List<CmsVoteSubTopic> findByVoteTopic(Integer voteTopicId);

    CmsVoteSubTopic findById(Integer id);

    CmsVoteSubTopic save(CmsVoteSubTopic bean);

    CmsVoteSubTopic updateByUpdater(Updater<CmsVoteSubTopic> updater);

    CmsVoteSubTopic deleteById(Integer id);
}