package com.bfly.manage.user;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.user.entity.User;
import com.bfly.cms.user.entity.UserRole;
import com.bfly.cms.user.service.IUserService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.StringUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.Constants;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.cache.UserRightContainer;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import com.bfly.core.security.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 系统管理员用户Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/7 14:11
 */
@RestController
@RequestMapping(value = "/manage/user")
public class UserController extends BaseManageController {

    @Autowired
    private IUserService userService;

    /**
     * 用户登录
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/24 12:46
     */
    @PostMapping(value = "/login")
    @Login(required = false)
    @ActionModel(value = "用户登录", need = false)
    public void login(HttpServletResponse response, @RequestBody User user) {
        user = userService.login(user.getUserName(), user.getPassword());
        String appAuth = StringUtil.getRandomString(32);
        getSession().setAttribute(Constants.USER_LOGIN_KEY, user);
        getSession().setAttribute(Constants.APP_AUTH, appAuth);

        JSONObject json = new JSONObject();
        json.put("userName", user.getUserName());
        json.put(Constants.APP_AUTH, appAuth);
        if (StringUtils.hasLength(user.getFace())) {
            json.put("face", ResourceConfig.getServer() + user.getFace());
        }

        UserRightContainer.loadUserRight(user);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 用户退出
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/25 16:34
     */
    @GetMapping(value = "/logout")
    @Login(required = false)
    @ActionModel(value = "用户登出", need = false)
    public void logout(HttpServletResponse response) {
        User user = getUser();
        getSession().invalidate();
        if (user != null) {
            String userName = user.getUserName();
            userService.logout(userName);

            UserRightContainer.clear(user);
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 管理员列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 21:13
     */
    @GetMapping("/list")
    @ActionModel(value = "系统用户列表", need = false)
    public void listUser(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Map<String, Object> property = new HashMap<String, Object>(1) {
            private static final long serialVersionUID = 7166011434886278754L;

            {
                String roleId = request.getParameter("roleId");
                if (roleId != null) {
                    Set<UserRole> userRoleSet = new HashSet<>();
                    UserRole role = new UserRole();
                    role.setId(Integer.valueOf(roleId));
                    userRoleSet.add(role);
                    put("roles", userRoleSet);
                }
                String status = request.getParameter("status");
                if (status != null) {
                    put("status", status);
                }
            }
        };
        Pager pager = userService.getPage(property);
        JSONObject json = JsonUtil.toJsonFilter(pager, "password", "roles");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 新增管理员
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 11:53
     */
    @PostMapping(value = "/add")
    @ActionModel(value = "新增系统用户")
    public void addUser(@RequestBody @Valid User user, BindingResult result, HttpServletResponse response) {
        validData(result);
        userService.save(user);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改管理员
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:45
     */
    @PostMapping(value = "/edit")
    @ActionModel(value = "修改系统用户")
    public void editUser(@RequestBody @Valid User user, BindingResult result, HttpServletResponse response) {
        validData(result);
        userService.edit(user);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 获取管理员基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:50
     */
    @GetMapping(value = "/{userId}")
    @ActionModel(value = "查看系统用户详情", need = false)
    public void viewUser(@PathVariable("userId") int userId, HttpServletResponse response) {
        User user = userService.get(userId);
        if (StringUtils.hasLength(user.getFace())) {
            user.setFace(ResourceConfig.getServer() + user.getFace());
        }
        JSONObject json = JsonUtil.toJsonFilter(user, "password", "users","menus");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 删除管理员信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:53
     */
    @PostMapping(value = "/del")
    @ActionModel(value = "删除系统用户")
    public void delUser(HttpServletResponse response, @RequestBody Integer... userId) {
        userService.remove(userId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 回收用户角色
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/12 13:51
     */
    @GetMapping(value = "/recycle/{userId}-{roleId}")
    @ActionModel(value = "回收用户角色")
    public void recycleUserRole(@PathVariable("userId") int userId, @PathVariable("roleId") int roleId, HttpServletResponse response) {
        userService.recyclingRole(userId, roleId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
