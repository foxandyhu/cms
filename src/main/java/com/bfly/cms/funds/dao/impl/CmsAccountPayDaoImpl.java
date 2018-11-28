package com.bfly.cms.funds.dao.impl;

import java.util.Date;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.bfly.core.base.dao.impl.Finder;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.DateUtils;
import com.bfly.cms.funds.dao.CmsAccountPayDao;
import com.bfly.cms.funds.entity.CmsAccountPay;

@Repository
public class CmsAccountPayDaoImpl extends AbstractHibernateBaseDao<CmsAccountPay, Long>
	implements CmsAccountPayDao {
	
	@Override
    public Pagination getPage(String drawNum, Integer payUserId, Integer drawUserId,
                              Date payTimeBegin, Date payTimeEnd, int pageNo, int pageSize) {
		String hql="from CmsAccountPay bean where 1=1 ";
		Finder f=Finder.create(hql);
		if(StringUtils.isNotBlank(drawNum)){
			f.append(" and bean.drawNum=:drawNum").setParam("drawNum", drawNum);
		}
		if(payUserId!=null){
			if(payUserId==0){
				f.append(" and 1!=1");
			}else{
				f.append(" and bean.payUser.id=:payUserId")
				.setParam("payUserId", payUserId);
			}
		}
		if(drawUserId!=null){
			if(drawUserId==0){
				f.append(" and 1!=1");
			}else{
				f.append(" and bean.drawUser.id=:drawUserId")
				.setParam("drawUserId", drawUserId);
			}
		}
		if(payTimeBegin!=null){
			f.append(" and bean.payTime>=:payTimeBegin")
			.setParam("payTimeBegin", DateUtils.getStartDate(payTimeBegin));
		}
		if(payTimeEnd!=null){
			f.append(" and bean.payTime<=:payTimeEnd")
			.setParam("payTimeEnd", DateUtils.getFinallyDate(payTimeEnd));
		}
		return find(f, pageNo, pageSize);
	}

	@Override
    public CmsAccountPay findById(Long id) {
		CmsAccountPay entity = get(id);
		return entity;
	}

	@Override
    public CmsAccountPay save(CmsAccountPay bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsAccountPay deleteById(Long id) {
		CmsAccountPay entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsAccountPay> getEntityClass() {
		return CmsAccountPay.class;
	}
}