package com.bfly.cms.ad.service.impl;

import com.bfly.cms.ad.service.CmsAdvertisingSpaceMng;
import com.bfly.cms.ad.dao.CmsAdvertisingSpaceDao;
import com.bfly.cms.ad.entity.CmsAdvertisingSpace;
import com.bfly.common.hibernate4.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:57
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsAdvertisingSpaceMngImpl implements CmsAdvertisingSpaceMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<CmsAdvertisingSpace> getList(Integer siteId) {
        return dao.getList(siteId);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsAdvertisingSpace findById(Integer id) {
        CmsAdvertisingSpace entity = dao.findById(id);
        return entity;
    }

    @Override
    public CmsAdvertisingSpace save(CmsAdvertisingSpace bean) {
        dao.save(bean);
        return bean;
    }

    @Override
    public CmsAdvertisingSpace update(CmsAdvertisingSpace bean) {
        Updater<CmsAdvertisingSpace> updater = new Updater<>(
                bean);
        bean = dao.updateByUpdater(updater);
        return bean;
    }

    @Override
    public CmsAdvertisingSpace deleteById(Integer id) {
        CmsAdvertisingSpace bean = dao.deleteById(id);
        return bean;
    }

    @Override
    public CmsAdvertisingSpace[] deleteByIds(Integer[] ids) {
        CmsAdvertisingSpace[] beans = new CmsAdvertisingSpace[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private CmsAdvertisingSpaceDao dao;
}