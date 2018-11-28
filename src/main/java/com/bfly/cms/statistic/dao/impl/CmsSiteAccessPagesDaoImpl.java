package com.bfly.cms.statistic.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bfly.cms.statistic.dao.CmsSiteAccessPagesDao;
import com.bfly.cms.statistic.entity.CmsSiteAccessPages;
import com.bfly.core.base.dao.impl.Finder;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.common.page.Pagination;

/**
 * @author Tom
 */
@Repository
public class CmsSiteAccessPagesDaoImpl extends
        AbstractHibernateBaseDao<CmsSiteAccessPages, Integer> implements
		CmsSiteAccessPagesDao {

	@Override
	public CmsSiteAccessPages findAccessPage(String sessionId, Integer pageIndex) {
		Finder f = Finder.create("from CmsSiteAccessPages bean where bean.sessionId=:sessionId and bean.pageIndex=:pageIndex")
				.setParam("sessionId", sessionId).setParam("pageIndex",
						pageIndex);
		List<CmsSiteAccessPages> pages = find(f);
		if (pages != null && pages.size() > 0) {
			return pages.get(0);
		} else {
			return null;
		}
	}
	
	@Override
    public Pagination findPages(Integer siteId, Integer orderBy, Integer pageNo, Integer pageSize){
		Finder f = Finder.create("select bean.accessPage,count(bean.accessPage),count(distinct bean.sessionId),sum(bean.visitSecond)/count(bean.accessPage) " +
				"from CmsSiteAccessPages bean where bean.site.id=:siteId").setParam("siteId", siteId);
		f.append(" group by bean.accessPage ");
		String totalHql="select count(distinct bean.accessPage) from CmsSiteAccessPages bean where bean.site.id=:siteId";
		if(orderBy!=null){
			if(orderBy==2){
				//访客数降序
				f.append(" order by count(distinct bean.sessionId) desc");
			}else if(orderBy==3){
				//每次停留时间降序
				f.append(" order by sum(bean.visitSecond)/count(bean.accessPage) desc");
			}else{
				//pv降序
				f.append(" order by count(bean.accessPage) desc");
			}
		}else{
			f.append(" order by count(bean.accessPage) desc");
		}
		return find(f,totalHql,pageNo,pageSize);
	}

	@Override
    public void clearByDate(Date date) {
		//只保留当天数据
		String hql="delete from CmsSiteAccessPages bean where bean.accessDate!=:date";
		getSession().createQuery(hql).setParameter("date",date).executeUpdate();
	}

	@Override
    public CmsSiteAccessPages save(CmsSiteAccessPages bean) {
		getSession().save(bean);
		return bean;
	}


	@Override
    protected Class<CmsSiteAccessPages> getEntityClass() {
		return CmsSiteAccessPages.class;
	}

}
