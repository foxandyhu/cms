package com.bfly.cms.system.service.impl;

import com.bfly.cms.system.entity.SiteInfo;
import com.bfly.cms.system.service.ISiteInfoService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:03
 */
@Service
@Transactional(propagation= Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SiteInfoServiceImpl extends BaseServiceImpl<SiteInfo, Integer> implements ISiteInfoService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SiteInfo siteInfo) {
        SiteInfo site = getSite();
        //不存在则新增 存在则修改
        if (site == null) {
            return super.save(siteInfo);
        }
        siteInfo.setId(site.getId());
        return super.edit(siteInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SiteInfo siteInfo) {
        return edit(siteInfo);
    }

    @Override
    public SiteInfo getSite() {
        List<SiteInfo> sites = getList();
        return sites != null ? sites.get(0) : null;
    }
}
