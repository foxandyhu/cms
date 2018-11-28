package com.bfly.cms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 管理员后台Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/27 16:57
 */
@Controller
public class AdminController {

    /**
     * 后台首页
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/20 10:20
     */
    @GetMapping(value = "/admin/index.html")
    public String index() {
        return "/admin/index.html";
    }
}
