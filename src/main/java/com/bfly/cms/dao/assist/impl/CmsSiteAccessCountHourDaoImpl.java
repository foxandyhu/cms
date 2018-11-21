package com.bfly.cms.dao.assist.impl;

import java.util.Date;
import java.util.List;

import com.bfly.common.hibernate4.AbstractHibernateBaseDao;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.bfly.common.hibernate4.Finder;
import com.bfly.common.page.Pagination;
import com.bfly.cms.dao.assist.CmsSiteAccessCountHourDao;
import com.bfly.cms.entity.assist.CmsSiteAccessCountHour;

@Repository
public class CmsSiteAccessCountHourDaoImpl extends AbstractHibernateBaseDao<CmsSiteAccessCountHour, Integer> implements CmsSiteAccessCountHourDao {
	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}
	
	@Override
    public List<CmsSiteAccessCountHour> getList(Date date){
		String hql="from  CmsSiteAccessCountHour bean ";
		Finder f=Finder.create(hql);
		if(date!=null){
			f.append(" where bean.accessDate=:date").setParam("date", date);
		}
		f.setCacheable(true);
		return find(f);
	}

	@Override
    public CmsSiteAccessCountHour findById(Integer id) {
		CmsSiteAccessCountHour entity = get(id);
		return entity;
	}

	@Override
    public CmsSiteAccessCountHour save(CmsSiteAccessCountHour bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsSiteAccessCountHour deleteById(Integer id) {
		CmsSiteAccessCountHour entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsSiteAccessCountHour> getEntityClass() {
		return CmsSiteAccessCountHour.class;
	}
}