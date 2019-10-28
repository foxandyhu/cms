package com.bfly.web.words.directive;

import com.bfly.cms.words.entity.Dictionary;
import com.bfly.cms.words.entity.SearchWords;
import com.bfly.cms.words.service.IDictionaryService;
import com.bfly.cms.words.service.ISearchWordsService;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseTemplateDirective;
import com.bfly.core.context.PagerThreadLocal;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典标签
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/10 15:29
 */
@Component("dictionaryDirective")
public class DictionaryDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    @Autowired
    private IDictionaryService dictionaryService;

    /**
     * type 类型
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/10 16:34
     */
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        PagerThreadLocal.clear();
        String type = "type";
        Map<String, Object> map = new HashMap<>(2);
        if (params.containsKey(type)) {
            map.put("type", getData(type, params, String.class));
            List<Dictionary> list = dictionaryService.getList(map);
            env.setVariable("list", getObjectWrapper().wrap(list));
        }
        body.render(env.getOut());
    }
}
