package com.bfly.cms.user.service;

import com.bfly.cms.user.entity.User;
import com.bfly.core.base.service.IBaseService;

/**
 * 用户管理业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/7 10:32
 */
public interface IUserService extends IBaseService<User, Integer> {

    /**
     * 用户登录
     *
     * @param password 明文密码
     * @param userName 用户名
     * @return 返回用户对象
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:05
     */
    User login(String userName, String password);
}
