package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.service.CmsScoreGroupMng;
import com.bfly.cms.content.dao.CmsScoreGroupDao;
import com.bfly.cms.content.entity.CmsScoreGroup;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:06
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsScoreGroupMngImpl implements CmsScoreGroupMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = dao.getPage(pageNo, pageSize);
        return page;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsScoreGroup findById(Integer id) {
        CmsScoreGroup entity = dao.findById(id);
        return entity;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsScoreGroup findDefault(Integer siteId) {
        return dao.findDefault(siteId);
    }

    @Override
    public CmsScoreGroup save(CmsScoreGroup bean) {
        dao.save(bean);
        return bean;
    }

    @Override
    public CmsScoreGroup update(CmsScoreGroup bean) {
        Updater<CmsScoreGroup> updater = new Updater<>(bean);
        bean = dao.updateByUpdater(updater);
        return bean;
    }

    @Override
    public CmsScoreGroup deleteById(Integer id) {
        CmsScoreGroup bean = dao.deleteById(id);
        return bean;
    }

    @Override
    public CmsScoreGroup[] deleteByIds(Integer[] ids) {
        CmsScoreGroup[] beans = new CmsScoreGroup[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private CmsScoreGroupDao dao;
}