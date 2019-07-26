package com.bfly.cms.statistic.service;

import com.bfly.cms.statistic.entity.SiteAccessPage;
import com.bfly.core.base.service.IBaseService;

import java.util.Date;

/**
 * 页面访问
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/23 21:34
 */
public interface ISiteAccessPageService extends IBaseService<SiteAccessPage, Integer> {

    /**
     * 根据SessionID,seq
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 18:09
     */
    SiteAccessPage findSiteAccessPageBySessionIdAndSeq(String sessionId, long seq);

    /**
     * 根据日期删除页面访问记录
     * @author andy_hulibo@163.com
     * @date 2019/7/24 10:04
     */
    void clearSiteAccessPageByDate(Date date);
}
