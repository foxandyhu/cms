package com.bfly.web.content.directive;

import com.bfly.cms.content.entity.Channel;
import com.bfly.cms.content.service.IArticleService;
import com.bfly.cms.content.service.IChannelService;
import com.bfly.common.IDEncrypt;
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
 * 文章内容标签
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/3 12:23
 */
@Component("articleDirective")
public class ArticleDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    @Autowired
    private IArticleService articleService;
    @Autowired
    private IChannelService channelService;

    /**
     * 查询条件 limit 返回条数 recommend 是否推荐  type 内容类型  channelId 栏目ID pageable 是否分页
     * topLevelSort 置顶排序 true升序 false降序 status状态 fileType文件类型
     * recommendLevelSort 推荐级别 true升序 false降序
     * viewsSort 点击率排序 true升序 false降序
     * commentsSort 评论数排序 true升序 false降序
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/3 12:29
     */
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String pageSize = "pageSize", status = "status", recommend = "recommend", type = "type",fileType="fileType", channelId = "channelId", pageable = "pageable";
        String topLevelSort = "topLevelSort", recommendLevelSort = "recommendLevelSort", viewsSort = "viewsSort", commentsSort = "commentsSort";
        Map<String, Object> map = new HashMap<>(4);
        int size = 0;
        if (params.containsKey(pageSize)) {
            size = getData(pageSize, params, Integer.class);
            Pager pager = new Pager(1, size, Integer.MAX_VALUE);
            PagerThreadLocal.set(pager);
        }
        if (params.containsKey(status)) {
            map.put(status, getData(status, params, Integer.class));
        }
        if (params.containsKey(recommend)) {
            map.put(recommend, getData(recommend, params, Boolean.class));
        }
        if (params.containsKey(type)) {
            map.put(type, getData(type, params, Integer.class));
        }
        if (params.containsKey(channelId)) {
            Integer cId = getData(channelId, params, Integer.class);
            map.put(channelId, cId);
        }
        if(params.containsKey(fileType)){
            map.put(fileType,getData(fileType,params,String.class));
        }

        Map<String, Sort.Direction> sort = new HashMap<>(4);
        if (params.containsKey(topLevelSort)) {
            addSortPro(topLevelSort, params, sort);
        }
        if (params.containsKey(recommendLevelSort)) {
            addSortPro(recommendLevelSort, params, sort);
        }
        if (params.containsKey(viewsSort)) {
            addSortPro(viewsSort, params, sort);
        }
        if (params.containsKey(commentsSort)) {
            addSortPro(commentsSort, params, sort);
        }

        List list;
        if (params.containsKey(pageable) && getData(pageable, params, Boolean.class)) {
            PagerThreadLocal.set(getRequest(),size);
            Pager pager = articleService.getPage(map, null, sort);
            list = pager.getData();
            idConvert(list);
            env.setVariable("pager", getObjectWrapper().wrap(pager));
        } else {
            list = articleService.getList(map, null, sort);
            idConvert(list);
            env.setVariable("list", getObjectWrapper().wrap(list));
        }


        if (map.containsKey(channelId)) {
            Channel channel = null;
            if (list.isEmpty()) {
                Integer cId = (Integer) map.get(channelId);
                if (cId != null) {
                    channel = channelService.get(cId);
                }
            } else {
                channel = new Channel();
                Map<String, Object> dataMap = (Map<String, Object>) list.get(0);
                channel.setPath(String.valueOf(dataMap.get("channelPath")));
                channel.setName(String.valueOf(dataMap.get("channelName")));
                channel.setId((int) dataMap.get("channelId"));
            }
            env.setVariable("channel", getObjectWrapper().wrap(channel));
        }
        PagerThreadLocal.clear();
        body.render(env.getOut());
    }

    private void addSortPro(String column, Map params, Map<String, Sort.Direction> sort) throws TemplateException {
        sort.put(column, getData(column, params, Boolean.class) ? Sort.Direction.ASC : Sort.Direction.DESC);
    }

    private void idConvert(List list){
        if (list != null) {
            list.forEach(item -> {
                Map<String, Object> m = (Map<String, Object>) item;
                Integer id = (Integer) m.get("id");
                m.put("idStr", IDEncrypt.encode(id));
            });
        }
    }
}
