package com.bfly.web.content.directive;

import freemarker.template.TemplateMethodModelEx;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 栏目选中自定义方法
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/30 16:43
 */
@Component("channelActiveMethod")
public class ChannelActiveMethod implements TemplateMethodModelEx {

    @Override
    public Object exec(List arguments) {
        String uri = String.valueOf(arguments.get(0));
        String channelPath = "index";
        String[] paths = uri.split("/");
        if (paths.length > 1) {
            channelPath = paths[1];
        }
        return channelPath;
    }
}
