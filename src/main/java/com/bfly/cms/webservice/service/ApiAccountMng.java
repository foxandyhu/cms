package com.bfly.cms.webservice.service;

import com.bfly.cms.webservice.entity.ApiAccount;
import com.bfly.common.page.Pagination;

import javax.servlet.http.HttpServletRequest;

/**
 * API应用账户业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 15:07
 */
public interface ApiAccountMng {

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
     * 根据查询条件查询API接口账户
     *
     * @param request HttpServletRequest
     * @return API接口
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:17
     */
    ApiAccount getApiAccount(HttpServletRequest request);

    /**
     * 获得默认的API账户信息
     *
     * @return API账户
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:17
     */
    ApiAccount findByDefault();

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
     * @param bean   API账户
     * @param aesKey AES加解密密钥
     * @param appKey APP Key
     * @param ivKey  AES iv key
     * @return API账户
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:08
     */
    ApiAccount update(ApiAccount bean, String appKey, String aesKey, String ivKey);

    /**
     * 根据ID删除账户
     *
     * @param id ID集合
     * @return API账户
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:07
     */
    ApiAccount deleteById(Integer id);

    /**
     * 根据ID删除账户
     *
     * @param ids ID集合
     * @return API账户
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:07
     */
    ApiAccount[] deleteByIds(Integer[] ids);
}