package com.bfly.cms.dao.main.impl;

import java.util.List;

import com.bfly.common.hibernate4.AbstractHibernateBaseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bfly.cms.dao.main.ContentTypeDao;
import com.bfly.cms.entity.main.ContentType;
import com.bfly.common.hibernate4.Finder;

@Repository
public class ContentTypeDaoImpl extends AbstractHibernateBaseDao<ContentType, Integer>
		implements ContentTypeDao {
	@Override
    @SuppressWarnings("unchecked")
	public List<ContentType> getList(Boolean containDisabled) {
		Finder f = Finder.create("from ContentType bean");
		if (containDisabled!=null) {
			f.append(" where bean.disabled=:disabled").setParam("disabled", containDisabled);
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
		if (list.size() > 0) {
			return (ContentType) list.get(0);
		} else {
			return null;
		}
	}

	@Override
    public ContentType findById(Integer id) {
		ContentType entity = get(id);
		return entity;
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