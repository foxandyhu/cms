package com.jeecms.cms.dao.assist.impl;

import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsVoteReplyDao;
import com.jeecms.cms.entity.assist.CmsVoteReply;
import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.page.Pagination;

@Repository
public class CmsVoteReplyDaoImpl extends
        AbstractHibernateBaseDao<CmsVoteReply, Integer> implements CmsVoteReplyDao {
	
	@Override
    public Pagination getPage(Integer  subTopicId, int pageNo, int pageSize){
		String hql="select bean from CmsVoteReply bean";
		Finder f=Finder.create(hql);
		if(subTopicId!=null){
			f.append(" where bean.subTopic.id=:subTopicId").setParam("subTopicId", subTopicId);
		}
		f.setCacheable(true);
		return find(f, pageNo, pageSize);
	}
	
	@Override
    public CmsVoteReply findById(Integer id) {
		CmsVoteReply entity = get(id);
		return entity;
	}

	@Override
    public CmsVoteReply save(CmsVoteReply bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsVoteReply deleteById(Integer id) {
		CmsVoteReply entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsVoteReply> getEntityClass() {
		return CmsVoteReply.class;
	}
}