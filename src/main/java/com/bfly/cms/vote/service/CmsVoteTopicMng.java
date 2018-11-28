package com.bfly.cms.vote.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bfly.cms.vote.entity.CmsVoteItem;
import com.bfly.cms.vote.entity.CmsVoteSubTopic;
import com.bfly.cms.vote.entity.CmsVoteTopic;
import com.bfly.common.page.Pagination;
import com.bfly.cms.user.entity.CmsUser;

public interface CmsVoteTopicMng {
	 Pagination getPage(Integer siteId,Short statu, int pageNo, int pageSize);
	
	 List<CmsVoteTopic> getList(Boolean def,Integer siteId,
			Integer first,int count);

	 CmsVoteTopic findById(Integer id);

	 CmsVoteTopic getDefTopic(Integer siteId);

	 CmsVoteTopic vote(Integer topicId,Integer[]subIds, List<Integer[]> itemIds,String[]replys, CmsUser user,
			String ip, String cookie);

	 CmsVoteTopic save(CmsVoteTopic bean, Set<CmsVoteSubTopic> subTopics,Map<Integer,Set<CmsVoteItem>>items);
	
	 CmsVoteTopic save(CmsVoteTopic bean, Set<CmsVoteSubTopic> subTopics);
	
	 CmsVoteTopic save(CmsVoteTopic bean);

	 CmsVoteTopic update(CmsVoteTopic bean);
	
	 CmsVoteTopic update(CmsVoteTopic bean, Set<CmsVoteSubTopic> subTopics,Map<Integer,Set<CmsVoteItem>>items);
	
	 void updatePriority(Integer[] id,Integer defId,Boolean[] disabled,Integer siteId);

	 CmsVoteTopic deleteById(Integer id);

	 CmsVoteTopic[] deleteByIds(Integer[] ids);
}