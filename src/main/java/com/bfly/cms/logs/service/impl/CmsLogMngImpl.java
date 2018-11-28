package com.bfly.cms.logs.service.impl;

import com.bfly.cms.logs.entity.CmsLog;
import com.bfly.cms.logs.service.CmsLogMng;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.common.page.Pagination;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.springmvc.MessageResolver;
import com.bfly.cms.logs.dao.CmsLogDao;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.core.web.util.CmsUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/25 18:33
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsLogMngImpl implements CmsLogMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPage(Integer category, Integer siteId,
                              String username, String title, String ip, int pageNo, int pageSize) {
        Pagination page;
        if (StringUtils.isBlank(username)) {
            page = dao.getPage(category, siteId, null, title, ip, pageNo,
                    pageSize);
        } else {
            CmsUser user = cmsUserMng.findByUsername(username);
            if (user != null) {
                page = dao.getPage(category, siteId, user.getId(), title, ip,
                        pageNo, pageSize);
            } else {
                page = new Pagination(1, pageSize, 0, new ArrayList<CmsLog>(0));
            }
        }
        return page;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsLog findById(Integer id) {
        return dao.findById(id);
    }

    public CmsLog save(Integer category, CmsSite site, CmsUser user,
                       String url, String ip, Date date, String title, String content) {
        CmsLog log = new CmsLog();
        log.setSite(site);
        log.setUser(user);
        log.setCategory(category);
        log.setIp(ip);
        log.setTime(date);
        log.setUrl(url);
        log.setTitle(title);
        log.setContent(content);
        save(log);
        return log;
    }

    @Override
    public CmsLog loginSuccess(HttpServletRequest request, CmsUser user) {
        String ip = RequestUtils.getIpAddr(request);
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        Date date = new Date();
        return save(CmsLog.LOGIN_SUCCESS, null, user, uri, ip, date, CmsLog.LOGIN_SUCCESS_TITLE, null);
    }

    @Override
    public CmsLog loginFailure(HttpServletRequest request, String content) {
        String ip = RequestUtils.getIpAddr(request);
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        Date date = new Date();
        return save(CmsLog.LOGIN_FAILURE, null, null, uri, ip, date, CmsLog.LOGIN_FAILURE_TITLE, content);
    }

    @Override
    public CmsLog operating(HttpServletRequest request, String title,
                            String content) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        String ip = RequestUtils.getIpAddr(request);
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        Date date = new Date();
        return save(CmsLog.OPERATING, site, user, uri, ip, date,
                MessageResolver.getMessage(request, title), content);
    }

    @Override
    public CmsLog save(CmsLog bean) {
        return dao.save(bean);
    }

    @Override
    public int deleteBatch(Integer category, Integer siteId, Integer days) {
        Date date = null;
        if (days != null && days > 0) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -days);
            date = cal.getTime();
        }
        return dao.deleteBatch(category, siteId, date);
    }

    @Override
    public CmsLog deleteById(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public CmsLog[] deleteByIds(Integer[] ids) {
        CmsLog[] beans = new CmsLog[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private CmsUserMng cmsUserMng;
    @Autowired
    private CmsLogDao dao;
}