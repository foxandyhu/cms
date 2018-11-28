package com.bfly.cms.weixin.dao.impl;

import java.util.List;

import com.bfly.core.base.dao.impl.Finder;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.common.page.Pagination;
import com.bfly.cms.weixin.dao.WeixinMessageDao;
import com.bfly.cms.weixin.service.WeixinMessage;
import org.springframework.stereotype.Repository;

@Repository
public class WeixinMessageDaoImpl extends AbstractHibernateBaseDao<WeixinMessage, Integer> implements WeixinMessageDao {
	
	@Override
    public Pagination getPage(Integer siteId, int pageNo, int pageSize){
		Finder f = Finder.create(" from WeixinMessage bean where bean.site.id=:siteId and bean.welcome=false").setParam("siteId", siteId);
		return find(f,pageNo,pageSize);
	}
	
	@Override
    public List<WeixinMessage> getList(Integer siteId){
		Finder f = Finder.create(" from WeixinMessage bean where bean.site.id=:siteId and bean.welcome=false order by bean.number");
		f.setParam("siteId", siteId);
		return find(f);
	}
	
	@Override
    public WeixinMessage getWelcome(Integer siteId){
		Finder f = Finder.create(" from WeixinMessage bean where bean.site.id=:siteId and bean.welcome=true order by bean.number");
		f.setParam("siteId", siteId);
		List<WeixinMessage> lists = find(f);
		if(lists!=null && lists.size()>0){
			return lists.get(0);
		}
		return null;
	}
	
	@Override
    public WeixinMessage findByNumber(String number, Integer siteId){
		Finder f = Finder.create(" from WeixinMessage bean where bean.site.id=:siteId and bean.number=:number order by bean.id desc");
		f.setParam("number", number);
		f.setParam("siteId", siteId);
		List<WeixinMessage> lists = find(f);
		if(lists!=null && lists.size()>0){
			return lists.get(0);
		}
		return null;
	}
	
	@Override
    public WeixinMessage findById(Integer id){
		return get(id);
	}
	
	@Override
    public WeixinMessage save(WeixinMessage bean){
		getSession().save(bean);
		return bean;
	}
	
	@Override
    public WeixinMessage deleteById(Integer id){
		WeixinMessage entity = get(id);
		if(entity!=null){
			getSession().delete(entity);
			return entity;
		}
		return null;
	}

	@Override
	protected Class<WeixinMessage> getEntityClass() {
		return WeixinMessage.class;
	}

}
