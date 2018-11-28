package com.bfly.cms.system.dao;

import com.bfly.cms.system.entity.Config;

import java.util.List;

/**
 * 系统配置属性数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 17:23
 */
public interface ConfigDao {

    /**
     * 获得所有的配置属性
     *
     * @return 配置属性集合
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:23
     */
    List<Config> getList();

    /**
     * 根据配置属性key获得配置属性
     *
     * @param id 属性key
     * @return 配置属性
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:24
     */
    Config findById(String id);

    /**
     * 保存配置属性
     *
     * @param bean 配置属性
     * @return 配置属性
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:24
     */
    Config save(Config bean);

    /**
     * 根据配置属性key删除配置属性
     *
     * @param id 属性key
     * @return 配置属性
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:25
     */
    Config deleteById(String id);
}