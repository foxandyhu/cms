package com.bfly.cms.user.service.impl;

import com.bfly.cms.user.dao.AuthenticationDao;
import com.bfly.cms.user.entity.Authentication;
import com.bfly.cms.user.entity.UnifiedUser;
import com.bfly.cms.user.service.AuthenticationMng;
import com.bfly.cms.user.service.UnifiedUserMng;
import com.bfly.common.page.Pagination;
import com.bfly.core.exception.BadCredentialsException;
import com.bfly.core.exception.UsernameNotFoundException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/28 15:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AuthenticationMngImpl implements AuthenticationMng {
    private Logger log = LoggerFactory.getLogger(AuthenticationMngImpl.class);

    @Override
    public Authentication login(String username, String password, String ip,
                                HttpServletRequest request, HttpServletResponse response) throws UsernameNotFoundException,
            BadCredentialsException {
        UnifiedUser user = unifiedUserMng.login(username, password, ip);
        Authentication auth = new Authentication();
        auth.setUid(user.getId());
        auth.setUsername(user.getUsername());
        auth.setEmail(user.getEmail());
        auth.setLoginIp(ip);
        save(auth);
        request.getSession().setAttribute(AUTH_KEY, auth.getId());
        return auth;
    }


    @Override
    public Authentication retrieve(String authId) {
        long current = System.currentTimeMillis();
        // 是否刷新数据库
        if (refreshTime < current) {
            refreshTime = getNextRefreshTime(current, interval);
            int count = dao.deleteExpire(new Date(current - timeout));
            log.info("refresh Authentication, delete count: {}", count);
        }
        Authentication auth = findById(authId);
        if (auth != null && auth.getUpdateTime().getTime() + timeout > current) {
            auth.setUpdateTime(new Timestamp(current));
            return auth;
        }
        return null;
    }

    @Override
    public void storeAuthIdToSession(HttpServletRequest request, HttpServletResponse response, String authId) {
        request.getSession().setAttribute(AUTH_KEY, authId);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPage(int pageNo, int pageSize) {
        return dao.getPage(pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Authentication findById(String id) {
        return dao.findById(id);
    }

    @Override
    public Authentication save(Authentication bean) {
        bean.setId(StringUtils.remove(UUID.randomUUID().toString(), '-'));
        bean.init();
        dao.save(bean);
        return bean;
    }

    @Override
    public Authentication deleteById(String id) {
        return dao.deleteById(id);
    }

    @Override
    public Authentication[] deleteByIds(String[] ids) {
        Authentication[] beans = new Authentication[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    /**
     * 过期时间 30分钟
     */
    private int timeout = 30 * 60 * 1000;

    /**
     * 间隔时间 4小时
     */
    private int interval = 4 * 60 * 60 * 1000;

    /**
     * 刷新时间
     */
    private long refreshTime = getNextRefreshTime(System.currentTimeMillis(), this.interval);

    /**
     * @param timeout 单位分钟
     * @author andy_hulibo@163.com
     * @date 2018/11/28 15:37
     * 设置认证过期时间。默认30分钟。
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout * 60 * 1000;
    }

    /**
     * @param interval 单位分钟
     * @author andy_hulibo@163.com
     * @date 2018/11/28 15:37
     * 设置刷新数据库时间。默认4小时。
     */
    public void setInterval(int interval) {
        this.interval = interval * 60 * 1000;
        this.refreshTime = getNextRefreshTime(System.currentTimeMillis(),
                this.interval);
    }

    /**
     * @param current  当前时间
     * @param interval 周期
     * @return 随机间隔时间
     * @author andy_hulibo@163.com
     * @date 2018/11/28 15:37
     * 获得下一个刷新时间。
     */
    private long getNextRefreshTime(long current, int interval) {
        return current + interval;
    }

    @Autowired
    private UnifiedUserMng unifiedUserMng;

    @Autowired
    private AuthenticationDao dao;
}