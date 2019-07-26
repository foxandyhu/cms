package com.bfly.cms.statistic.service.impl;

import com.bfly.cms.statistic.dao.ISiteAccessPageDao;
import com.bfly.cms.statistic.entity.SiteAccessPage;
import com.bfly.cms.statistic.service.ISiteAccessPageService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author andy_hulibo@163.com
 * @date 2019/7/23 17:31
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SiteAccessPageServiceImpl extends BaseServiceImpl<SiteAccessPage, Integer> implements ISiteAccessPageService {

    @Autowired
    private ISiteAccessPageDao accessPageDao;


    @Override
    public SiteAccessPage findSiteAccessPageBySessionIdAndSeq(String sessionId, long seq) {
        return accessPageDao.findSiteAccessPageBySessionIdAndSeq(sessionId, seq);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearSiteAccessPageByDate(Date date) {
        accessPageDao.removeSiteAccessPageByAccessDate(date);
    }
}
