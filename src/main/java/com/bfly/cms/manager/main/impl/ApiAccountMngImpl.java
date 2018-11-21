package com.bfly.cms.manager.main.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.common.web.RequestUtils;
import com.bfly.cms.api.Constants;
import com.bfly.cms.dao.main.ApiAccountDao;
import com.bfly.cms.entity.main.ApiAccount;
import com.bfly.cms.manager.main.ApiAccountMng;

@Service
@Transactional
public class ApiAccountMngImpl implements ApiAccountMng {
	@Override
    @Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}
	
	@Override
    @Transactional(readOnly = true)
	public ApiAccount getApiAccount(HttpServletRequest request){
		String appId=RequestUtils.getQueryParam(request,Constants.COMMON_PARAM_APPID);
		ApiAccount apiAccount = null;
		if(StringUtils.isNotBlank(appId)){
			apiAccount=findByAppId(appId);
		}
		return apiAccount;
	}
	
	@Override
    @Transactional(readOnly = true)
	public ApiAccount findByDefault(){
		ApiAccount account=dao.findAdmin();
		if(account==null){
			List<ApiAccount>list=dao.getList(0, 1);
			account=list.get(0);
		}
		return account;
	}
	
	@Override
    @Transactional(readOnly = true)
	public ApiAccount findByAppId(String appId){
		return dao.findByAppId(appId);
	}

	@Override
    @Transactional(readOnly = true)
	public ApiAccount findById(Integer id) {
		ApiAccount entity = dao.findById(id);
		return entity;
	}

	@Override
    public ApiAccount save(ApiAccount bean) {
		dao.save(bean);
		return bean;
	}

	@Override
    public ApiAccount update(ApiAccount bean, String appKey, String aesKey, String ivKey) {
		Updater<ApiAccount> updater = new Updater<ApiAccount>(bean);
		if(StringUtils.isBlank(appKey)){
			updater.exclude("appKey");
		}
		if(StringUtils.isBlank(aesKey)){
			updater.exclude("aesKey");
		}
		if(StringUtils.isBlank(ivKey)){
			updater.exclude("ivKey");
		}
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Override
    public ApiAccount deleteById(Integer id) {
		ApiAccount bean = dao.deleteById(id);
		return bean;
	}
	
	@Override
    public ApiAccount[] deleteByIds(Integer[] ids) {
		ApiAccount[] beans = new ApiAccount[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	@Autowired
	private ApiAccountDao dao;
}