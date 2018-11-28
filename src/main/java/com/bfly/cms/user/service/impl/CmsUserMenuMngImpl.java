package com.bfly.cms.user.service.impl;

import com.bfly.cms.user.service.CmsUserMenuMng;
import com.bfly.cms.user.dao.CmsUserMenuDao;
import com.bfly.cms.user.entity.CmsUserMenu;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CmsUserMenuMngImpl implements CmsUserMenuMng {
    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(Integer userId, int pageNo, int pageSize) {
        Pagination page = dao.getPage(userId, pageNo, pageSize);
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CmsUserMenu> getList(Integer userId, int count) {
        return dao.getList(userId, count);
    }

    @Override
    @Transactional(readOnly = true)
    public CmsUserMenu findById(Integer id) {
        CmsUserMenu entity = dao.findById(id);
        return entity;
    }

    @Override
    public CmsUserMenu save(CmsUserMenu bean) {
        dao.save(bean);
        return bean;
    }

    @Override
    public CmsUserMenu update(CmsUserMenu bean) {
        Updater<CmsUserMenu> updater = new Updater<CmsUserMenu>(bean);
        bean = dao.updateByUpdater(updater);
        return bean;
    }

    @Override
    public CmsUserMenu deleteById(Integer id) {
        CmsUserMenu bean = dao.deleteById(id);
        return bean;
    }

    @Override
    public CmsUserMenu[] deleteByIds(Integer[] ids) {
        CmsUserMenu[] beans = new CmsUserMenu[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private CmsUserMenuDao dao;

}