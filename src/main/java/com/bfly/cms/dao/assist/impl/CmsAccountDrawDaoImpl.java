package com.bfly.cms.dao.assist.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bfly.common.hibernate4.Finder;
import com.bfly.common.hibernate4.AbstractHibernateBaseDao;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.DateUtils;
import com.bfly.cms.dao.assist.CmsAccountDrawDao;
import com.bfly.cms.entity.assist.CmsAccountDraw;

@Repository
public class CmsAccountDrawDaoImpl extends AbstractHibernateBaseDao<CmsAccountDraw, Integer> implements CmsAccountDrawDao {
	@Override
    public Pagination getPage(Integer userId, Short applyStatus,
                              Date applyTimeBegin, Date applyTimeEnd, int pageNo, int pageSize) {
		Finder f=createFinder(userId, applyStatus, applyTimeBegin, applyTimeEnd);
		return find(f, pageNo, pageSize);
	}
	
	@Override
    public List<CmsAccountDraw> getList(Integer userId, Short applyStatus,
                                        Date applyTimeBegin, Date applyTimeEnd, Integer first, Integer count){
		Finder f=createFinder(userId, applyStatus, applyTimeBegin, applyTimeEnd);
		if(first!=null){
			f.setFirstResult(first);
		}
		if(count!=null){
			f.setMaxResults(count);
		}
		return find(f);
	}
	
	@Override
    public List<CmsAccountDraw> getList(Integer userId, Short[] status, Integer count){
		String hql="select bean  from CmsAccountDraw bean where 1=1 ";
		Finder f=Finder.create(hql);
		if(userId!=null){
			f.append(" and bean.drawUser.id=:userId").setParam("userId", userId);
		}
		if(status!=null){
			f.append(" and bean.applyStatus in(:status)").setParamList("status", status);
		}
		f.setCacheable(true);
		f.setMaxResults(count);
		return find(f);
	}

	@Override
    public CmsAccountDraw findById(Integer id) {
		CmsAccountDraw entity = get(id);
		return entity;
	}

	@Override
    public CmsAccountDraw save(CmsAccountDraw bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsAccountDraw deleteById(Integer id) {
		CmsAccountDraw entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	private Finder createFinder(Integer userId,Short applyStatus,
			Date applyTimeBegin,Date applyTimeEnd){
		String hql="select bean  from CmsAccountDraw bean where 1=1 ";
		Finder f=Finder.create(hql);
		if(userId!=null){
			if(userId!=0){
				f.append(" and bean.drawUser.id=:userId").setParam("userId", userId);
			}else{
				f.append(" and 1!=1");
			}
		}
		if(applyStatus!=null&&applyStatus!=-1){
			f.append(" and bean.applyStatus=:applyStatus").setParam("applyStatus", applyStatus);
		}
		if(applyTimeBegin!=null){
			f.append(" and bean.applyTime>=:applyTimeBegin")
			.setParam("applyTimeBegin", DateUtils.getStartDate(applyTimeBegin));
		}
		if(applyTimeEnd!=null){
			f.append(" and bean.applyTime<=:applyTimeEnd")
			.setParam("applyTimeEnd", DateUtils.getFinallyDate(applyTimeEnd));
		}
		f.setCacheable(true);
		return f;
	}
	
	@Override
	protected Class<CmsAccountDraw> getEntityClass() {
		return CmsAccountDraw.class;
	}
}