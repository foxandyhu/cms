package com.bfly.cms.statistic.dao.impl;

import java.util.Date;
import java.util.List;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.bfly.cms.statistic.dao.CmsSiteAccessCountDao;
import com.bfly.cms.statistic.entity.CmsSiteAccessCount;
import com.bfly.core.base.dao.impl.Finder;

/**
 * @author Tom
 */
@Repository
public class CmsSiteAccessCountDaoImpl extends
        AbstractHibernateBaseDao<CmsSiteAccessCount, Integer> implements
		CmsSiteAccessCountDao {


	@Override
	public List<Object[]> statisticVisitorCountByDate(Integer siteId,Date begin,Date end){
		String hql="select sum(bean.visitors),bean.pageCount from CmsSiteAccessCount bean where bean.site.id=:siteId";
		Finder f=Finder.create(hql).setParam("siteId", siteId);
		if(begin!=null){
			f.append(" and bean.statisticDate>=:begin").setParam("begin", begin);
		}
		if(end!=null){
			f.append(" and bean.statisticDate<=:end").setParam("end", end);
		}
		f.append(" group by  bean.pageCount");
		return find(f);
	}

	@Override
	public List<Object[]> statisticVisitorCountByYear(Integer siteId,Integer year) {
		String hql="select sum(bean.visitors),bean.pageCount from CmsSiteAccessCount bean where bean.site.id=:siteId";
		Finder f=Finder.create(hql).setParam("siteId", siteId);
		if(year!=null){
			f.append(" and  year(bean.statisticDate)=:year").setParam("year", year);
		}
		f.append(" group by  bean.pageCount");
		return find(f);
	}
	
	

	@Override
    public CmsSiteAccessCount save(CmsSiteAccessCount bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    protected Class<CmsSiteAccessCount> getEntityClass() {
		return CmsSiteAccessCount.class;
	}

}
