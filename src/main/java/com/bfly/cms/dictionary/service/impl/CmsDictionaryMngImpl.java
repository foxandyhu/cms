package com.bfly.cms.dictionary.service.impl;

import com.bfly.cms.dictionary.entity.CmsDictionary;
import com.bfly.cms.dictionary.service.CmsDictionaryMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.dictionary.dao.CmsDictionaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/24 18:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsDictionaryMngImpl implements CmsDictionaryMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPage(String queryType, int pageNo, int pageSize) {
        return dao.getPage(queryType, pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<CmsDictionary> getList(String queryType) {
        return dao.getList(queryType);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<String> getTypeList() {
        return dao.getTypeList();
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsDictionary findById(Integer id) {
        return dao.findById(id);
    }


    @Override
    public CmsDictionary save(CmsDictionary bean) {
        return dao.save(bean);
    }

    @Override
    public CmsDictionary update(CmsDictionary bean) {
        Updater<CmsDictionary> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

    @Override
    public CmsDictionary deleteById(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public CmsDictionary[] deleteByIds(Integer[] ids) {
        CmsDictionary[] beans = new CmsDictionary[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Override
    public boolean dicDeplicateValue(String value, String type) {
        return dao.countByValue(value, type) > 0;
    }

    @Autowired
    private CmsDictionaryDao dao;
}