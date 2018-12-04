package com.bfly.cms.user.dao;

import com.bfly.cms.user.entity.CmsAdmin;

/**
 * 系统管理员数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/3 16:00
 */
public interface CmsAdminDao {

    /**
     * 根据用户名查找系统管理员
     *
     * @param username 管理员登录用户名
     * @return 管理员对象
     * @author andy_hulibo@163.com
     * @date 2018/12/3 16:00
     */
    CmsAdmin findByUsername(String username);

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
     * 更新登录信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/3 16:25
     */
    void updateLoginInfo(CmsAdmin admin);
}