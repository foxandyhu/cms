package com.bfly.cms.system.service;

import com.bfly.cms.system.entity.SysTask;
import com.bfly.core.base.service.IBaseService;

/**
 * 系统任务业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/14 11:56
 */
public interface ISysTaskService extends IBaseService<SysTask, Integer> {

    /**
     * 启动系统任务
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 12:32
     */
    boolean startTask(String name);

    /**
     * 停止系统任务
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 12:31
     */
    boolean stopTask(String name);
}
