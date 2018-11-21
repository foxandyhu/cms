package com.bfly.cms.dao.main.impl;

import java.util.List;

import com.bfly.common.hibernate4.AbstractHibernateBaseDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bfly.cms.dao.main.ContentTagDao;
import com.bfly.cms.entity.main.ContentTag;
import com.bfly.common.hibernate4.Finder;
import com.bfly.common.page.Pagination;

@Repository
public class ContentTagDaoImpl extends AbstractHibernateBaseDao<ContentTag, Integer>
		implements ContentTagDao {
	@Override
    @SuppressWarnings("unchecked")
	public List<ContentTag> getList(Integer first,Integer count, boolean cacheable) {
		String hql = "from ContentTag bean order by bean.count desc";
		Query query = getSession().createQuery(hql);
		if (count != null) {
			query.setMaxResults(count);
		}
		if(first!=null){
			query.setFirstResult(first);
		}
		query.setCacheable(cacheable);
		return query.list();
	}

	@Override
    public Pagination getPage(String name, int pageNo, int pageSize,
                              boolean cacheable) {
		Finder f = Finder.create("from ContentTag bean");
		if (!StringUtils.isBlank(name)) {
			f.append(" where bean.name like :name");
			f.setParam("name", "%" + name + "%");
		}
		f.append(" order by bean.count desc");
		f.setCacheable(cacheable);
		return find(f, pageNo, pageSize);
	}

	@Override
    public ContentTag findById(Integer id) {
		ContentTag entity = get(id);
		return entity;
	}

	@Override
    public ContentTag findByName(String name, boolean cacheable) {
		String hql = "from ContentTag bean where bean.name=:name";
		return (ContentTag) getSession().createQuery(hql).setParameter("name",
				name).setCacheable(cacheable).uniqueResult();
	}

	@Override
    public ContentTag save(ContentTag bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public ContentTag deleteById(Integer id) {
		ContentTag entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
    public int deleteContentRef(Integer id) {
		Query query = getSession().createSQLQuery("delete from jc_contenttag where tag_id=?");
		return query.setParameter(0, id).executeUpdate();
	}

	@Override
    public int countContentRef(Integer id) {
		Query query = getSession().createSQLQuery("select count(*) from jc_contenttag where tag_id=?");
		return ((Number) (query.setParameter(0, id).list().iterator().next()))
				.intValue();
	}

	@Override
	protected Class<ContentTag> getEntityClass() {
		return ContentTag.class;
	}
}