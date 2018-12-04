package com.bfly.cms.user.service.impl;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.service.CmsSiteMng;
import com.bfly.cms.user.dao.CmsUserSiteDao;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.entity.CmsUserSite;
import com.bfly.cms.user.service.CmsUserSiteMng;
import com.bfly.common.hibernate4.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class CmsUserSiteMngImpl implements CmsUserSiteMng {
    @Override
    @Transactional(readOnly = true)
    public CmsUserSite findById(Integer id) {
        CmsUserSite entity = dao.findById(id);
        return entity;
    }

    @Override
    public CmsUserSite save(CmsSite site, CmsUser user, Byte step,
                            Boolean allChannel) {
        CmsUserSite bean = new CmsUserSite();
        bean.setSite(site);
        bean.setUser(user);
        bean.setCheckStep(step);
        bean.setAllChannel(allChannel);
        dao.save(bean);
        return bean;
    }

    @Override
    public CmsUserSite update(CmsUserSite bean) {
        Updater<CmsUserSite> updater = new Updater<CmsUserSite>(bean);
        bean = dao.updateByUpdater(updater);
        return bean;
    }

    @Override
    public void updateByUser(CmsUser user, Integer siteId, Byte step,
                             Boolean allChannel) {
        if (siteId == null || step == null || allChannel == null) {
            return;
        }
    }

    @Override
    public void updateByUser(CmsUser user, Integer[] siteIds, Byte[] steps,
                             Boolean[] allChannels) {
        // 先删除、更新
        Set<CmsUserSite> toDel = new HashSet<>();
        int i;
        delete(toDel);
        // 再增加
        i = 0;
        Set<CmsUserSite> toSave = new HashSet<>();
        for (Integer sid : siteIds) {
            toSave.add(save(cmsSiteMng.findById(sid), user, steps[i],
                    allChannels[i]));
            i++;
        }
    }

    @Override
    public int deleteBySiteId(Integer siteId) {
        return dao.deleteBySiteId(siteId);
    }

    private void delete(Collection<CmsUserSite> coll) {
        if (coll == null) {
            return;
        }
    }

    @Override
    public CmsUserSite deleteById(Integer id) {
        CmsUserSite bean = dao.deleteById(id);
        return bean;
    }

    @Override
    public CmsUserSite[] deleteByIds(Integer[] ids) {
        CmsUserSite[] beans = new CmsUserSite[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private CmsSiteMng cmsSiteMng;
    @Autowired
    private CmsUserSiteDao dao;
}