package com.bfly.cms.user.service;

import com.bfly.cms.user.entity.UserRole;
import com.bfly.core.base.service.IBaseService;

/**
 * 用户角色业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/10 15:44
 */
public interface IUserRoleService extends IBaseService<UserRole, Integer> {

    /**
     * 角色授权
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/13 16:16
     */
    void grantRoleMenu(UserRole role);

    /**
     * 回收角色和菜单之间的关系
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/13 17:04
     */
    void recycleRoleMenu(int roleId, int menuId);
}
