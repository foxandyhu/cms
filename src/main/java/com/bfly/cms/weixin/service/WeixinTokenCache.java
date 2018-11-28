package com.bfly.cms.weixin.service;

import java.util.Map;

/**
 * 微信Token缓存接口
 */
public interface WeixinTokenCache {
    /**
     * 获取token
     *
     * @return
     */
    Map<String, String> getToken();
}
