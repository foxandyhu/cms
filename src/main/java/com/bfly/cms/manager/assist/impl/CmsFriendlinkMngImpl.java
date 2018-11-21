package com.bfly.cms.manager.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.dao.assist.CmsFriendlinkDao;
import com.bfly.cms.entity.assist.CmsFriendlink;
import com.bfly.cms.manager.assist.CmsFriendlinkCtgMng;
import com.bfly.cms.manager.assist.CmsFriendlinkMng;
import com.bfly.common.hibernate4.Updater;

@Service
@Transactional
public class CmsFriendlinkMngImpl implements CmsFriendlinkMng {
	@Override
    @Transactional(readOnly = true)
	public List<CmsFriendlink> getList(Integer siteId, Integer ctgId,
			Boolean enabled) {
		List<CmsFriendlink> list = dao.getList(siteId, ctgId, enabled);
		return list;
	}

	@Override
    @Transactional(readOnly = true)
	public int countByCtgId(Integer ctgId) {
		return dao.countByCtgId(ctgId);
	}

	@Override
    @Transactional(readOnly = true)
	public CmsFriendlink findById(Integer id) {
		CmsFriendlink entity = dao.findById(id);
		return entity;
	}

	@Override
    public int updateViews(Integer id) {
		CmsFriendlink link = findById(id);
		if (link != null) {
			link.setViews(link.getViews() + 1);
		}
		return link.getViews();
	}

	@Override
    public CmsFriendlink save(CmsFriendlink bean, Integer ctgId) {
		bean.setCategory(cmsFriendlinkCtgMng.findById(ctgId));
		bean.init();
		dao.save(bean);
		return bean;
	}

	@Override
    public CmsFriendlink update(CmsFriendlink bean, Integer ctgId) {
		Updater<CmsFriendlink> updater = new Updater<CmsFriendlink>(bean);
		bean = dao.updateByUpdater(updater);
		if (ctgId != null) {
			bean.setCategory(cmsFriendlinkCtgMng.findById(ctgId));
		}
		bean.blankToNull();
		return bean;
	}

	@Override
    public void updatePriority(Integer[] ids, Integer[] priorities) {
		if (ids == null || priorities == null || ids.length <= 0
				|| ids.length != priorities.length) {
			return;
		}
		CmsFriendlink link;
		for (int i = 0, len = ids.length; i < len; i++) {
			link = findById(ids[i]);
			link.setPriority(priorities[i]);
		}

	}

	@Override
    public CmsFriendlink deleteById(Integer id) {
		CmsFriendlink bean = dao.deleteById(id);
		return bean;
	}

	@Override
    public CmsFriendlink[] deleteByIds(Integer[] ids) {
		CmsFriendlink[] beans = new CmsFriendlink[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	private CmsFriendlinkDao dao;
	@Autowired
	private CmsFriendlinkCtgMng cmsFriendlinkCtgMng;
}