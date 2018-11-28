package com.bfly.cms.webservice.dao;

import com.bfly.cms.webservice.entity.ApiAccount;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/25 15:16
 */
public interface ApiAccountDao {

    /**
     * 获得所有的API账户信息
     *
     * @param pageNo   页码
     * @param pageSize 每页数据
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:16
     */
    Pagination getPage(int pageNo, int pageSize);

    /**
     * 获得API账户集合
     *
     * @param first 开始索引
     * @param count 条数
     * @return API账户集合
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:13
     */
    List<ApiAccount> getList(int first, int count);

    /**
     * 根据API APPID查找账户信息
     *
     * @param appId APPID
     * @return API账户
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:10
     */
    ApiAccount findByAppId(String appId);

    /**
     * 查找默认管理API账户的账户
     *
     * @return API账户
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:12
     */
    ApiAccount findAdmin();

    /**
     * 根据API账户ID查找账户信息
     *
     * @param id 账户ID
     * @return API账户
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:10
     */
    ApiAccount findById(Integer id);

    /**
     * 新增API账户
     *
     * @param bean API账户
     * @return API账户
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:10
     */
    ApiAccount save(ApiAccount bean);

    /**
     * 更新API账户
     *
     * @param updater API账户
     * @return API账户
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:08
     */
    ApiAccount updateByUpdater(Updater<ApiAccount> updater);

    /**
     * 根据ID删除账户
     *
     * @param id ID集合
     * @return API账户
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:07
     */
    ApiAccount deleteById(Integer id);
}