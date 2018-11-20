package com.jeecms.cms.dao.assist.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsWebserviceAuthDao;
import com.jeecms.cms.entity.assist.CmsWebserviceAuth;
import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import com.jeecms.common.page.Pagination;

@Repository
public class CmsWebserviceAuthDaoImpl extends AbstractHibernateBaseDao<CmsWebserviceAuth, Integer> implements CmsWebserviceAuthDao {
	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}
	
	@Override
    @SuppressWarnings("unchecked")
	public CmsWebserviceAuth findByUsername(String username){
		String hql="from CmsWebserviceAuth bean where bean.username=:username";
		Finder f=Finder.create(hql).setParam("username", username);
		f.setCacheable(true);
		List<CmsWebserviceAuth>list=find(f);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
    public CmsWebserviceAuth findById(Integer id) {
		CmsWebserviceAuth entity = get(id);
		return entity;
	}

	@Override
    public CmsWebserviceAuth save(CmsWebserviceAuth bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsWebserviceAuth deleteById(Integer id) {
		CmsWebserviceAuth entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsWebserviceAuth> getEntityClass() {
		return CmsWebserviceAuth.class;
	}
}