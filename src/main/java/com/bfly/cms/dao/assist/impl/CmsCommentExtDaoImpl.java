package com.bfly.cms.dao.assist.impl;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.bfly.cms.dao.assist.CmsCommentExtDao;
import com.bfly.cms.entity.assist.CmsCommentExt;
import com.bfly.common.hibernate4.AbstractHibernateBaseDao;
import com.bfly.common.page.Pagination;

@Repository
public class CmsCommentExtDaoImpl extends
        AbstractHibernateBaseDao<CmsCommentExt, Integer> implements CmsCommentExtDao {
	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	@Override
    public CmsCommentExt findById(Integer id) {
		CmsCommentExt entity = get(id);
		return entity;
	}

	@Override
    public CmsCommentExt save(CmsCommentExt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public int deleteByContentId(Integer contentId) {
		String hql = "delete from CmsCommentExt bean where bean.id in"
				+ " (select c.id from CmsComment c where c.content.id=:contentId)";
		return getSession().createQuery(hql).setParameter("contentId",
				contentId).executeUpdate();
	}

	@Override
    public CmsCommentExt deleteById(Integer id) {
		CmsCommentExt entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsCommentExt> getEntityClass() {
		return CmsCommentExt.class;
	}
}