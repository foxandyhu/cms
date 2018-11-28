package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.ContentType;

import java.util.List;

/**
 * 内容类型业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 10:24
 */
public interface ContentTypeMng {

    /**
     * 获得内容类型集合
     *
     * @param isDisabled 是否禁用
     * @return 内容类型集合
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:27
     */
    List<ContentType> getList(Boolean isDisabled);

    /**
     * 获得默认的内容类型
     *
     * @return 内容类型
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:29
     */
    ContentType getDef();

    /**
     * 根据内容类型Id获得对应的类型
     *
     * @param id 类型ID
     * @return 内容类型
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:29
     */
    ContentType findById(Integer id);

    /**
     * 新增内容类型
     *
     * @param bean 内容类型
     * @return 内容类型
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:30
     */
    ContentType save(ContentType bean);

    /**
     * 修改内容类型
     *
     * @param bean 内容类型
     * @return 内容类型
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:30
     */
    ContentType update(ContentType bean);

    /**
     * 根据类型Id删除内容类型
     *
     * @param id 内容类型ID
     * @return 内容类型
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:30
     */
    ContentType deleteById(Integer id);

    /**
     * 根据类型Id批量删除内容类型
     *
     * @param ids 内容类型ID
     * @return 内容类型集合
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:30
     */
    ContentType[] deleteByIds(Integer[] ids);
}