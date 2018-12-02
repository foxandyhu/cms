package com.bfly.web.system.action;

import com.bfly.core.base.action.RenderController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * RSS Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/30 10:51
 */
@Controller
public class RssAct extends RenderController {

    @GetMapping(value = "/rss.html")
    public String rss(HttpServletResponse response, ModelMap model) {
        response.setContentType("text/xml;charset=UTF-8");
        return renderPage("special/RSS.html", model);
    }
}
