package com.bfly.cms.acquisition.dao.impl;

import com.bfly.cms.acquisition.dao.CmsAcquisitionDao;
import com.bfly.cms.acquisition.entity.CmsAcquisition;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.core.base.dao.impl.Finder;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CmsAcquisitionDaoImpl extends AbstractHibernateBaseDao<CmsAcquisition, Integer> implements CmsAcquisitionDao {

    @Override
    public List<CmsAcquisition> getList() {
        Finder f = Finder.create("from CmsAcquisition bean");
        f.append(" order by bean.id asc");
        return find(f);
    }

    @Override
    public CmsAcquisition findById(Integer id) {
        CmsAcquisition entity = get(id);
        return entity;
    }

    @Override
    public CmsAcquisition save(CmsAcquisition bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public CmsAcquisition deleteById(Integer id) {
        CmsAcquisition entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    public int countByChannelId(Integer channelId) {
        String hql = "select count(*) from CmsAcquisition bean"
                + " where bean.channel.id=:channelId";
        Query query = getSession().createQuery(hql);
        query.setParameter("channelId", channelId);
        return ((Number) query.iterate().next()).intValue();
    }

    @Override
    public CmsAcquisition getStarted() {
        Criteria crit = createCriteria(Restrictions.eq("status", CmsAcquisition.START)).setMaxResults(1);
        return (CmsAcquisition) crit.uniqueResult();
    }

    @Override
    public Integer getMaxQueue() {
        Query query = createQuery("select max(bean.queue) from CmsAcquisition bean");
        return ((Number) query.iterate().next()).intValue();
    }

    @Override
    public List<CmsAcquisition> getLargerQueues(Integer queueNum) {
        Finder f = Finder.create("from CmsAcquisition bean where bean.queue>:queueNum and bean.site.id=:siteId").setParam("queueNum", queueNum);
        return find(f);
    }

    @Override
    public CmsAcquisition popAcquFromQueue() {
        Query query = getSession().createQuery("from CmsAcquisition bean where bean.queue>0 order by bean.queue").setMaxResults(1);
        return (CmsAcquisition) query.uniqueResult();
    }

    @Override
    protected Class<CmsAcquisition> getEntityClass() {
        return CmsAcquisition.class;
    }

}