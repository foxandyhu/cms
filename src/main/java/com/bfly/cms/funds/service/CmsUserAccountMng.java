package com.bfly.cms.funds.service;

import java.util.Date;

import com.bfly.common.page.Pagination;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.funds.entity.CmsUserAccount;

public interface CmsUserAccountMng {
	
	 Pagination getPage(String username,Date drawTimeBegin,Date drawTimeEnd,
			int orderBy,int pageNo,int pageSize);
	
	 CmsUserAccount findById(Integer userId);
	/**
	 * 平台转账给笔者
	 * @param drawAmout
	 * @param user
	 * @param payTime
	 * @return
	 */
	public CmsUserAccount payToAuthor(Double drawAmout, CmsUser user,Date payTime);
	
	/**
	 * 用户购买 笔者金额统计
	 * @param payAmout
	 * @param user
	 * @return
	 */
	public CmsUserAccount userPay(Double payAmout, CmsUser user);
	
	public CmsUserAccount updateAccountInfo(String accountWeiXin,
			String accountAlipy, Short drawAccount,CmsUser user);
	
	public CmsUserAccount updateWeiXinOpenId(Integer userId,String weiXinOpenId);
}