package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.Model;
import com.bfly.core.base.service.IBaseService;

/**
 * 模型业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 10:30
 */
public interface IModelService extends IBaseService<Model, Integer> {

    /**
     * 排序模型
     *
     * @param upItemId   排序上移的项
     * @param downItemId 排序下移的项
     * @author andy_hulibo@163.com
     * @date 2019/8/4 14:55
     */
    void sortModel(int upItemId, int downItemId);

    /**
     * 更新模型状态
     * @author andy_hulibo@163.com
     * @date 2019/8/4 20:03
     */
    boolean editModelEnabled(int modelId, boolean enabled);
}
