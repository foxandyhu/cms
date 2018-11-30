package com.bfly.cms.comment.action;

import com.bfly.cms.comment.entity.CmsGuestbook;
import com.bfly.cms.comment.entity.CmsGuestbookCtg;
import com.bfly.cms.comment.service.CmsGuestbookCtgMng;
import com.bfly.cms.comment.service.CmsGuestbookMng;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.system.entity.CmsConfig;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.words.service.CmsSensitivityMng;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.annotation.Token;
import com.bfly.core.base.action.RenderController;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 留言Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/30 14:49
 */
@Controller
public class GuestbookAct extends RenderController {

    private static final Logger log = LoggerFactory.getLogger(GuestbookAct.class);

    /**
     * 留言板首页或类别页
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 14:49
     */
    @Token(save = true)
    @GetMapping(value = "/guestbook*.html")
    public String index(Integer ctgId, ModelMap model) {
        CmsGuestbookCtg ctg = null;
        if (ctgId != null) {
            ctg = cmsGuestbookCtgMng.findById(ctgId);
        }
        if (ctg == null) {
            // 留言板首页
            return renderPagination("special/guestbook_index.html", model);
        }
        // 留言板类别页
        model.addAttribute("ctg", ctg);
        return renderPagination("special/guestbook_ctg.html", model);
    }

    @Token(save = true)
    @GetMapping(value = "/guestbook/{id}.html")
    public String detail(@PathVariable Integer id, ModelMap model) {
        CmsGuestbook guestbook = null;
        if (id != null) {
            guestbook = cmsGuestbookMng.findById(id);
        }
        model.addAttribute("guestbook", guestbook);
        return renderPage("special/guestbook_detail.html", model);

    }

    /**
     * 提交留言。ajax提交。
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 14:55
     */
    @Token(remove = true)
    @PostMapping(value = "/guestbook.html")
    public void submit(HttpServletResponse response, Integer siteId, Integer ctgId, String title, String content, String email, String phone, String qq, String captcha) throws JSONException, IOException {
        CmsSite site = getSite();
        CmsConfig config = site.getConfig();
        if (siteId == null) {
            siteId = site.getId();
        }
        JSONObject json = new JSONObject();
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
            ResponseUtils.renderJson(response, json.toString());
            log.warn("", e);
            return;
        }
        //留言尚未开启
        if (!config.getGuestbookOpen()) {
            json.put("success", false);
            json.put("status", 2);
            ResponseUtils.renderJson(response, json.toString());
            return;
        }
        CmsUser user = getUser();
        //需要用户登陆
        if (user == null && config.getGuestbookNeedLogin()) {
            json.put("success", false);
            json.put("status", 3);
            ResponseUtils.renderJson(response, json.toString());
            return;
        }
        if (user != null) {
            Integer dayLimit = config.getGuestbookDayLimit();
            //0 不限制留言数   大于限制数则不允许发
            if (dayLimit != 0 && dayLimit <= user.getUserExt().getTodayGuestbookTotal()) {
                json.put("success", false);
                json.put("status", 4);
                ResponseUtils.renderJson(response, json.toString());
                return;
            }
        }
        boolean haveSensitive = sensitivityMng.haveSensitivity(content, title);
        if (haveSensitive) {
            json.put("success", false);
            json.put("status", 10);
            ResponseUtils.renderJson(response, json.toString());
            return;
        }
        String ip = RequestUtils.getIpAddr(getRequest());
        cmsGuestbookMng.save(user, siteId, ctgId, ip, title, content, email, phone, qq);
        json.put("success", true);
        json.put("status", 0);
        ResponseUtils.renderJson(response, json.toString());
    }

    @Autowired
    private CmsGuestbookCtgMng cmsGuestbookCtgMng;
    @Autowired
    private CmsGuestbookMng cmsGuestbookMng;
    @Autowired
    private ImageCaptchaService imageCaptchaService;
    @Autowired
    private CmsSensitivityMng sensitivityMng;

}
