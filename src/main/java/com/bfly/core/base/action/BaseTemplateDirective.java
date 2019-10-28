package com.bfly.core.base.action;

import freemarker.template.*;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 模板指令标签基类
 * @author andy_hulibo@163.com
 * @date 2019/8/29 20:14
 */
public class BaseTemplateDirective {

    private static final DefaultObjectWrapperBuilder DEFAULT_OBJECT_WRAPPER_BUILDER=new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_28);

    public static DefaultObjectWrapperBuilder getObjectWrapperBuilderInstance(){
        return DEFAULT_OBJECT_WRAPPER_BUILDER;
    }

    public static DefaultObjectWrapper getObjectWrapper(){
        return getObjectWrapperBuilderInstance().build();
    }

    /**
     * 获得模板参数
     * @author andy_hulibo@163.com
     * @date 2019/8/30 16:58
     */
    public <T> T getData(String name, Map<String, TemplateModel> params,Class<T> cls)
            throws TemplateException {
        TemplateModel model = params.get(name);
        if (model == null) {
            return null;
        }
        if (model instanceof TemplateScalarModel) {
            String s = ((TemplateScalarModel) model).getAsString();
            return (T)ConvertUtils.convert(s,cls);
        }
        return null;
    }

    /**
     * 获得request
     * @author andy_hulibo@163.com
     * @date 2019/9/9 11:18
     */
    public HttpServletRequest getRequest(){
        ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        return request;
    }
}
