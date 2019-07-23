package com.bfly.cms.system.service;

import com.bfly.cms.system.entity.SiteConfig;
import com.bfly.cms.system.entity.SysFirewall;
import com.bfly.core.base.service.IBaseService;

/**
 * 系统防火墙设置业务接口
 * @author andy_hulibo@163.com
 * @date 2019/7/22 10:16
 */
public interface ISysFirewallService extends IBaseService<SysFirewall, Integer> {

    /**
     * 得到系统防火墙配置
     * @author andy_hulibo@163.com
     * @date 2019/7/22 10:17
     */
    SysFirewall getSysFirewall();
}
