package com.bfly.cms.friendlink.dao.impl;

import com.bfly.cms.friendlink.dao.CmsFriendlinkDao;
import com.bfly.cms.friendlink.entity.CmsFriendlink;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.core.base.dao.impl.Finder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:49
 */
@Repository
public class CmsFriendlinkDaoImpl extends
        AbstractHibernateBaseDao<CmsFriendlink, Integer> implements CmsFriendlinkDao {

    @Override
    public List<CmsFriendlink> getList(Integer siteId, Integer ctgId,
                                       Boolean enabled) {
        Finder f = Finder.create("from CmsFriendlink bean where 1=1");
        if (enabled != null) {
            f.append(" and bean.enabled=:enabled");
            f.setParam("enabled", enabled);
        }
        if (siteId != null) {
            f.append(" and bean.site.id=:siteId");
            f.setParam("siteId", siteId);
        }
        if (ctgId != null) {
            f.append(" and bean.category.id=:ctgId");
            f.setParam("ctgId", ctgId);
        }
        f.append(" order by bean.priority asc");
        f.setCacheable(true);
        return find(f);
    }

    @Override
    public CmsFriendlink findById(Integer id) {
        CmsFriendlink entity = get(id);
        return entity;
    }

    @Override
    public CmsFriendlink save(CmsFriendlink bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public CmsFriendlink deleteById(Integer id) {
        CmsFriendlink entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<CmsFriendlink> getEntityClass() {
        return CmsFriendlink.class;
    }
}