package com.bfly.cms.webservice.service.impl;

import com.bfly.core.Constants;
import com.bfly.cms.webservice.dao.ApiUserLoginDao;
import com.bfly.cms.webservice.entity.ApiAccount;
import com.bfly.cms.webservice.entity.ApiUserLogin;
import com.bfly.cms.webservice.service.ApiAccountMng;
import com.bfly.cms.webservice.service.ApiUserLoginMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.AES128Util;
import com.bfly.common.util.DateUtils;
import com.bfly.common.web.RequestUtils;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.cms.user.service.UnifiedUserMng;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/25 15:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiUserLoginMngImpl implements ApiUserLoginMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPage(int pageNo, int pageSize) {
        return dao.getPage(pageNo, pageSize);
    }

    @Override
    public void clearByDate(Date end) {
        dao.clearByDate(end);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<ApiUserLogin> getList(Date end, int first, int count) {
        return dao.getList(end, first, count);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ApiUserLogin findById(Long id) {
        return dao.findById(id);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ApiUserLogin findUserLogin(String username, String sessionKey) {
        return dao.findUserLogin(username, sessionKey);
    }

    @Override
    public CmsUser getUser(HttpServletRequest request) {
        CmsUser user = null;
        ApiAccount apiAccount = apiAccountMng.getApiAccount(request);
        if (apiAccount != null && !apiAccount.getDisabled()) {
            user = getUser(apiAccount, request);
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsUser getUser(ApiAccount apiAccount, HttpServletRequest request) {
        String sessionKey = RequestUtils.getQueryParam(request, Constants.COMMON_PARAM_SESSIONKEY);
        String aesKey = apiAccount.getAesKey();
        return findUser(sessionKey, aesKey, apiAccount.getIvKey());
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsUser findUser(String sessionKey, String aesKey, String ivKey) {
        String decryptSessionKey = "";
        CmsUser user = null;
        if (StringUtils.isNotBlank(sessionKey)) {
            try {
                //sessionKey用户会话标志加密串
                decryptSessionKey = AES128Util.decrypt(sessionKey, aesKey, ivKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ApiUserLogin apiUserLogin = findUserLogin(null, decryptSessionKey);
            if (apiUserLogin != null && StringUtils.isNotBlank(decryptSessionKey)) {
                String username = apiUserLogin.getUsername();
                if (StringUtils.isNotBlank(username)) {
                    user = userMng.findByUsername(username);
                }
            }
        }
        return user;
    }

    @Override
    public ApiUserLogin userLogin(String username, String appId, String sessionKey,
                                  HttpServletRequest request, HttpServletResponse response) {
        ApiAccount apiAccount;
        if (StringUtils.isNotBlank(appId)) {
            apiAccount = apiAccountMng.findByAppId(appId);
        } else {
            apiAccount = apiAccountMng.findByDefault();
        }
        // 一个账户只允许一个设备登陆则修改
        ApiUserLogin login;
        boolean isLimitSingleDev = false;
        if (apiAccount != null && !apiAccount.getDisabled() && apiAccount.getLimitSingleDevice()) {
            isLimitSingleDev = true;
        }
        if (isLimitSingleDev) {
            login = findUserLogin(username, null);
        } else {
            login = findUserLogin(username, sessionKey);
        }
        if (login == null) {
            login = new ApiUserLogin();
            login.setLoginTime(Calendar.getInstance().getTime());
            login.setActiveTime(Calendar.getInstance().getTime());
            login.setLoginCount(1);
            login.setSessionKey(sessionKey);
            login.setUsername(username);
            login = save(login);
        } else {
            login.setLoginTime(Calendar.getInstance().getTime());
            login.setActiveTime(Calendar.getInstance().getTime());
            login.setLoginCount(1 + login.getLoginCount());
            login.setSessionKey(sessionKey);
            update(login);
        }
        onLoginSuccess(username, request, response);
        return login;
    }

    @Override
    public ApiUserLogin userLogout(String username, String appId, String sessionKey) {
        ApiUserLogin login = findUserLogin(username, sessionKey);
        if (login != null) {
            boolean isLimitSingleDev = false;
            ApiAccount apiAccount;
            if (StringUtils.isNotBlank(appId)) {
                apiAccount = apiAccountMng.findByAppId(appId);
            } else {
                apiAccount = apiAccountMng.findByDefault();
            }
            // 一个账户只允许一个设备登陆则修改
            if (apiAccount != null && !apiAccount.getDisabled() && apiAccount.getLimitSingleDevice()) {
                isLimitSingleDev = true;
            }
            if (isLimitSingleDev) {
                login.setSessionKey("");
                login.setActiveTime(null);
                update(login);
            } else {
                deleteById(login.getId());
            }
        }
        return login;
    }

    @Override
    public void userActive(HttpServletRequest request, HttpServletResponse response) {
        String sessionKey = RequestUtils.getQueryParam(request, Constants.COMMON_PARAM_SESSIONKEY);
        ApiAccount apiAccount = apiAccountMng.getApiAccount(request);
        Short status = getStatus(apiAccount, request, response);
        if (apiAccount != null) {
            String decryptSessionKey = "";
            String aesKey = apiAccount.getAesKey();
            try {
                decryptSessionKey = AES128Util.decrypt(sessionKey, aesKey, apiAccount.getIvKey());
            } catch (Exception e) {
            }
            if (StringUtils.isNotBlank(decryptSessionKey)) {
                userActive(decryptSessionKey);
            }
        }
        if (apiAccount != null && status.equals(ApiUserLogin.USER_STATUS_LOGIN)) {
            userActive(sessionKey);
        }
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Short getStatus(ApiAccount apiAccount,
                           HttpServletRequest request, HttpServletResponse response) {
        String sessionKey = RequestUtils.getQueryParam(request, Constants.COMMON_PARAM_SESSIONKEY);
        Short loginStatus = ApiUserLogin.USER_STATUS_LOGOUT;
        if (apiAccount != null) {
            String decryptSessionKey = "";
            String aesKey = apiAccount.getAesKey();
            try {
                decryptSessionKey = AES128Util.decrypt(sessionKey, aesKey, apiAccount.getIvKey());
            } catch (Exception e) {
            }
            if (StringUtils.isNotBlank(decryptSessionKey)) {
                loginStatus = getUserStatus(decryptSessionKey);
            }
        }
        return loginStatus;
    }


    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Short getUserStatus(String sessionKey) {
        ApiUserLogin login = findUserLogin(null, sessionKey);
        if (login != null && login.getActiveTime() != null && login.getSessionKey().equals(sessionKey)) {
            Date activeTime = login.getActiveTime();
            Date now = Calendar.getInstance().getTime();
            if (DateUtils.getDiffMinuteTwoDate(activeTime, now) <= Constants.USER_OVER_TIME) {
                return ApiUserLogin.USER_STATUS_LOGIN;
            } else {
                return ApiUserLogin.USER_STATUS_LOGOVERTIME;
            }
        } else {
            return ApiUserLogin.USER_STATUS_LOGOUT;
        }
    }


    @Override
    public ApiUserLogin save(ApiUserLogin bean) {
        dao.save(bean);
        return bean;
    }

    @Override
    public ApiUserLogin update(ApiUserLogin bean) {
        Updater<ApiUserLogin> updater = new Updater<>(bean);
        bean = dao.updateByUpdater(updater);
        return bean;
    }

    @Override
    public ApiUserLogin deleteById(Long id) {
        return dao.deleteById(id);
    }

    @Override
    public ApiUserLogin[] deleteByIds(Long[] ids) {
        ApiUserLogin[] beans = new ApiUserLogin[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    private ApiUserLogin userActive(String sessionKey) {
        ApiUserLogin login = findUserLogin(null, sessionKey);
        if (login != null) {
            login.setActiveTime(Calendar.getInstance().getTime());
        }
        return login;
    }

    private void onLoginSuccess(String username,
                                HttpServletRequest request, HttpServletResponse response) {
        CmsUser user = userMng.findByUsername(username);
        String ip = RequestUtils.getIpAddr(request);
        Date now = new Timestamp(System.currentTimeMillis());
        String userSessionId = request.getSession().getId();
        userMng.updateLoginInfo(user.getId(), ip, now, userSessionId);
        unifiedUserMng.updateLoginSuccess(user.getId(), ip);
    }

    @Autowired
    private ApiUserLoginDao dao;
    @Autowired
    private CmsUserMng userMng;
    @Autowired
    private ApiAccountMng apiAccountMng;
    @Autowired
    private UnifiedUserMng unifiedUserMng;
}