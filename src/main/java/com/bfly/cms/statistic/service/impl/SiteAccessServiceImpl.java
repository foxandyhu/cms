package com.bfly.cms.statistic.service.impl;

import com.bfly.cms.statistic.dao.ISiteAccessDao;
import com.bfly.cms.statistic.entity.SiteAccess;
import com.bfly.cms.statistic.entity.SiteAccessPage;
import com.bfly.cms.statistic.service.ISiteAccessPageService;
import com.bfly.cms.statistic.service.ISiteAccessService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.cache.EhCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author andy_hulibo@163.com
 * @date 2019/7/23 17:31
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SiteAccessServiceImpl extends BaseServiceImpl<SiteAccess, Integer> implements ISiteAccessService {

    @Autowired
    private ISiteAccessDao siteAccessDao;
    @Autowired
    private ISiteAccessPageService pageService;


    @Override
    public SiteAccess findSiteAccessBySessionId(String sessionId) {
        return siteAccessDao.findSiteAccessBySessionId(sessionId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async
    public void cacheToDb() {
        EhCacheCache siteCache = (EhCacheCache) EhCacheUtil.getCache(EhCacheUtil.SITE_ACCESS_CACHE);
        EhCacheCache pageCache = (EhCacheCache) EhCacheUtil.getCache(EhCacheUtil.SITE_ACCESS_PAGE_CACHE);

        List<String> siteKeys = siteCache.getNativeCache().getKeys();

        //把缓存数据写入数据库
        siteKeys.forEach(key -> {
            SiteAccess siteAccess = siteCache.get(key, SiteAccess.class);
            if (siteAccess.getId() == 0) {
                save(siteAccess);
            } else {
                edit(siteAccess);
            }
        });

        List<String> pageKeys = pageCache.getNativeCache().getKeys();
        pageKeys.forEach(key -> {
            SiteAccessPage accessPage = pageCache.get(key, SiteAccessPage.class);
            if (accessPage.getId() == 0) {
                pageService.save(accessPage);
            } else {
                pageService.edit(accessPage);
            }
        });

        //清除缓存
        siteKeys.forEach(key -> siteCache.evict(key));
        pageKeys.forEach(key -> pageCache.evict(key));
    }


}
