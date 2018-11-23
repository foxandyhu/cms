package com.bfly.core.manager.impl;

import com.bfly.common.hibernate4.Updater;
import com.bfly.core.dao.CmsUserSiteDao;
import com.bfly.core.entity.CmsSite;
import com.bfly.core.entity.CmsUser;
import com.bfly.core.entity.CmsUserSite;
import com.bfly.core.manager.CmsSiteMng;
import com.bfly.core.manager.CmsUserSiteMng;
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
        Set<CmsUserSite> uss = user.getUserSites();
        if (siteId == null || step == null || allChannel == null) {
            return;
        }
        // 只更新单站点信息
        for (CmsUserSite us : uss) {
            if (siteId.equals(us.getSite().getId())) {
                us.setCheckStep(step);
                us.setAllChannel(allChannel);
            }
        }
    }

    @Override
    public void updateByUser(CmsUser user, Integer[] siteIds, Byte[] steps,
                             Boolean[] allChannels) {
        Set<CmsUserSite> uss = user.getUserSites();
        // 全删
        if (siteIds == null) {
            user.getUserSites().clear();
            for (CmsUserSite us : uss) {
                dao.delete(us);
            }
            return;
        }
        // 先删除、更新
        Set<CmsUserSite> toDel = new HashSet<CmsUserSite>();
        boolean contains;
        int i;
        for (CmsUserSite us : uss) {
            contains = false;
            for (i = 0; i < siteIds.length; i++) {
                if (siteIds[i].equals(us.getSite().getId())) {
                    contains = true;
                    break;
                }
            }
            if (contains) {
                us.setCheckStep(steps[i]);
                us.setAllChannel(allChannels[i]);
            } else {
                toDel.add(us);
            }
        }
        delete(toDel, uss);
        // 再增加
        i = 0;
        Set<CmsUserSite> toSave = new HashSet<CmsUserSite>();
        for (Integer sid : siteIds) {
            contains = false;
            for (CmsUserSite us : uss) {
                if (us.getSite().getId().equals(sid)) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                toSave.add(save(cmsSiteMng.findById(sid), user, steps[i],
                        allChannels[i]));
            }
            i++;
        }
        uss.addAll(toSave);
    }

    @Override
    public int deleteBySiteId(Integer siteId) {
        return dao.deleteBySiteId(siteId);
    }

    private void delete(Collection<CmsUserSite> coll, Set<CmsUserSite> set) {
        if (coll == null) {
            return;
        }
        for (CmsUserSite us : coll) {
            dao.delete(us);
            set.remove(us);
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