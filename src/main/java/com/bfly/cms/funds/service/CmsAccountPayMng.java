package com.bfly.cms.funds.service;

import com.bfly.common.page.Pagination;
import com.bfly.cms.user.entity.CmsUser;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.bfly.cms.funds.entity.CmsAccountPay;

public interface CmsAccountPayMng {
	
	 String weixinTransferPay(String serverUrl,Integer drawId,
			CmsUser drawUser,CmsUser payUser,Double payAmount,String orderNum,
			HttpServletRequest request,HttpServletResponse response,
			ModelMap model);
	
	 Pagination getPage(String drawNum,Integer payUserId,Integer drawUserId,
			Date payTimeBegin,Date payTimeEnd,int pageNo, int pageSize);

	 CmsAccountPay findById(Long id);

	 CmsAccountPay save(CmsAccountPay bean);

	 CmsAccountPay update(CmsAccountPay bean);

	 CmsAccountPay deleteById(Long id);
	
	 CmsAccountPay[] deleteByIds(Long[] ids);
}