package com.bfly.web.content.directive;

import com.bfly.cms.content.service.IChannelService;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 栏目标签
 * 指定父类parentId 和 display属性
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/29 18:21
 */
@Component("channelListDirect")
public class ChannelDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    @Autowired
    private IChannelService channelService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        Map<String, Object> map = new HashMap<>(2);
        String display = "display", parentId = "parentId";
        if (params.containsKey(display)) {
            map.put(display, getData(display, params, Boolean.class));
        }
        if (params.containsKey(parentId)) {
            map.put(parentId, getData(parentId, params, Integer.class));
        }
        PagerThreadLocal.clear();
        List list = channelService.getList(map);

        Collections.sort(list);
        env.setVariable("list", getObjectWrapper().wrap(list));
        body.render(env.getOut());
    }
}
