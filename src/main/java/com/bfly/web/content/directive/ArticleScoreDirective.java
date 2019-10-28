package com.bfly.web.content.directive;

import com.bfly.cms.content.service.IScoreItemService;
import com.bfly.common.IDEncrypt;
import com.bfly.core.base.action.BaseTemplateDirective;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 文章的评分标签
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/8 18:24
 */
@Component("articleScoreDirective")
public class ArticleScoreDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    private Logger logger = LoggerFactory.getLogger(ArticleScoreDirective.class);

    @Autowired
    private IScoreItemService scoreItemService;

    /**
     * 文章的评分项集合
     * articleId 文章ID 必填
     * groupId 评分组ID 必填
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/8 18:35
     */
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String articleId = "articleId", groupId = "groupId";

        List<Map<String, Object>> list = null;
        if (params.containsKey(articleId) && params.containsKey(groupId)) {
            String aIdStr = getData(articleId, params, String.class);
            Long aId = IDEncrypt.decode(aIdStr);
            int gId = getData(groupId, params, Integer.class);
            if (aId != null) {
                list = scoreItemService.getArticleScoreItems(aId.intValue(), gId);
            }
        } else {
            logger.warn("缺少参数!");
        }
        env.setVariable("list", getObjectWrapper().wrap(list));
        body.render(env.getOut());
    }
}
