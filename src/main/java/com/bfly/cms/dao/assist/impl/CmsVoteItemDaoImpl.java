package com.bfly.cms.dao.assist.impl;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.bfly.cms.dao.assist.CmsVoteItemDao;
import com.bfly.cms.entity.assist.CmsVoteItem;
import com.bfly.common.hibernate4.AbstractHibernateBaseDao;
import com.bfly.common.page.Pagination;

@Repository
public class CmsVoteItemDaoImpl extends AbstractHibernateBaseDao<CmsVoteItem, Integer>
		implements CmsVoteItemDao {
	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	@Override
    public CmsVoteItem findById(Integer id) {
		CmsVoteItem entity = get(id);
		return entity;
	}

	@Override
    public CmsVoteItem save(CmsVoteItem bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsVoteItem deleteById(Integer id) {
		CmsVoteItem entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsVoteItem> getEntityClass() {
		return CmsVoteItem.class;
	}
}