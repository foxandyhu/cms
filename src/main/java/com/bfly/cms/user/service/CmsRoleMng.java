package com.bfly.cms.user.service;

import java.util.List;
import java.util.Set;

import com.bfly.cms.user.entity.CmsRole;

public interface CmsRoleMng {
	
	 List<CmsRole> getList(Integer level);

	 CmsRole findById(Integer id);

	 CmsRole save(CmsRole bean, Set<String> perms);

	 CmsRole update(CmsRole bean, Set<String> perms);

	 CmsRole deleteById(Integer id);

	 CmsRole[] deleteByIds(Integer[] ids);

	 void deleteMembers(CmsRole role, Integer[] userIds);
}