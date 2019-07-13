package com.bfly.cms.user.service.impl;

import com.bfly.cms.logs.entity.SysLog;
import com.bfly.cms.logs.service.ISysLogService;
import com.bfly.cms.user.dao.IUserDao;
import com.bfly.cms.user.entity.User;
import com.bfly.cms.user.entity.UserRole;
import com.bfly.cms.user.service.IUserRoleService;
import com.bfly.cms.user.service.IUserService;
import com.bfly.common.FileUtil;
import com.bfly.core.Constants;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.IpThreadLocal;
import com.bfly.core.context.ServletRequestThreadLocal;
import com.bfly.core.context.UserThreadLocal;
import com.bfly.core.enums.LogsType;
import com.bfly.core.security.Md5PwdEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

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
    @Autowired
    private ResourceConfig resourceConfig;
    @Autowired
    private IUserRoleService roleService;

    /**
     * 得到用户头像相对路劲
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/4 12:18
     */
    private String getFaceRelativePath(String faceName) {
        //把临时文件夹中的图片复制到face目录中
        if (StringUtils.hasLength(faceName) && faceName.endsWith(Constants.TEMP_RESOURCE_SUFFIX)) {
            String source = resourceConfig.getTemp() + File.separator + faceName;
            String destination = resourceConfig.getFace();
            boolean result = FileUtil.copyFileToDirectory(source, destination);
            if (result) {
                faceName = faceName.substring(0, faceName.lastIndexOf(Constants.TEMP_RESOURCE_SUFFIX));
                return resourceConfig.getRelativePathForRoot(destination + File.separator + faceName);
            }
            return null;
        }
        return null;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(User user) {
        Assert.notNull(user, "用户信息为空!");

        User u = get(new HashMap<String, Object>(1) {{
            put("userName", user.getUserName());
        }});
        Assert.isTrue(u == null, "该用户名已存在!");

        user.setRegisterTime(new Date());
        user.setRegisterIp(IpThreadLocal.get());
        //默认账号可用
        user.setStatus(User.AVAILABLE_STATUS);
        user.setPassword(new Md5PwdEncoder().encodePassword(user.getPassword()));

        String face = getFaceRelativePath(user.getFace());
        user.setFace(face);

        Set<UserRole> userRoles = checkRoles(user);
        user.setRoles(userRoles);

        userDao.save(user);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(User user) {
        Assert.notNull(user, "用户信息为空!");

        User orUser = get(new HashMap<String, Object>(1) {{
            put("userName", user.getUserName());
        }});
        Assert.notNull(orUser, "不存在该用户!");

        orUser.setEmail(user.getEmail());
        orUser.setStatus(user.getStatus());

        String face = getFaceRelativePath(user.getFace());
        if (face != null) {
            orUser.setFace(face);
        }

        Set<UserRole> userRoles = checkRoles(user);
        orUser.setRoles(userRoles);
        return super.edit(orUser);
    }

    /**
     * 校验用户角色
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/11 12:13
     */
    private Set<UserRole> checkRoles(User user) {
        Set<UserRole> roles = user.getRoles();
        Set<UserRole> userRoles = new HashSet<>();
        for (UserRole role : roles) {
            role = roleService.get(role.getId());
            Assert.notNull(role, "不存在该角色!");
            userRoles.add(role);
        }
        return userRoles;
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
        user.setLoginCount(user.getLoginCount() + 1);
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
        Iterator<UserRole> it = roles.iterator();
        while (it.hasNext()) {
            //回收指定的角色
            UserRole role = it.next();
            if (roleId == role.getId()) {
                it.remove();
            }
        }
        user.setRoles(roles);
        super.edit(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer... integers) {
        User user = UserThreadLocal.get();
        int count = 0;
        for (int id : integers) {
            if (id == user.getId()) {
                throw new RuntimeException("不能删除当前登录用户!");
            }
            userDao.deleteById(id);
            count++;
        }
        return count;
    }
}
