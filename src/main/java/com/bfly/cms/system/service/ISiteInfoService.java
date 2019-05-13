package com.bfly.cms.system.service;

import com.bfly.cms.system.entity.SiteInfo;
import com.bfly.core.base.service.IBaseService;

/**
 * 站点信息业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:03
 */
public interface ISiteInfoService extends IBaseService<SiteInfo, Integer> {

    /**
     * 得到系统站点信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 11:11
     */
    SiteInfo getSite();
}
