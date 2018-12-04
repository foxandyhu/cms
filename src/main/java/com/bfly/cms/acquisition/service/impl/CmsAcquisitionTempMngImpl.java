package com.bfly.cms.acquisition.service.impl;

import com.bfly.cms.acquisition.dao.CmsAcquisitionTempDao;
import com.bfly.cms.acquisition.entity.CmsAcquisitionTemp;
import com.bfly.cms.acquisition.service.CmsAcquisitionTempMng;
import com.bfly.common.hibernate4.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/4 10:05
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsAcquisitionTempMngImpl implements CmsAcquisitionTempMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<CmsAcquisitionTemp> getList() {
        return dao.getList();
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsAcquisitionTemp findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public CmsAcquisitionTemp save(CmsAcquisitionTemp bean) {
        clear(bean.getChannelUrl());
        dao.save(bean);
        return bean;
    }

    @Override
    public CmsAcquisitionTemp update(CmsAcquisitionTemp bean) {
        Updater<CmsAcquisitionTemp> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

    @Override
    public CmsAcquisitionTemp deleteById(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public CmsAcquisitionTemp[] deleteByIds(Integer[] ids) {
        CmsAcquisitionTemp[] beans = new CmsAcquisitionTemp[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Override
    public Integer getPercent() {
        return dao.getPercent();
    }

    @Override
    public void clear() {
        dao.clear(null);
    }

    @Override
    public void clear(String channelUrl) {
        dao.clear(channelUrl);
    }

    @Autowired
    private CmsAcquisitionTempDao dao;
}