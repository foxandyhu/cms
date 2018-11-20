package com.jeecms.cms.dao.assist.impl;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsVoteItemDao;
import com.jeecms.cms.entity.assist.CmsVoteItem;
import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import com.jeecms.common.page.Pagination;

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