package com.bfly.cms.siteconfig.dao.impl;

import com.bfly.cms.siteconfig.dao.CmsSiteCompanyDao;
import com.bfly.cms.siteconfig.entity.CmsSiteCompany;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/23 13:56
 */
@Repository
public class CmsSiteCompanyDaoImpl extends
        AbstractHibernateBaseDao<CmsSiteCompany, Integer> implements CmsSiteCompanyDao {

    @Override
    public CmsSiteCompany save(CmsSiteCompany bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    protected Class<CmsSiteCompany> getEntityClass() {
        return CmsSiteCompany.class;
    }
}