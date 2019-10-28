package com.bfly.web;

import com.bfly.cms.content.entity.Channel;
import com.bfly.cms.content.entity.Model;
import com.bfly.cms.content.service.IChannelService;
import com.bfly.cms.content.service.IModelService;
import com.bfly.cms.system.entity.SiteConfig;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.context.ContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 网站首页Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/29 10:29
 */
@Controller
public class IndexController extends RenderController {

    @Autowired
    private IChannelService channelService;
    @Autowired
    private IModelService modelService;

    /**
     * 站点首页
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/21 15:20
     */
    @GetMapping(value = {"/", "/index.html"})
    public String index() {
        SiteConfig config = ContextUtil.getSiteConfig(getRequest().getServletContext());
        String tpl = isMobileRequest() ? config.getTplMobile() : config.getTplPc();
        if (!StringUtils.hasLength(tpl)) {
            //如果没有设置站点首页则默认首页是index.html
            tpl = "index.html";
        }
        return renderTplPath(tpl, "index");
    }

    /**
     * 栏目首页
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/19 16:25
     */
    @GetMapping(value = "/{path}/index")
    public String channelIndex(@PathVariable("path") String path) {
        Channel channel = channelService.getChannelByPath(path);
        Model model = verifyChannel(channel);
        String tpl = isMobileRequest() ? channel.getTplMobileChannel() : channel.getTplPcChannel();
        if (!StringUtils.hasLength(tpl)) {
            //如果没有指定栏目模板 则默认为对应模型下的index.html
            tpl = "index.html";
        }
        getRequest().setAttribute("channel", channel);
        return renderTplPath(tpl, model.getTplDir());
    }

    /**
     * 验证栏目 返回栏目对应的模型对象
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/19 17:03
     */
    private Model verifyChannel(Channel channel) {
        if (channel == null) {
            throw new RuntimeException("不存在的栏目信息!");
        }
        Model model = modelService.get(channel.getModelId());
        if (model == null) {
            throw new RuntimeException("未知的模型信息!");
        }
        return model;
    }

    /**
     * 站点关闭
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/20 15:29
     */
    @GetMapping(value = "/close")
    public String siteClose() {
        return renderTplPath("site_close.html", "common");
    }
}
