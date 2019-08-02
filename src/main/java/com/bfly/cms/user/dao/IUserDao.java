package com.bfly.cms.user.dao;

import com.bfly.cms.user.entity.User;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/7 10:25
 */
public interface IUserDao extends IBaseDao<User, Integer> {

    /**
     * 修改用户密码
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/29 15:01
     */
    @Modifying
    @Query("update User set password=:password where id=:userId")
    int editUserPassword(@Param("userId") int userId, @Param("password") String password);

    /**
     * 根据用户名查找用户
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/1 13:29
     */
    @Query("select user  from User as user where user.userName=:userName")
    User getUserByUserName(@Param("userName") String userName);
}
