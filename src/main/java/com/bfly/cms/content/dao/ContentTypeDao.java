package com.bfly.cms.content.dao;

import com.bfly.cms.content.entity.ContentType;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

/**
 * 内容类型数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 10:33
 */
public interface ContentTypeDao {

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
     * @param updater 内容类型
     * @return 内容类型
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:30
     */
    ContentType updateByUpdater(Updater<ContentType> updater);

    /**
     * 根据类型Id删除内容类型
     *
     * @param id 内容类型ID
     * @return 内容类型
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:30
     */
    ContentType deleteById(Integer id);
}