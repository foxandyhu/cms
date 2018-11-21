package com.bfly.cms.manager.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.dao.assist.CmsOriginDao;
import com.bfly.cms.entity.assist.CmsOrigin;
import com.bfly.cms.manager.assist.CmsOriginMng;

@Service
@Transactional
public class CmsOriginMngImpl implements CmsOriginMng {
	@Override
    @Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}
	
	@Override
    @Transactional(readOnly = true)
	public List<CmsOrigin> getList(String name){
		return dao.getList(name);
	}

	@Override
    @Transactional(readOnly = true)
	public CmsOrigin findById(Integer id) {
		CmsOrigin entity = dao.findById(id);
		return entity;
	}

	@Override
    public CmsOrigin save(CmsOrigin bean) {
		dao.save(bean);
		return bean;
	}

	@Override
    public CmsOrigin update(CmsOrigin bean) {
		Updater<CmsOrigin> updater = new Updater<CmsOrigin>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Override
    public CmsOrigin deleteById(Integer id) {
		CmsOrigin bean = dao.deleteById(id);
		return bean;
	}

	@Override
    public CmsOrigin[] deleteByIds(Integer[] ids) {
		CmsOrigin[] beans = new CmsOrigin[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	private CmsOriginDao dao;
}