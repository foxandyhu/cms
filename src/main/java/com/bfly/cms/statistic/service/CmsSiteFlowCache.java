package com.bfly.cms.statistic.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 站点流量缓存接口
 */
public interface CmsSiteFlowCache {
    Long[] flow(HttpServletRequest request, String page, String referrer);
}
