package com.bfly.cms.vote.dao.impl;

import java.util.List;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.bfly.cms.vote.dao.CmsVoteRecordDao;
import com.bfly.cms.vote.entity.CmsVoteRecord;

@Repository
public class CmsVoteRecordDaoImpl extends
        AbstractHibernateBaseDao<CmsVoteRecord, Integer> implements CmsVoteRecordDao {

	@Override
    public CmsVoteRecord save(CmsVoteRecord bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public int deleteByTopic(Integer topicId) {
		String hql = "delete from CmsVoteRecord bean"
				+ " where bean.topic.id=:topicId";
		return getSession().createQuery(hql).setParameter("topicId", topicId)
				.executeUpdate();
	}

	@Override
    @SuppressWarnings("unchecked")
	public CmsVoteRecord findByUserId(Integer userId, Integer topicId) {
		String hql = "from CmsVoteRecord bean where bean.user.id=:userId"
				+ " and bean.topic.id=:topicId order by bean.time desc";
		List<CmsVoteRecord> list = getSession().createQuery(hql).setParameter(
				"userId", userId).setParameter("topicId", topicId)
				.setMaxResults(1).list();
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
    @SuppressWarnings("unchecked")
	public CmsVoteRecord findByIp(String ip, Integer topicId) {
		String hql = "from CmsVoteRecord bean where bean.ip=:ip"
				+ " and bean.topic.id=:topicId order by bean.time desc";
		List<CmsVoteRecord> list = getSession().createQuery(hql).setParameter(
				"ip", ip).setParameter("topicId", topicId).setMaxResults(1)
				.list();
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
    @SuppressWarnings("unchecked")
	public CmsVoteRecord findByCookie(String cookie, Integer topicId) {
		String hql = "from CmsVoteRecord bean where bean.cookie=:cookie"
				+ " and bean.topic.id=:topicId order by bean.time desc";
		List<CmsVoteRecord> list = getSession().createQuery(hql).setParameter(
				"cookie", cookie).setParameter("topicId", topicId)
				.setMaxResults(1).list();
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	protected Class<CmsVoteRecord> getEntityClass() {
		return CmsVoteRecord.class;
	}
}