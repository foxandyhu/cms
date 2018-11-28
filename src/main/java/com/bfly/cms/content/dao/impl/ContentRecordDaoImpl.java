package com.bfly.cms.content.dao.impl;

import java.util.List;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.bfly.core.base.dao.impl.Finder;
import com.bfly.common.page.Pagination;
import com.bfly.cms.content.dao.ContentRecordDao;
import com.bfly.cms.content.entity.ContentRecord;

@Repository
public class ContentRecordDaoImpl extends AbstractHibernateBaseDao<ContentRecord, Long> implements ContentRecordDao {
	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	@Override
    public List<ContentRecord> getListByContentId(Integer contentId) {
		String hql=" select bean from ContentRecord bean where bean.content.id=:contentId";
		Finder f=Finder.create(hql).setParam("contentId", contentId);
		f.setCacheable(true);
		List<ContentRecord>list=find(f);
		return list;
	}

	@Override
    public ContentRecord findById(Long id) {
		ContentRecord entity = get(id);
		return entity;
	}

	@Override
    public ContentRecord save(ContentRecord bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public ContentRecord deleteById(Long id) {
		ContentRecord entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<ContentRecord> getEntityClass() {
		return ContentRecord.class;
	}
}