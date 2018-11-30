package com.bfly.cms.comment.action;

import com.bfly.cms.channel.entity.ChannelExt;
import com.bfly.cms.comment.entity.CmsComment;
import com.bfly.cms.comment.service.CmsCommentMng;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.service.ContentMng;
import com.bfly.cms.system.entity.CmsConfig;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.words.service.CmsSensitivityMng;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.base.action.RenderController;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 评论Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/30 10:53
 */
@Controller
public class CommentAct extends RenderController {
    private static final Logger log = LoggerFactory.getLogger(CommentAct.class);

    @GetMapping(value = "/comment*.html")
    public String page(Integer contentId, ModelMap model) {
        if (contentId == null) {
            return renderMessagePage(model, "comment.contentNotFound");
        }
        Content content = contentMng.findById(contentId);
        if (content == null) {
            return renderMessagePage(model, "comment.contentNotFound");
        }
        if (content.getChannel().getCommentControl() == ChannelExt.COMMENT_OFF) {
            return renderMessagePage(model, "comment.closed");
        }
        model.putAll(RequestUtils.getQueryParams(getRequest()));
        model.addAttribute("content", content);
        return renderPagination("special/comment_page.html", model);
    }

    @RequestMapping(value = "/comment_input_csi.html")
    public String custom(Integer contentId, ModelMap model) {
        if (contentId == null) {
            return renderMessagePage(model, "comment.contentNotFound");
        }
        Content content = contentMng.findById(contentId);
        if (content == null) {
            return renderMessagePage(model, "comment.contentNotFound");
        }
        if (content.getChannel().getCommentControl() == ChannelExt.COMMENT_OFF) {
            return renderMessagePage(model, "comment.closed");
        }
        model.putAll(RequestUtils.getQueryParams(getRequest()));
        model.addAttribute("content", content);
        return renderPage("special/comment_input.html", model);
    }

    @RequestMapping(value = "/comment_list.html")
    public String list(Integer siteId, Integer contentId, Integer parentId, Integer greatTo, Integer recommend, Short checked, Integer orderBy, Integer count, ModelMap model) {
        if (count == null || count <= 0 || count > 200) {
            count = 200;
        }
        boolean desc;
        if (orderBy == null || orderBy == 0) {
            desc = true;
        } else {
            desc = false;
        }
        Boolean rec;
        if (recommend != null) {
            rec = recommend != 0;
        } else {
            rec = null;
        }
        List<CmsComment> list = cmsCommentMng.getListForTag(siteId, contentId, parentId, greatTo, checked, rec, desc, 0, count);
        model.putAll(RequestUtils.getQueryParams(getRequest()));
        model.addAttribute("list", list);
        model.addAttribute("contentId", contentId);
        model.addAttribute("content", contentMng.findById(contentId));
        return renderPage("csi/comment_list.html", model);
    }

    @PostMapping(value = "/comment.html")
    public void submit(HttpServletResponse response, Integer contentId, Integer parentId, Integer score, String text, String captcha) throws JSONException, IOException {
        CmsConfig config = getSite().getConfig();
        JSONObject json = new JSONObject();
        if (contentId == null) {
            json.put("success", false);
            json.put("status", 100);
            ResponseUtils.renderJson(response, json.toString());
            return;
        }
        if (StringUtils.isBlank(text)) {
            json.put("success", false);
            json.put("status", 101);
            ResponseUtils.renderJson(response, json.toString());
            return;
        }
        CmsUser user = getUser();
        if (user == null || user.getGroup().getNeedCaptcha()) {
            // 验证码错误
            try {
                if (!imageCaptchaService.validateResponseForID(getSession().getId(), captcha)) {
                    json.put("success", false);
                    json.put("status", 1);
                    ResponseUtils.renderJson(response, json.toString());
                    return;
                }
            } catch (CaptchaServiceException e) {
                json.put("success", false);
                json.put("status", 1);
                log.warn("", e);
                ResponseUtils.renderJson(response, json.toString());
                return;
            }
        }
        Content content = contentMng.findById(contentId);
        if (content == null) {
            // 内容不存在
            json.put("success", false);
            json.put("status", 2);
        } else if (content.getChannel().getCommentControl() == ChannelExt.COMMENT_OFF || !config.getCommentOpen()) {
            // 评论关闭
            json.put("success", false);
            json.put("status", 3);
        } else if ((content.getChannel().getCommentControl() == ChannelExt.COMMENT_LOGIN | content.getChannel().getCommentControl() == ChannelExt.COMMENT_LOGIN_MANY)
                && user == null) {  //拦截未登录的用户评论
            // 需要登录才能评论
            json.put("success", false);
            json.put("status", 4);
        } else if (content.getChannel().getCommentControl() == ChannelExt.COMMENT_LOGIN && user != null && hasCommented(user, content)) {
            // 已经评论过，不能重复评论
            json.put("success", false);
            json.put("status", 5);
        } else {
            short checked = 0;
            Integer userId = null;
            boolean limit = false;
            if (user != null) {
                if (!user.getGroup().getNeedCheck()) {
                    checked = 1;
                }
                userId = user.getId();
                //评论次数限制:获取站点配置信息
                if (config.getCommentDayLimit() != 0 && user.getUserExt().getTodayCommentTotal() >= config.getCommentDayLimit()) {
                    limit = true;
                }
            }
            if (!limit) {
                // 敏感词过滤
                boolean haveSensitive = sensitivityMng.haveSensitivity(text);
                if (haveSensitive) {
                    json.put("success", false);
                    json.put("status", 10);
                    ResponseUtils.renderJson(response, json.toString());
                    return;
                }
                cmsCommentMng.comment(score, text, RequestUtils.getIpAddr(getRequest()), contentId, getSite().getId(), userId, checked, false, parentId);
                json.put("success", true);
                json.put("status", 0);
            } else {
                json.put("success", false);
                json.put("status", 6);
            }
        }
        ResponseUtils.renderJson(response, json.toString());
    }

    @RequestMapping(value = "/comment_up.html")
    public void up(Integer commentId, HttpServletResponse response) {
        if (exist(commentId)) {
            cmsCommentMng.ups(commentId);
            ResponseUtils.renderJson(response, "true");
        } else {
            ResponseUtils.renderJson(response, "false");
        }
    }

    @RequestMapping(value = "/comment_down.html")
    public void down(Integer commentId, HttpServletResponse response) {
        if (exist(commentId)) {
            cmsCommentMng.downs(commentId);
            ResponseUtils.renderJson(response, "true");
        } else {
            ResponseUtils.renderJson(response, "false");
        }
    }

    private boolean hasCommented(CmsUser user, Content content) {
        return content.hasCommentUser(user);
    }

    private boolean exist(Integer id) {
        if (id == null) {
            return false;
        }
        CmsComment comment = cmsCommentMng.findById(id);
        return comment != null;
    }

    @Autowired
    private CmsCommentMng cmsCommentMng;
    @Autowired
    private ContentMng contentMng;
    @Autowired
    private ImageCaptchaService imageCaptchaService;
    @Autowired
    private CmsSensitivityMng sensitivityMng;
}
