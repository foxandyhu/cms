package com.bfly.cms.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bfly.core.base.dao.impl.Finder;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.cms.user.dao.CmsRoleDao;
import com.bfly.cms.user.entity.CmsRole;

@Repository
public class CmsRoleDaoImpl extends AbstractHibernateBaseDao<CmsRole, Integer>
		implements CmsRoleDao {

	@Override
	public List<CmsRole> getList(Integer topLevel) {
		String hql = "from CmsRole bean ";
		Finder f=Finder.create(hql);
		if(topLevel!=null){
			f.append(" where bean.level<=:topLevel").setParam("topLevel", topLevel);
		}
		f.append(" order by bean.priority asc");
		return find(f);
	}

	@Override
    public CmsRole findById(Integer id) {
		CmsRole entity = get(id);
		return entity;
	}

	@Override
    public CmsRole save(CmsRole bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsRole deleteById(Integer id) {
		CmsRole entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsRole> getEntityClass() {
		return CmsRole.class;
	}
}