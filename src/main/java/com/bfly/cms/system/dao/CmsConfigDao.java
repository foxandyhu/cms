package com.bfly.cms.system.dao;

import com.bfly.cms.system.entity.CmsConfig;
import com.bfly.common.hibernate4.Updater;

/**
 * 系统配置业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 17:27
 */
public interface CmsConfigDao {

    /**
     * 根据配置ID获得对应的系统配置对象
     *
     * @param id 配置ID
     * @return 配置对象
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:28
     */
    CmsConfig findById(Integer id);

    /**
     * 更新系统配置
     *
     * @param updater 系统配置
     * @return 系统配置
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:28
     */
    CmsConfig updateByUpdater(Updater<CmsConfig> updater);
}