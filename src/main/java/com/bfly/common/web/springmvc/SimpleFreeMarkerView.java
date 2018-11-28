package com.bfly.common.web.springmvc;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 轻量级的FreeemarkerView
 * 不支持jsp标签、不支持request、session、application等对象，可用于前台模板页面。
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/28 17:43
 */
public class SimpleFreeMarkerView extends FreeMarkerView {

    /**
     * 部署路径调用名称
     */
    public static final String CONTEXT_PATH = "base";

    /**
     * 每页的title显示公司名称,后期从数据库读取
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/28 17:44
     */
    public static final String COMPANY_NAME = "company_name";

    @Override
    protected void renderMergedTemplateModel(Map model,
                                             HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        model.put(CONTEXT_PATH, request.getContextPath());
        model.put(COMPANY_NAME, "公司名称");
        super.renderMergedTemplateModel(model, request, response);
    }
}
