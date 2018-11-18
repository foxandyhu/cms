package com.jeecms.core.manager.impl;

import com.jeecms.common.hibernate4.Updater;
import com.jeecms.core.dao.CmsUserResumeDao;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.entity.CmsUserResume;
import com.jeecms.core.manager.CmsUserResumeMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CmsUserResumeMngImpl implements CmsUserResumeMng {
    public CmsUserResume save(CmsUserResume resume, CmsUser user) {
        resume.setUser(user);
        dao.save(resume);
        return resume;
    }

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