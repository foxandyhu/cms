package com.bfly.core.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.common.hibernate4.Updater;
import com.bfly.core.dao.CmsSiteCompanyDao;
import com.bfly.core.entity.CmsSite;
import com.bfly.core.entity.CmsSiteCompany;
import com.bfly.core.manager.CmsSiteCompanyMng;

@Service
@Transactional
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
		Updater<CmsSiteCompany> updater = new Updater<CmsSiteCompany>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Autowired
	private CmsSiteCompanyDao dao;
}