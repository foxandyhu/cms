package com.jeecms.cms.dao.assist.impl;

import java.util.List;

import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.page.Pagination;
import com.jeecms.cms.dao.assist.CmsTaskDao;
import com.jeecms.cms.entity.assist.CmsTask;

@Repository
public class CmsTaskDaoImpl extends AbstractHibernateBaseDao<CmsTask, Integer> implements CmsTaskDao {
	@Override
    public Pagination getPage(Integer siteId, int pageNo, int pageSize) {
		Finder f=Finder.create("from CmsTask task where task.site.id=:siteId").setParam("siteId", siteId);
		Pagination page = find(f, pageNo, pageSize);
		return page;
	}
	
	@Override
    @SuppressWarnings("unchecked")
	public List<CmsTask> getList(){
		Finder f=Finder.create("from CmsTask");
		return find(f);
	}

	@Override
    public CmsTask findById(Integer id) {
		CmsTask entity = get(id);
		return entity;
	}

	@Override
    public CmsTask save(CmsTask bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsTask deleteById(Integer id) {
		CmsTask entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsTask> getEntityClass() {
		return CmsTask.class;
	}
}