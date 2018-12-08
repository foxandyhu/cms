//package com.bfly.web.content.directive;
//
//import static com.bfly.core.Constants.TPL_STYLE_LIST;
//import static com.bfly.core.Constants.TPL_SUFFIX;
//import static com.bfly.common.web.Constants.UTF8;
//import static com.bfly.common.web.freemarker.DirectiveUtils.OUT_LIST;
//import static com.bfly.core.web.util.FrontUtils.PARAM_STYLE_LIST;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.bfly.cms.content.entity.Topic;
//import com.bfly.cms.siteconfig.entity.Site;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.bfly.cms.content.service.CmsTopicMng;
//import com.bfly.common.web.freemarker.DefaultObjectWrapperBuilderFactory;
//import com.bfly.common.web.freemarker.DirectiveUtils;
//import com.bfly.core.exception.ParamsRequiredException;
//import com.bfly.common.web.freemarker.DirectiveUtils.InvokeType;
//import com.bfly.core.web.util.FrontUtils;
//
//import freemarker.core.Environment;
//import freemarker.template.TemplateDirectiveBody;
//import freemarker.template.TemplateDirectiveModel;
//import freemarker.template.TemplateException;
//import freemarker.template.TemplateModel;
//import org.springframework.stereotype.Component;
//
///**
// * 专题列表标签
// */
//@Component("cms_topic_list")
//public class CmsTopicListDirective implements TemplateDirectiveModel {
//	/**
//	 * 模板名称
//	 */
//	public static final String TPL_NAME = "topic_list";
//
//	/**
//	 * 输入参数，栏目ID。
//	 */
//	public static final String PARAM_CHANNEL_ID = "channelId";
//
//	/**
//	 * 输入参数，是否推荐。
//	 */
//	public static final String PARAM_RECOMMEND = "recommend";
//
//	@Override
//    @SuppressWarnings("unchecked")
//	public void execute(Environment env, Map params, TemplateModel[] loopVars,
//			TemplateDirectiveBody body) throws TemplateException, IOException {
//		Site site = FrontUtils.getSite(env);
//		List<Topic> list = cmsTopicMng.getListForTag(getChannelId(params),
//				getRecommend(params), 0,FrontUtils.getCount(params));
//
//		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
//				params);
//		paramWrap.put(OUT_LIST, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(list));
//		Map<String, TemplateModel> origMap = DirectiveUtils
//				.addParamsToVariable(env, paramWrap);
//		InvokeType type = DirectiveUtils.getInvokeType(params);
//		String listStyle = DirectiveUtils.getString(PARAM_STYLE_LIST, params);
//		if (InvokeType.sysDefined == type) {
//			if (StringUtils.isBlank(listStyle)) {
//				throw new ParamsRequiredException(PARAM_STYLE_LIST);
//			}
//			env.include(TPL_STYLE_LIST + listStyle + TPL_SUFFIX, UTF8, true);
//		} else if (InvokeType.userDefined == type) {
//			if (StringUtils.isBlank(listStyle)) {
//				throw new ParamsRequiredException(PARAM_STYLE_LIST);
//			}
//			FrontUtils.includeTpl(TPL_STYLE_LIST, site, env);
//		} else if (InvokeType.custom == type) {
//			FrontUtils.includeTpl(TPL_NAME, site, params, env);
//		} else if (InvokeType.body == type) {
//			body.render(env.getOut());
//		} else {
//			throw new RuntimeException("invoke type not handled: " + type);
//		}
//		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
//	}
//
//	private Integer getChannelId(Map<String, TemplateModel> params)
//			throws TemplateException {
//		return DirectiveUtils.getInt(PARAM_CHANNEL_ID, params);
//	}
//
//	private boolean getRecommend(Map<String, TemplateModel> params)
//			throws TemplateException {
//		Boolean recommend = DirectiveUtils.getBool(PARAM_RECOMMEND, params);
//		return recommend != null ? recommend : false;
//
//	}
//
//	@Autowired
//	private CmsTopicMng cmsTopicMng;
//}
