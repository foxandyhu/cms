package com.bfly.cms.manager.assist;

import com.bfly.cms.entity.assist.CmsWebserviceAuth;
import com.bfly.common.page.Pagination;

public interface CmsWebserviceAuthMng {
	public Pagination getPage(int pageNo, int pageSize);
	
	public boolean isPasswordValid(String username, String password);
	
	public CmsWebserviceAuth findByUsername(String username);

	public CmsWebserviceAuth findById(Integer id);

	public CmsWebserviceAuth save(CmsWebserviceAuth bean);

	public CmsWebserviceAuth update(CmsWebserviceAuth bean);
	
	public CmsWebserviceAuth update(Integer id,String username,String password,String system,Boolean enable);

	public CmsWebserviceAuth deleteById(Integer id);
	
	public CmsWebserviceAuth[] deleteByIds(Integer[] ids);

}