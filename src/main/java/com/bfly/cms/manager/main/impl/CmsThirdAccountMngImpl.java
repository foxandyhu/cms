package com.bfly.cms.manager.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.dao.main.CmsThirdAccountDao;
import com.bfly.cms.entity.main.CmsThirdAccount;
import com.bfly.cms.manager.main.CmsThirdAccountMng;

@Service
@Transactional
public class CmsThirdAccountMngImpl implements CmsThirdAccountMng {
	@Override
    @Transactional(readOnly = true)
	public Pagination getPage(String username,String source,int pageNo, int pageSize) {
		Pagination page = dao.getPage(username,source,pageNo, pageSize);
		return page;
	}

	@Override
    @Transactional(readOnly = true)
	public CmsThirdAccount findById(Long id) {
		CmsThirdAccount entity = dao.findById(id);
		return entity;
	}
	
	@Override
    @Transactional(readOnly = true)
	public CmsThirdAccount findByKey(String key){
		return dao.findByKey(key);
	}

	@Override
    public CmsThirdAccount save(CmsThirdAccount bean) {
		dao.save(bean);
		return bean;
	}

	@Override
    public CmsThirdAccount update(CmsThirdAccount bean) {
		Updater<CmsThirdAccount> updater = new Updater<CmsThirdAccount>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Override
    public CmsThirdAccount deleteById(Long id) {
		CmsThirdAccount bean = dao.deleteById(id);
		return bean;
	}
	
	@Override
    public CmsThirdAccount[] deleteByIds(Long[] ids) {
		CmsThirdAccount[] beans = new CmsThirdAccount[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	@Autowired
	private CmsThirdAccountDao dao;
}