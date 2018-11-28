package com.bfly.cms.logs.dao.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bfly.core.base.dao.impl.Finder;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.common.page.Pagination;
import com.bfly.cms.logs.dao.CmsLogDao;
import com.bfly.cms.logs.entity.CmsLog;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 18:36
 */
@Repository
public class CmsLogDaoImpl extends AbstractHibernateBaseDao<CmsLog, Integer> implements
		CmsLogDao {
	@Override
    public Pagination getPage(Integer category, Integer siteId, Integer userId,
                              String title, String ip, int pageNo, int pageSize) {
		Finder f = Finder.create("from CmsLog bean where 1=1");
		if (category != null) {
			f.append(" and bean.category=:category");
			f.setParam("category", category);
		}
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (userId != null) {
			f.append(" and bean.user.id=:userId");
			f.setParam("userId", userId);
		}
		if (StringUtils.isNotBlank(title)) {
			f.append(" and bean.title like :title");
			f.setParam("title", "%" + title + "%");
		}
		if (StringUtils.isNotBlank(ip)) {
			f.append(" and bean.ip like :ip");
			f.setParam("ip", ip + "%");
		}
		f.append(" order by bean.id desc");
		return find(f, pageNo, pageSize);
	}

	@Override
    public CmsLog findById(Integer id) {
		return get(id);
	}

	@Override
    public CmsLog save(CmsLog bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsLog deleteById(Integer id) {
		CmsLog entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
    public int deleteBatch(Integer category, Integer siteId, Date before) {
		Finder f = Finder.create("delete CmsLog bean where 1=1");
		if (category != null) {
			f.append(" and bean.category=:category");
			f.setParam("category", category);
		}
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (before != null) {
			f.append(" and bean.time<=:before");
			f.setParam("before", before);
		}
		Query q = f.createQuery(getSession());
		return q.executeUpdate();
	}

	@Override
	protected Class<CmsLog> getEntityClass() {
		return CmsLog.class;
	}
}