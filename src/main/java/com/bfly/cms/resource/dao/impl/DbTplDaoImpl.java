package com.bfly.cms.resource.dao.impl;

import java.util.List;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.bfly.cms.resource.dao.DbTplDao;
import com.bfly.cms.resource.entity.DbTpl;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 12:18
 */
@Repository
public class DbTplDaoImpl extends AbstractHibernateBaseDao<DbTpl, String> implements
		DbTplDao {

	@Override
	public List<DbTpl> getStartWith(String prefix) {
		StringUtils.replace(prefix, "_", "\\_");
		prefix = prefix + "%";
		String hql = "from DbTpl bean where bean.id like ? order by bean.id";
		return find(hql, prefix);
	}

	@Override
	public List<DbTpl> getChild(String path, boolean isDirectory) {
		StringUtils.replace(path, "_", "\\_");
		path = path + "/%";
		String notLike = path + "/%";
		String hql = "from DbTpl bean"
				+ " where bean.id like ? and bean.id not like ?"
				+ " and bean.directory=? order by bean.id";
		return find(hql, path, notLike, isDirectory);
	}

	@Override
    public DbTpl findById(String id) {
		return get(id);
	}

	@Override
    public DbTpl save(DbTpl bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public DbTpl deleteById(String id) {
		DbTpl entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<DbTpl> getEntityClass() {
		return DbTpl.class;
	}
}