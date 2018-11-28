package com.bfly.cms.comment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.comment.dao.CmsGuestbookCtgDao;
import com.bfly.cms.comment.entity.CmsGuestbookCtg;
import com.bfly.cms.comment.service.CmsGuestbookCtgMng;
import com.bfly.common.hibernate4.Updater;

@Service
@Transactional
public class CmsGuestbookCtgMngImpl implements CmsGuestbookCtgMng {
	@Override
    @Transactional(readOnly = true)
	public List<CmsGuestbookCtg> getList(Integer siteId) {
		return dao.getList(siteId);
	}

	@Override
    @Transactional(readOnly = true)
	public CmsGuestbookCtg findById(Integer id) {
		CmsGuestbookCtg entity = dao.findById(id);
		return entity;
	}

	@Override
    public CmsGuestbookCtg save(CmsGuestbookCtg bean) {
		dao.save(bean);
		return bean;
	}

	@Override
    public CmsGuestbookCtg update(CmsGuestbookCtg bean) {
		Updater<CmsGuestbookCtg> updater = new Updater<CmsGuestbookCtg>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Override
    public CmsGuestbookCtg deleteById(Integer id) {
		CmsGuestbookCtg bean = dao.deleteById(id);
		return bean;
	}

	@Override
    public CmsGuestbookCtg[] deleteByIds(Integer[] ids) {
		CmsGuestbookCtg[] beans = new CmsGuestbookCtg[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	private CmsGuestbookCtgDao dao;
}