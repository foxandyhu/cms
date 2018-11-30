package com.bfly.cms.funds.action;

import com.bfly.cms.funds.entity.CmsAccountDraw;
import com.bfly.cms.funds.service.CmsAccountDrawMng;
import com.bfly.cms.system.entity.CmsConfigContentCharge;
import com.bfly.cms.system.entity.MemberConfig;
import com.bfly.cms.system.service.CmsConfigContentChargeMng;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.common.page.Pagination;
import com.bfly.common.web.CookieUtils;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.web.WebErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

import static com.bfly.common.page.SimplePage.cpn;

/**
 * 账户提现
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/30 17:00
 */
@Controller
public class AccountDrawAct extends RenderController {

    private static final Logger log = LoggerFactory.getLogger(AccountDrawAct.class);

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

    @RequestMapping(value = "/member/draw_list.html")
    public String drawList(Integer pageNo, ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        CmsUser user = getUser();
        if (user.getUserAccount() == null) {
            WebErrors errors = WebErrors.create(getRequest());
            errors.addErrorCode("error.userAccount.notfound");
            return renderErrorPage(model, errors);
        }
        Pagination pagination = accountDrawMng.getPage(user.getId(), null, null, null, cpn(pageNo), CookieUtils.getPageSize(getRequest()));
        model.addAttribute("pagination", pagination);
        model.addAttribute("userAccount", user.getUserAccount());
        return renderPage("member/member_account_draw_list.html", model);
    }

    @RequestMapping(value = "/member/draw_del.html")
    public String drawDel(Integer[] ids, String nextUrl, ModelMap model) {
        WebErrors errors = validateDelete(ids);
        if (errors.hasErrors()) {
            return renderErrorPage(model, errors);
        }
        accountDrawMng.deleteByIds(ids);
        return renderSuccessPage(model, nextUrl);
    }

    /**
     * 提现申请输入页
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 17:05
     */
    @GetMapping(value = "/member/draw.html")
    public String drawInput(ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        CmsUser user = getUser();
        CmsConfigContentCharge config = configContentChargeMng.getDefault();
        Double appliedSum = accountDrawMng.getAppliedSum(user.getId());
        model.addAttribute("userAccount", user.getUserAccount());
        model.addAttribute("minDrawAmount", config.getMinDrawAmount());
        if (user.getUserAccount() != null) {
            model.addAttribute("maxDrawAmount", user.getUserAccount().getContentNoPayAmount() - appliedSum);
        }
        return renderPage("member/member_account_draw.html", model);
    }


    /**
     * 提现申请提交页
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 17:07
     */
    @PostMapping(value = "/member/draw.html")
    public String drawSubmit(Double drawAmout, String applyAcount, String nextUrl, ModelMap model) throws IOException {
        String result = check(model);
        if (result != null) {
            return result;
        }
        WebErrors errors = WebErrors.create(getRequest());
        CmsUser user = getUser();
        if (user.getUserAccount() == null) {
            errors.addErrorCode("error.userAccount.notfound");
            return renderErrorPage(model, errors);
        }
        if (drawAmout != null) {
            CmsConfigContentCharge config = configContentChargeMng.getDefault();
            if (drawAmout > user.getUserAccount().getContentNoPayAmount()) {
                errors.addErrorCode("error.userAccount.balanceNotEnough");
            }
            if (drawAmout < config.getMinDrawAmount()) {
                errors.addErrorCode("error.userAccount.drawLessMinAmount", config.getMinDrawAmount());
            }
            if (errors.hasErrors()) {
                return renderErrorPage(model, errors);
            }
        }
        accountDrawMng.draw(user, drawAmout, applyAcount);
        log.info("update CmsUserExt success. id={}", user.getId());
        return renderSuccessPage(model, nextUrl);
    }

    private WebErrors validateDelete(Integer[] ids) {
        WebErrors errors = WebErrors.create(getRequest());
        if (vldOpt(errors, ids)) {
            return errors;
        }
        return errors;
    }

    private boolean vldOpt(WebErrors errors, Integer[] ids) {
        for (Integer id : ids) {
            if (errors.ifNull(id, "id", true)) {
                return true;
            }
            CmsAccountDraw d = accountDrawMng.findById(id);
            // 数据不存在
            if (errors.ifNotExist(d, CmsAccountDraw.class, id, true)) {
                return true;
            }
            // 非本用户数据
            if (!d.getDrawUser().getId().equals(getUser().getId())) {
                errors.noPermission(CmsAccountDraw.class, id, true);
                return true;
            }
            // 提现申请状态是申请成功待支付和提现成功
            if (CmsAccountDraw.CHECKED_SUCC.equals(d.getApplyStatus())
                    || CmsAccountDraw.DRAW_SUCC.equals(d.getApplyStatus())) {
                errors.addErrorCode("error.account.draw.hasChecked");
                return true;
            }
        }
        return false;
    }

    @Autowired
    private CmsAccountDrawMng accountDrawMng;
    @Autowired
    private CmsConfigContentChargeMng configContentChargeMng;
}
