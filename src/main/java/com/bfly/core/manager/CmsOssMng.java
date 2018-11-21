package com.bfly.core.manager;

import java.util.List;

import com.bfly.common.page.Pagination;
import com.bfly.core.entity.CmsOss;

public interface CmsOssMng {
	public Pagination getPage(int pageNo, int pageSize);
	
	public List<CmsOss> getList();

	public CmsOss findById(Integer id);

	public CmsOss save(CmsOss bean);

	public CmsOss update(CmsOss bean);

	public CmsOss deleteById(Integer id);
	
	public CmsOss[] deleteByIds(Integer[] ids);
}