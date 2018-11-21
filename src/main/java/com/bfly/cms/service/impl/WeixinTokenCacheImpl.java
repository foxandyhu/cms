package com.bfly.cms.service.impl;


import com.bfly.cms.service.WeiXinSvc;
import com.bfly.cms.service.WeixinTokenCache;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * 微信Token缓存接口
 */
@Service
public class WeixinTokenCacheImpl implements WeixinTokenCache {


    @Override
    public Map<String, String> getToken() {
        String token = "";
        Map<String, String> msg = new HashMap<>();
        Element e = cache.get("token");
        if (e != null) {
            token = (String) e.getObjectValue();
            if (StringUtils.isNotBlank(token)) {
                msg.put("errmsg", null);
            } else {
                msg.put("errmsg", "token is null");
            }
            msg.put("token", (String) e.getObjectValue());
            String tokenFresh = refreshCache();
            if (StringUtils.isNotBlank(tokenFresh)) {
                token = tokenFresh;
            }
        } else {
            msg = weiXinSvc.getToken();
            cache.put(new Element("token", msg.get("token")));
        }
        return msg;
    }


    private String refreshCache() {
        long time = System.currentTimeMillis();
        String token = "";
        if (time > refreshTime + interval) {
            refreshTime = time;
            // 更新缓存
            token = weiXinSvc.getToken().get("token");
            cache.put(new Element("token", token));
        }
        return token;
    }


    // 间隔时间
    private int interval = 60 * 60 * 1000; // 1小时
    // 最后刷新时间
    private long refreshTime = System.currentTimeMillis();

    @Autowired
    private WeiXinSvc weiXinSvc;

    private Ehcache cache;


    /**
     * 刷新间隔时间
     *
     * @param interval 单位分钟
     */
    public void setInterval(int interval) {
        this.interval = interval * 60 * 1000;
    }

    @Autowired
    public void setCache(EhCacheCacheManager cacheManager) {
        EhCacheCache ehcache = (EhCacheCache) cacheManager.getCache("tokenCache");
        cache = ehcache.getNativeCache();
    }


}
