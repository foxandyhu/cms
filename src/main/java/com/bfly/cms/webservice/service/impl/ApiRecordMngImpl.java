package com.bfly.cms.webservice.service.impl;

import com.bfly.cms.webservice.dao.ApiRecordDao;
import com.bfly.cms.webservice.entity.ApiInfo;
import com.bfly.cms.webservice.service.ApiAccountMng;
import com.bfly.cms.webservice.service.ApiInfoMng;
import com.bfly.cms.webservice.entity.ApiRecord;
import com.bfly.cms.webservice.service.ApiRecordMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/25 10:20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiRecordMngImpl implements ApiRecordMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPage(int pageNo, int pageSize) {
        return dao.getPage(pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ApiRecord findById(Long id) {
        return dao.findById(id);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ApiRecord findBySign(String sign, String appId) {
        return dao.findBySign(sign, appId);
    }

    @Override
    public ApiRecord callApiRecord(String ip, String appId, String apiUrl, String sign) {
        ApiRecord record = new ApiRecord();
        record.setCallIp(ip);
        record.setCallTime(Calendar.getInstance().getTime());
        record.setCallTimeStamp(System.currentTimeMillis());
        record.setApiAccount(apiAccountMng.findByAppId(appId));
        record.setSign(sign);
        ApiInfo info = apiInfoMng.findByUrl(apiUrl);
        if (info != null) {
            record.setApiInfo(info);
            statisCallCount(info);
        }
        record = save(record);
        return record;

    }

    private void statisCallCount(ApiInfo info) {
        Calendar curr = Calendar.getInstance();
        Calendar last = Calendar.getInstance();
        if (info.getLastCallTime() != null) {
            last.setTime(info.getLastCallTime());
            int currDay = curr.get(Calendar.DAY_OF_YEAR);
            int lastDay = last.get(Calendar.DAY_OF_YEAR);
            if (currDay != lastDay) {
                int currWeek = curr.get(Calendar.WEEK_OF_YEAR);
                int lastWeek = last.get(Calendar.WEEK_OF_YEAR);
                int currMonth = curr.get(Calendar.MONTH);
                int lastMonth = last.get(Calendar.MONTH);
                if (currWeek != lastWeek) {
                    info.setCallWeekCount(0);
                }
                if (currMonth != lastMonth) {
                    info.setCallMonthCount(0);
                }
                info.setCallDayCount(0);
            }
            info.setCallDayCount(info.getCallDayCount() + 1);
            info.setCallWeekCount(info.getCallWeekCount() + 1);
            info.setCallMonthCount(info.getCallMonthCount() + 1);
            info.setCallTotalCount(info.getCallTotalCount() + 1);
        } else {
            info.setCallDayCount(1);
            info.setCallWeekCount(1);
            info.setCallMonthCount(1);
            info.setCallTotalCount(1);
        }
        info.setLastCallTime(curr.getTime());
        apiInfoMng.update(info);
    }

    @Override
    public ApiRecord save(ApiRecord bean) {
        return dao.save(bean);
    }

    @Override
    public ApiRecord update(ApiRecord bean) {
        Updater<ApiRecord> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

    @Override
    public ApiRecord deleteById(Long id) {
        return dao.deleteById(id);
    }

    @Override
    public ApiRecord[] deleteByIds(Long[] ids) {
        ApiRecord[] beans = new ApiRecord[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private ApiRecordDao dao;
    @Autowired
    private ApiInfoMng apiInfoMng;
    @Autowired
    private ApiAccountMng apiAccountMng;
}