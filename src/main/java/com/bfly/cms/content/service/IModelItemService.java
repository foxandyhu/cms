package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.ModelItem;
import com.bfly.core.base.service.IBaseService;

import java.util.List;

/**
 * 模型项业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 10:30
 */
public interface IModelItemService extends IBaseService<ModelItem, Integer> {

    /**
     * 查找模型下的项集合
     * @param modelId 模型ID
     * @return 模型项集合
     * @author andy_hulibo@163.com
     * @date 2019/8/4 13:58
     */
    List<ModelItem> getModelItems(int modelId);

    /**
     * 系统默认模型项和模型绑定
     *
     * @param modelId 模型ID
     * @param itemIds 系统模型项ID
     * @author andy_hulibo@163.com
     * @date 2019/8/4 14:14
     */
    void bindModel(int modelId, Integer... itemIds);

    /**
     * 排序模型项
     *
     * @param upItemId   排序上移的项
     * @param downItemId 排序下移的项
     * @author andy_hulibo@163.com
     * @date 2019/8/4 14:55
     */
    void sortModelItem(int upItemId, int downItemId);
}
