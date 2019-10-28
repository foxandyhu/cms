package com.bfly.web.message.directive;

import com.bfly.cms.message.service.ICommentService;
import com.bfly.core.base.action.BaseTemplateDirective;
import com.bfly.core.enums.CommentStatus;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 最新评论标签
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/13 20:51
 */
@Component("commentLatestDirective")
public class CommentLatestDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    @Autowired
    private ICommentService commentService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String size = "size";
        int pageSize = 10;
        if (params.containsKey(size)) {
            pageSize = getData(size, params, Integer.class);
        }
        List<Map<String, Object>> list = commentService.getLatestComment(pageSize, CommentStatus.PASSED.getId());
        env.setVariable("list",getObjectWrapper().wrap(list));
        body.render(env.getOut());
    }
}
