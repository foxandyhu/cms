package com.bfly.web.content.directive;

import com.bfly.cms.content.entity.SpecialTopic;
import com.bfly.cms.content.service.ISpecialTopicService;
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
 * 专题标签
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/4 11:15
 */
@Component("specialTopicDirective")
public class SpecialTopicDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    @Autowired
    private ISpecialTopicService topicService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String pageSize = "pageSize", recommend = "recommend", seqSort = "seqSort", pageable = "pageable";
        Map<String, Object> map = new HashMap<>(2);
        Map<String, Sort.Direction> sortMap = null;
        int size = 0;
        if (params.containsKey(pageSize)) {
            size = getData(pageSize, params, Integer.class);
            Pager pager = new Pager(1, size, Integer.MAX_VALUE);
            PagerThreadLocal.set(pager);
        }
        if (params.containsKey(recommend)) {
            map.put(recommend, getData(recommend, params, Boolean.class));
        }
        if (params.containsKey(seqSort)) {
            sortMap = new HashMap<>(1);
            Boolean s = getData(seqSort, params, Boolean.class);
            sortMap.put("seq", s ? Sort.Direction.ASC : Sort.Direction.DESC);
        }
        if (params.containsKey(pageable) && getData(pageable, params, Boolean.class)) {
            PagerThreadLocal.set(getRequest(), size);
            Pager pager = topicService.getPage(map, null, sortMap);
            env.setVariable("pager", getObjectWrapper().wrap(pager));
        } else {
            List<SpecialTopic> list = topicService.getList(map, null, sortMap);
            env.setVariable("list", getObjectWrapper().wrap(list));
        }
        PagerThreadLocal.clear();
        body.render(env.getOut());
    }
}
