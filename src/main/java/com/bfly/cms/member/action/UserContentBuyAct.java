package com.bfly.cms.member.action;

import com.bfly.cms.content.service.ContentBuyMng;
import com.bfly.cms.content.service.ContentChargeMng;
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
 * 用户账户相关
 * 包含笔者所写文章被用户购买记录
 * 自己的消费记录
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/30 10:08
 */
@Controller
public class UserContentBuyAct extends RenderController {

    /**
     * 自己消费记录
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 10:08
     */
    @RequestMapping(value = "/member/buy_list.html")
    public String buyList(String orderNum, Integer pageNo, ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        Pagination pagination = contentBuyMng.getPage(orderNum, getUser().getId(), null, null, cpn(pageNo), CookieUtils.getPageSize(getRequest()));
        model.addAttribute("pagination", pagination);
        return renderPage("member/buy_list.html", model);
    }

    /**
     * 订单列表(被购买记录)
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 10:11
     */
    @RequestMapping(value = "/member/order_list.html")
    public String orderList(String orderNum, Integer pageNo, ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        Pagination pagination = contentBuyMng.getPage(orderNum, null, getUser().getId(), null, cpn(pageNo), CookieUtils.getPageSize(getRequest()));
        model.addAttribute("pagination", pagination);
        return renderPage("member/order_list.html", model);
    }

    /**
     * 我的内容收益列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 10:30
     */
    @RequestMapping(value = "/member/charge_list.html")
    public String contentChargeList(Integer orderBy, Integer pageNo, ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        if (orderBy == null) {
            orderBy = 1;
        }
        Pagination pagination = contentChargeMng.getPage(null, getUser().getId(), null, null, orderBy, cpn(pageNo), CookieUtils.getPageSize(getRequest()));
        model.addAttribute("pagination", pagination);
        model.addAttribute("orderBy", orderBy);
        return renderPage("member/content_charge_list.html", model);
    }


    /**
     * 用户账户配置检查
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 10:33
     */
    private String check(ModelMap model) {
        MemberConfig mcfg = getSite().getConfig().getMemberConfig();
        if (!mcfg.isMemberOn()) {
            return renderMessagePage(model, "没有开启会员功能");
        }
        if (getUser() == null) {
            return renderLoginPage(model);
        }
        if (getUser().getUserAccount() == null) {
            WebErrors errors = WebErrors.create(getRequest());
            errors.addErrorCode("error.userAccount.notfound");
            return renderErrorPage(model, errors);
        }
        return null;
    }

    @Autowired
    private ContentBuyMng contentBuyMng;
    @Autowired
    private ContentChargeMng contentChargeMng;
}
