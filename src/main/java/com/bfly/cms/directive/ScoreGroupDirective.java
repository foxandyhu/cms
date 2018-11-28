package com.bfly.cms.directive;

import static com.bfly.common.web.freemarker.DirectiveUtils.OUT_BEAN;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.bfly.cms.content.entity.CmsScoreGroup;
import com.bfly.cms.content.service.CmsScoreGroupMng;
import com.bfly.common.web.freemarker.DefaultObjectWrapperBuilderFactory;
import com.bfly.common.web.freemarker.DirectiveUtils;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.core.web.util.FrontUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

/**
 * 评分组对象标签
 */
@Component("cms_score_group")
public class ScoreGroupDirective implements TemplateDirectiveModel {
	
	/**
	 * 输入参数，组ID。
	 */
	/**
	 * 输入参数，站点ID。默认为当前站点。
	 */
	public static final String PARAM_SITE_ID = "siteId";
	public static final String PARAM_ID = "id";
	@Override
    @SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		CmsSite site = FrontUtils.getSite(env);
		Integer id = DirectiveUtils.getInt(PARAM_ID, params);
		CmsScoreGroup group;
		Integer siteId = getSiteId(params);
		if (siteId == null) {
			siteId = site.getId();
		}
		if (id != null) {
			group = scoreGroupMng.findById(id);
		} else {
			//找默认的分组
			group = scoreGroupMng.findDefault(siteId);
		}
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		if(group!=null){
			paramWrap.put(OUT_BEAN, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(group));
		}
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
	    if (body != null) {  
		    body.render(env.getOut());
        }  
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}
	
	private Integer getSiteId(Map<String, TemplateModel> params)throws TemplateException {
		return DirectiveUtils.getInt(PARAM_SITE_ID, params);
	}

	@Autowired
	private CmsScoreGroupMng scoreGroupMng;
}
