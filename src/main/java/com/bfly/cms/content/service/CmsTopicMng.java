package com.bfly.cms.content.service;

import java.util.List;

import com.bfly.cms.content.entity.CmsTopic;
import com.bfly.common.page.Pagination;

public interface CmsTopicMng extends ChannelDeleteChecker{

	 List<CmsTopic> getListForTag(Integer channelId, boolean recommend,
			Integer first,Integer count);

	 Pagination getPageForTag(Integer channelId, boolean recommend,
			int pageNo, int pageSize);

	 Pagination getPage(String initials,int pageNo, int pageSize);

	 List<CmsTopic> getListByChannel(Integer channelId);

	 CmsTopic findById(Integer id);

	 CmsTopic save(CmsTopic bean, Integer channelId,Integer[]channelIds);

	 CmsTopic update(CmsTopic bean, Integer channelId,Integer[]channelIds);

	 CmsTopic deleteById(Integer id);

	 CmsTopic[] deleteByIds(Integer[] ids);

	 CmsTopic[] updatePriority(Integer[] ids, Integer[] priority);
}