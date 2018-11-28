package com.bfly.cms.sms.service.impl;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.sms.entity.CmsSms;
import com.bfly.cms.sms.entity.CmsSmsRecord;
import com.bfly.cms.sms.service.CmsSmsRecordMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.sms.dao.CmsSmsRecordDao;
import com.bfly.cms.user.entity.CmsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/25 16:43
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsSmsRecordMngImpl implements CmsSmsRecordMng {

    @Autowired
    private CmsSmsRecordDao dao;

    @Override
    public List<CmsSmsRecord> getList(Integer smsId) {
        return dao.getList(smsId);
    }

    @Override
    public CmsSmsRecord findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public List<CmsSmsRecord> findByPhone(String phone) {
        Date currentTime = new Date();
        long time = currentTime.getTime();
        time = time / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        Date startTime = new Date(time);
        Date endTime = new Date(time + 24 * 60 * 60 * 1000 - 1);
        return dao.findByPhone(phone, startTime, endTime);
    }

    @Override
    public CmsSmsRecord save(CmsSmsRecord bean) {
        dao.save(bean);
        return bean;
    }

    @Override
    public CmsSmsRecord updateByUpdater(CmsSmsRecord bean) {
        Updater<CmsSmsRecord> updater = new Updater<>(bean);
        bean = dao.updateByUpdater(updater);
        return bean;
    }

    @Override
    public CmsSmsRecord deleteById(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public CmsSmsRecord[] deleteByIds(Integer[] ids) {
        CmsSmsRecord[] beans = new CmsSmsRecord[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Override
    public Pagination getPage(Byte sms, int pageNo, int pageSize, String phone, Integer validateType,
                              String username, Date drawTimeBegin, Date drawTimeEnd) {
        return dao.getPage(sms, pageNo, pageSize, phone, validateType, username, drawTimeBegin, drawTimeEnd);
    }

    @Override
    public CmsSmsRecord save(CmsSms sms, String mobilePhone, Integer smsSendType, CmsSite site, CmsUser user) {
        CmsSmsRecord record = new CmsSmsRecord();
        record.setPhone(mobilePhone);
        record.setSendTime(new Date());
        record.setSms(sms);
        record.setSite(site);
        record.setValidateType(smsSendType);
        record.setUser(user);
        return save(record);
    }
}
