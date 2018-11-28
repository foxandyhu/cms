package com.bfly.cms.system.service.impl;

import com.bfly.cms.content.service.ContentQueryFreshTimeCache;
import com.bfly.cms.system.entity.CmsConfig;
import com.bfly.cms.system.service.CmsConfigMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.cms.system.dao.CmsConfigDao;
import com.bfly.cms.system.entity.CmsConfigAttr;
import com.bfly.cms.system.entity.MarkConfig;
import com.bfly.cms.system.entity.MemberConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/24 16:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsConfigMngImpl implements CmsConfigMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsConfig get() {
        return dao.findById(1);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Integer getContentFreshMinute() {
        CmsConfig entity = get();
        return entity.getContentFreshMinute();
    }

    @Override
    public void updateCountCopyTime(Date d) {
        dao.findById(1).setCountCopyTime(d);
    }

    @Override
    public void updateCountClearTime(Date d) {
        dao.findById(1).setCountClearTime(d);
    }

    @Override
    public void updateFlowClearTime(Date d) {
        dao.findById(1).setFlowClearTime(d);
    }

    @Override
    public void updateChannelCountClearTime(Date d) {
        dao.findById(1).setChannelCountClearTime(d);
    }


    @Override
    public CmsConfig update(CmsConfig bean) {
        Updater<CmsConfig> updater = new Updater<>(bean);
        CmsConfig entity = dao.updateByUpdater(updater);
        entity.blankToNull();
        return entity;
    }

    @Override
    public MarkConfig updateMarkConfig(MarkConfig mark) {
        get().setMarkConfig(mark);
        return mark;
    }

    @Override
    public void updateMemberConfig(MemberConfig memberConfig) {
        get().getAttr().putAll(memberConfig.getAttr());
    }

    @Override
    public void updateConfigAttr(CmsConfigAttr configAttr) {
        Map<String, String> attrMap = configAttr.getAttr();
        if (StringUtils.isBlank(attrMap.get(CmsConfigAttr.WEIXIN_APP_ID))) {
            attrMap.remove(CmsConfigAttr.WEIXIN_APP_ID);
        }
        if (StringUtils.isBlank(attrMap.get(CmsConfigAttr.WEIXIN_APP_SECRET))) {
            attrMap.remove(CmsConfigAttr.WEIXIN_APP_SECRET);
        }
        if (StringUtils.isBlank(attrMap.get(CmsConfigAttr.WEIXIN_LOGIN_ID))) {
            attrMap.remove(CmsConfigAttr.WEIXIN_LOGIN_ID);
        }
        if (StringUtils.isBlank(attrMap.get(CmsConfigAttr.WEIXIN_LOGIN_SECRET))) {
            attrMap.remove(CmsConfigAttr.WEIXIN_LOGIN_SECRET);
        }
        get().getAttr().putAll(attrMap);
        contentQueryFreshTimeCache.setInterval(getContentFreshMinute());
    }

    @Override
    public void updateSsoAttr(Map<String, String> ssoAttr) {
        Map<String, String> oldAttr = get().getAttr();
        Iterator<String> keys = oldAttr.keySet().iterator();
        String key;
        while (keys.hasNext()) {
            key = keys.next();
            if (key.startsWith("sso_")) {
                keys.remove();
            }
        }
        oldAttr.putAll(ssoAttr);
    }

    @Override
    public void updateRewardFixAttr(Map<String, String> fixAttr) {
        Map<String, String> oldAttr = get().getAttr();
        Iterator<String> keys = oldAttr.keySet().iterator();
        String key;
        while (keys.hasNext()) {
            key = keys.next();
            if (key.startsWith(CmsConfig.REWARD_FIX_PREFIX)) {
                keys.remove();
            }
        }
        oldAttr.putAll(fixAttr);
    }

    @Autowired
    private CmsConfigDao dao;
    @Autowired
    private ContentQueryFreshTimeCache contentQueryFreshTimeCache;
}