package com.jeecms.plug.store.manager.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.common.hibernate4.Updater;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.security.encoder.PwdEncoder;
import com.jeecms.plug.store.dao.PlugStoreConfigDao;
import com.jeecms.plug.store.entity.PlugStoreConfig;
import com.jeecms.plug.store.manager.PlugStoreConfigMng;

@Service
@Transactional
public class PlugStoreConfigMngImpl implements PlugStoreConfigMng {
	@Override
    @Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}

	@Override
    @Transactional(readOnly = true)
	public PlugStoreConfig findById(Integer id) {
		PlugStoreConfig entity = dao.findById(id);
		return entity;
	}
	
	@Override
    @Transactional(readOnly = true)
	public PlugStoreConfig getDefault(){
		return findById(1);
	}

	@Override
    public PlugStoreConfig save(PlugStoreConfig bean) {
		dao.save(bean);
		return bean;
	}

	@Override
    public PlugStoreConfig update(PlugStoreConfig bean, String password) {
		Updater<PlugStoreConfig> updater = new Updater<PlugStoreConfig>(bean);
		if (StringUtils.isNotBlank(password)) {
			bean.setPassword(pwdEncoder.encodePassword(password));
		}else{
			updater.exclude("password");
		}
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Override
    public PlugStoreConfig deleteById(Integer id) {
		PlugStoreConfig bean = dao.deleteById(id);
		return bean;
	}
	
	@Override
    public PlugStoreConfig[] deleteByIds(Integer[] ids) {
		PlugStoreConfig[] beans = new PlugStoreConfig[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	private PlugStoreConfigDao dao;
	@Autowired
	private PwdEncoder pwdEncoder;
}