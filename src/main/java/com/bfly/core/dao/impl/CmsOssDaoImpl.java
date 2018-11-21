package com.bfly.core.dao.impl;

import java.util.List;

import com.bfly.common.hibernate4.AbstractHibernateBaseDao;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.bfly.common.hibernate4.Finder;
import com.bfly.common.page.Pagination;
import com.bfly.core.dao.CmsOssDao;
import com.bfly.core.entity.CmsOss;

@Repository
public class CmsOssDaoImpl extends AbstractHibernateBaseDao<CmsOss, Integer> implements CmsOssDao {
	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}
	
	@Override
    public List<CmsOss> getList(){
		String hql="from CmsOss";
		Finder f=Finder.create(hql);
		f.setCacheable(true);
		return find(f);
	}

	@Override
    public CmsOss findById(Integer id) {
		CmsOss entity = get(id);
		return entity;
	}

	@Override
    public CmsOss save(CmsOss bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsOss deleteById(Integer id) {
		CmsOss entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsOss> getEntityClass() {
		return CmsOss.class;
	}
}