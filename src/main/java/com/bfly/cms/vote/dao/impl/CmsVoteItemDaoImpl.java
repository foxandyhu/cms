package com.bfly.cms.vote.dao.impl;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.bfly.cms.vote.dao.CmsVoteItemDao;
import com.bfly.cms.vote.entity.CmsVoteItem;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
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