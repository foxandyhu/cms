package com.bfly.cms.ad.dao.impl;

import com.bfly.cms.ad.dao.CmsAdvertisingSpaceDao;
import com.bfly.cms.ad.entity.CmsAdvertisingSpace;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.core.base.dao.impl.Finder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/4 11:24
 */
@Repository
public class CmsAdvertisingSpaceDaoImpl extends AbstractHibernateBaseDao<CmsAdvertisingSpace, Integer> implements CmsAdvertisingSpaceDao {

    @Override
    public List<CmsAdvertisingSpace> getList() {
        Finder f = Finder.create("from CmsAdvertisingSpace bean");
        return find(f);
    }

    @Override
    public CmsAdvertisingSpace findById(Integer id) {
        return get(id);
    }

    @Override
    public CmsAdvertisingSpace save(CmsAdvertisingSpace bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public CmsAdvertisingSpace deleteById(Integer id) {
        CmsAdvertisingSpace entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<CmsAdvertisingSpace> getEntityClass() {
        return CmsAdvertisingSpace.class;
    }
}