package com.bfly.cms.content.dao.impl;

import org.springframework.stereotype.Repository;

import com.bfly.core.base.dao.impl.Finder;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.common.page.Pagination;
import com.bfly.cms.content.dao.CmsScoreItemDao;
import com.bfly.cms.content.entity.CmsScoreItem;

@Repository
public class CmsScoreItemDaoImpl extends AbstractHibernateBaseDao<CmsScoreItem, Integer> implements CmsScoreItemDao {
	@Override
    public Pagination getPage(Integer groupId, int pageNo, int pageSize) {
		String hql="from CmsScoreItem item ";
		Finder f=Finder.create(hql);
		if(groupId!=null){
			f.append(" where item.group.id=:groupId").setParam("groupId", groupId);
		}
		return find(f, pageNo, pageSize);
	}

	@Override
    public CmsScoreItem findById(Integer id) {
		CmsScoreItem entity = get(id);
		return entity;
	}

	@Override
    public CmsScoreItem save(CmsScoreItem bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsScoreItem deleteById(Integer id) {
		CmsScoreItem entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsScoreItem> getEntityClass() {
		return CmsScoreItem.class;
	}
}