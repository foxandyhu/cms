package com.bfly.cms.user.service;

import com.bfly.cms.user.entity.CmsAdmin;

/**
 * 系统管理员业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/3 15:57
 */
public interface CmsAdminService {

    /**
     * 根据系统管理员ID查找管理员
     *
     * @param id 管理员ID
     * @return 管理员对象
     * @author andy_hulibo@163.com
     * @date 2018/12/3 16:12
     */
    CmsAdmin findById(Integer id);

    /**
     * 根据用户名查找管理员
     *
     * @param username 用户名
     * @return 系统管理员
     * @author andy_hulibo@163.com
     * @date 2018/12/3 15:58
     */
    CmsAdmin findByUsername(String username);

    /**
     * 密码校验
     *
     * @param password 密码
     * @param userId   系统管理员ID
     * @return true, false
     * @author andy_hulibo@163.com
     * @date 2018/12/3 16:10
     */
    boolean isPasswordValid(Integer userId, String password);

    /**
     * 更新登录信息
     *
     * @param admin 系统管理员
     * @author andy_hulibo@163.com
     * @date 2018/12/3 16:27
     */
    void updateLoginInfo(CmsAdmin admin);
}