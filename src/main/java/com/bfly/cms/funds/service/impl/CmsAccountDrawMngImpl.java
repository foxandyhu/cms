package com.bfly.cms.funds.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.funds.dao.CmsAccountDrawDao;
import com.bfly.cms.funds.entity.CmsAccountDraw;
import com.bfly.cms.funds.service.CmsAccountDrawMng;

@Service
@Transactional
public class CmsAccountDrawMngImpl implements CmsAccountDrawMng {
	
	@Override
    public CmsAccountDraw draw(CmsUser user, Double amount, String applyAccount){
		CmsAccountDraw apply=new CmsAccountDraw();
		apply.setApplyAccount(applyAccount);
		apply.setApplyAmount(amount);
		apply.setApplyStatus(CmsAccountDraw.CHECKING);
		apply.setApplyTime(Calendar.getInstance().getTime());
		apply.setDrawUser(user);
		return save(apply);
	}
	
	@Override
    public Double getAppliedSum(Integer userId){
		Short[]status={0,1};
		Double applyAmoutTotal=0d;
		List<CmsAccountDraw>list=dao.getList(userId, status, 1000);
		for(CmsAccountDraw d:list){
			applyAmoutTotal+=d.getApplyAmount();
		}
		return applyAmoutTotal;
	}
	
	@Override
    @Transactional(readOnly = true)
	public Pagination getPage(Integer userId,Short applyStatus,
			Date applyTimeBegin,Date applyTimeEnd,int pageNo, int pageSize) {
		Pagination page = dao.getPage(userId,applyStatus
				,applyTimeBegin,applyTimeEnd,pageNo, pageSize);
		return page;
	}
	
	@Override
    @Transactional(readOnly = true)
	public List<CmsAccountDraw> getList(Integer userId,Short applyStatus,
			Date applyTimeBegin,Date applyTimeEnd,Integer first,Integer count){
		return dao.getList(userId, applyStatus, applyTimeBegin, 
				applyTimeEnd, first, count);
	}

	@Override
    @Transactional(readOnly = true)
	public CmsAccountDraw findById(Integer id) {
		CmsAccountDraw entity = dao.findById(id);
		return entity;
	}

	@Override
    public CmsAccountDraw save(CmsAccountDraw bean) {
		dao.save(bean);
		return bean;
	}

	@Override
    public CmsAccountDraw update(CmsAccountDraw bean) {
		Updater<CmsAccountDraw> updater = new Updater<CmsAccountDraw>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Override
    public CmsAccountDraw deleteById(Integer id) {
		CmsAccountDraw bean = dao.deleteById(id);
		return bean;
	}
	
	@Override
    public CmsAccountDraw[] deleteByIds(Integer[] ids) {
		CmsAccountDraw[] beans = new CmsAccountDraw[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	private CmsAccountDrawDao dao;
}