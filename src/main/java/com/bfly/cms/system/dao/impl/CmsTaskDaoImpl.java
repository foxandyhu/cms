package com.bfly.cms.system.dao.impl;

import java.util.List;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.bfly.core.base.dao.impl.Finder;
import com.bfly.common.page.Pagination;
import com.bfly.cms.system.dao.CmsTaskDao;
import com.bfly.cms.system.entity.CmsTask;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 14:49
 */
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