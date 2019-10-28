package com.bfly.web.content.directive;

import com.bfly.cms.content.service.IArticleService;
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
import java.util.Map;

/**
 * 上一篇 下一篇 文章标签
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/7 15:39
 */
@Component("nextArticleDirective")
public class NextArticleDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    private Logger logger = LoggerFactory.getLogger(NextArticleDirective.class);

    @Autowired
    private IArticleService articleService;

    /**
     * 查询上一篇或下一篇文章的标签
     * currentArticleId 当前文章ID
     * channelId 栏目Id
     * next=true 下一篇 false上一篇
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/3 12:29
     */
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String currentArticleId = "currentArticleId", channelId = "channelId", next = "next";
        Long aId;
        if (!params.containsKey(currentArticleId)) {
            logger.warn("未指定当前文章ID");
            return;
        } else {
            String idStr = getData(currentArticleId, params, String.class);
            aId = IDEncrypt.decode(idStr);
            if (aId == null) {
                logger.warn("未指定当前文章ID");
                return;
            }
        }
        Integer cId = null;
        if (params.containsKey(channelId)) {
            cId = getData(channelId, params, Integer.class);
        }
        if (!params.containsKey(next)) {
            return;
        }
        Boolean isNext = getData(next, params, Boolean.class);
        if (isNext == null) {
            return;
        }
        Map<String, Object> result;
        int articleId = aId.intValue();
        if (isNext) {
            result = articleService.getNext(articleId, cId);
        } else {
            result = articleService.getPrev(articleId, cId);
        }
        env.setVariable("nextArticle", getObjectWrapper().wrap(result));
        body.render(env.getOut());
    }
}
