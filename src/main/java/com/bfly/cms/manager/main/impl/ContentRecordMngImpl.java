package com.bfly.cms.manager.main.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.core.entity.CmsUser;
import com.bfly.cms.dao.main.ContentRecordDao;
import com.bfly.cms.entity.main.Content;
import com.bfly.cms.entity.main.ContentRecord;
import com.bfly.cms.entity.main.ContentRecord.ContentOperateType;
import com.bfly.cms.manager.main.ContentRecordMng;

@Service
@Transactional
public class ContentRecordMngImpl implements ContentRecordMng {
	@Override
    @Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}
	
	@Override
    @Transactional(readOnly = true)
	public List<ContentRecord>getListByContentId(Integer contentId){
		return dao.getListByContentId(contentId);
	}

	@Override
    @Transactional(readOnly = true)
	public ContentRecord findById(Long id) {
		ContentRecord entity = dao.findById(id);
		return entity;
	}
	
	@Override
    public ContentRecord record(Content content, CmsUser user, ContentOperateType operate){
		ContentRecord record=new ContentRecord();
		record.setContent(content);
		record.setOperateTime(Calendar.getInstance().getTime());
		record.setUser(user);
		Byte operateByte = ContentRecord.ADD;
		if(operate==ContentOperateType.edit){
			operateByte=ContentRecord.EDIT;
		}else if(operate==ContentOperateType.cycle){
			operateByte=ContentRecord.CYCLE;
		}else if(operate==ContentOperateType.check){
			operateByte=ContentRecord.CHECK;
		}else if(operate==ContentOperateType.rejected){
			operateByte=ContentRecord.REJECTED;
		}else if(operate==ContentOperateType.move){
			operateByte=ContentRecord.MOVE;
		}else if(operate==ContentOperateType.shared){
			operateByte=ContentRecord.SHARED;
		}else if(operate==ContentOperateType.pigeonhole){
			operateByte=ContentRecord.PIGEONHOLE;
		}else if(operate==ContentOperateType.reuse){
			operateByte=ContentRecord.REUSE;
		}else if(operate==ContentOperateType.createPage){
			operateByte=ContentRecord.CREATEPAGE;
		}
		record.setOperateType(operateByte);
		save(record);
		return record;
	}

	@Override
    public ContentRecord save(ContentRecord bean) {
		dao.save(bean);
		return bean;
	}

	@Override
    public ContentRecord update(ContentRecord bean) {
		Updater<ContentRecord> updater = new Updater<ContentRecord>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Override
    public ContentRecord deleteById(Long id) {
		ContentRecord bean = dao.deleteById(id);
		return bean;
	}
	
	@Override
    public ContentRecord[] deleteByIds(Long[] ids) {
		ContentRecord[] beans = new ContentRecord[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	@Autowired
	private ContentRecordDao dao;
}