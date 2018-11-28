package com.bfly.cms.content.dao.impl;

import java.util.List;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bfly.cms.content.dao.ContentTypeDao;
import com.bfly.cms.content.entity.ContentType;
import com.bfly.core.base.dao.impl.Finder;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 10:34
 */
@Repository
public class ContentTypeDaoImpl extends AbstractHibernateBaseDao<ContentType, Integer>
		implements ContentTypeDao {

	@Override
	public List<ContentType> getList(Boolean isDisabled) {
		Finder f = Finder.create("from ContentType bean");
		if (isDisabled!=null) {
			f.append(" where bean.disabled=:disabled").setParam("disabled", isDisabled);
		}
		f.append(" order by bean.id asc");
		return find(f);
	}

	@Override
    public ContentType getDef() {
		String hql = "from ContentType bean"
				+ " where bean.disabled=false order by bean.id asc";
		Query query = getSession().createQuery(hql).setMaxResults(1);
		List<?> list = query.list();
		return list.size()>0?(ContentType) list.get(0):null;
	}

	@Override
    public ContentType findById(Integer id) {
		return get(id);
	}

	@Override
    public ContentType save(ContentType bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public ContentType deleteById(Integer id) {
		ContentType entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<ContentType> getEntityClass() {
		return ContentType.class;
	}
}