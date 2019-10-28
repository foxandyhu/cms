package com.bfly.web.words.directive;

import com.bfly.cms.words.entity.SearchWords;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索词标签
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/4 22:42
 */
@Component("searchWordsDirective")
public class SearchWordsDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    @Autowired
    private ISearchWordsService wordsService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String pageSize = "pageSize", recommend = "recommend", hitCountSort = "hitCountSort";
        Map<String, Object> map = new HashMap<>(2);
        Map<String, Sort.Direction> sortMap = null;
        if (params.containsKey(pageSize)) {
            int size = getData(pageSize, params, Integer.class);
            Pager pager = new Pager(1, size, Integer.MAX_VALUE);
            PagerThreadLocal.set(pager);
        }
        if (params.containsKey(recommend)) {
            map.put(recommend, getData(recommend, params, Boolean.class));
        }
        if (params.containsKey(hitCountSort)) {
            sortMap = new HashMap<>(1);
            Boolean sort = getData(hitCountSort, params, Boolean.class);
            sortMap.put("hitCount", (sort == null || sort) ? Sort.Direction.ASC : Sort.Direction.DESC);
        }

        List<SearchWords> list = wordsService.getList(map, null, sortMap);

        env.setVariable("list", getObjectWrapper().wrap(list));
        PagerThreadLocal.clear();
        body.render(env.getOut());
    }
}
