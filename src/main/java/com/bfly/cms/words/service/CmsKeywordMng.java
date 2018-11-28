package com.bfly.cms.words.service;

import java.util.List;

import com.bfly.cms.words.entity.CmsKeyword;

public interface CmsKeywordMng {
	 List<CmsKeyword> getListBySiteId(Integer siteId,
			boolean onlyEnabled, boolean cacheable);

	 String attachKeyword(Integer siteId, String txt);

	 CmsKeyword findById(Integer id);

	 CmsKeyword save(CmsKeyword bean);

	 void updateKeywords(Integer[] ids, String[] names, String[] urls,
			Boolean[] disableds);

	 CmsKeyword deleteById(Integer id);

	 CmsKeyword[] deleteByIds(Integer[] ids);
}