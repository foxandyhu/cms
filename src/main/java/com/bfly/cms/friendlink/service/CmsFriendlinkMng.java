package com.bfly.cms.friendlink.service;

import java.util.List;

import com.bfly.cms.friendlink.entity.CmsFriendlink;

public interface CmsFriendlinkMng {
	 List<CmsFriendlink> getList(Integer siteId, Integer ctgId,
			Boolean enabled);

	 CmsFriendlink findById(Integer id);

	 int updateViews(Integer id);

	 CmsFriendlink save(CmsFriendlink bean, Integer ctgId);

	 CmsFriendlink update(CmsFriendlink bean, Integer ctgId);

	 void updatePriority(Integer[] ids, Integer[] priorities);

	 CmsFriendlink deleteById(Integer id);

	 CmsFriendlink[] deleteByIds(Integer[] ids);
}