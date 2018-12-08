package com.bfly.manage.user.action;

import com.bfly.cms.user.entity.User;
import com.bfly.cms.user.service.IUserService;
import com.bfly.core.base.action.BaseManageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/list")
    public void list() {
        User user = userService.login("admin", "password");
    }
}
