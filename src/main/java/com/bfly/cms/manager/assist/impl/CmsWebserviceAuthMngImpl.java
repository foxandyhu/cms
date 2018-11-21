package com.bfly.cms.manager.assist.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.dao.assist.CmsWebserviceAuthDao;
import com.bfly.cms.entity.assist.CmsWebserviceAuth;
import com.bfly.cms.manager.assist.CmsWebserviceAuthMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.common.security.encoder.PwdEncoder;

@Service
@Transactional
public class CmsWebserviceAuthMngImpl implements CmsWebserviceAuthMng {
	@Override
    @Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}
	
	@Override
    @Transactional(readOnly = true)
	public boolean isPasswordValid(String username, String password){
		CmsWebserviceAuth auth=findByUsername(username);
		if(auth!=null&&auth.getEnable()){
			return pwdEncoder.isPasswordValid(auth.getPassword(), password);
		}else{
			return false;
		}
	}

	@Override
    @Transactional(readOnly = true)
	public CmsWebserviceAuth findByUsername(String username) {
		CmsWebserviceAuth entity = dao.findByUsername(username);
		return entity;
	}
	
	@Override
    @Transactional(readOnly = true)
	public CmsWebserviceAuth findById(Integer id) {
		CmsWebserviceAuth entity = dao.findById(id);
		return entity;
	}

	@Override
    public CmsWebserviceAuth save(CmsWebserviceAuth bean) {
		dao.save(bean);
		return bean;
	}

	@Override
    public CmsWebserviceAuth update(CmsWebserviceAuth bean) {
		Updater<CmsWebserviceAuth> updater = new Updater<CmsWebserviceAuth>(bean);
		CmsWebserviceAuth entity = dao.updateByUpdater(updater);
		return entity;
	}
	
	@Override
    public CmsWebserviceAuth update(Integer id, String username, String password, String system, Boolean enable){
		CmsWebserviceAuth entity =findById(id);
		if(StringUtils.isNotBlank(username)){
			entity.setUsername(username);
		}
		if(StringUtils.isNotBlank(password)){
			entity.setPassword(pwdEncoder.encodePassword(password));
		}
		if(StringUtils.isNotBlank(system)){
			entity.setSystem(system);
		}
		if(enable!=null){
			entity.setEnable(enable);
		}
		return entity;
	}

	@Override
    public CmsWebserviceAuth deleteById(Integer id) {
		CmsWebserviceAuth bean = dao.deleteById(id);
		return bean;
	}
	
	@Override
    public CmsWebserviceAuth[] deleteByIds(Integer[] ids) {
		CmsWebserviceAuth[] beans = new CmsWebserviceAuth[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	
	@Autowired
	private PwdEncoder pwdEncoder;
	@Autowired
	private CmsWebserviceAuthDao dao;
}