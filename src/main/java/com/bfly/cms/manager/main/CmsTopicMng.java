package com.bfly.cms.manager.main;

import java.util.List;

import com.bfly.cms.entity.main.CmsTopic;
import com.bfly.cms.service.ChannelDeleteChecker;
import com.bfly.common.page.Pagination;

public interface CmsTopicMng extends ChannelDeleteChecker{

	public List<CmsTopic> getListForTag(Integer channelId, boolean recommend,
			Integer first,Integer count);

	public Pagination getPageForTag(Integer channelId, boolean recommend,
			int pageNo, int pageSize);

	public Pagination getPage(String initials,int pageNo, int pageSize);

	public List<CmsTopic> getListByChannel(Integer channelId);

	public CmsTopic findById(Integer id);

	public CmsTopic save(CmsTopic bean, Integer channelId,Integer[]channelIds);

	public CmsTopic update(CmsTopic bean, Integer channelId,Integer[]channelIds);

	public CmsTopic deleteById(Integer id);

	public CmsTopic[] deleteByIds(Integer[] ids);

	public CmsTopic[] updatePriority(Integer[] ids, Integer[] priority);
}