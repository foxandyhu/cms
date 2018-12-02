package com.bfly.web.comment.directive;

import static com.bfly.core.Constants.TPL_STYLE_LIST;
import static com.bfly.core.Constants.TPL_SUFFIX;
import static com.bfly.common.web.Constants.UTF8;
import static com.bfly.common.web.freemarker.DirectiveUtils.OUT_LIST;
import static com.bfly.common.web.freemarker.DirectiveUtils.OUT_PAGINATION;
import static com.bfly.core.web.util.FrontUtils.PARAM_STYLE_LIST;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bfly.common.page.Pagination;
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
 * 留言分页标签
 */
@Component("cms_guestbook_page")
public class CmsGuestbookPageDirective extends AbstractCmsGuestbookDirective {
	/**
	 * 模板名称
	 */
	public static final String TPL_NAME = "guestbook_page";

	@Override
    @SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		CmsSite site = FrontUtils.getSite(env);
		int pageNo = FrontUtils.getPageNo(env);
		int count = FrontUtils.getCount(params);
		Short check = 0;
		if (getChecked(params)) {
			check =1;
		}
		Pagination page = cmsGuestbookMng.getPage(getSiteId(params),
				getCtgId(params),null,null, getRecommend(params), check,
				getDesc(params), true, pageNo, count);

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(OUT_PAGINATION, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(page));
		paramWrap.put(OUT_LIST, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(page.getList()));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		InvokeType type = DirectiveUtils.getInvokeType(params);
		String listStyle = DirectiveUtils.getString(PARAM_STYLE_LIST, params);
		if (InvokeType.sysDefined == type) {
			if (StringUtils.isBlank(listStyle)) {
				throw new ParamsRequiredException(PARAM_STYLE_LIST);
			}
			env.include(TPL_STYLE_LIST + listStyle + TPL_SUFFIX, UTF8, true);
			FrontUtils.includePagination(site, params, env);
		} else if (InvokeType.userDefined == type) {
			if (StringUtils.isBlank(listStyle)) {
				throw new ParamsRequiredException(PARAM_STYLE_LIST);
			}
			FrontUtils.includeTpl(TPL_STYLE_LIST, site, env);
			FrontUtils.includePagination(site, params, env);
		} else if (InvokeType.custom == type) {
			FrontUtils.includeTpl(TPL_NAME, site, params, env);
			FrontUtils.includePagination(site, params, env);
		} else if (InvokeType.body == type) {
			body.render(env.getOut());
			FrontUtils.includePagination(site, params, env);
		} else {
			throw new RuntimeException("invoke type not handled: " + type);
		}
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}
}
