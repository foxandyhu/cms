package com.bfly.cms.content.dao;

import com.bfly.cms.content.entity.ModelItem;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/19 10:32
 */
public interface IModelItemDao extends IBaseDao<ModelItem, Integer> {

    /**
     * 根据模型ID查找模型项
     *
     * @param modelId 模型项
     * @return 模型项集合
     * @author andy_hulibo@163.com
     * @date 2019/8/4 14:01
     */
    @Query("select item from ModelItem as item where item.modelId=:modelId order by item.seq asc ")
    List<ModelItem> getModelItems(@Param("modelId") int modelId);


    /**
     * 查询模型有多少模型项字段
     *
     * @param modelId 模型项
     * @return 模型项总数
     * @author andy_hulibo@163.com
     * @date 2019/8/4 16:10
     */
    @Query(value = "select count(1) from model_item where model_id=:modelId", nativeQuery = true)
    long getCount(@Param("modelId") int modelId);

    /**
     * 根据模型ID和模型项字段名查找是否有模型项
     *
     * @param modelId 模型ID
     * @param field   字段名称
     * @return 模型项
     * @author andy_hulibo@163.com
     * @date 2019/8/4 14:25
     */
    @Query("select item from ModelItem as item where item.modelId=:modelId and item.field=:field")
    ModelItem getModelItem(@Param("modelId") int modelId, @Param("field") String field);

    /**
     * 修改模型项排序
     *
     * @param seq         排序
     * @param modelItemId 模型项ID
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/4 15:04
     */
    @Modifying
    @Query("UPDATE ModelItem set seq=:seq where id=:modelItemId")
    int editModelItemSeq(@Param("modelItemId") int modelItemId, @Param("seq") int seq);

    /**
     * 获得最大的排序序号
     *
     * @param modelId 所属模型ID
     * @return 返回最大的排序序号
     * @author andy_hulibo@163.com
     * @date 2019/8/4 20:46
     */
    @Query(value = "select IFNULL(max(seq),0) as seq from model_item where model_id=:modelId", nativeQuery = true)
    int getMaxSeq(@Param("modelId") int modelId);
}
