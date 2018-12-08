package com.bfly.cms.user.service.impl;

import com.bfly.cms.user.dao.IUserDao;
import com.bfly.cms.user.entity.User;
import com.bfly.cms.user.service.IUserService;
import com.bfly.common.ContextUtil;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.security.Md5PwdEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/7 10:33
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User login(String userName, String password) {
        Assert.hasText(userName, "用户名不能为空!");
        Assert.hasText(password, "密码不能为空!");
        User user = get(new HashMap<String, Object>(1) {{
            put("username", userName);
        }});
        Assert.notNull(user, "用户名或密码不正确!");

        boolean flag = new Md5PwdEncoder().isPasswordValid(user.getPassword(), password);
        Assert.isTrue(flag, "用户名或密码错误!");
        Assert.isTrue(User.UNCHECK_STATUS != user.getStatus(), "此账号正在审核中!");
        Assert.isTrue(User.DISABLE_STATUS != user.getStatus(), "此账号已被禁用!");
        updateLoginInfo(user);
        return user;
    }

    /**
     * 更新登录信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:33
     */
    protected void updateLoginInfo(User user) {
        user.setLastLoginTime(new Date());
        user.setLastLoginIp(ContextUtil.getIpFromThreadLocal());
        super.edit(user);
    }
}
