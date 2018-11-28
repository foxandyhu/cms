package com.bfly.cms.webservice.dao;

import com.bfly.cms.webservice.entity.ApiRecord;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

/**
 * API接口调用记录数据层
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 10:32
 */
public interface ApiRecordDao {

    /**
     * 获得API接口记录
     *
     * @param pageNo   页码
     * @param pageSize 每页数据
     * @return API接口记录
     * @author andy_hulibo@163.com
     * @date 2018/11/24 22:20
     */
    Pagination getPage(int pageNo, int pageSize);

    /**
     * 根据签名和APPID查找记录
     *
     * @param sign  签名
     * @param appId 应用ID
     * @return API接口记录
     * @author andy_hulibo@163.com
     * @date 2018/11/24 22:19
     */
    ApiRecord findBySign(String sign, String appId);

    /**
     * 根据ID查找记录
     *
     * @param id ID
     * @return API接口记录
     * @author andy_hulibo@163.com
     * @date 2018/11/24 22:19
     */
    ApiRecord findById(Long id);

    /**
     * 保存API接口记录
     *
     * @param bean API接口记录
     * @return API接口记录
     * @author andy_hulibo@163.com
     * @date 2018/11/24 22:16
     */
    ApiRecord save(ApiRecord bean);

    /**
     * 更新API接口记录
     *
     * @param updater API接口记录
     * @return API接口记录
     * @author andy_hulibo@163.com
     * @date 2018/11/24 22:16
     */
    ApiRecord updateByUpdater(Updater<ApiRecord> updater);

    /**
     * 删除API调用记录
     *
     * @param id ID集合
     * @return API接口记录
     * @author andy_hulibo@163.com
     * @date 2018/11/24 22:15
     */
    ApiRecord deleteById(Long id);
}