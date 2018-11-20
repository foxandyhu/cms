package com.jeecms.plug.weixin.dao.impl;

import java.util.List;

import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.page.Pagination;
import com.jeecms.plug.weixin.dao.WeixinDao;
import com.jeecms.plug.weixin.entity.Weixin;
import org.springframework.stereotype.Repository;

@Repository
public class WeixinDaoImpl extends AbstractHibernateBaseDao<Weixin, Integer> implements WeixinDao{
	
	@Override
    public Pagination getPage(Integer siteId, int pageNo, int pageSize){
		Finder f = Finder.create(" from Weixin bean where 1=1");
		f.append(" and bean.site.id=:siteId");
		f.setParam("siteId", siteId);
		return find(f,pageNo,pageSize);
	}
	
	@Override
    public Weixin findById(Integer id){
		return get(id);
	}
	
	@Override
    public Weixin find(Integer siteId){
		Finder f = Finder.create(" from Weixin bean where 1=1");
		f.append(" and bean.site.id=:siteId").setParam("siteId", siteId);
		List<Weixin> list = find(f);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
    public Weixin deleteById(Integer id){
		Weixin entity = get(id);
		if(entity!=null){
			getSession().delete(entity);
			return entity;
		}
		return null;
	}
	
	@Override
    public Weixin save(Weixin bean){
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<Weixin> getEntityClass() {
		return Weixin.class;
	}

}
