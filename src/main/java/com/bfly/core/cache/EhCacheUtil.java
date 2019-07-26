package com.bfly.core.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * EhCache工具类
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/23 18:46
 */
@Component("EhCacheUtil")
public class EhCacheUtil implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(EhCacheUtil.class);

    /**
     * 站点访问缓存标识
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 18:01
     */
    public static final String SITE_ACCESS_CACHE = "siteAccessCache";

    /**
     * 站点页面访问缓存标识
     * 每次访问页面记录一次到缓存中
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 21:25
     */
    public static final String SITE_ACCESS_PAGE_CACHE = "siteAccessPageCache";

    /**
     * 数据字典缓存
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 18:42
     */
    public static final String DICTIONAY_CACHE = "dictionayCache";

    private static CacheManager cacheManager;

    @Autowired
    public void setCacheManager(CacheManager cacheManager) {
        EhCacheUtil.cacheManager = cacheManager;
    }

    /**
     * 获取EhCache缓存数据
     *
     * @param cacheName 可为空 采用默认缓存
     * @param key       缓存key
     * @param cls       返回的类型
     * @author andy_hulibo@163.com
     * @date 2019/7/23 18:49
     */
    public static <T> T getData(String cacheName, String key, Class<T> cls) {
        Cache cache = getCache(cacheName);
        return cache.get(key, cls);
    }

    /**
     * 设置缓存数据
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 21:42
     */
    public static void setData(String cacheName, String key, Object obj) {
        Cache cache = getCache(cacheName);
        cache.put(key, obj);
    }

    /**
     * 获得缓存区域
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 10:10
     */
    public static Cache getCache(String cacheName) {
        if (cacheName == null) {
            cacheName = "default";
        }
        Cache cache = cacheManager.getCache(cacheName);
        return cache;
    }
}
