package com.bfly.cms.dictionary.dao.impl;

import java.util.List;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bfly.core.base.dao.impl.Finder;
import com.bfly.common.page.Pagination;
import com.bfly.cms.dictionary.dao.CmsDictionaryDao;
import com.bfly.cms.dictionary.entity.CmsDictionary;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 18:54
 */
@Repository
public class CmsDictionaryDaoImpl extends AbstractHibernateBaseDao<CmsDictionary, Integer> implements CmsDictionaryDao {

	@Override
    public Pagination getPage(String queryType, int pageNo, int pageSize) {
		Criteria c = createCriteria();
		if(StringUtils.isNotBlank(queryType)){
			Criterion cron = Restrictions.like("type",queryType);
			c.add(cron);
		}
		return findByCriteria(c, pageNo, pageSize);
	}
	
	@Override
	public List<CmsDictionary> getList(String type){
		Criterion cron = Restrictions.like("type",type); 
		return findByCriteria(cron);
	}
	
	@Override
	public List<String> getTypeList(){
		Finder f=Finder.create("select  type from CmsDictionary dic group by type ");
		return find(f);
	}

	@Override
    public CmsDictionary findById(Integer id) {
		return get(id);
	}
	
	@Override
    public CmsDictionary save(CmsDictionary bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsDictionary deleteById(Integer id) {
		CmsDictionary entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
    public int countByValue(String value, String type) {
		String hql = "select count(*) from CmsDictionary bean where bean.value=:value and bean.type=:type";
		Query query = getSession().createQuery(hql);
		query.setParameter("value", value);
		query.setParameter("type", type);
		return ((Number) query.iterate().next()).intValue();
	}
	
	@Override
	protected Class<CmsDictionary> getEntityClass() {
		return CmsDictionary.class;
	}
}