package com.bfly.cms.ad.dao.impl;

import com.bfly.cms.ad.dao.CmsAdvertisingDao;
import com.bfly.cms.ad.entity.CmsAdvertising;
import com.bfly.common.page.Pagination;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.core.base.dao.impl.Finder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/4 11:01
 */
@Repository
public class CmsAdvertisingDaoImpl extends AbstractHibernateBaseDao<CmsAdvertising, Integer> implements CmsAdvertisingDao {

    @Override
    public Pagination getPage(Integer adspaceId, Boolean enabled, int pageNo, int pageSize) {
        Finder f = Finder.create("from CmsAdvertising bean where 1=1");
        if (adspaceId != null) {
            f.append(" and bean.adspace.id=:adspaceId");
            f.setParam("adspaceId", adspaceId);
        }
        if (enabled != null) {
            f.append(" and bean.enabled=:enabled");
            f.setParam("enabled", enabled);
        }
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    @Override
    public List<CmsAdvertising> getList(Integer adspaceId, Boolean enabled) {
        Finder f = Finder.create("from CmsAdvertising bean where 1=1");
        if (adspaceId != null) {
            f.append(" and bean.adspace.id=:adspaceId");
            f.setParam("adspaceId", adspaceId);
        }
        if (enabled != null) {
            f.append(" and bean.enabled=:enabled");
            f.setParam("enabled", enabled);
        }
        return find(f);
    }

    @Override
    public CmsAdvertising findById(Integer id) {
        return get(id);
    }

    @Override
    public CmsAdvertising save(CmsAdvertising bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public CmsAdvertising deleteById(Integer id) {
        CmsAdvertising entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<CmsAdvertising> getEntityClass() {
        return CmsAdvertising.class;
    }
}