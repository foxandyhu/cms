package com.bfly.cms.content.dao;

import com.bfly.cms.content.entity.Model;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/19 10:32
 */
public interface IModelDao extends IBaseDao<Model, Integer> {

    /**
     * 修改模型排序
     *
     * @param seq     排序
     * @param modelId 模型ID
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/4 15:04
     */
    @Modifying
    @Query("UPDATE Model set seq=:seq where id=:modelId")
    int editModelSeq(@Param("modelId") int modelId, @Param("seq") int seq);

    /**
     * 更新模型状态
     *
     * @param modelId 模型ID
     * @param enabled 是否启用
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/4 20:04
     */
    @Modifying
    @Query("update Model set enabled=:enabled where id=:modelId")
    int editModelEnabled(@Param("modelId") int modelId, @Param("enabled") boolean enabled);

    /**
     * 获得最大的排序序号
     *
     * @return 返回最大的排序序号
     * @author andy_hulibo@163.com
     * @date 2019/8/4 20:46
     */
    @Query(value = "select IFNULL(max(seq),0) as seq from model", nativeQuery = true)
    int getMaxSeq();
}
