package com.bfly.cms.friendlink.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.friendlink.dao.CmsFriendlinkCtgDao;
import com.bfly.cms.friendlink.entity.CmsFriendlinkCtg;
import com.bfly.cms.friendlink.service.CmsFriendlinkCtgMng;
import com.bfly.common.hibernate4.Updater;

@Service
@Transactional(rollbackFor = Exception.class)
public class CmsFriendlinkCtgMngImpl implements CmsFriendlinkCtgMng {

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public List<CmsFriendlinkCtg> getList(Integer siteId) {
		return dao.getList(siteId);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public CmsFriendlinkCtg findById(Integer id) {
		CmsFriendlinkCtg entity = dao.findById(id);
		return entity;
	}

	@Override
    public CmsFriendlinkCtg save(CmsFriendlinkCtg bean) {
		dao.save(bean);
		return bean;
	}

	@Override
    public CmsFriendlinkCtg update(CmsFriendlinkCtg bean) {
		Updater<CmsFriendlinkCtg> updater = new Updater<CmsFriendlinkCtg>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Override
    public void updateFriendlinkCtgs(Integer[] ids, String[] names,
                                     Integer[] priorities) {
		if (ids == null || ids.length == 0) {
			return;
		}
		CmsFriendlinkCtg ctg;
		for (int i = 0; i < ids.length; i++) {
			ctg = dao.findById(ids[i]);
			ctg.setName(names[i]);
			ctg.setPriority(priorities[i]);
		}
	}

	@Override
    public CmsFriendlinkCtg deleteById(Integer id) {
		CmsFriendlinkCtg bean = dao.deleteById(id);
		return bean;
	}

	@Override
    public CmsFriendlinkCtg[] deleteByIds(Integer[] ids) {
		CmsFriendlinkCtg[] beans = new CmsFriendlinkCtg[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	private CmsFriendlinkCtgDao dao;
}