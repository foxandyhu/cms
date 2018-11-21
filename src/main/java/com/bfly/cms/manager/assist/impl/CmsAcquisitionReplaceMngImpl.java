package com.bfly.cms.manager.assist.impl;

import com.bfly.cms.dao.assist.impl.CmsAcquisitionReplaceDaoImpl;
import com.bfly.cms.entity.assist.CmsAcquisitionReplace;
import com.bfly.cms.manager.assist.CmsAcquisitionReplaceMng;
import com.bfly.common.hibernate4.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CmsAcquisitionReplaceMngImpl implements CmsAcquisitionReplaceMng {

    @Override
    public CmsAcquisitionReplace save(CmsAcquisitionReplace bean) {
        return cmsAcquisitionReplaceDao.save(bean);
    }

    @Override
    public CmsAcquisitionReplace updateByUpdater(Updater<CmsAcquisitionReplace> updater) {
        return cmsAcquisitionReplaceDao.updateByUpdater(updater);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CmsAcquisitionReplace> getList(Integer acquisitionId) {
        return cmsAcquisitionReplaceDao.getList(acquisitionId);
    }

    @Autowired
    private CmsAcquisitionReplaceDaoImpl cmsAcquisitionReplaceDao;


}
