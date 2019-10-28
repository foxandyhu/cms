package com.bfly.web.vote.directive;

import com.bfly.cms.vote.entity.VoteTopic;
import com.bfly.cms.vote.service.IVoteTopicService;
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
 * 投票标签
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/10 22:12
 */
@Component("voteDirective")
public class VoteDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    @Autowired
    private IVoteTopicService voteTopicService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        PagerThreadLocal.clear();
        String pageSize = "pageSize", enabled = "enabled", status = "status", pageable = "pageable";
        Map<String, Object> map = new HashMap<>(3);
        int size = 0;
        if (params.containsKey(pageSize)) {
            size = getData(pageSize, params, Integer.class);
            Pager pager = new Pager(1, size, Integer.MAX_VALUE);
            PagerThreadLocal.set(pager);
        }
        if (params.containsKey(enabled)) {
            map.put(enabled, getData(enabled, params, Boolean.class));
        }
        if (params.containsKey(status)) {
            map.put(status, getData(status, params, Integer.class));
        }
        if (params.containsKey(pageable) && getData(pageable, params, Boolean.class)) {
            PagerThreadLocal.set(getRequest(), size);
            Pager pager = voteTopicService.getPage(map);
            env.setVariable("pager", getObjectWrapper().wrap(pager));
        } else {
            List<VoteTopic> list = voteTopicService.getList(map);
            env.setVariable("list", getObjectWrapper().wrap(list));
        }
        body.render(env.getOut());
    }
}
