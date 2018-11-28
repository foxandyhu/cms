package com.bfly.cms.friendlink.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bfly.cms.friendlink.dao.CmsFriendlinkCtgDao;
import com.bfly.cms.friendlink.entity.CmsFriendlinkCtg;
import com.bfly.core.base.dao.impl.Finder;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;

@Repository
public class CmsFriendlinkCtgDaoImpl extends
        AbstractHibernateBaseDao<CmsFriendlinkCtg, Integer> implements
		CmsFriendlinkCtgDao {

	@Override
	public List<CmsFriendlinkCtg> getList(Integer siteId) {
		Finder f = Finder.create("from CmsFriendlinkCtg bean");
		if (siteId != null) {
			f.append(" where bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		f.append(" order by bean.priority asc");
		f.setCacheable(true);
		return find(f);
	}

	@Override
    public CmsFriendlinkCtg findById(Integer id) {
		CmsFriendlinkCtg entity = get(id);
		return entity;
	}

	@Override
    public CmsFriendlinkCtg save(CmsFriendlinkCtg bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsFriendlinkCtg deleteById(Integer id) {
		CmsFriendlinkCtg entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsFriendlinkCtg> getEntityClass() {
		return CmsFriendlinkCtg.class;
	}
}