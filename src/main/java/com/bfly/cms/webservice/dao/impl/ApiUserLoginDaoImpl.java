package com.bfly.cms.webservice.dao.impl;

import java.util.Date;
import java.util.List;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bfly.core.base.dao.impl.Finder;
import com.bfly.common.page.Pagination;
import com.bfly.cms.webservice.dao.ApiUserLoginDao;
import com.bfly.cms.webservice.entity.ApiUserLogin;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 15:53
 */
@Repository
public class ApiUserLoginDaoImpl extends AbstractHibernateBaseDao<ApiUserLogin, Long> implements ApiUserLoginDao {

	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria c = createCriteria();
		return findByCriteria(c, pageNo, pageSize);
	}
	
	@Override
    public void clearByDate(Date end){
		String hql="delete from ApiUserLogin bean where bean.activeTime<=:end";
		Query query = getSession().createQuery(hql).setParameter("end", end);
		query.executeUpdate();
	}
	
	@Override
    public List<ApiUserLogin> getList(Date end, int first, int count){
		Finder f=Finder.create("from  ApiUserLogin bean ");
		if(end!=null){
			f.append("where bean.loginTime<=:end").setParam("end", end);
		}
		f.setFirstResult(first);
		f.setMaxResults(count);
		f.setCacheable(true);
		return find(f);
	}

	@Override
    public ApiUserLogin findById(Long id) {
		return get(id);
	}
	
	@Override
    public ApiUserLogin findUserLogin(String username, String sessionKey){
		String hql="from ApiUserLogin bean where 1=1";
		Finder f=Finder.create(hql);
		if(StringUtils.isNotBlank(username)){
			f.append(" and bean.username=:username").setParam("username", username);
		}
		if(StringUtils.isNotBlank(sessionKey)){
			f.append(" and bean.sessionKey=:sessionKey").setParam("sessionKey", sessionKey);
		}
		List<ApiUserLogin>li=find(f);
		return li.size()>0?li.get(0):null;
	}

	@Override
    public ApiUserLogin save(ApiUserLogin bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public ApiUserLogin deleteById(Long id) {
		ApiUserLogin entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<ApiUserLogin> getEntityClass() {
		return ApiUserLogin.class;
	}
}