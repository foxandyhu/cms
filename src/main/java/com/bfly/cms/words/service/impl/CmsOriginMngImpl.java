package com.bfly.cms.words.service.impl;

import com.bfly.cms.words.dao.CmsOriginDao;
import com.bfly.cms.words.entity.CmsOrigin;
import com.bfly.cms.words.service.CmsOriginMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsOriginMngImpl implements CmsOriginMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = dao.getPage(pageNo, pageSize);
        return page;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<CmsOrigin> getList(String name) {
        return dao.getList(name);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsOrigin findById(Integer id) {
        CmsOrigin entity = dao.findById(id);
        return entity;
    }

    @Override
    public CmsOrigin save(CmsOrigin bean) {
        dao.save(bean);
        return bean;
    }

    @Override
    public CmsOrigin update(CmsOrigin bean) {
        Updater<CmsOrigin> updater = new Updater<>(bean);
        bean = dao.updateByUpdater(updater);
        return bean;
    }

    @Override
    public CmsOrigin deleteById(Integer id) {
        CmsOrigin bean = dao.deleteById(id);
        return bean;
    }

    @Override
    public CmsOrigin[] deleteByIds(Integer[] ids) {
        CmsOrigin[] beans = new CmsOrigin[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private CmsOriginDao dao;
}