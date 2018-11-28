package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.CmsModelItem;

import java.util.List;

/**
 * 模型字段业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 11:29
 */
public interface CmsModelItemMng {

    /**
     * 获得模型字段集合
     *
     * @param modelId     模型类型ID
     * @param isChannel   是否栏目模型项
     * @param hasDisabled 是否显示
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:34
     */
    List<CmsModelItem> getList(Integer modelId, boolean isChannel,
                               Boolean hasDisabled);

    /**
     * 根据Id获得模型字段
     *
     * @param id 模型字段ID
     * @return 模型字段
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:34
     */
    CmsModelItem findById(Integer id);

    /**
     * 新增模型字段
     *
     * @param bean 模型字段
     * @return 模型字段
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:33
     */
    CmsModelItem save(CmsModelItem bean);

    /**
     * 新增模型字段
     *
     * @param bean    模型字段
     * @param modelId 模型ID
     * @return 模型字段
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:33
     */
    CmsModelItem save(CmsModelItem bean, Integer modelId);

    /**
     * 批量保存模型字段
     *
     * @param list 模型字段集合
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:32
     */
    void saveList(List<CmsModelItem> list);

    /**
     * 更新模型字段顺序
     *
     * @param priority 顺序
     * @param label    名称
     * @param wids     模型字段ID
     * @param display  是否显示
     * @param single   是否独占一行
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:30
     */
    void updatePriority(Integer[] wids, Integer[] priority,
                        String[] label, Boolean[] single, Boolean[] display);

    /**
     * 更新模型字段
     *
     * @param bean 模型字段
     * @return 模型字段
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:30
     */
    CmsModelItem update(CmsModelItem bean);

    /**
     * 删除模型字段
     *
     * @param id 模型字段ID
     * @return 模型字段
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:29
     */
    CmsModelItem deleteById(Integer id);

    /**
     * 批量删除模型字段
     *
     * @param ids 模型字段ID
     * @return 模型字段
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:29
     */
    CmsModelItem[] deleteByIds(Integer[] ids);
}