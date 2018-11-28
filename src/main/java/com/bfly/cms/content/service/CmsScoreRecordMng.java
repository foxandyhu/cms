package com.bfly.cms.content.service;

import java.util.Map;

import com.bfly.common.page.Pagination;
import com.bfly.cms.content.entity.CmsScoreRecord;

public interface CmsScoreRecordMng {
	 Pagination getPage(int pageNo, int pageSize);
	
	 Map<String,String> viewContent(Integer contentId);

	 int contentScore(Integer contentId,Integer itemId);
	
	 CmsScoreRecord findByScoreItemContent(Integer itemId,Integer contentId);

	 CmsScoreRecord findById(Integer id);

	 CmsScoreRecord save(CmsScoreRecord bean);

	 CmsScoreRecord update(CmsScoreRecord bean);

	 CmsScoreRecord deleteById(Integer id);
	
	 CmsScoreRecord[] deleteByIds(Integer[] ids);
}