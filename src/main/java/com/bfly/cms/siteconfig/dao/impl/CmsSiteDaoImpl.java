package com.bfly.cms.siteconfig.dao.impl;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.cms.siteconfig.dao.CmsSiteDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/23 11:18
 */
@Repository
public class CmsSiteDaoImpl extends AbstractHibernateBaseDao<CmsSite, Integer>
        implements CmsSiteDao {


    @Override
    public List<CmsSite> getList(boolean cacheAble) {
        String hql = "from CmsSite bean order by bean.id asc";
        return getSession().createQuery(hql).setCacheable(cacheAble).list();
    }

    @Override
    public int getCountByProperty(String property) {
        String hql = "select count(distinct " + property + ") from CmsSite bean ";
        Query query = getSession().createQuery(hql);
        return ((Number) query.iterate().next()).intValue();
    }

    @Override
    public CmsSite findByDomain(String domain) {
        return findUniqueByProperty("domain", domain);
    }

    @Override
    public CmsSite findById(Integer id) {
        return get(id);
    }

    @Override
    public CmsSite save(CmsSite bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public CmsSite deleteById(Integer id) {
        CmsSite entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<CmsSite> getEntityClass() {
        return CmsSite.class;
    }
}