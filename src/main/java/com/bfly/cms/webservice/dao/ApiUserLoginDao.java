package com.bfly.cms.webservice.dao;

import com.bfly.cms.webservice.entity.ApiUserLogin;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.Date;
import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/25 15:51
 */
public interface ApiUserLoginDao {

    /**
     * 获得API用户登录信息集合
     *
     * @param pageSize 每页数据
     * @param pageNo   页码
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:34
     */
    Pagination getPage(int pageNo, int pageSize);

    /**
     * 清除 end之前的登陆信息
     *
     * @param end 时间
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:33
     */
    void clearByDate(Date end);

    /**
     * 获得API用户登录信息集合
     *
     * @param end   时间
     * @param first 索引
     * @param count 条数
     * @return 对象集合
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:34
     */
    List<ApiUserLogin> getList(Date end, int first, int count);

    /**
     * 根据Id获得登录信息
     *
     * @param id 登录信息ID
     * @return API用户登录信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:36
     */
    ApiUserLogin findById(Long id);

    /**
     * 根据username,sessionKey获得登录信息
     *
     * @param username   登录信息ID
     * @param sessionKey sessionKey
     * @return API用户登录信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:36
     */
    ApiUserLogin findUserLogin(String username, String sessionKey);

    /**
     * 保存API登录信息
     *
     * @param bean 登录信息
     * @return 登录信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:43
     */
    ApiUserLogin save(ApiUserLogin bean);

    /**
     * 更新API登录信息
     *
     * @param updater 登录信息
     * @return 登录信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:43
     */
    ApiUserLogin updateByUpdater(Updater<ApiUserLogin> updater);

    /**
     * 批量删除登录信息
     *
     * @param id 登录ID
     * @return 登录信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:42
     */
    ApiUserLogin deleteById(Long id);
}