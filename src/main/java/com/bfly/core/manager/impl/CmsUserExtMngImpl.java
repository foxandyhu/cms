package com.bfly.core.manager.impl;

import com.bfly.common.hibernate4.Updater;
import com.bfly.core.dao.CmsUserExtDao;
import com.bfly.core.entity.CmsUser;
import com.bfly.core.entity.CmsUserExt;
import com.bfly.core.manager.CmsUserExtMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CmsUserExtMngImpl implements CmsUserExtMng {
    @Override
    @Transactional(readOnly = true)
    public CmsUserExt findById(Integer userId) {
        return dao.findById(userId);
    }

    @Override
    public CmsUserExt save(CmsUserExt ext, CmsUser user) {
        ext.blankToNull();
        ext.setUser(user);
        dao.save(ext);
        return ext;
    }

    @Override
    public CmsUserExt update(CmsUserExt ext, CmsUser user) {
        CmsUserExt entity = dao.findById(user.getId());
        if (entity == null) {
            entity = save(ext, user);
            user.setUserExt(entity);
            return entity;
        } else {
            Updater<CmsUserExt> updater = new Updater<CmsUserExt>(ext);
            //	updater.include("gender");
            //	updater.include("birthday");
            ext = dao.updateByUpdater(updater);
            ext.blankToNull();
            return ext;
        }
    }

    @Override
    public void clearDayCount() {
        dao.clearDayCount();
    }

    @Autowired
    private CmsUserExtDao dao;

    @Override
    public int countByPhone(String mobile) {
        return dao.countByPhone(mobile);
    }
}