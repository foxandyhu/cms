package com.bfly.web.system.directive;

import freemarker.template.TemplateMethodModelEx;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 字符串截取
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/3 20:09
 */
@Component("chartCutMethod")
public class ChartCutMethod implements TemplateMethodModelEx {

    @Override
    public Object exec(List arguments) {
        String str = String.valueOf(arguments.get(0));
        int size = (int) arguments.get(1);
        if (str.length() > size) {
            str = str.substring(0, size);
        }
        return str;
    }
}
