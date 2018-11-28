package com.bfly.cms.vote.service;

import java.util.Collection;

import com.bfly.cms.vote.entity.CmsVoteItem;
import com.bfly.cms.vote.entity.CmsVoteSubTopic;
import com.bfly.common.page.Pagination;

public interface CmsVoteItemMng {
	 Pagination getPage(int pageNo, int pageSize);

	 CmsVoteItem findById(Integer id);

	 Collection<CmsVoteItem> save(Collection<CmsVoteItem> items,
			CmsVoteSubTopic topic);

	 Collection<CmsVoteItem> update(Collection<CmsVoteItem> items,
			CmsVoteSubTopic topic);

	 CmsVoteItem deleteById(Integer id);

	 CmsVoteItem[] deleteByIds(Integer[] ids);

}