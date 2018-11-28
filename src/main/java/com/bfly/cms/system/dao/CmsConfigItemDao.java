package com.bfly.cms.system.dao;

import com.bfly.cms.system.entity.CmsConfigItem;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

/**
 * 系统模型项配置数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 17:40
 */
public interface CmsConfigItemDao {

    /**
     * 根据配置ID和类型获得模型项配置
     *
     * @param configId 配置ID
     * @param category 类型
     * @return 模型项配置集合
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:40
     */
    List<CmsConfigItem> getList(Integer configId, Integer category);

    /**
     * 根据ID获得模型项配置
     *
     * @param id 模型项配置ID
     * @return 模型项
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:41
     */
    CmsConfigItem findById(Integer id);

    /**
     * 保存模型项
     *
     * @param bean 模型项
     * @return 模型项
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:43
     */
    CmsConfigItem save(CmsConfigItem bean);

    /**
     * 更新模型项
     *
     * @param updater 模型项
     * @return 模型项
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:43
     */
    CmsConfigItem updateByUpdater(Updater<CmsConfigItem> updater);

    /**
     * 根据ID删除模型项
     *
     * @param id 模型项ID
     * @return 模型项
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:44
     */
    CmsConfigItem deleteById(Integer id);
}