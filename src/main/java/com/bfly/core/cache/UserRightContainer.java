package com.bfly.core.cache;

import com.bfly.cms.system.entity.SysMenu;
import com.bfly.cms.system.service.ISysMenuService;
import com.bfly.cms.user.entity.User;
import com.bfly.cms.user.entity.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户权限容器
 *
 * @author 胡礼波-Andy
 * @2015年12月14日下午5:48:46
 */
@Component("UsersRightContainer")
public class UserRightContainer implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(UserRightContainer.class);

    /**
     * 缓存权限key
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/13 18:51
     */
    public static final String SYS_ROLE_RIGHT_KEY = "sys_role_right_key_";

    /**
     * @author 胡礼波-Andy
     * @2015年12月14日下午5:48:57
     */
    private static final long serialVersionUID = -3929888818854769210L;

    /**
     * 缓存名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/13 18:45
     */
    private static final String USER_RIGHT_CACHE = "userRightCache";

    private static Cache userRightsCache;
    private static ISysMenuService menuService;

    @Autowired
    public void setMenuService(ISysMenuService menuService) {
        UserRightContainer.menuService = menuService;
    }

    @Autowired
    public void setCacheManager(CacheManager cacheManager) {
        userRightsCache = cacheManager.getCache(USER_RIGHT_CACHE);
    }

    /**
     * 加载指定用户权限到系统缓存中
     *
     * @author 胡礼波-Andy
     * @2015年12月14日下午5:49:46
     */
    public static void loadUserRight(User user) {
        try {
            List<SysMenu> menus;
            if (user.isSuperAdmin()) {
                menus = menuService.getList();
            } else {
                List<UserRole> roles = user.getRoles();
                menus = new ArrayList<>();
                if (roles != null) {
                    for (UserRole role : roles) {
                        menus.addAll(role.getMenus());
                    }
                }
            }
            userRightsCache.put(SYS_ROLE_RIGHT_KEY + user.getId(), menus);
            logger.info("【" + user.getUserName() + "】用户菜单权限加载缓存完毕!");
        } catch (Exception e) {
            logger.error("加载用户菜单权限异常," + e.getMessage());
            throw new RuntimeException("加载用户菜单权限异常," + e.getMessage());
        }
    }

    /**
     * 清除登录用户的权限缓存
     *
     * @param user
     * @author 胡礼波-Andy
     * @2015年12月14日下午6:02:00
     */
    public static void clear(User user) {
        userRightsCache.evict(SYS_ROLE_RIGHT_KEY + user.getId());
        logger.info("【" + user.getUserName() + "】用户菜单权限清除完毕!");
    }

    /**
     * 判断权限是否存在
     *
     * @param user
     * @param right
     * @return
     * @author 胡礼波-Andy
     * @2015年12月14日下午6:04:02
     */
    public static boolean exist(User user, String right) {
        if (userRightsCache.get(SYS_ROLE_RIGHT_KEY + user.getId()).get() == null) {
            return false;
        }
        //得到缓存的资源权限
        List<SysMenu> menus = userRightsCache.get(SYS_ROLE_RIGHT_KEY + user.getId(), List.class);
        if (menus == null) {
            return false;
        }
        for (SysMenu menu : menus) {
            if (menu.getUrl().equals(right)) {
                return true;
            }
        }
        return false;
    }
}
