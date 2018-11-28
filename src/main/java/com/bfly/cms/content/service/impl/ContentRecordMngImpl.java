package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.service.ContentRecordMng;
import com.bfly.cms.content.dao.ContentRecordDao;
import com.bfly.cms.content.entity.ContentRecord;
import com.bfly.cms.content.entity.ContentRecord.ContentOperateType;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ContentRecordMngImpl implements ContentRecordMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = dao.getPage(pageNo, pageSize);
        return page;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<ContentRecord> getListByContentId(Integer contentId) {
        return dao.getListByContentId(contentId);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ContentRecord findById(Long id) {
        ContentRecord entity = dao.findById(id);
        return entity;
    }

    @Override
    public ContentRecord record(Content content, CmsUser user, ContentOperateType operate) {
        ContentRecord record = new ContentRecord();
        record.setContent(content);
        record.setOperateTime(Calendar.getInstance().getTime());
        record.setUser(user);
        Byte operateByte = ContentRecord.ADD;
        if (operate == ContentOperateType.edit) {
            operateByte = ContentRecord.EDIT;
        } else if (operate == ContentOperateType.cycle) {
            operateByte = ContentRecord.CYCLE;
        } else if (operate == ContentOperateType.check) {
            operateByte = ContentRecord.CHECK;
        } else if (operate == ContentOperateType.rejected) {
            operateByte = ContentRecord.REJECTED;
        } else if (operate == ContentOperateType.move) {
            operateByte = ContentRecord.MOVE;
        } else if (operate == ContentOperateType.shared) {
            operateByte = ContentRecord.SHARED;
        } else if (operate == ContentOperateType.pigeonhole) {
            operateByte = ContentRecord.PIGEONHOLE;
        } else if (operate == ContentOperateType.reuse) {
            operateByte = ContentRecord.REUSE;
        } else if (operate == ContentOperateType.createPage) {
            operateByte = ContentRecord.CREATEPAGE;
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
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private ContentRecordDao dao;
}