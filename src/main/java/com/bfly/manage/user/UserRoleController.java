package com.bfly.manage.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.system.entity.SysMenu;
import com.bfly.cms.system.service.ISysMenuService;
import com.bfly.cms.user.entity.UserRole;
import com.bfly.cms.user.service.IUserRoleService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

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
    @Autowired
    private ISysMenuService menuService;

    /**
     * 角色列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 15:52
     */
    @GetMapping("/list")
    @ActionModel(value = "系统角色列表", need = false)
    public void listUserRole(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Pager pager = userRoleService.getPage(null);
        JSONObject json = JsonUtil.toJsonFilter(pager, "users","menus");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 所有角色集合
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/4 15:08
     */
    @GetMapping(value = "/all")
    @ActionModel(value = "系统所有角色列表", need = false)
    public void listUserRole(HttpServletResponse response) {
        List<UserRole> roles = userRoleService.getList();
        JSONArray json = JsonUtil.toJsonFilterForArray(roles, "users","menus");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 新增角色
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 15:54
     */
    @PostMapping(value = "/add")
    @ActionModel(value = "新增系统角色")
    public void addUserRole(@RequestBody @Valid UserRole role, BindingResult result, HttpServletResponse response) {
        validData(result);
        userRoleService.save(role);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改角色
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 15:55
     */
    @PostMapping(value = "/edit")
    @ActionModel(value = "修改系统角色")
    public void editUserRole(@RequestBody @Valid UserRole role, BindingResult result, HttpServletResponse response) {
        validData(result);
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
    @ActionModel(value = "查看系统角色详情", need = false)
    public void viewUserRole(@PathVariable("roleId") int roleId, HttpServletResponse response) {
        UserRole role = userRoleService.get(roleId);
        JSONObject json = JsonUtil.toJsonFilter(role, "users","menus");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 删除角色
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:53
     */
    @PostMapping(value = "/del")
    @ActionModel(value = "删除系统角色")
    public void removeUserRole(HttpServletResponse response, @RequestBody Integer... roleId) {
        userRoleService.remove(roleId);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 获得角色菜单权限
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/13 12:24
     */
    @GetMapping(value = "/menu/{roleId}")
    public void getUserRoleMenu(@PathVariable("roleId") int roleId, HttpServletResponse response) {
        UserRole role = userRoleService.get(roleId);
        List<SysMenu> menus = role.getMenus();
        List<SysMenu> allMenus = menuService.getList();
        JSONArray array = new JSONArray();
        if (allMenus != null) {
            JSONObject json;
            for (SysMenu menu : allMenus) {
                json = new JSONObject();
                json.put("id", menu.getId());
                json.put("pId", menu.getParent() == null ? 0 : menu.getParent().getId());
                json.put("name", menu.getName());
                json.put("open", true);
                if (menus != null) {
                    for (SysMenu roleMenu : menus) {
                        // 选中的菜单
                        if (roleMenu.getId() == menu.getId()) {
                            json.put("checked", true);
                            break;
                        }
                    }
                }
                array.add(json);
            }
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(array));
    }


    /**
     * 修改角色菜单权限
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/13 16:13
     */
    @PostMapping(value = "/menu/grant")
    public void grantRoleMenu(@RequestBody UserRole role, HttpServletResponse response) {
        userRoleService.grantRoleMenu(role);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
