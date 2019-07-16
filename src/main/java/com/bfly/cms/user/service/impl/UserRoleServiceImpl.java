package com.bfly.cms.user.service.impl;

import com.bfly.cms.system.entity.SysMenu;
import com.bfly.cms.system.service.ISysMenuService;
import com.bfly.cms.user.entity.User;
import com.bfly.cms.user.entity.UserRole;
import com.bfly.cms.user.service.IUserRoleService;
import com.bfly.cms.user.service.IUserService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/10 15:48
 */
@Service
@Transactional(propagation= Propagation.SUPPORTS, rollbackFor = Exception.class)
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole, Integer> implements IUserRoleService {

    @Autowired
    private IUserService userService;

    @Autowired
    private ISysMenuService menuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(UserRole userRole) {
        checkRole(userRole);
        userRole.setName(userRole.getName().trim());
        return super.save(userRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(UserRole userRole) {
        UserRole role = get(userRole.getId());
        //如果修改角色的名称和数据库中的不匹配表示修改了名称 需要做校验
        if (!role.getName().equals(userRole.getName().trim())) {
            checkRole(userRole);
        }
        userRole.setName(userRole.getName().trim());
        return super.edit(userRole);
    }

    /**
     * 角色数据校验
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/11 16:46
     */
    private void checkRole(UserRole userRole) {
        long count = getCount(new HashMap<String, Object>(1) {{
            put("name", userRole.getName().trim());
        }});
        Assert.isTrue(count < 1, "角色名称已存在!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer... integers) {
        for (int roleId : integers) {
            UserRole role = get(roleId);
            Assert.notNull(role, "角色信息不存在!");
            Set<User> users = role.getUsers();
            //  回收角色和用户之间的关系
            if (users != null) {
                for (User user : users) {
                    userService.recyclingRole(user.getId(), roleId);
                }
            }
            //  回收角色和功能之间的关系
            role.setMenus(null);
        }
        return super.remove(integers);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRoleMenu(UserRole role) {
        UserRole userRole = get(role.getId());
        Assert.notNull(userRole, "角色信息不存在!");
        List<SysMenu> menus = role.getMenus();
        List<SysMenu> list = new ArrayList<>();
        if (menus != null) {
            for (SysMenu menu : menus) {
                menu = menuService.get(menu.getId());
                Assert.notNull(menu, "角色信息不存在!");
                list.add(menu);
            }
        }
        userRole.setMenus(list);
        super.edit(userRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recycleRoleMenu(int roleId, int menuId) {
        UserRole role = get(roleId);
        Assert.notNull(role, "角色信息不存在!");

        List<SysMenu> menus = role.getMenus();
        Assert.notEmpty(menus, "不存在该菜单功能的授权!");

        Iterator<SysMenu> it = menus.iterator();
        while (it.hasNext()) {
            SysMenu menu = it.next();
            if (menu.getId() == menuId) {
                it.remove();
                break;
            }
        }
        role.setMenus(menus);
        super.edit(role);
    }
}
