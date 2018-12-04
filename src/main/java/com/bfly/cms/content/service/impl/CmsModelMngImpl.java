package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.entity.CmsModel;
import com.bfly.cms.content.service.CmsModelMng;
import com.bfly.cms.content.dao.CmsModelDao;
import com.bfly.common.hibernate4.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/26 10:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsModelMngImpl implements CmsModelMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<CmsModel> getList(boolean containDisabled, Boolean hasContent) {
        return dao.getList(containDisabled, hasContent);
    }

    @Override
    public CmsModel getDefModel() {
        return dao.getDefModel();
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsModel findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsModel findByPath(String path) {
        return dao.findByPath(path);
    }

    @Override
    public CmsModel save(CmsModel bean) {
        bean.init();
        return dao.save(bean);
    }

    @Override
    public CmsModel update(CmsModel bean) {
        Updater<CmsModel> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

    @Override
    public CmsModel deleteById(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public CmsModel[] deleteByIds(Integer[] ids) {
        CmsModel[] beans = new CmsModel[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Override
    public CmsModel[] updatePriority(Integer[] ids, Integer[] priority,
                                     Boolean[] disabled, Integer defId) {
        int len = ids.length;
        CmsModel[] beans = new CmsModel[len];
        for (int i = 0; i < len; i++) {
            beans[i] = findById(ids[i]);
            beans[i].setPriority(priority[i]);
            beans[i].setDisabled(disabled[i]);
            if (beans[i].getId().equals(defId)) {
                beans[i].setDef(true);
            } else {
                beans[i].setDef(false);
            }
        }
        return beans;
    }

    @Autowired
    private CmsModelDao dao;
}