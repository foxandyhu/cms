package com.bfly.cms.system.service.impl;

import com.bfly.cms.system.dao.CmsConfigItemDao;
import com.bfly.cms.system.entity.CmsConfigItem;
import com.bfly.cms.system.service.CmsConfigItemMng;
import com.bfly.common.hibernate4.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/24 17:38
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsConfigItemMngImpl implements CmsConfigItemMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<CmsConfigItem> getList(Integer configId, Integer category) {
        return dao.getList(configId, category);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsConfigItem findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public CmsConfigItem save(CmsConfigItem bean) {
        bean.init();
        dao.save(bean);
        return bean;
    }

    @Override
    public void updatePriority(Integer[] wid, Integer[] priority,
                               String[] label) {
        CmsConfigItem item;
        for (int i = 0, len = wid.length; i < len; i++) {
            item = findById(wid[i]);
            item.setLabel(label[i]);
            item.setPriority(priority[i]);
        }
    }

    @Override
    public CmsConfigItem update(CmsConfigItem bean) {
        Updater<CmsConfigItem> updater = new Updater<>(bean);
        CmsConfigItem entity = dao.updateByUpdater(updater);
        entity.emptyToNull();
        return entity;
    }

    @Override
    public CmsConfigItem deleteById(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public CmsConfigItem[] deleteByIds(Integer[] ids) {
        CmsConfigItem[] beans = new CmsConfigItem[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private CmsConfigItemDao dao;
}