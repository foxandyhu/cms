package com.bfly.cms.dao.assist.impl;

import java.util.Date;
import java.util.List;

import com.bfly.common.hibernate4.AbstractHibernateBaseDao;
import org.apache.commons.lang.StringUtils;

import com.bfly.cms.dao.assist.CmsMessageDao;
import com.bfly.cms.entity.assist.CmsMessage;
import com.bfly.common.hibernate4.Finder;
import com.bfly.common.page.Pagination;
import org.springframework.stereotype.Repository;

/**
 *江西金磊科技发展有限公司jeecms研发
 */
@Repository
public class CmsMessageDaoImpl extends AbstractHibernateBaseDao<CmsMessage, Integer>
		implements CmsMessageDao {

	@Override
    public Pagination getPage(Integer siteId, Integer sendUserId,
                              Integer receiverUserId, String title, Date sendBeginTime,
                              Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
                              int pageNo, int pageSize) {
		Finder finder=createFinder(siteId, sendUserId, receiverUserId, 
				title, sendBeginTime, sendEndTime,status, box, cacheable);
		return find(finder, pageNo, pageSize);
	}
	
	@Override
    public List<CmsMessage> getList(Integer siteId, Integer sendUserId,
                                    Integer receiverUserId, String title, Date sendBeginTime,
                                    Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
                                    Integer first, Integer count){
		Finder finder=createFinder(siteId, sendUserId, receiverUserId, 
				title, sendBeginTime, sendEndTime,status, box, cacheable);
		if(first!=null){
			finder.setFirstResult(first);
		}
		if(count!=null){
			finder.setMaxResults(count);
		}
		return find(finder);
	}
	
	
	@Override
    public CmsMessage findById(Integer id) {
		return super.get(id);
	}

	@Override
    public CmsMessage save(CmsMessage bean) {
		getSession().save(bean);
		return bean;
	}
	
	@Override
    public CmsMessage update(CmsMessage bean){
		getSession().update(bean);
		return bean;
	}

	@Override
    public CmsMessage deleteById(Integer id) {
		CmsMessage entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
    public CmsMessage[] deleteByIds(Integer[] ids) {
		CmsMessage[] messages = new CmsMessage[ids.length];
		for (int i = 0; i < ids.length; i++) {
			messages[i] = get(ids[i]);
			deleteById(ids[i]);
		}
		return messages;
	}
	
	private Finder createFinder(Integer siteId, Integer sendUserId,
			Integer receiverUserId, String title, Date sendBeginTime,
			Date sendEndTime, Boolean status, Integer box, Boolean cacheable){
		String hql = " select msg from CmsMessage msg where 1=1 ";
		Finder finder = Finder.create(hql);
		if (siteId != null) {
			finder.append(" and msg.site.id=:siteId")
					.setParam("siteId", siteId);
		}
		if(sendUserId != null&&receiverUserId != null){
			finder.append(" and (msg.msgSendUser.id=:sendUserId or msg.msgReceiverUser.id=:receiverUserId)").setParam(
					"sendUserId", sendUserId).setParam("receiverUserId", receiverUserId);
		}else{
			if (sendUserId != null) {
				finder.append(" and msg.msgSendUser.id=:sendUserId").setParam(
						"sendUserId", sendUserId);
			}
			if (receiverUserId != null) {
				finder.append(" and msg.msgReceiverUser.id=:receiverUserId")
						.setParam("receiverUserId", receiverUserId);
			}
		}
		
		if (StringUtils.isNotBlank(title)) {
			finder.append(" and msg.msgTitle like:title").setParam("title",
					"%" + title + "%");
		}
		if (sendBeginTime != null) {
			finder.append(" and msg.sendTime >=:sendBeginTime").setParam(
					"sendBeginTime", sendBeginTime);
		}
		if (sendEndTime != null) {
			finder.append(" and msg.sendTime <=:sendEndTime").setParam(
					"sendEndTime", sendEndTime);
		}
		if (status != null) {
			if (status) {
				finder.append(" and msg.msgStatus =true");
			} else {
				finder.append(" and msg.msgStatus =false");
			}
		}
		if (box != null) {
			finder.append(" and msg.msgBox =:box").setParam("box", box);
		}
		finder.append(" order by msg.id desc");
		return finder;
	}


	@Override
	protected Class<CmsMessage> getEntityClass() {
		return CmsMessage.class;
	}

}
