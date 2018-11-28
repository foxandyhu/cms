package com.bfly.cms.siteconfig.dao;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

/**
 * 站点信息数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/23 11:17
 */
public interface CmsSiteDao {

    /**
     * 查询所有站点信息并设置是否查询缓存
     *
     * @param cacheAble 是否启用查询缓存
     * @return 站点集合
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:03
     */
    List<CmsSite> getList(boolean cacheAble);

    /**
     * 查询是否有重复的属性值
     *
     * @param property 属性名
     * @return 返回重复个数
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:22
     */
    int getCountByProperty(String property);

    /**
     * 根据域名查询站点信息
     *
     * @param domain 域名
     * @return 站点对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:16
     */
    CmsSite findByDomain(String domain);

    /**
     * 根据站点信息ID查询站点
     *
     * @param id 站点ID
     * @return 站点对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:24
     */
    CmsSite findById(Integer id);

    /**
     * 保存站点信息
     *
     * @param bean 站点对象
     * @return 站点对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:26
     */
    CmsSite save(CmsSite bean);

    /**
     * 更新站点信息
     *
     * @param updater 更新站点
     * @return 站点对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:37
     */
    CmsSite updateByUpdater(Updater<CmsSite> updater);

    /**
     * 根据ID删除站点
     *
     * @param id 站点ID
     * @return 站点对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:38
     */
    CmsSite deleteById(Integer id);


}