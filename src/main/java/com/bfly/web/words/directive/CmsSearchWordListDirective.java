//package com.bfly.web.words.directive;
//
//import static com.bfly.common.web.freemarker.DirectiveUtils.OUT_LIST;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.bfly.cms.words.entity.SearchWords;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.bfly.cms.words.service.CmsSearchWordsMng;
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
// * 搜索热词列表标签
// */
//@Component("cms_searchword_list")
//public class CmsSearchWordListDirective implements TemplateDirectiveModel {
//	/**
//	 * 输入参数siteId，站点ID。
//	 */
//	public static final String PARAM_SITE_ID = "siteId";
//	/**
//	 * 输入参数recommend，推荐  1, 非推荐 0 全部 2。
//	 */
//	public static final String PARAM_RECOMMEND = "recommend";
//	/**
//	 * 输入参数word，搜索词。
//	 */
//	public static final String PARAM_WORD = "word";
//	/**
//	 * 输入参数orderBy，排序  1搜索次数降序 2搜索次数升序 3排序降序 4排序升序。
//	 */
//	public static final String PARAM_ORDERBY = "orderBy";
//	/**
//	 * 输入参数count，个数。
//	 */
//	public static final String PARAM_COUNT = "count";
//
//	@Override
//    @SuppressWarnings("unchecked")
//	public void execute(Environment env, Map params, TemplateModel[] loopVars,
//			TemplateDirectiveBody body) throws TemplateException, IOException {
//		Integer siteId = getSiteId(params);
//		if (siteId == null) {
//			siteId = FrontUtils.getSite(env).getId();
//		}
//		Integer recommend = getRecommend(params);
//		if (recommend == null) {
//			recommend = 2;
//		}
//		Integer orderBy = getOrderBy(params);
//		if (orderBy == null) {
//			orderBy = 1;
//		}
//		String word=getWord(params);
//		Integer count=FrontUtils.getCount(params);
//
//		List<SearchWords> list = cmsSearchWordsMng.getList(siteId, word, recommend,
//				orderBy,0,count, true);
//
//		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
//				params);
//		paramWrap.put(OUT_LIST, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(list));
//		Map<String, TemplateModel> origMap = DirectiveUtils
//				.addParamsToVariable(env, paramWrap);
//		body.render(env.getOut());
//		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
//	}
//
//	private Integer getSiteId(Map<String, TemplateModel> params)
//			throws TemplateException {
//		return DirectiveUtils.getInt(PARAM_SITE_ID, params);
//	}
//
//	private Integer getRecommend(Map<String, TemplateModel> params)
//			throws TemplateException {
//		return DirectiveUtils.getInt(PARAM_RECOMMEND, params);
//	}
//
//	private String getWord(Map<String, TemplateModel> params)
//			throws TemplateException {
//		return DirectiveUtils.getString(PARAM_WORD, params);
//	}
//
//	private Integer getOrderBy(Map<String, TemplateModel> params)
//			throws TemplateException {
//		return DirectiveUtils.getInt(PARAM_ORDERBY, params);
//	}
//
//	@Autowired
//	private CmsSearchWordsMng cmsSearchWordsMng;
//}
