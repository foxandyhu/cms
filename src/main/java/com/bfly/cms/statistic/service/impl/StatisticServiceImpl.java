package com.bfly.cms.statistic.service.impl;

import com.bfly.cms.statistic.entity.SiteAccess;
import com.bfly.cms.statistic.entity.SiteAccessPage;
import com.bfly.cms.statistic.service.ISiteAccessPageService;
import com.bfly.cms.statistic.service.ISiteAccessService;
import com.bfly.cms.statistic.service.IStatisticService;
import com.bfly.cms.words.entity.Dictionary;
import com.bfly.cms.words.service.IDictionaryService;
import com.bfly.common.ipseek.IPLocation;
import com.bfly.common.ipseek.IpSeekerUtil;
import com.bfly.core.cache.EhCacheUtil;
import com.bfly.core.context.ServletRequestDTO;
import com.bfly.core.enums.SiteAccessSource;
import eu.bitwalker.useragentutils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author andy_hulibo@163.com
 * @date 2019/7/23 17:31
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class StatisticServiceImpl implements IStatisticService {

    /**
     * 访问来源数据字典KEY
     *
     * @date 2019/7/23 18:54
     */
    private static final String SITE_ACCESS_SOURCE = "site_access_source";


    @Autowired
    private ISiteAccessService siteAccessService;
    @Autowired
    private ISiteAccessPageService siteAccessPageService;
    @Autowired
    private IDictionaryService dictionaryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async
    public void flow(String page, ServletRequestDTO request) {
        String sessionId = request.getRequestedSessionId();

        SiteAccess access = getSiteAccess(request, page);
        SiteAccessPage accessPage = getSiteAccessPage(request, page, access.getVisitPageCount());

        //把当前会话写入缓存
        EhCacheUtil.setData(EhCacheUtil.SITE_ACCESS_CACHE, sessionId, access);

        //把当前页面访问记录放到缓存
        String key = sessionId.concat("-page-").concat((accessPage.getSeq()) + "");
        EhCacheUtil.setData(EhCacheUtil.SITE_ACCESS_PAGE_CACHE, key, accessPage);
    }

    /**
     * 得到该次的会话记录信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 18:16
     */
    private SiteAccess getSiteAccess(ServletRequestDTO request, String page) {
        String sessionId = request.getRequestedSessionId();
        SiteAccess access = EhCacheUtil.getData(EhCacheUtil.SITE_ACCESS_CACHE, sessionId, SiteAccess.class);
        //防止缓存数据丢失,重新查找数据库
        if (access == null) {
            access = siteAccessService.findSiteAccessBySessionId(sessionId);
        }
        //新用户访问
        if (access == null) {
            access = new SiteAccess();
            access.setEntryPage(page);
        }

        access.setStopPage(page);
        access.setSessionId(sessionId);

        analysisVisitSecond(access);
        analysisAccessLocation(access, request);
        analysisAccessSource(access, request);
        analysisBrowser(access, request);

        return access;
    }

    /**
     * 获得访问页面信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 21:15
     */
    private SiteAccessPage getSiteAccessPage(ServletRequestDTO request, String page, long accessCount) {
        String sessionId = request.getRequestedSessionId();
        Date now = new Date();

        //得到上一个访问的页面信息
        String key = sessionId.concat("-page-").concat((accessCount - 1) + "");
        SiteAccessPage preAccessPage = EhCacheUtil.getData(EhCacheUtil.SITE_ACCESS_PAGE_CACHE, key, SiteAccessPage.class);
        if (preAccessPage == null) {
            preAccessPage = siteAccessPageService.findSiteAccessPageBySessionIdAndSeq(sessionId, accessCount - 1);
        }
        //修改上一个页面的停留时间并重新刷新缓存
        if (preAccessPage != null) {
            Date preDate = combine(preAccessPage.getAccessDate(), preAccessPage.getAccessTime());
            preAccessPage.setVisitSecond((now.getTime() - preDate.getTime()) / 1000);
            EhCacheUtil.setData(EhCacheUtil.SITE_ACCESS_PAGE_CACHE, key, preAccessPage);
        }

        SiteAccessPage accessPage = new SiteAccessPage();
        accessPage.setSessionId(sessionId);
        accessPage.setAccessPage(page);

        accessPage.setAccessDate(now);
        accessPage.setAccessTime(now);
        accessPage.setVisitSecond(0);
        accessPage.setSeq(accessCount);

        return accessPage;
    }


    /**
     * 分析客户端信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 20:34
     */
    private void analysisBrowser(SiteAccess access, ServletRequestDTO request) {
        String agent = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);

        Version version = userAgent.getBrowserVersion();
        if (version != null) {
            String browserVersion = version.getVersion();
            access.setBrowserVersion(browserVersion);
        }

        Browser browser = userAgent.getBrowser();
        if (browser != null) {
            access.setBrowserName(browser.getName());
        }

        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        if (operatingSystem != null) {
            access.setOsName(operatingSystem.getName());

            DeviceType deviceType = operatingSystem.getDeviceType();
            if (deviceType != null) {
                access.setOsType(deviceType.getName());
            }
        }
    }

    /**
     * 分析访问时间
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 20:24
     */
    private void analysisVisitSecond(SiteAccess access) {
        Date now = new Date();
        if (access.getAccessDate() == null) {
            access.setAccessDate(now);
        }
        if (access.getAccessTime() == null) {
            access.setAccessTime(now);
        }
        Date lastDate = combine(access.getAccessDate(), access.getAccessTime());
        access.setVisitSecond((now.getTime() - lastDate.getTime()) / 1000);
        access.setAccessTime(now);
        access.setAccessDate(now);
        access.setVisitPageCount(access.getVisitPageCount() + 1);
    }

    /**
     * 分析访问地区
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 19:23
     */
    private void analysisAccessLocation(SiteAccess access, ServletRequestDTO request) {
        access.setAccessIp(request.getRemoteAddr());
        IPLocation location = IpSeekerUtil.getIPLocation(access.getAccessIp());
        access.setAccessCountry(location == null ? null : location.getCountry());
        access.setAccessArea(location == null ? null : location.getArea());
    }

    /**
     * 分析访问来源
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 19:17
     */
    private void analysisAccessSource(SiteAccess access, ServletRequestDTO request) {
        String referer = request.getParameter("referer");
        //直接访问
        if (!StringUtils.hasLength(referer)) {
            access.setAccessSource(SiteAccessSource.DIRECT.getName());
            access.setAccessValue(access.getStopPage());
            return;
        }
        List<Dictionary> dictionaries = EhCacheUtil.getData(EhCacheUtil.DICTIONARY_CACHE, SITE_ACCESS_SOURCE, List.class);
        if (dictionaries == null) {
            Map<String, Object> exactMap = new HashMap<>(1);
            exactMap.put("type", SITE_ACCESS_SOURCE);
            dictionaries = dictionaryService.getList(exactMap);
        }
        //搜索引擎访问
        if (dictionaries != null) {
            for (Dictionary dictionary : dictionaries) {
                if (referer.contains(dictionary.getValue())) {
                    access.setAccessSource(SiteAccessSource.ENGINE.getName());
                    access.setAccessValue(dictionary.getName());
                    return;
                }
            }
        }
        //外部链接访问
        if (!referer.contains(request.getServerName())) {
            access.setAccessSource(SiteAccessSource.EXTERNAL.getName());
            access.setAccessValue(referer);
        } else {
            access.setAccessSource(SiteAccessSource.DIRECT.getName());
        }
    }

    /**
     * 时间组合
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 21:50
     */
    private Date combine(Date date, Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar timec = Calendar.getInstance();
        timec.setTime(time);

        calendar.set(Calendar.HOUR_OF_DAY, timec.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, timec.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, timec.get(Calendar.SECOND));
        return date;
    }
}
