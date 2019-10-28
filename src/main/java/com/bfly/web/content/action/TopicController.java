package com.bfly.web.content.action;

import com.bfly.cms.content.service.ISpecialTopicService;
import com.bfly.common.IDEncrypt;
import com.bfly.core.base.action.RenderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 专题Controller
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/29 18:12
 */
@Controller
@RequestMapping(value = "/topic")
public class TopicController extends RenderController {

    @Autowired
    private ISpecialTopicService topicService;

    /**
     * 专题列表
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/25 15:58
     */
    @GetMapping(value = "/list")
    public String topicList() {
        return renderTplPath("list.html", "topic");
    }

    /**
     * 专题详情
     * @author andy_hulibo@163.com
     * @date 2019/9/4 11:31
     */
    @GetMapping(value = "/id_{id}")
    public String topicDetail(@PathVariable("id") String id){
        Long topicId= IDEncrypt.decode(id);
        topicService.get(topicId==null?0:topicId.intValue());
        return renderTplPath("detail.html","topic");
    }
}
