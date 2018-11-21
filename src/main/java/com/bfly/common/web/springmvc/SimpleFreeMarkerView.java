package com.bfly.common.web.springmvc;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 轻量级的FreeemarkerView
 * 
 * 不支持jsp标签、不支持request、session、application等对象，可用于前台模板页面。
 */
public class SimpleFreeMarkerView extends FreeMarkerView {
	/**
	 * 部署路径调用名称
	 */
	public static final String CONTEXT_PATH = "base";

	@Override
	protected void renderMergedTemplateModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		model.put(CONTEXT_PATH, request.getContextPath());
		super.renderMergedTemplateModel(model, request, response);
	}
}
