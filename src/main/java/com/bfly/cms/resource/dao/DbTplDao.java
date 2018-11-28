package com.bfly.cms.resource.dao;

import com.bfly.cms.resource.entity.DbTpl;

import java.util.List;

/**
 * 模板数据库接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 12:13
 */
public interface DbTplDao {

    /**
     * 获得模板集合
     *
     * @param prefix 前缀
     * @return 模板集合
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:13
     */
    List<DbTpl> getStartWith(String prefix);

    /**
     * 获得模板子项
     *
     * @param path        路径
     * @param isDirectory 是否文件夹
     * @return 模板集合
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:15
     */
    List<DbTpl> getChild(String path, boolean isDirectory);

    /**
     * 获得模板
     *
     * @param id 模板ID
     * @return 模板
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:16
     */
    DbTpl findById(String id);

    /**
     * 保存模板
     *
     * @param bean 模板
     * @return 模板
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:17
     */
    DbTpl save(DbTpl bean);

    /**
     * 删除模板
     *
     * @param id 模板Id
     * @return 模板
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:17
     */
    DbTpl deleteById(String id);
}