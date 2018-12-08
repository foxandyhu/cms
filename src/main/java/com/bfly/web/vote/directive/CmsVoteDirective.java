//package com.bfly.web.vote.directive;
//
//import static com.bfly.common.web.freemarker.DirectiveUtils.OUT_BEAN;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import com.bfly.cms.siteconfig.entity.Site;
//import com.bfly.cms.vote.entity.VoteTopic;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.bfly.cms.vote.service.CmsVoteTopicMng;
//import com.bfly.common.web.freemarker.DefaultObjectWrapperBuilderFactory;
//import com.bfly.common.web.freemarker.DirectiveUtils;
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
// * 投票标签
// */
//@Component("cms_vote")
//public class CmsVoteDirective implements TemplateDirectiveModel {
//	/**
//	 * 输入参数，投票ID。可以为空，为空则获取站点的默认投票。
//	 */
//	public static final String PARAM_ID = "id";
//	/**
//	 * 输入参数，站点ID。默认为当前站点。
//	 */
//	public static final String PARAM_SITE_ID = "siteId";
//
//	@Override
//    @SuppressWarnings("unchecked")
//	public void execute(Environment env, Map params, TemplateModel[] loopVars,
//			TemplateDirectiveBody body) throws TemplateException, IOException {
//		Site site = FrontUtils.getSite(env);
//		VoteTopic vote;
//		Integer id = getId(params);
//		if (id != null) {
//			vote = cmsVoteTopicMng.findById(id);
//		} else {
//			Integer siteId = getSiteId(params);
//			if (siteId == null) {
//				siteId = site.getId();
//			}
//			vote = cmsVoteTopicMng.getDefTopic(siteId);
//		}
//
//		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
//				params);
//		paramWrap.put(OUT_BEAN, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(vote));
//		Map<String, TemplateModel> origMap = DirectiveUtils
//				.addParamsToVariable(env, paramWrap);
//		body.render(env.getOut());
//		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
//	}
//
//	private Integer getId(Map<String, TemplateModel> params)
//			throws TemplateException {
//		return DirectiveUtils.getInt(PARAM_ID, params);
//	}
//
//	private Integer getSiteId(Map<String, TemplateModel> params)
//			throws TemplateException {
//		return DirectiveUtils.getInt(PARAM_SITE_ID, params);
//	}
//
//	@Autowired
//	private CmsVoteTopicMng cmsVoteTopicMng;
//}
