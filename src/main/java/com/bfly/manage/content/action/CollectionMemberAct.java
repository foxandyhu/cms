package com.bfly.manage.content.action;

import com.bfly.cms.content.service.ContentMng;
import com.bfly.cms.system.entity.MemberConfig;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.web.CookieUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.base.action.RenderController;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

import static com.bfly.common.page.SimplePage.cpn;

/**
 * 收藏信息Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/30 17:15
 */
@Controller
public class CollectionMemberAct extends RenderController {

    /**
     * 校验
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 17:01
     */
    private String check(ModelMap model) {
        MemberConfig mcfg = getSite().getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return renderMessagePage(model, "member.memberClose");
        }
        if (getUser() == null) {
            return renderLoginPage(model);
        }
        return null;
    }

    /**
     * 我的收藏信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 17:15
     */
    @RequestMapping(value = "/member/collection_list.html")
    public String collectionList(String queryTitle, Integer queryChannelId, Integer pageNo, ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        Pagination p = contentMng.getPageForCollection(getSite().getId(), getUser().getId(), cpn(pageNo), CookieUtils.getPageSize(getRequest()));
        model.addAttribute("pagination", p);
        if (!StringUtils.isBlank(queryTitle)) {
            model.addAttribute("queryTitle", queryTitle);
        }
        if (queryChannelId != null) {
            model.addAttribute("queryChannelId", queryChannelId);
        }
        return renderPage("member/collection_list.html", model);
    }

    @RequestMapping(value = "/member/collect.html")
    public void collect(Integer cId, Integer operate, HttpServletResponse response) throws JSONException {
        CmsUser user = getUser();
        JSONObject object = new JSONObject();
        if (user == null) {
            object.put("result", false);
        } else {
            object.put("result", true);
            userMng.updateUserConllection(user, cId, operate);
        }
        ResponseUtils.renderJson(response, object.toString());
    }

    @RequestMapping(value = "/member/collect_cancel.html")
    public String collectCancel(Integer[] cIds, Integer pageNo, ModelMap model) throws JSONException {
        String result = check(model);
        if (result != null) {
            return result;
        }
        for (Integer id : cIds) {
            userMng.updateUserConllection(getUser(), id, 0);
        }
        return collectionList(null, null, pageNo, model);
    }

    @RequestMapping(value = "/member/collect_exist.html")
    public void collectExist(Integer cId, HttpServletResponse response) throws JSONException {
        JSONObject object = new JSONObject();
        if (getUser() == null) {
            object.put("result", false);
        } else {
            if (cId != null && getUser().getCollectContents().contains(contentMng.findById(cId))) {
                object.put("result", true);
            } else {
                object.put("result", false);
            }
        }
        ResponseUtils.renderJson(response, object.toString());
    }

    @Autowired
    private ContentMng contentMng;
    @Autowired
    private CmsUserMng userMng;

}
