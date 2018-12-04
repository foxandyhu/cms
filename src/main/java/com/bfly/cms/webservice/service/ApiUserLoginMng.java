package com.bfly.cms.webservice.service;

import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.webservice.entity.ApiAccount;
import com.bfly.cms.webservice.entity.ApiUserLogin;
import com.bfly.common.page.Pagination;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * API用户登录信息业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 15:32
 */
public interface ApiUserLoginMng {

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
     * 根据API用户登录信息和请求参数获得登录用户
     *
     * @param request    HttpServletRequest
     * @param apiAccount 登录信息
     * @return 用户
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:39
     */
    CmsUser getUser(ApiAccount apiAccount, HttpServletRequest request);

    /**
     * 根据API请求参数获得登录用户
     *
     * @param request HttpServletRequest
     * @return 用户
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:39
     */
    CmsUser getUser(HttpServletRequest request);

    /**
     * 根据API请求参数获得登录用户
     *
     * @param sessionKey sessionKey
     * @param aesKey     aesKey
     * @param ivKey      ivKey
     * @return 用户
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:39
     */
    CmsUser findUser(String sessionKey, String aesKey, String ivKey);

    /**
     * 更新用户登录信息
     *
     * @param sessionKey sessionKey
     * @param appId      APPID
     * @param username   用户名
     * @param request    HttpServletRequest
     * @param response   HttpServletResponse
     * @return API用户登录信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:46
     */
    ApiUserLogin userLogin(String username, String appId, String sessionKey, HttpServletRequest request, HttpServletResponse response);

    /**
     * 更新用户登出信息
     *
     * @param sessionKey sessionKey
     * @param appId      APPID
     * @param username   用户名
     * @return API用户登录信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:46
     */
    ApiUserLogin userLogout(String username, String appId, String sessionKey);

    /**
     * 获得API用户账户状态
     *
     * @param sessionKey sessionKey
     * @return 状态
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:45
     */
    Short getUserStatus(String sessionKey);

    /**
     * 获得API用户账户状态
     *
     * @param response   HttpServletResponse
     * @param request    HttpServletRequest
     * @param apiAccount 账户信息
     * @return 状态
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:45
     */
    Short getStatus(ApiAccount apiAccount,
                    HttpServletRequest request, HttpServletResponse response);

    /**
     * 更新API用户登录活动时间
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:44
     */
    void userActive(HttpServletRequest request, HttpServletResponse response);

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
     * @param bean 登录信息
     * @return 登录信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:43
     */
    ApiUserLogin update(ApiUserLogin bean);

    /**
     * 批量删除登录信息
     *
     * @param id 登录ID
     * @return 登录信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:42
     */
    ApiUserLogin deleteById(Long id);

    /**
     * 批量删除登录信息
     *
     * @param ids 登录ID集合
     * @return 登录信息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 15:42
     */
    ApiUserLogin[] deleteByIds(Long[] ids);
}