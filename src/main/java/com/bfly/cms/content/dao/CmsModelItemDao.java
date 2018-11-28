package com.bfly.cms.content.dao;

import com.bfly.cms.content.entity.CmsModelItem;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

/**
 * 模型字段数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 11:36
 */
public interface CmsModelItemDao {

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
     * 更新模型字段
     *
     * @param updater 模型字段
     * @return 模型字段
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:30
     */
    CmsModelItem updateByUpdater(Updater<CmsModelItem> updater);

    /**
     * 删除模型字段
     *
     * @param id 模型字段ID
     * @return 模型字段
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:29
     */
    CmsModelItem deleteById(Integer id);
}