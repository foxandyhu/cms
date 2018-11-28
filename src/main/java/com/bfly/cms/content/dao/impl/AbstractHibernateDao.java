package com.bfly.cms.content.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bfly.cms.acquisition.dao.CmsAcquisitionShieldDao;
import com.bfly.cms.acquisition.entity.CmsAcquisitionShield;
import com.bfly.core.base.dao.impl.Finder;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;

@Component
public class AbstractHibernateDao extends AbstractHibernateBaseDao<CmsAcquisitionShield, Integer> implements CmsAcquisitionShieldDao {

    @Override
    public CmsAcquisitionShield save(CmsAcquisitionShield bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public List<CmsAcquisitionShield> getList(Integer acquisitionId) {
        Finder f = Finder.create("from CmsAcquisitionShield bean where 1 = 1");
        if (acquisitionId != null) {
            f.append(" and bean.acquisition.id = :acquisitionId");
            f.setParam("acquisitionId", acquisitionId);
        }
        return find(f);
    }

    @Override
    protected Class<CmsAcquisitionShield> getEntityClass() {
        return CmsAcquisitionShield.class;
    }


}
