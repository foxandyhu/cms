package com.bfly.core.dao;

import java.util.List;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.core.entity.CmsSms;

public interface CmsSmsDao {
public Pagination getPage(Byte source,int pageNo, int pageSize);
	
	public List<CmsSms> getList();

	public CmsSms findById(Integer id);

	public CmsSms save(CmsSms bean);

	public CmsSms updateByUpdater(Updater<CmsSms> updater);

	public CmsSms deleteById(Integer id);

	public CmsSms findBySource(Byte source);
}
