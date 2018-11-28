package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.CmsModel;

import java.util.List;

/**
 * 模型业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 10:47
 */
public interface CmsModelMng {

    /**
     * 获得模型列表
     *
     * @param isDisabled 是否禁用
     * @param siteId     站点
     * @param hasContent 是否有内容
     * @return 模型集合
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:47
     */
    List<CmsModel> getList(boolean isDisabled, Boolean hasContent, Integer siteId);

    /**
     * 获得默认模型
     *
     * @return 模型
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:48
     */
    CmsModel getDefModel();

    /**
     * 根据Id获得模型
     *
     * @param id 模型ID
     * @return 模型
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:48
     */
    CmsModel findById(Integer id);

    /**
     * 根据模型路径获得模型
     *
     * @param path 路径
     * @return 模型
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:49
     */
    CmsModel findByPath(String path);

    /**
     * 新增模型
     *
     * @param bean 模型
     * @return 模型
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:49
     */
    CmsModel save(CmsModel bean);

    /**
     * 更新模型
     *
     * @param bean 模型
     * @return 模型
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:49
     */
    CmsModel update(CmsModel bean);

    /**
     * 删除模型
     *
     * @param id 模型ID
     * @return 模型
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:50
     */
    CmsModel deleteById(Integer id);

    /**
     * 批量删除模型
     *
     * @param ids 模型ID
     * @return 模型
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:50
     */
    CmsModel[] deleteByIds(Integer[] ids);

    /**
     * 更新模型顺序
     *
     * @param ids      模型ID
     * @param priority 顺序
     * @param defId    默认模型ID
     * @param disabled 是否禁用
     * @return 模型
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:50
     */
    CmsModel[] updatePriority(Integer[] ids, Integer[] priority,
                              Boolean[] disabled, Integer defId);
}