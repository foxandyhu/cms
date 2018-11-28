package com.bfly.cms.job.service.impl;

import com.bfly.common.hibernate4.Updater;
import com.bfly.cms.job.dao.CmsUserResumeDao;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.job.entity.CmsUserResume;
import com.bfly.cms.job.service.CmsUserResumeMng;
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