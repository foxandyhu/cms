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
    public List<CmsAdvertisingSpace> getList() {
        return dao.getList();
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsAdvertisingSpace findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public CmsAdvertisingSpace save(CmsAdvertisingSpace bean) {
        return dao.save(bean);
    }

    @Override
    public CmsAdvertisingSpace update(CmsAdvertisingSpace bean) {
        Updater<CmsAdvertisingSpace> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

    @Override
    public CmsAdvertisingSpace deleteById(Integer id) {
        return dao.deleteById(id);
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