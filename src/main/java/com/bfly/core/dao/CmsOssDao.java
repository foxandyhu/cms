package com.bfly.core.dao;

import java.util.List;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.core.entity.CmsOss;

public interface CmsOssDao {
	public Pagination getPage(int pageNo, int pageSize);
	
	public List<CmsOss> getList();

	public CmsOss findById(Integer id);

	public CmsOss save(CmsOss bean);

	public CmsOss updateByUpdater(Updater<CmsOss> updater);

	public CmsOss deleteById(Integer id);
}