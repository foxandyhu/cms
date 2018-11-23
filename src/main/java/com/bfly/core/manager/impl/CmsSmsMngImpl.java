package com.bfly.core.manager.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.core.dao.CmsSmsDao;
import com.bfly.core.entity.CmsConfig;
import com.bfly.core.entity.CmsSms;
import com.bfly.core.manager.CmsConfigMng;
import com.bfly.core.manager.CmsSmsMng;
@Service
@Transactional
public class CmsSmsMngImpl implements CmsSmsMng {

	@Override
    @Transactional(readOnly = true)
	public Pagination getPage(Byte source,int pageNo, int pageSize) {
		Pagination page = dao.getPage(source,pageNo, pageSize);
		return page;
	}

	@Override
    @Transactional(readOnly = true)
	public List<CmsSms> getList() {
		return dao.getList();
	}

	@Override
	public CmsSms findById(Integer id) {
		CmsSms entity = dao.findById(id);
		return entity;
	}

	@Override
	public CmsSms save(CmsSms bean) {
		dao.save(bean);
		return bean;
	}

	@Override
	public CmsSms update(CmsSms bean) {
		Updater<CmsSms> updater = new Updater<CmsSms>(bean);
		if (StringUtils.isBlank(bean.getAccessKeyId())) {
			updater.exclude("accessKeyId");
		}
		if (StringUtils.isBlank(bean.getAccessKeySecret())) {
			updater.exclude("accessKeySecret");
		}
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Override
	public CmsSms deleteById(Integer id) {
		CmsSms bean = dao.deleteById(id);
		return bean;
	}

	@Override
	public CmsSms[] deleteByIds(Integer[] ids) {
		CmsSms[] beans = new CmsSms[ids.length];
		for (int i = 0; i < beans.length; i++) {
			//查询设置中是否引用了当前配置
			CmsConfig cmsConfig = manager.get();
			Long smsID = cmsConfig.getSmsID();
			if(smsID != null) {
				if(Integer.valueOf(cmsConfig.getSmsID().toString()).equals(ids[i])) {
					cmsConfig.setSmsID(null);
					manager.update(cmsConfig);
				}				
			}
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	private CmsSmsDao dao;

	@Override
	public CmsSms findBySource(Byte source) {
		CmsSms sms = dao.findBySource(source);
		return sms;
	}
	@Autowired
	private CmsConfigMng manager;
}