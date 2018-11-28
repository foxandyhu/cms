package com.bfly.cms.webservice.dao;

import com.bfly.cms.webservice.entity.ApiInfo;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

/**
 * API接口信息数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 14:55
 */
public interface ApiInfoDao {

    /**
     * 查询所有API接口信息
     *
     * @param pageSize 每页数据
     * @param pageNo   页码
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2018/11/25 14:56
     */
    Pagination getPage(int pageNo, int pageSize);

    /**
     * 根据接口URL查询API接口信息
     *
     * @param url 接口URI
     * @return API接口信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 14:57
     */
    ApiInfo findByUrl(String url);

    /**
     * 根据ID查询接口信息
     *
     * @param id API接口ID
     * @return API接口信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 14:58
     */
    ApiInfo findById(Integer id);

    /**
     * 保存API接口信息
     *
     * @param bean 接口信息
     * @return API接口信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:00
     */
    ApiInfo save(ApiInfo bean);

    /**
     * 更新API接口信息
     *
     * @param updater 接口信息
     * @return 接口信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:00
     */
    ApiInfo updateByUpdater(Updater<ApiInfo> updater);

    /**
     * 根据ID删除接口信息
     *
     * @param id 接口ID
     * @return 接口信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:01
     */
    ApiInfo deleteById(Integer id);
}