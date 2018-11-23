package com.bfly.cms.manager.assist.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.dao.assist.CmsScoreGroupDao;
import com.bfly.cms.entity.assist.CmsScoreGroup;
import com.bfly.cms.manager.assist.CmsScoreGroupMng;

@Service
@Transactional
public class CmsScoreGroupMngImpl implements CmsScoreGroupMng {
	@Override
    @Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}

	@Override
    @Transactional(readOnly = true)
	public CmsScoreGroup findById(Integer id) {
		CmsScoreGroup entity = dao.findById(id);
		return entity;
	}
	
	@Override
    @Transactional(readOnly = true)
	public CmsScoreGroup findDefault(Integer siteId){
		return dao.findDefault(siteId);
	}

	@Override
    public CmsScoreGroup save(CmsScoreGroup bean) {
		dao.save(bean);
		return bean;
	}

	@Override
    public CmsScoreGroup update(CmsScoreGroup bean) {
		Updater<CmsScoreGroup> updater = new Updater<CmsScoreGroup>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Override
    public CmsScoreGroup deleteById(Integer id) {
		CmsScoreGroup bean = dao.deleteById(id);
		return bean;
	}
	
	@Override
    public CmsScoreGroup[] deleteByIds(Integer[] ids) {
		CmsScoreGroup[] beans = new CmsScoreGroup[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	@Autowired
	private CmsScoreGroupDao dao;
}