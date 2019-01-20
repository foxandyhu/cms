package com.bfly.cms.system.service;

import com.bfly.cms.system.entity.SysConfig;
import com.bfly.core.base.service.IBaseService;

/**
 * 系统配置业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/20 10:49
 */
public interface ISysConfigService extends IBaseService<SysConfig, Integer> {

    /**
     * 获得系统配置
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 10:50
     */
    SysConfig getSysConfig();
}
