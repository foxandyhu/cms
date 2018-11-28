package com.bfly.cms.directive;

import static com.bfly.common.web.freemarker.DirectiveUtils.OUT_BEAN;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.bfly.common.web.freemarker.DefaultObjectWrapperBuilderFactory;
import com.bfly.common.web.freemarker.DirectiveUtils;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.core.web.util.FrontUtils;
import com.bfly.cms.weixin.entity.Weixin;
import com.bfly.cms.weixin.service.WeixinMng;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

@Component("cms_weixin")
public class WeixinDirective implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		CmsSite site = FrontUtils.getSite(env);
		Weixin entity = manager.find(site.getId());

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(OUT_BEAN, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(entity));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}
	
	@Autowired
	private WeixinMng manager;
}
