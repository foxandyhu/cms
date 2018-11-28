package com.bfly.cms.vote.service;

import com.bfly.cms.vote.entity.CmsVoteItem;
import com.bfly.cms.vote.entity.CmsVoteSubTopic;
import com.bfly.cms.vote.entity.CmsVoteTopic;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface CmsVoteSubTopicMng {

    CmsVoteSubTopic findById(Integer id);

    CmsVoteTopic save(CmsVoteTopic bean, Set<CmsVoteSubTopic> subTopics);

    CmsVoteSubTopic save(CmsVoteSubTopic bean);

    CmsVoteSubTopic save(CmsVoteSubTopic bean, List<CmsVoteItem> items);

    CmsVoteSubTopic update(CmsVoteSubTopic bean, Collection<CmsVoteItem> items);

    CmsVoteTopic update(CmsVoteTopic bean, Collection<CmsVoteSubTopic> subTopics);

    Collection<CmsVoteSubTopic> update(Collection<CmsVoteSubTopic> subTopics, CmsVoteTopic topic);

    CmsVoteSubTopic deleteById(Integer id);

    CmsVoteSubTopic[] deleteByIds(Integer[] ids);

}