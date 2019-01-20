package com.bfly.manage.user;

import com.bfly.cms.user.entity.UserRole;
import com.bfly.cms.user.service.IUserRoleService;
import com.bfly.common.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 用户角色Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/10 15:49
 */
@RestController
@RequestMapping(value = "/manage/user/role")
public class UserRoleController extends BaseManageController {

    @Autowired
    private IUserRoleService userRoleService;

    /**
     * 角色列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 15:52
     */
    @GetMapping("/list")
    public void listUserRole(HttpServletRequest request, HttpServletResponse response) {
        ContextUtil.initPager(request);
        Pager pager = userRoleService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 新增角色
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 15:54
     */
    @PostMapping(value = "/add")
    public void addUserRole(@Valid UserRole role, BindingResult result, HttpServletResponse response) {
        validData(result);
        userRoleService.save(role);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 修改角色
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 15:55
     */
    @PostMapping(value = "/edit")
    public void editUserRole(UserRole role, HttpServletResponse response) {
        userRoleService.edit(role);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 获取角色基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:50
     */
    @GetMapping(value = "/{roleId}")
    public void viewUserRole(@PathVariable("roleId") int roleId, HttpServletResponse response) {
        UserRole role = userRoleService.get(roleId);
        ResponseUtil.writeJson(response, role);
    }

    /**
     * 删除角色
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:53
     */
    @PostMapping(value = "/del")
    public void removeUserRole(HttpServletRequest request, HttpServletResponse response) {
        String userIdStr = request.getParameter("ids");
        Integer[] roleIds = DataConvertUtils.convertToIntegerArray(userIdStr.split(","));
        userRoleService.remove(roleIds);
        ResponseUtil.writeJson(response, "");
    }
}
