package com.bfly.web.channel.directive;

import static com.bfly.core.Constants.TPL_STYLE_LIST;
import static com.bfly.core.Constants.TPL_SUFFIX;
import static com.bfly.common.web.Constants.UTF8;
import static com.bfly.common.web.freemarker.DirectiveUtils.OUT_LIST;
import static com.bfly.core.web.util.FrontUtils.PARAM_STYLE_LIST;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.common.web.freemarker.DefaultObjectWrapperBuilderFactory;
import com.bfly.common.web.freemarker.DirectiveUtils;
import com.bfly.core.exception.ParamsRequiredException;
import com.bfly.common.web.freemarker.DirectiveUtils.InvokeType;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.core.web.util.FrontUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

/**
 * 栏目列表标签
 */
@Component("cms_channel_list")
public class ChannelListDirective extends AbstractChannelDirective {
	/**
	 * 模板名称
	 */
	public static final String TPL_NAME = "channel_list";

	@Override
    @SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		CmsSite site = FrontUtils.getSite(env);
		Integer parentId = DirectiveUtils.getInt(PARAM_PARENT_ID, params);
		boolean hasContentOnly = getHasContentOnly(params);

		List<Channel> list;
		if (parentId != null) {
			list = channelMng.getChildListForTag(parentId, hasContentOnly);
		} else {
			list = channelMng.getTopListForTag(hasContentOnly);
		}

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(OUT_LIST, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(list));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		InvokeType type = DirectiveUtils.getInvokeType(params);
		String listStyle = DirectiveUtils.getString(PARAM_STYLE_LIST, params);
		if (InvokeType.sysDefined == type) {
			if (StringUtils.isBlank(listStyle)) {
				throw new ParamsRequiredException(PARAM_STYLE_LIST);
			}
			env.include(TPL_STYLE_LIST + listStyle + TPL_SUFFIX, UTF8, true);
		} else if (InvokeType.userDefined == type) {
			if (StringUtils.isBlank(listStyle)) {
				throw new ParamsRequiredException(PARAM_STYLE_LIST);
			}
			FrontUtils.includeTpl(TPL_STYLE_LIST, site, env);
		} else if (InvokeType.custom == type) {
			FrontUtils.includeTpl(TPL_NAME, site, params, env);
		} else if (InvokeType.body == type) {
			body.render(env.getOut());
		} else {
			throw new RuntimeException("invoke type not handled: " + type);
		}
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}
}
