package com.bfly.manage.user;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.user.entity.User;
import com.bfly.cms.user.service.IUserService;
import com.bfly.common.*;
import com.bfly.common.page.Pager;
import com.bfly.core.Constants;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.ContextUtil;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
    public void login(HttpServletResponse response, @RequestBody User user) {
        user = userService.login(user.getUserName(), user.getPassword());
        String appAuth = StringUtil.getRandomString(32);
        getSession().setAttribute(Constants.USER_LOGIN_KEY, user);
        getSession().setAttribute(Constants.APP_AUTH, appAuth);

        JSONObject json = new JSONObject();
        json.put("userName", user.getUserName());
        json.put(Constants.APP_AUTH, appAuth);

        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 用户退出
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/25 16:34
     */
    @GetMapping(value = "/logout")
    public void logout(HttpServletResponse response) {
        getSession().invalidate();
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 管理员列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 21:13
     */
    @GetMapping("/list")
    public void listUser(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Map<String, Object> property = new HashMap<String, Object>(4) {
            private static final long serialVersionUID = 7166011434886278754L;

            {
                put("username", request.getParameter("username"));
                put("email", request.getParameter("email"));
                put("status", request.getParameter("status"));
                put("roles.id", request.getParameter("roleId"));
            }
        };
        Pager pager = userService.getPage(property);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 新增管理员
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 11:53
     */
    @PostMapping(value = "/add")
    public void addUser(@Valid User user, BindingResult result, HttpServletResponse response) {
        validData(result);
        userService.save(user);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 修改管理员
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:45
     */
    @PostMapping(value = "/edit")
    public void editUser(User user, HttpServletResponse response) {
        userService.edit(user);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 获取管理员基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:50
     */
    @GetMapping(value = "/{userId}")
    public void viewUser(@PathVariable("userId") int userId, HttpServletResponse response) {
        User user = userService.get(userId);
        ResponseUtil.writeJson(response, user);
    }

    /**
     * 删除管理员信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:53
     */
    @PostMapping(value = "/del")
    public void removeUser(HttpServletRequest request, HttpServletResponse response) {
        String userIdStr = request.getParameter("ids");
        Integer[] userIds = DataConvertUtils.convertToIntegerArray(userIdStr.split(","));
        userService.remove(userIds);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 解除管理员角色
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 16:00
     */
    @PostMapping(value = "/removerole")
    public void removeRole(HttpServletRequest request) {
        int userId = DataConvertUtils.convertToInteger(request.getParameter("userId"));
        int roleId = DataConvertUtils.convertToInteger(request.getParameter("roleId"));
        userService.recyclingRole(userId, roleId);
    }
}
