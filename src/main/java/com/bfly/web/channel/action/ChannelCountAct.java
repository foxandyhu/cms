package com.bfly.web.channel.action;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.channel.service.ChannelCountCache;
import com.bfly.cms.channel.service.ChannelMng;
import com.bfly.common.web.ResponseUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ChannelCountAct {
    @RequestMapping(value = "/channel_view.html", method = RequestMethod.GET)
    public void contentView(Integer channelId, HttpServletResponse response) throws JSONException {
        if (channelId == null) {
            ResponseUtils.renderJson(response, "[]");
            return;
        }
        //栏目访问量计数
        Channel channel = channelMng.findById(channelId);
        int[] counts = channelCountCache.viewAndGet(channel.getId());
        String json;
        if (counts != null) {
            json = new JSONArray(counts).toString();
            ResponseUtils.renderJson(response, json);
        } else {
            ResponseUtils.renderJson(response, "[]");
        }
    }


    @Autowired
    private ChannelCountCache channelCountCache;
    @Autowired
    private ChannelMng channelMng;
}
