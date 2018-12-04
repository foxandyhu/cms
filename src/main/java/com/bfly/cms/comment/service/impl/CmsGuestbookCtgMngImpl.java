package com.bfly.cms.comment.service.impl;

import com.bfly.cms.comment.dao.CmsGuestbookCtgDao;
import com.bfly.cms.comment.entity.CmsGuestbookCtg;
import com.bfly.cms.comment.service.CmsGuestbookCtgMng;
import com.bfly.common.hibernate4.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/4 14:47
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsGuestbookCtgMngImpl implements CmsGuestbookCtgMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<CmsGuestbookCtg> getList() {
        return dao.getList();
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsGuestbookCtg findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public CmsGuestbookCtg save(CmsGuestbookCtg bean) {
        return dao.save(bean);
    }

    @Override
    public CmsGuestbookCtg update(CmsGuestbookCtg bean) {
        Updater<CmsGuestbookCtg> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

    @Override
    public CmsGuestbookCtg deleteById(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public CmsGuestbookCtg[] deleteByIds(Integer[] ids) {
        CmsGuestbookCtg[] beans = new CmsGuestbookCtg[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private CmsGuestbookCtgDao dao;
}