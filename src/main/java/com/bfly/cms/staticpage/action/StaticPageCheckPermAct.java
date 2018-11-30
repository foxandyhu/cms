package com.bfly.cms.staticpage.action;


import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.service.ContentBuyMng;
import com.bfly.cms.content.service.ContentMng;
import com.bfly.cms.user.entity.CmsGroup;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.web.util.CmsUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 静态页检查浏览权限
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/30 17:28
 */
@Controller
public class StaticPageCheckPermAct extends RenderController {

    public static final String GROUP_FORBIDDEN = "login.groupAccessForbidden";

    private static final Logger log = LoggerFactory.getLogger(StaticPageCheckPermAct.class);

    @RequestMapping(value = "/page_checkperm.html")
    public void checkPerm(Integer contentId, HttpServletRequest request, HttpServletResponse response) throws JSONException {
        Content content = contentMng.findById(contentId);
        String result = "1";
        if (content == null) {
            log.debug("Content id not found: {}", contentId);
            result = "2";
        }
        CmsUser user = CmsUtils.getUser(request);
        Set<CmsGroup> groups = content.getViewGroupsExt();
        int len = groups.size();
        // 需要浏览权限
        if (len != 0) {
            // 没有登录
            if (user == null) {
                result = "3";
            } else {
                // 已经登录但没有权限
                Integer gid = user.getGroup().getId();
                boolean right = false;
                for (CmsGroup group : groups) {
                    if (group.getId().equals(gid)) {
                        right = true;
                        break;
                    }
                }
                //无权限
                if (!right) {
                    result = "4";
                }
            }
        }
        //收费模式，检查是否已购买
        if (content.getCharge()) {
            if (user == null) {
                result = "3";
            } else {
                if (!content.getUser().equals(user)) {
                    boolean hasBuy = contentBuyMng.hasBuyContent(user.getId(), contentId);
                    if (!hasBuy) {
                        result = "5";
                    }
                }
            }
        }
        ResponseUtils.renderJson(response, result);
    }

    @RequestMapping(value = "/user_no_login.html")
    public String userNoLogin(ModelMap model) {
        return renderLoginPage(model);
    }

    @RequestMapping(value = "/group_forbidden.html")
    public String groupForbidden(ModelMap model) {
        CmsUser user = getUser();
        if (getUser() != null) {
            return renderMessagePage(model, GROUP_FORBIDDEN, user.getGroup().getName());
        }
        return userNoLogin(model);
    }

    @Autowired
    private ContentMng contentMng;
    @Autowired
    private ContentBuyMng contentBuyMng;
}
