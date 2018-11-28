package com.bfly.cms.content.dao.impl;

import java.util.List;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.bfly.core.base.dao.impl.Finder;
import com.bfly.common.page.Pagination;
import com.bfly.cms.content.dao.CmsScoreRecordDao;
import com.bfly.cms.content.entity.CmsScoreRecord;

@Repository
public class CmsScoreRecordDaoImpl extends AbstractHibernateBaseDao<CmsScoreRecord, Integer> implements CmsScoreRecordDao {
	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}
	
	@Override
	public List<CmsScoreRecord> findListByContent(Integer contentId){
		String hql="from CmsScoreRecord bean where 1=1 ";
		Finder f=Finder.create(hql);
		if(contentId!=null){
			f.append(" and bean.content.id=:contentId").setParam("contentId", contentId);
		}
		return find(f);
	}
	
	@Override
    @SuppressWarnings("unchecked")
	public CmsScoreRecord findByScoreItemContent(Integer itemId,Integer contentId){
		String hql="from CmsScoreRecord bean where 1=1 ";
		Finder f=Finder.create(hql);
		if(itemId!=null){
			f.append(" and bean.item.id=:itemId").setParam("itemId", itemId);
		}
		if(contentId!=null){
			f.append(" and bean.content.id=:contentId").setParam("contentId", contentId);
		}
		List<CmsScoreRecord>list=find(f);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
    public CmsScoreRecord findById(Integer id) {
		CmsScoreRecord entity = get(id);
		return entity;
	}

	@Override
    public CmsScoreRecord save(CmsScoreRecord bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsScoreRecord deleteById(Integer id) {
		CmsScoreRecord entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsScoreRecord> getEntityClass() {
		return CmsScoreRecord.class;
	}
}