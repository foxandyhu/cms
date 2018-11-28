package com.bfly.cms.siteconfig.service.impl;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.service.CmsSiteCompanyMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.cms.siteconfig.dao.CmsSiteCompanyDao;
import com.bfly.cms.siteconfig.entity.CmsSiteCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/23 10:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsSiteCompanyMngImpl implements CmsSiteCompanyMng {

    @Override
    public CmsSiteCompany save(CmsSite site, CmsSiteCompany bean) {
        site.setSiteCompany(bean);
        bean.setSite(site);
        dao.save(bean);
        return bean;
    }

    @Override
    public CmsSiteCompany update(CmsSiteCompany bean) {
        Updater<CmsSiteCompany> updater = new Updater<>(bean);
        bean = dao.updateByUpdater(updater);
        return bean;
    }

    @Autowired
    private CmsSiteCompanyDao dao;
}