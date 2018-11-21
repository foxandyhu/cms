package com.bfly.core.manager;

import java.util.Date;
import java.util.Map;

import com.bfly.core.entity.CmsConfig;
import com.bfly.core.entity.CmsConfigAttr;
import com.bfly.core.entity.MarkConfig;
import com.bfly.core.entity.MemberConfig;

public interface CmsConfigMng {
	public CmsConfig get();
	
	public Integer getContentFreshMinute();

	public void updateCountCopyTime(Date d);

	public void updateCountClearTime(Date d);
	
	public void updateFlowClearTime(Date d);
	
	public void updateChannelCountClearTime(Date d);

	public CmsConfig update(CmsConfig bean);

	public MarkConfig updateMarkConfig(MarkConfig mark);

	public void updateMemberConfig(MemberConfig memberConfig);
	
	public void updateConfigAttr(CmsConfigAttr configAttr);
	
	public void updateSsoAttr(Map<String,String> ssoAttr);
	
	public void updateRewardFixAttr(Map<String,String> fixAttr);
}