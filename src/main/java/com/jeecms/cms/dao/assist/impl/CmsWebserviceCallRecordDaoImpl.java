package com.jeecms.cms.dao.assist.impl;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsWebserviceCallRecordDao;
import com.jeecms.cms.entity.assist.CmsWebserviceCallRecord;
import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import com.jeecms.common.page.Pagination;

@Repository
public class CmsWebserviceCallRecordDaoImpl extends AbstractHibernateBaseDao<CmsWebserviceCallRecord, Integer> implements CmsWebserviceCallRecordDao {
	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	@Override
    public CmsWebserviceCallRecord findById(Integer id) {
		CmsWebserviceCallRecord entity = get(id);
		return entity;
	}

	@Override
    public CmsWebserviceCallRecord save(CmsWebserviceCallRecord bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsWebserviceCallRecord deleteById(Integer id) {
		CmsWebserviceCallRecord entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsWebserviceCallRecord> getEntityClass() {
		return CmsWebserviceCallRecord.class;
	}
}