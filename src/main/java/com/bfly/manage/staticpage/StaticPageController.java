package com.bfly.manage.staticpage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 静态化页面Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/12 16:50
 */
@RestController
@RequestMapping(value = "/manager/staticpage")
public class StaticPageController {

    /**
     * 静态化首页
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 17:02
     */
    @GetMapping(value = "/welcome")
    public void staticIndex(HttpServletResponse response) {

    }

    /**
     * 静态化栏目
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 17:03
     */
    @GetMapping(value = "/channel")
    public void staticChannel(HttpServletResponse response) {

    }

    /**
     * 静态化内容
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 17:03
     */
    @GetMapping(value = "/content")
    public void staticContent(HttpServletResponse response) {

    }
}
