package com.jeecms.cms.dao.assist.impl;

import java.util.List;

import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.page.Pagination;
import com.jeecms.cms.dao.assist.CmsDirectiveTplDao;
import com.jeecms.cms.entity.assist.CmsDirectiveTpl;

@Repository
public class CmsDirectiveTplDaoImpl extends AbstractHibernateBaseDao<CmsDirectiveTpl, Integer> implements CmsDirectiveTplDao {
	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}
	
	@Override
    @SuppressWarnings("unchecked")
	public List<CmsDirectiveTpl> getList(int count){
		String hql="from CmsDirectiveTpl";
		Finder f=Finder.create(hql);
		f.setFirstResult(0);
		f.setMaxResults(count);
		return find(f);
	}

	@Override
    public CmsDirectiveTpl findById(Integer id) {
		CmsDirectiveTpl entity = get(id);
		return entity;
	}

	@Override
    public CmsDirectiveTpl save(CmsDirectiveTpl bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsDirectiveTpl deleteById(Integer id) {
		CmsDirectiveTpl entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsDirectiveTpl> getEntityClass() {
		return CmsDirectiveTpl.class;
	}
}