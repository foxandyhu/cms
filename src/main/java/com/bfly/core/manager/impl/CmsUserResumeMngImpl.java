package com.bfly.core.manager.impl;

import com.bfly.common.hibernate4.Updater;
import com.bfly.core.dao.CmsUserResumeDao;
import com.bfly.core.entity.CmsUser;
import com.bfly.core.entity.CmsUserResume;
import com.bfly.core.manager.CmsUserResumeMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CmsUserResumeMngImpl implements CmsUserResumeMng {
    @Override
    public CmsUserResume save(CmsUserResume resume, CmsUser user) {
        resume.setUser(user);
        dao.save(resume);
        return resume;
    }

    @Override
    public CmsUserResume update(CmsUserResume ext, CmsUser user) {
        CmsUserResume entity = dao.findById(user.getId());
        if (entity == null) {
            entity = save(ext, user);
            user.setUserResume(entity);
            return entity;
        } else {
            Updater<CmsUserResume> updater = new Updater<CmsUserResume>(ext);
            ext = dao.updateByUpdater(updater);
            return ext;
        }
    }

    @Autowired
    private CmsUserResumeDao dao;
}