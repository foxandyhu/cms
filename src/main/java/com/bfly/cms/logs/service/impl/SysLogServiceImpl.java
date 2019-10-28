package com.bfly.cms.logs.service.impl;

import com.bfly.cms.logs.dao.ISysLogDao;
import com.bfly.cms.logs.entity.SysLog;
import com.bfly.cms.logs.service.ISysLogService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.enums.LogsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/10 16:34
 */
@Service
@Transactional(propagation= Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SysLogServiceImpl extends BaseServiceImpl<SysLog, Integer> implements ISysLogService {

    @Autowired
    private ISysLogDao sysLogDao;

    /**
     * 保存日志
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 13:34
     */
    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void save(LogsType category, String userName, String ip, String url, String title, String content, boolean success,boolean isMember) {
        SysLog sysLog = new SysLog();
        sysLog.setTime(new Date());
        sysLog.setIp(ip);
        sysLog.setTitle(title);
        sysLog.setSuccess(success);
        sysLog.setUrl(url);
        sysLog.setContent(content);
        sysLog.setUserName(userName);
        sysLog.setCategory(category.getId());
        sysLog.setMember(isMember);
        sysLogDao.save(sysLog);
    }
}
