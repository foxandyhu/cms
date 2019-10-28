package com.bfly.cms.member.service.impl;

import com.bfly.cms.logs.entity.SysLog;
import com.bfly.cms.logs.service.ISysLogService;
import com.bfly.cms.member.dao.IMemberDao;
import com.bfly.core.context.IpThreadLocal;
import com.bfly.core.context.ServletRequestThreadLocal;
import com.bfly.core.enums.LogsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 用户登录日志业务类
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/18 15:18
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class MemberLoginLogsServiceImpl {

    @Autowired
    private IMemberDao memberDao;
    @Autowired
    private ISysLogService sysLogService;

    /**
     * 保存登录/登出信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/26 18:33
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveLoginLogs(String userName, String title, LogsType type, boolean isSuccess) {
        HttpServletRequest request = ServletRequestThreadLocal.get();
        SysLog log = new SysLog();
        log.setTime(new Date());
        log.setTitle(title);
        log.setUserName(userName);
        log.setIp(IpThreadLocal.get());
        log.setUrl(request.getRequestURL().toString());
        log.setSuccess(isSuccess);
        log.setContent(null);
        log.setCategory(type.getId());
        sysLogService.save(log);
    }

    /**
     * 更新登录信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:33
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateLoginInfo(String userName, boolean loginSuccess) {
        HttpServletRequest request = ServletRequestThreadLocal.get();
        String sessionId = null;
        if (request != null) {
            sessionId = request.getSession().getId();
        }
        if (loginSuccess) {
            memberDao.incrementMemberLoginSuccess(IpThreadLocal.get(), new Date(), sessionId, userName);
            return;
        }
        memberDao.incrementMemberLoginError(IpThreadLocal.get(), new Date(), sessionId, userName);
    }
}
