package com.bfly.cms.content.action;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.channel.service.ChannelMng;
import com.bfly.cms.content.entity.CmsTopic;
import com.bfly.cms.content.service.CmsTopicMng;
import com.bfly.core.base.action.RenderController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 主题Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/30 15:11
 */
@Controller
public class TopicAct extends RenderController {

    @GetMapping(value = "/topic*.html")
    public String index(Integer channelId, Integer topicId, ModelMap model) {
        // 全部？按站点？按栏目？要不同模板？
        if (topicId != null) {
            CmsTopic topic = cmsTopicMng.findById(topicId);
            if (topic == null) {
                return renderNotFoundPage(model);
            }
            model.addAttribute("topic", topic);
            String tpl = topic.getTplContent();
            if (StringUtils.isBlank(tpl)) {
                tpl = renderPagination("topic/topic_default.html", model);
            }
            return tpl;
        }
        if (channelId != null) {
            Channel channel = channelMng.findById(channelId);
            if (channel != null) {
                model.addAttribute("channel", channel);
            } else {
                return renderNotFoundPage(model);
            }
            return renderPagination("topic/topic_channel.html", model);
        }
        return renderPagination("topic/topic_index.html", model);
    }

    @GetMapping(value = "/topic/{id}.html")
    public String topic(@PathVariable String id, ModelMap model) {
        if (id == null) {
            return renderNotFoundPage(model);
        }
        Integer topicId;
        try {
            topicId = Integer.parseInt(id);
        } catch (Exception e) {
            return renderNotFoundPage(model);
        }
        CmsTopic topic = null;
        if (topicId != null) {
            topic = cmsTopicMng.findById(topicId);
        }
        if (topic == null) {
            return renderNotFoundPage(model);
        }
        model.addAttribute("topic", topic);
        String tpl = topic.getTplContent();
        if (StringUtils.isBlank(tpl)) {
            tpl = renderPagination("topic/topic_default.html", model);
        }
        return tpl;
    }

    @Autowired
    private CmsTopicMng cmsTopicMng;
    @Autowired
    private ChannelMng channelMng;
}
