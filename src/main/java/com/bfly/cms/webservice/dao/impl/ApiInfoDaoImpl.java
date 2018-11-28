package com.bfly.cms.webservice.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.bfly.core.base.dao.impl.Finder;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.common.page.Pagination;
import com.bfly.cms.webservice.dao.ApiInfoDao;
import com.bfly.cms.webservice.entity.ApiInfo;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 15:04
 */
@Repository
public class ApiInfoDaoImpl extends AbstractHibernateBaseDao<ApiInfo, Integer> implements ApiInfoDao {
	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria c = createCriteria();
		return findByCriteria(c, pageNo, pageSize);
	}
	
	@Override
    public ApiInfo findByUrl(String url){
		String hql="from ApiInfo bean where bean.url=:url";
		Finder f=Finder.create(hql).setParam("url", url);
		List<ApiInfo>list=find(f);
		return list.size()>0?list.get(0):null;
	}

	@Override
    public ApiInfo findById(Integer id) {
		return get(id);
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