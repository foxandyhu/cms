package com.bfly.core.manager;

import java.util.Date;
import java.util.List;

import com.bfly.common.page.Pagination;
import com.bfly.core.entity.CmsSite;
import com.bfly.core.entity.CmsSms;
import com.bfly.core.entity.CmsSmsRecord;
import com.bfly.core.entity.CmsUser;

public interface CmsSmsRecordMng {

	public Pagination getPage(Byte smsId,int pageNo, int pageSize, String phone, Integer validateType, String username, Date drawTimeBegin, Date drawTimeEnd);
	
	public List<CmsSmsRecord> getList(Integer smsId);

	public CmsSmsRecord findById(Integer id);

	public CmsSmsRecord save(CmsSmsRecord bean);

	public CmsSmsRecord updateByUpdater(CmsSmsRecord bean);

	public CmsSmsRecord deleteById(Integer id);
	
	public List<CmsSmsRecord> findByPhone(String phone);

	public CmsSmsRecord[] deleteByIds(Integer[] idArr);

	public CmsSmsRecord save(CmsSms sms, String mobilePhone,Integer smsSendType,CmsSite site,CmsUser user);
}
