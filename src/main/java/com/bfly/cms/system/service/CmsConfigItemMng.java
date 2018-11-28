package com.bfly.cms.system.service;

import com.bfly.cms.system.entity.CmsConfigItem;

import java.util.List;

/**
 * 系统配置模型项业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 17:37
 */
public interface CmsConfigItemMng {

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
     * 更新模型排列顺序
     *
     * @param wid      模型项ID集合
     * @param priority 排列顺序
     * @param label    名称
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:47
     */
    void updatePriority(Integer[] wid, Integer[] priority, String[] label);

    /**
     * 更新模型项
     *
     * @param bean 模型项
     * @return 模型项
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:43
     */
    CmsConfigItem update(CmsConfigItem bean);

    /**
     * 根据ID删除模型项
     *
     * @param id 模型项ID
     * @return 模型项
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:44
     */
    CmsConfigItem deleteById(Integer id);

    /**
     * 批量根据ID删除模型项
     *
     * @param ids 模型项ID集合
     * @return 模型项
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:44
     */
    CmsConfigItem[] deleteByIds(Integer[] ids);
}