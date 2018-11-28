package com.bfly.cms.content.action;

import static com.bfly.core.Constants.TPLDIR_MEMBER;
import static com.bfly.common.page.SimplePage.cpn;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfly.core.web.WebErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bfly.cms.content.service.ContentBuyMng;
import com.bfly.cms.content.service.ContentChargeMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.web.CookieUtils;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.system.entity.MemberConfig;
import com.bfly.core.web.util.CmsUtils;
import com.bfly.core.web.util.FrontUtils;

/**
 * 用户账户相关
 * 包含笔者所写文章被用户购买记录
 * 自己的消费记录
 */
@Controller
public class UserContentBuyAct {
	
	public static final String MEMBER_BUY_LIST = "tpl.memberBuyList";
	public static final String MEMBER_ORDER_LIST = "tpl.memberOrderList";
	public static final String CONTENT_CHARGE_LIST = "tpl.memberContentChargeList";

	/**
	 * 自己消费记录
	 * @param pageNo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/buy_list.html")
	public String buyList(String orderNum,Integer pageNo,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		Pagination pagination=contentBuyMng.getPage(orderNum,user.getId(),
				null,null,cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination",pagination);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, MEMBER_BUY_LIST);
	}
	
	/**
	 * 订单列表(被购买记录)
	 * @param pageNo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/order_list.html")
	public String orderList(String orderNum,Integer pageNo,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		if(user.getUserAccount()==null){
			WebErrors errors= WebErrors.create(request);
			errors.addErrorCode("error.userAccount.notfound");
			return FrontUtils.showError(request, response, model, errors);
		}
		Pagination pagination=contentBuyMng.getPage(orderNum, null, user.getId(),
				null,cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination",pagination);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, MEMBER_ORDER_LIST);
	}
	
	/**
	 * 我的内容收益列表
	 * @param pageNo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/charge_list.html")
	public String contentChargeList(Integer orderBy,
			Integer pageNo,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		if(user.getUserAccount()==null){
			WebErrors errors= WebErrors.create(request);
			errors.addErrorCode("error.userAccount.notfound");
			return FrontUtils.showError(request, response, model, errors);
		}
		if(orderBy==null){
			orderBy=1;
		}
		Pagination pagination=contentChargeMng.getPage(null,user.getId(),
				null,null,orderBy,cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("orderBy", orderBy);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, CONTENT_CHARGE_LIST);
	}
	
	
	
	@Autowired
	private ContentBuyMng contentBuyMng;
	@Autowired
	private ContentChargeMng contentChargeMng;
}
