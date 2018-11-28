package com.bfly.cms.siteconfig.dao;

import com.bfly.cms.siteconfig.entity.CmsOss;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

/**
 * 云存储数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/23 16:30
 */
public interface CmsOssDao {

    /**
     * 获得分页对象的云存储对象
     *
     * @param pageNo   页码
     * @param pageSize 每页显示数量
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 16:30
     */
    Pagination getPage(int pageNo, int pageSize);

    /**
     * 获得云存储集合
     *
     * @return 云存储集合
     * @author andy_hulibo@163.com
     * @date 2018/11/23 16:31
     */
    List<CmsOss> getList();

    /**
     * 根据云存储ID查找对应的对象
     *
     * @param id 云存储ID
     * @return 配置对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 16:31
     */
    CmsOss findById(Integer id);

    /**
     * 添加云存储对象
     *
     * @param bean 云存储对象
     * @return 云存储对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 16:31
     */
    CmsOss save(CmsOss bean);

    /**
     * 更新云存储对象
     *
     * @param updater 云存储对象
     * @return 云存储对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 16:32
     */
    CmsOss updateByUpdater(Updater<CmsOss> updater);

    /**
     * 根据云存储对象Id删除配置
     *
     * @param id 云存储对象ID
     * @return 云存储对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 16:32
     */
    CmsOss deleteById(Integer id);
}