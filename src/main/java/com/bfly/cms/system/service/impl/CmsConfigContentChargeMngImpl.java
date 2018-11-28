package com.bfly.cms.system.service.impl;

import com.bfly.cms.system.dao.CmsConfigContentChargeDao;
import com.bfly.cms.system.entity.CmsConfigContentCharge;
import com.bfly.cms.system.service.CmsConfigContentChargeMng;
import com.bfly.cms.system.service.CmsConfigMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.cms.user.service.impl.Md5PwdEncoder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/24 18:19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsConfigContentChargeMngImpl implements CmsConfigContentChargeMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsConfigContentCharge findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public CmsConfigContentCharge getDefault() {
        return findById(1);
    }

    @Override
    public CmsConfigContentCharge update(CmsConfigContentCharge bean,
                                         String payTransferPassword, Map<String, String> keys, Map<String, String> fixVal) {
        Updater<CmsConfigContentCharge> updater = new Updater<>(bean);
        for (Entry<String, String> att : keys.entrySet()) {
            if (StringUtils.isBlank(att.getValue())) {
                updater.exclude(att.getKey());
            }
        }
        if (StringUtils.isBlank(payTransferPassword)) {
            updater.exclude("payTransferPassword");
        } else {
            bean.setPayTransferPassword(pwdEncoder.encodePassword(payTransferPassword));
        }
        if (fixVal != null) {
            cmsConfigMng.updateRewardFixAttr(fixVal);
        }
        bean = dao.updateByUpdater(updater);
        return bean;
    }

    @Override
    public CmsConfigContentCharge afterUserPay(Double payAmout) {
        CmsConfigContentCharge config = getDefault();
        Calendar curr = Calendar.getInstance();
        Calendar last = Calendar.getInstance();
        if (config.getLastBuyTime() != null) {
            last.setTime(config.getLastBuyTime());
            int currDay = curr.get(Calendar.DAY_OF_YEAR);
            int lastDay = last.get(Calendar.DAY_OF_YEAR);
            int currYear = curr.get(Calendar.YEAR);
            int lastYear = last.get(Calendar.YEAR);
            int currMonth = curr.get(Calendar.MONTH);
            int lastMonth = last.get(Calendar.MONTH);
            if (lastYear != currYear) {
                config.setCommissionYear(0d);
                config.setCommissionMonth(0d);
                config.setCommissionDay(0d);
            } else {
                if (currMonth != lastMonth) {
                    config.setCommissionMonth(0d);
                    config.setCommissionDay(0d);
                } else {
                    if (currDay != lastDay) {
                        config.setCommissionDay(0d);
                    }
                }
            }
        }
        config.setCommissionDay(config.getCommissionDay() + payAmout);
        config.setCommissionMonth(config.getCommissionMonth() + payAmout);
        config.setCommissionYear(config.getCommissionYear() + payAmout);
        config.setCommissionTotal(config.getCommissionTotal() + payAmout);
        config.setLastBuyTime(curr.getTime());
        return config;
    }


    @Autowired
    private CmsConfigContentChargeDao dao;
    @Autowired
    private Md5PwdEncoder pwdEncoder;
    @Autowired
    private CmsConfigMng cmsConfigMng;
}