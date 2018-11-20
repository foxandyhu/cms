package com.jeecms.cms.dao.assist.impl;

import java.util.List;

import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsVoteSubTopicDao;
import com.jeecms.cms.entity.assist.CmsVoteSubTopic;
import com.jeecms.common.hibernate4.Finder;

@Repository
public class CmsVoteSubTopicDaoImpl extends
        AbstractHibernateBaseDao<CmsVoteSubTopic, Integer> implements CmsVoteSubTopicDao {
	
	@Override
    @SuppressWarnings("unchecked")
	public List<CmsVoteSubTopic> findByVoteTopic(Integer voteTopicId){
		String hql="select bean from CmsVoteSubTopic bean";
		Finder finder=Finder.create(hql);
		if(voteTopicId!=null){
			finder.append(" where bean.voteTopic.id=:voteTopicId").setParam("voteTopicId", voteTopicId);
		}
		finder.append(" order by  bean.priority asc,bean.id desc");
		finder.setCacheable(true);
		return find(finder);
	}
	
	@Override
    public CmsVoteSubTopic findById(Integer id) {
		CmsVoteSubTopic entity = get(id);
		return entity;
	}

	@Override
    public CmsVoteSubTopic save(CmsVoteSubTopic bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsVoteSubTopic deleteById(Integer id) {
		CmsVoteSubTopic entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsVoteSubTopic> getEntityClass() {
		return CmsVoteSubTopic.class;
	}
}