package com.bfly.cms.system.dao.impl;

import com.bfly.cms.system.dao.CmsConfigDao;
import com.bfly.cms.system.entity.CmsConfig;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/24 17:29
 */
@Repository
public class CmsConfigDaoImpl extends AbstractHibernateBaseDao<CmsConfig, Integer>
        implements CmsConfigDao {

    @Override
    public CmsConfig findById(Integer id) {
        return get(id);
    }

    @Override
    protected Class<CmsConfig> getEntityClass() {
        return CmsConfig.class;
    }
}