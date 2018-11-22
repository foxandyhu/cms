package com.bfly.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bfly.cms.dao.assist.CmsAcquisitionShieldDao;
import com.bfly.cms.entity.assist.CmsAcquisitionShield;
import com.bfly.common.hibernate4.Finder;
import com.bfly.common.hibernate4.AbstractHibernateBaseDao;

@Component
public class CmsAcquisitionShieldDaoImpl extends AbstractHibernateBaseDao<CmsAcquisitionShield, Integer> implements CmsAcquisitionShieldDao {

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
