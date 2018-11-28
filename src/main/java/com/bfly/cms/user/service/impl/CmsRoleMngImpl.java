package com.bfly.cms.user.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.common.hibernate4.Updater;
import com.bfly.cms.user.dao.CmsRoleDao;
import com.bfly.cms.user.entity.CmsRole;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.service.CmsRoleMng;
import com.bfly.cms.user.service.CmsUserMng;

@Service
@Transactional
public class CmsRoleMngImpl implements CmsRoleMng {
	@Override
    @Transactional(readOnly = true)
	public List<CmsRole> getList(Integer level) {
		return dao.getList(level);
	}

	@Override
    @Transactional(readOnly = true)
	public CmsRole findById(Integer id) {
		CmsRole entity = dao.findById(id);
		return entity;
	}

	@Override
    public CmsRole save(CmsRole bean, Set<String> perms) {
		bean.setPerms(perms);
		dao.save(bean);
		return bean;
	}

	@Override
    public CmsRole update(CmsRole bean, Set<String> perms) {
		Updater<CmsRole> updater = new Updater<CmsRole>(bean);
		bean = dao.updateByUpdater(updater);
		bean.setPerms(perms);
		return bean;
	}

	@Override
    public CmsRole deleteById(Integer id) {
		CmsRole bean = dao.deleteById(id);
		return bean;
	}

	@Override
    public CmsRole[] deleteByIds(Integer[] ids) {
		CmsRole[] beans = new CmsRole[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	
	@Override
    public void deleteMembers(CmsRole role, Integer[] userIds) {
		Updater<CmsRole> updater = new Updater<>(role);
		role = dao.updateByUpdater(updater);
		if (userIds != null) {
			CmsUser user;
			for (Integer uid : userIds) {
				user = userMng.findById(uid);
				role.delFromUsers(user);
				user.getRoles().remove(role);
			}
		}
	}

	private CmsRoleDao dao;
	@Autowired
	private CmsUserMng userMng;

	@Autowired
	public void setDao(CmsRoleDao dao) {
		this.dao = dao;
	}
}