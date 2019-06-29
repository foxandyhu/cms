package com.bfly.cms.logs.service;

import com.bfly.cms.logs.entity.SysLog;
import com.bfly.core.base.service.IBaseService;
import com.bfly.core.enums.LogsType;

/**
 * 系统日志操作业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/10 16:33
 */
public interface ISysLogService extends IBaseService<SysLog, Integer> {

    /**
     * 保存日志信息
     * @author andy_hulibo@163.com
     * @date 2019/6/27 17:24
     */
    void save(LogsType category, String userName, String ip, String url, String title, String content, boolean success);
}
