package com.bfly.manage.comment.action;

import com.bfly.cms.comment.entity.CmsGuestbook;
import com.bfly.cms.comment.service.CmsGuestbookMng;
import com.bfly.cms.system.entity.MemberConfig;
import com.bfly.common.page.Pagination;
import com.bfly.common.web.CookieUtils;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.web.WebErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.bfly.common.page.SimplePage.cpn;

/**
 * 会员中心获取留言Action
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/30 15:04
 */
@Controller
public class GuestbookMemberAct extends RenderController {

    /**
     * 我的留言
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 15:04
     */
    @RequestMapping(value = "/member/myguestbook.html")
    public String myguestbook(Integer pageNo, Integer ctgId, ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        Pagination pagination = guestbookMng.getPage(ctgId, null, getUser().getId(), null, null, true, false, cpn(pageNo), CookieUtils.getPageSize(getRequest()));
        model.addAttribute("pagination", pagination);
        return renderPage("guestbook/guestbook_list.html", model);
    }

    /**
     * 留言详细
     */
    @RequestMapping(value = "/member/guestbook_detail.html")
    public String guestbook_detail(Integer id, ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        CmsGuestbook guestbook = guestbookMng.findById(id);
        model.addAttribute("guestbook", guestbook);
        return renderPage("guestbook/guestbook_detail.html", model);
    }

    /**
     * 查看留言回复
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 15:10
     */
    @RequestMapping(value = "/member/guestbook_replay.html")
    public String guestbook_replay(Integer id, ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        CmsGuestbook guestbook = guestbookMng.findById(id);
        if (!guestbook.getMember().equals(getUser())) {
            WebErrors errors = WebErrors.create(getRequest());
            errors.addErrorCode("error.noPermissionsView");
            return renderErrorPage(model, errors);
        }
        model.addAttribute("guestbook", guestbook);
        return renderPage("guestbook/guestbook_replay.html", model);
    }

    /**
     * 校验
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 15:08
     */
    private String check(ModelMap model) {
        MemberConfig mcfg = getSite().getConfig().getMemberConfig();
        if (!mcfg.isMemberOn()) {
            return renderMessagePage(model, "member.memberClose");
        }
        if (getUser() == null) {
            return renderLoginPage(model);
        }
        return null;
    }

    @Autowired
    private CmsGuestbookMng guestbookMng;

}
