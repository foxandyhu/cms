package com.bfly.cms.user.service.impl;

import com.bfly.cms.logs.entity.SysLog;
import com.bfly.cms.logs.service.ISysLogService;
import com.bfly.cms.user.dao.IUserDao;
import com.bfly.cms.user.entity.User;
import com.bfly.cms.user.entity.UserRole;
import com.bfly.cms.user.service.IUserService;
import com.bfly.core.Constants;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.context.IpThreadLocal;
import com.bfly.core.context.ServletRequestThreadLocal;
import com.bfly.core.enums.LogsType;
import com.bfly.core.security.Md5PwdEncoder;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/7 10:33
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements IUserService {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private ISysLogService sysLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(User user) {
        Assert.notNull(user, "用户信息为空!");
        user.setRegisterTime(new Date());
        user.setRegisterIp(IpThreadLocal.get());
        //默认账号可用
        user.setStatus(User.AVAILABLE_STATUS);
        user.setPassword(new Md5PwdEncoder().encodePassword(user.getPassword()));
        return super.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(User user) {
        Assert.notNull(user, "用户信息为空!");

        User orUser = get(new HashMap<String, Object>(1) {{
            put("username", user.getUserName());
        }});
        Assert.notNull(orUser, "不能存在该用户!");

        //密码修改了
        if (StringUtils.hasLength(user.getPassword())) {
            user.setPassword(new Md5PwdEncoder().encodePassword(user.getPassword()));
        }

        user.setId(orUser.getId());
        user.setRegisterIp(orUser.getRegisterIp());
        user.setRegisterTime(orUser.getRegisterTime());
        user.setLastLoginIp(orUser.getLastLoginIp());
        user.setLastLoginTime(orUser.getLastLoginTime());
        return super.edit(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User login(String userName, String password) {
        Assert.hasText(userName, "用户名不能为空!");
        Assert.hasText(password, "密码不能为空!");
        User user = get(new HashMap<String, Object>(1) {{
            put("userName", userName);
        }});
        Assert.notNull(user, "用户名或密码错误!");

        boolean flag = new Md5PwdEncoder().isPasswordValid(user.getPassword(), password);
        Assert.isTrue(flag, "用户名或密码错误!");
        Assert.isTrue(User.UNCHECK_STATUS != user.getStatus(), "此账号正在审核中!");
        Assert.isTrue(User.DISABLE_STATUS != user.getStatus(), "此账号已被禁用!");
        updateLoginInfo(user);
        saveLoginLogs(userName, "用户登录", LogsType.LOGIN_LOG);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void logout(String userName) {
        saveLoginLogs(userName, "用户登出", LogsType.LOGOUT_LOG);
    }

    /**
     * 保存登录/登出信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/26 18:33
     */
    private void saveLoginLogs(String userName, String title, LogsType type) {
        HttpServletRequest request = ServletRequestThreadLocal.get();
        SysLog log = new SysLog();
        log.setTime(new Date());
        log.setTitle(title);
        log.setUserName(userName);
        log.setIp(IpThreadLocal.get());
        log.setUrl(request.getRequestURL().toString());
        log.setSuccess(true);
        log.setContent(null);
        log.setCategory(type.getId());
        sysLogService.save(log);
    }

    /**
     * 更新登录信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:33
     */
    protected void updateLoginInfo(User user) {
        user.setLastLoginTime(new Date());
        user.setLastLoginIp(IpThreadLocal.get());
        super.edit(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recyclingRole(int userId, int roleId) {
        User user = get(userId);
        Assert.notNull(user, "用户信息不存在!");
        Set<UserRole> roles = user.getRoles();
        if (roles == null) {
            return;
        }
        for (UserRole role : roles) {
            //回收指定的角色
            if (roleId == role.getId()) {
                roles.remove(role);
            }
        }
        user.setRoles(roles);
        super.edit(user);
    }
}
