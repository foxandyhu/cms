//package com.bfly.web.weixin.action;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.bfly.cms.siteconfig.entity.Site;
//import com.bfly.core.web.WebErrors;
//import org.apache.commons.lang.StringUtils;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.bfly.cms.content.entity.Content;
//import com.bfly.cms.content.service.ContentMng;
//import com.bfly.cms.weixin.service.WeiXinSvc;
//import com.bfly.core.Constants;
//import com.bfly.cms.siteconfig.service.CmsSiteMng;
//import com.bfly.core.web.util.CmsUtils;
//import com.bfly.cms.weixin.entity.Weixin;
//import com.bfly.cms.weixin.service.WeixinMng;
//
//@Controller
//public class WeixinAct {
//
//	@RequiresPermissions("content:o_sendToWeixin")
//	@RequestMapping("/content/o_sendToWeixin.html")
//	public String sendToWeixin(Integer[] ids,
//			HttpServletRequest request, ModelMap model) {
//		WebErrors errors = validateCheck(ids, request);
//		if (errors.hasErrors()) {
//			return errors.showErrorPage(model);
//		}
//		Content[] beans = new Content[ids.length];
//		for (int i = 0; i < ids.length; i++) {
//			beans[i] = contentMng.findById(ids[i]);
//		}
//		weiXinSvc.sendTextToAllUser(beans);
//		return  "redirect:v_list.html";
//	}
//
//
//	@RequiresPermissions("weixin:v_edit")
//	public String add(HttpServletRequest request, ModelMap model) {
//		return "weixin/ADD";
//	}
//
//	@RequiresPermissions("weixin:o_update")
//	@RequestMapping("/weixin/o_save.html")
//	public String save(Weixin bean,String wxAppkey,String wxAppSecret,HttpServletRequest request, ModelMap model) {
//		Site site=CmsUtils.getSite(request);
//		bean.setSite(site);
//		Map<String,String>wxMap=new HashMap<String,String>();
//		wxMap.put(Constants.WEIXIN_APPKEY, wxAppkey);
//		wxMap.put(Constants.WEIXIN_APPSECRET, wxAppSecret);
//		siteMng.updateAttr(site.getId(), wxMap);
//		manager.save(bean);
//		return edit(request, model);
//	}
//
//	@RequiresPermissions("weixin:v_edit")
//	@RequestMapping("/weixin/v_edit.html")
//	public String edit(HttpServletRequest request, ModelMap model) {
//		Weixin entity = manager.find(CmsUtils.getSiteId(request));
//		if(entity!=null){
//			model.addAttribute("site",CmsUtils.getSite(request));
//			model.addAttribute("weixin",entity);
//			return "weixin/EDIT";
//		}else{
//			return add(request, model);
//		}
//	}
//
//	@RequiresPermissions("weixin:o_update")
//	@RequestMapping("/weixin/o_update.html")
//	public String update(Weixin bean,String wxAppkey,String wxAppSecret,
//			HttpServletRequest request, ModelMap model) {
//		Site site=CmsUtils.getSite(request);
//		Map<String,String>wxMap=new HashMap<String,String>();
//		if(!StringUtils.isBlank(wxAppkey)){
//			wxMap.put(Constants.WEIXIN_APPKEY, wxAppkey);
//		}
//		if(!StringUtils.isBlank(wxAppSecret)){
//			wxMap.put(Constants.WEIXIN_APPSECRET, wxAppSecret);
//		}
//		siteMng.updateAttr(site.getId(), wxMap);
//		manager.update(bean);
//		return edit(request, model);
//	}
//
//	private WebErrors validateCheck(Integer[] ids, HttpServletRequest request) {
//		WebErrors errors = WebErrors.create(request);
//		Site site = CmsUtils.getSite(request);
//		errors.ifEmpty(ids, "ids", true);
//		for (Integer id : ids) {
//			vldExist(id, site.getId(), errors);
//		}
//		return errors;
//	}
//
//	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
//		if (errors.ifNull(id, "id", true)) {
//			return true;
//		}
//		Content entity = contentMng.findById(id);
//		if (errors.ifNotExist(entity, Content.class, id, true)) {
//			return true;
//		}
//		if (!entity.getSite().getId().equals(siteId)) {
//			errors.notInSite(Content.class, id);
//			return true;
//		}
//		return false;
//	}
//
//	@Autowired
//	private WeixinMng manager;
//	@Autowired
//	private WeiXinSvc weiXinSvc;
//	@Autowired
//	private ContentMng contentMng;
//	@Autowired
//	private CmsSiteMng siteMng;
//}
