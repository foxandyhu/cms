package com.bfly.cms.webservice.service;

import com.bfly.cms.webservice.entity.ApiInfo;
import com.bfly.common.page.Pagination;

/**
 * API接口信息业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 14:55
 */
public interface ApiInfoMng {

    /**
     * 获得所有API接口信息
     *
     * @param pageSize 每页数据
     * @param pageNo   页码
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2018/11/25 14:56
     */
    Pagination getPage(int pageNo, int pageSize);

    /**
     * 根据接口URL返回API接口信息
     *
     * @param url 接口URI
     * @return API接口信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 14:57
     */
    ApiInfo findByUrl(String url);

    /**
     * 根据ID返回接口信息
     *
     * @param id API接口ID
     * @return API接口信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 14:58
     */
    ApiInfo findById(Integer id);

    /***
     * 新增API接口信息
     * @param bean 接口信息
     * @return API接口信息
     */
    ApiInfo save(ApiInfo bean);

    /**
     * 更新API接口信息
     *
     * @param bean 接口信息
     * @return 接口信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:00
     */
    ApiInfo update(ApiInfo bean);

    /**
     * 根据ID删除接口信息
     *
     * @param id 接口ID
     * @return 接口信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:01
     */
    ApiInfo deleteById(Integer id);

    /**
     * 根据ID删除接口信息
     *
     * @param ids 接口ID
     * @return 接口信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:01
     */
    ApiInfo[] deleteByIds(Integer[] ids);
}