package com.bfly.cms.message.service.impl;

import com.bfly.cms.message.dao.CmsMessageDao;
import com.bfly.cms.message.entity.CmsMessage;
import com.bfly.cms.message.service.CmsMessageMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/25 18:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsMessageMngImpl implements CmsMessageMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPage(Integer siteId, Integer sendUserId,
                              Integer receiverUserId, String title, Date sendBeginTime,
                              Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
                              int pageNo, int pageSize) {
        return dao.getPage(siteId, sendUserId, receiverUserId, title,
                sendBeginTime, sendEndTime, status, box,pageNo,
                pageSize);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<CmsMessage> getList(Integer siteId, Integer sendUserId,
                                    Integer receiverUserId, String title, Date sendBeginTime,
                                    Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
                                    Integer first, Integer count) {
        return dao.getList(siteId, sendUserId, receiverUserId, title,
                sendBeginTime, sendEndTime, status, box, cacheable, first,
                count);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsMessage findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public CmsMessage save(CmsMessage bean) {
        return dao.save(bean);
    }

    @Override
    public CmsMessage update(CmsMessage bean) {
        Updater<CmsMessage> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

    @Override
    public CmsMessage deleteById(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public CmsMessage[] deleteByIds(Integer[] ids) {
        return dao.deleteByIds(ids);
    }

    @Autowired
    private CmsMessageDao dao;

}
