package com.jeecms.cms.dao.assist.impl;

import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.page.Pagination;
import com.jeecms.cms.dao.assist.CmsScoreGroupDao;
import com.jeecms.cms.entity.assist.CmsScoreGroup;

@Repository
public class CmsScoreGroupDaoImpl extends AbstractHibernateBaseDao<CmsScoreGroup, Integer> implements CmsScoreGroupDao {
	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	@Override
    public CmsScoreGroup findById(Integer id) {
		CmsScoreGroup entity = get(id);
		return entity;
	}
	
	@Override
    public CmsScoreGroup findDefault(Integer siteId){
		Finder f = Finder.create("from CmsScoreGroup bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		f.append(" and bean.def=true");
		f.setMaxResults(1);
		return (CmsScoreGroup) f.createQuery(getSession()).uniqueResult();
	}

	@Override
    public CmsScoreGroup save(CmsScoreGroup bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsScoreGroup deleteById(Integer id) {
		CmsScoreGroup entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsScoreGroup> getEntityClass() {
		return CmsScoreGroup.class;
	}
}