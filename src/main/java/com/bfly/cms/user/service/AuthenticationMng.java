package com.bfly.cms.user.service;

import com.bfly.cms.user.entity.Authentication;
import com.bfly.common.page.Pagination;
import com.bfly.core.exception.BadCredentialsException;
import com.bfly.core.exception.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证信息管理接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/28 15:29
 */
public interface AuthenticationMng {

    String AUTH_KEY = "auth_key";

    /**
     * 存储授权ID到session
     *
     * @param response HttpServletResponse
     * @param request  HttpServletRequest
     * @param authId   authId
     * @author andy_hulibo@163.com
     * @date 2018/11/28 15:33
     */
    void storeAuthIdToSession(HttpServletRequest request, HttpServletResponse response, String authId);

    /**
     * 通过认证ID，获得认证信息。本方法会检查认证是否过期。
     *
     * @param authId 认证ID
     * @return 返回Authentication对象。如果authId不存在或已经过期，则返回null。
     */
    Authentication retrieve(String authId);

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @param ip       登录IP
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return 认证信息
     * @throws UsernameNotFoundException 用户名没有找到
     * @throws BadCredentialsException   错误的认证信息，比如密码错误
     */
    Authentication login(String username, String password, String ip, HttpServletRequest request, HttpServletResponse response) throws UsernameNotFoundException, BadCredentialsException;

    /**
     * 获得认证分页信息
     *
     * @param pageNo   当前页数
     * @param pageSize 页数
     * @return 分页对象
     */
    Pagination getPage(int pageNo, int pageSize);

    /**
     * 根据认证ID查找认证信息
     *
     * @param id 认证ID
     * @return 认证信息
     */
    Authentication findById(String id);

    /**
     * 保存认证信息
     *
     * @param bean 认证信息
     * @return 认证信息
     */
    Authentication save(Authentication bean);

    /**
     * 删除认证信息
     *
     * @param id 认证信息ID
     * @return 认证信息
     */
    Authentication deleteById(String id);

    /**
     * 删除认证信息
     *
     * @param ids 认证信息ID
     * @return 认证信息
     */
    Authentication[] deleteByIds(String[] ids);

}