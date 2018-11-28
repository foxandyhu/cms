package com.bfly.cms.webservice.service.impl;

import com.bfly.core.Constants;
import com.bfly.cms.webservice.dao.ApiAccountDao;
import com.bfly.cms.webservice.entity.ApiAccount;
import com.bfly.cms.webservice.service.ApiAccountMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.common.web.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 15:20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiAccountMngImpl implements ApiAccountMng {

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public Pagination getPage(int pageNo, int pageSize) {
		return  dao.getPage(pageNo, pageSize);
	}
	
	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public ApiAccount getApiAccount(HttpServletRequest request){
		String appId=RequestUtils.getQueryParam(request,Constants.COMMON_PARAM_APPID);
		ApiAccount apiAccount = null;
		if(StringUtils.isNotBlank(appId)){
			apiAccount=findByAppId(appId);
		}
		return apiAccount;
	}
	
	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public ApiAccount findByDefault(){
		ApiAccount account=dao.findAdmin();
		if(account==null){
			List<ApiAccount>list=dao.getList(0, 1);
			account=list.get(0);
		}
		return account;
	}
	
	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public ApiAccount findByAppId(String appId){
		return dao.findByAppId(appId);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public ApiAccount findById(Integer id) {
		return dao.findById(id);
	}

	@Override
    public ApiAccount save(ApiAccount bean) {
		return dao.save(bean);
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
		return dao.updateByUpdater(updater);
	}

	@Override
    public ApiAccount deleteById(Integer id) {
		return dao.deleteById(id);
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