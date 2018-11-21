package com.bfly.cms.dao.main.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.bfly.common.hibernate4.Finder;
import com.bfly.common.hibernate4.AbstractHibernateBaseDao;
import com.bfly.common.page.Pagination;
import com.bfly.cms.dao.main.ApiInfoDao;
import com.bfly.cms.entity.main.ApiInfo;

@Repository
public class ApiInfoDaoImpl extends AbstractHibernateBaseDao<ApiInfo, Integer> implements ApiInfoDao {
	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}
	
	@Override
    public ApiInfo findByUrl(String url){
		String hql="from ApiInfo bean where bean.url=:url";
		Finder f=Finder.create(hql).setParam("url", url);
		List<ApiInfo>list=find(f);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
    public ApiInfo findById(Integer id) {
		ApiInfo entity = get(id);
		return entity;
	}

	@Override
    public ApiInfo save(ApiInfo bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public ApiInfo deleteById(Integer id) {
		ApiInfo entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<ApiInfo> getEntityClass() {
		return ApiInfo.class;
	}
}