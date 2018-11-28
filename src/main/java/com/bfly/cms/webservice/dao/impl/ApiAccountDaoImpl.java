package com.bfly.cms.webservice.dao.impl;

import java.util.List;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.bfly.core.base.dao.impl.Finder;
import com.bfly.common.page.Pagination;
import com.bfly.cms.webservice.dao.ApiAccountDao;
import com.bfly.cms.webservice.entity.ApiAccount;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 15:22
 */
@Repository
public class ApiAccountDaoImpl extends AbstractHibernateBaseDao<ApiAccount, Integer> implements ApiAccountDao {

	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria c = createCriteria();
		return findByCriteria(c, pageNo, pageSize);
	}
	
	@Override
    public List<ApiAccount> getList(int first, int count){
		String hql="from ApiAccount bean";
		Finder f=Finder.create(hql);
		return  find(f);
	}
	
	@Override
    public ApiAccount findByAppId(String appId){
		String hql="from ApiAccount bean where bean.appId=:appId";
		Finder f=Finder.create(hql).setParam("appId", appId);
		List<ApiAccount>li=find(f);
		return li.size()>0?li.get(0):null;
	}
	
	@Override
    public ApiAccount findAdmin(){
		String hql="from ApiAccount bean where bean.admin=true";
		Finder f=Finder.create(hql);
		List<ApiAccount>li=find(f);
		return li!=null&&li.size()>0?li.get(0):null;
	}

	@Override
    public ApiAccount findById(Integer id) {
		return get(id);
	}

	@Override
    public ApiAccount save(ApiAccount bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public ApiAccount deleteById(Integer id) {
		ApiAccount entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<ApiAccount> getEntityClass() {
		return ApiAccount.class;
	}
}