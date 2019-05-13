//package com.bfly.web.lucene.directive;
//
//import com.bfly.cms.siteconfig.entity.Site;
//import com.bfly.core.Constants;
//import com.bfly.cms.content.entity.Content;
//import com.bfly.cms.lucene.service.LuceneContentSvc;
//import com.bfly.common.web.freemarker.DefaultObjectWrapperBuilderFactory;
//import com.bfly.common.web.freemarker.DirectiveUtils;
//import com.bfly.common.web.freemarker.DirectiveUtils.InvokeType;
//import com.bfly.core.exception.ParamsRequiredException;
//import com.bfly.common.web.springmvc.RealPathResolver;
//import com.bfly.core.web.util.FrontUtils;
//import freemarker.core.Environment;
//import freemarker.template.TemplateDirectiveBody;
//import freemarker.template.TemplateException;
//import freemarker.template.TemplateModel;
//import org.apache.commons.lang.StringUtils;
//import org.apache.lucene.queryParser.ParseException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static com.bfly.core.Constants.TPL_STYLE_LIST;
//import static com.bfly.core.Constants.TPL_SUFFIX;
//import static com.bfly.common.web.Constants.UTF8;
//import static com.bfly.common.web.freemarker.DirectiveUtils.OUT_LIST;
//import static com.bfly.core.web.util.FrontUtils.PARAM_STYLE_LIST;
//
//@Component("cms_lucene_list")
//public class LuceneDirectiveList extends AbstractLuceneDirective {
//
//    /**
//     * 模板名称
//     */
//    public static final String TPL_NAME = "lucene_list";
//
//    @Override
//    public void execute(Environment env, Map params, TemplateModel[] loopVars,
//                        TemplateDirectiveBody body) throws TemplateException, IOException {
//        Site site = FrontUtils.getSite(env);
//        int first = FrontUtils.getFirst(params);
//        int count = FrontUtils.getCount(params);
//        String query = getQuery(params);
//        String workplace = getWorkplace(params);
//        String category = getCategory(params);
//        Integer siteId = getSiteId(params);
//        Integer channelId = getChannelId(params);
//        Date startDate = getStartDate(params);
//        Date endDate = getEndDate(params);
//        List<Content> list;
//        try {
//            String path = realPathResolver.get(Constants.LUCENE_PATH);
//            list = luceneContentSvc.searchList(path, query, category, workplace, siteId, channelId,
//                    startDate, endDate, first, count);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//
//        Map<String, TemplateModel> paramWrap = new HashMap<>(
//                params);
//        paramWrap.put(OUT_LIST, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(list));
//        Map<String, TemplateModel> origMap = DirectiveUtils
//                .addParamsToVariable(env, paramWrap);
//        InvokeType type = DirectiveUtils.getInvokeType(params);
//        String listStyle = DirectiveUtils.getString(PARAM_STYLE_LIST, params);
//        if (InvokeType.sysDefined == type) {
//            if (StringUtils.isBlank(listStyle)) {
//                throw new ParamsRequiredException(PARAM_STYLE_LIST);
//            }
//            env.include(TPL_STYLE_LIST + listStyle + TPL_SUFFIX, UTF8, true);
//        } else if (InvokeType.userDefined == type) {
//            if (StringUtils.isBlank(listStyle)) {
//                throw new ParamsRequiredException(PARAM_STYLE_LIST);
//            }
//            FrontUtils.includeTpl(TPL_STYLE_LIST, site, env);
//        } else if (InvokeType.custom == type) {
//            FrontUtils.includeTpl(TPL_NAME, site, params, env);
//        } else if (InvokeType.body == type) {
//            body.render(env.getOut());
//        } else {
//            throw new RuntimeException("invoke type not handled: " + type);
//        }
//        DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
//    }
//
//    @Autowired
//    private LuceneContentSvc luceneContentSvc;
//    @Autowired
//    private RealPathResolver realPathResolver;
//}
