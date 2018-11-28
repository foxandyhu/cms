package com.bfly.cms.sms.dao;

import com.bfly.cms.sms.entity.CmsSms;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

/**
 * 短信服务数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 16:05
 */
public interface CmsSmsDao {

    /**
     * 获得所有的短信服务
     *
     * @param source   运营商
     * @param pageNo   页码
     * @param pageSize 每页数据
     * @return 短信服务分页对象
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:26
     */
    Pagination getPage(Byte source, int pageNo, int pageSize);

    /**
     * 获得所有的短信服务
     *
     * @return 短信服务集合
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:26
     */
    List<CmsSms> getList();

    /**
     * 根据ID查找短信服务
     *
     * @param id 短信服务ID
     * @return 短信服务
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:25
     */
    CmsSms findById(Integer id);

    /**
     * 新增短信服务
     *
     * @param bean 短信服务
     * @return 短信服务
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:10
     */
    CmsSms save(CmsSms bean);

    /**
     * 更新短信服务
     *
     * @param updater 短信服务
     * @return 短信服务
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:10
     */
    CmsSms updateByUpdater(Updater<CmsSms> updater);

    /**
     * 删除短信服务
     *
     * @param id 短信服务ID
     * @return 短信服务
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:08
     */
    CmsSms deleteById(Integer id);
}
