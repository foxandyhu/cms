package com.bfly.core.manager;

import java.util.List;

import com.bfly.common.page.Pagination;
import com.bfly.core.entity.CmsSms;

public interface CmsSmsMng {
public Pagination getPage(Byte source,int pageNo, int pageSize);
	
	public List<CmsSms> getList();

	public CmsSms findById(Integer id);

	public CmsSms save(CmsSms bean);

	public CmsSms update(CmsSms bean);

	public CmsSms deleteById(Integer id);
	
	public CmsSms[] deleteByIds(Integer[] ids);

	public CmsSms findBySource(Byte source);
}
