package com.bfly.cms.service.impl;

import com.bfly.cms.service.ContentQueryFreshTimeCache;
import com.bfly.core.manager.CmsConfigMng;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;


@Service
public class ContentQueryFreshTimeCacheImpl implements ContentQueryFreshTimeCache {
    private Logger log = LoggerFactory.getLogger(ContentQueryFreshTimeCacheImpl.class);

    private final static String CONTENT_QUERY_FRESH_TIME = "contentQueryFreshTime";

    @Override
    public Date getTime() {
        Element e = cache.get(CONTENT_QUERY_FRESH_TIME);
        Date contentQueryFreshTime;
        if (e != null) {
            contentQueryFreshTime = (Date) e.getObjectValue();
        } else {
            contentQueryFreshTime = Calendar.getInstance().getTime();
            //上次创建的缓存时间
            lastCacheTime = System.currentTimeMillis();
        }
        cache.put(new Element(CONTENT_QUERY_FRESH_TIME, contentQueryFreshTime));
        refresh();
        return contentQueryFreshTime;
    }


    public void refresh() {
        //初次访问从数据库获取
        if (interval == null) {
            setInterval(cmsConfigMng.getContentFreshMinute());
        }
        long time = System.currentTimeMillis();
        if (time > lastCacheTime + interval) {
            // 清除缓存
            clearCache();
        }
    }

    //立即更新缓存
    @Override
    public void clearCache() {
        cache.removeAll();
    }

    // 间隔时间(可以采用后台配置)
    private Integer interval;
    // 最后刷新时间
    private long lastCacheTime = System.currentTimeMillis();

    /**
     * 刷新间隔时间
     *
     * @param interval 单位分钟
     */
    @Override
    public void setInterval(int interval) {
        this.interval = interval * 60 * 1000;
    }


    @Autowired
    private CmsConfigMng cmsConfigMng;
    private Ehcache cache;

    @Autowired
    public void setCache(EhCacheCacheManager cacheManager) {
        EhCacheCache ehcache = (EhCacheCache) cacheManager.getCache("contentQueryFreshTime");
        cache = ehcache.getNativeCache();
    }
}
