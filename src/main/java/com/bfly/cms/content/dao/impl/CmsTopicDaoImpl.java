package com.bfly.cms.content.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bfly.cms.content.dao.CmsTopicDao;
import com.bfly.cms.content.entity.CmsTopic;
import com.bfly.core.base.dao.impl.Finder;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.common.page.Pagination;

@Repository
public class CmsTopicDaoImpl extends AbstractHibernateBaseDao<CmsTopic, Integer>
		implements CmsTopicDao {

	@Override
	public List<CmsTopic> getList(Integer channelId, boolean recommend,
			Integer first,Integer count, boolean cacheable) {
		Finder f = Finder.create("select bean from CmsTopic bean ");
		if (channelId != null) {
			f.append(" join bean.channels channel where channel.id=:channelId");
			f.setParam("channelId", channelId);
		}else{
			f.append(" where 1=1 ");
		}
		if (recommend) {
			f.append(" and bean.recommend=true");
		}
		f.append(" order by bean.priority asc,bean.id desc");
		if(first!=null){
			f.setFirstResult(first);
		}
		if (count != null) {
			f.setMaxResults(count);
		}
		f.setCacheable(cacheable);
		return find(f);
	}

	@Override
    public Pagination getPage(Integer channelId, String initials, boolean recommend, int pageNo,
                              int pageSize, boolean cacheable) {
		Finder f = Finder.create("select bean from CmsTopic bean ");
		if (channelId != null) {
			f.append(" join bean.channels channel where channel.id=:channelId");
			f.setParam("channelId", channelId);
		}else{
			f.append(" where 1=1 ");
		}
		if (recommend) {
			f.append(" and bean.recommend=true");
		}
		if (StringUtils.isNotBlank(initials)) {
			f.append(" and bean.initials like :initials").setParam("initials", "%"+initials+"%");;
		}
		f.append(" order by bean.priority asc,bean.id desc");
		return find(f, pageNo, pageSize);
	}

	@Override
    @SuppressWarnings("unchecked")
	public List<CmsTopic> getListByChannelIds(Integer[] channelIds) {
		String hql = "select bean from CmsTopic bean join bean.channels channel where channel.id in (:ids) order by bean.id asc";
		return getSession().createQuery(hql)
				.setParameterList("ids", channelIds).list();
	}

	@Override
	public List<CmsTopic> getGlobalTopicList() {
		String hql = "select bean from CmsTopic bean left join bean.channels channel where  channel is null"
				+ " order by bean.priority asc,bean.id desc";
		return find(hql);
	}

	@Override
    public CmsTopic findById(Integer id) {
		CmsTopic entity = get(id);
		return entity;
	}

	@Override
    public CmsTopic save(CmsTopic bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsTopic deleteById(Integer id) {
		CmsTopic entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
    public int deleteContentRef(Integer id) {
		Query query = getSession().createSQLQuery("delete from jc_content_topic where topic_id=?");
		return query.setParameter(0, id).executeUpdate();
	}

	@Override
    public int countByChannelId(Integer channelId) {
		String hql = "select count(*) from CmsTopic bean join bean.channels channel"
				+ " where channel.id=:channelId";
		Query query = getSession().createQuery(hql);
		query.setParameter("channelId", channelId);
		return ((Number) query.iterate().next()).hashCode();
	}

	@Override
	protected Class<CmsTopic> getEntityClass() {
		return CmsTopic.class;
	}
}