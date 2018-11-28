package com.bfly.cms.user.service;

import java.util.List;

import com.bfly.cms.user.entity.CmsGroup;

public interface CmsGroupMng {
	 List<CmsGroup> getList();

	 CmsGroup getRegDef();

	 CmsGroup findById(Integer id);
	
	 CmsGroup findByName(String name);

	 void updateRegDef(Integer regDefId);

	 CmsGroup save(CmsGroup bean);
	
	public CmsGroup save(CmsGroup bean,Integer[] viewChannelIds, Integer[] contriChannelIds);

	public CmsGroup update(CmsGroup bean);
	
	public CmsGroup update(CmsGroup bean,Integer[] viewChannelIds, Integer[] contriChannelIds);

	public CmsGroup deleteById(Integer id);

	public CmsGroup[] deleteByIds(Integer[] ids);

	public CmsGroup[] updatePriority(Integer[] ids, Integer[] priority);
}