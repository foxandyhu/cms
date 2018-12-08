//package com.bfly.web.weixin.action;
//
//import static com.bfly.common.page.SimplePage.cpn;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.bfly.cms.siteconfig.entity.Site;
//import com.bfly.core.web.WebErrors;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.bfly.cms.weixin.service.WeiXinSvc;
//import com.bfly.common.page.Pagination;
//import com.bfly.common.web.CookieUtils;
//import com.bfly.core.web.util.CmsUtils;
//import com.bfly.cms.weixin.entity.WeixinMenu;
//import com.bfly.cms.weixin.service.WeixinMenuMng;
//
//@Controller
//public class WeixinMenuAct {
//
//	private static final Logger log = LoggerFactory.getLogger(WeixinMenuAct.class);
//
//	@RequiresPermissions("weixinMenu:v_list")
//	@RequestMapping("/weixinMenu/v_list.html")
//	public String list(Integer parentId,Integer pageNo,HttpServletRequest request, ModelMap model) {
//		Site site = CmsUtils.getSite(request);
//		Pagination p = manager.getPage(site.getId(),parentId, cpn(pageNo), CookieUtils.getPageSize(request));
//		if(parentId!=null){
//			WeixinMenu entity = manager.findById(parentId);
//			if(entity.getParent()!=null){
//				model.addAttribute("parentListId", entity.getParent().getId());
//			}
//		}
//
//		model.addAttribute("pagination", p);
//		model.addAttribute("parentId", parentId);
//		model.addAttribute("pageNo", pageNo);
//		return "weixinMenu/list";
//	}
//
//	@RequiresPermissions("weixinMenu:v_add")
//	@RequestMapping("/weixinMenu/v_add.html")
//	public String add(Integer parentId,Integer pageNo,HttpServletRequest request, ModelMap model) {
//		model.addAttribute("parentId", parentId);
//		model.addAttribute("pageNo", pageNo);
//		return "weixinMenu/ADD";
//	}
//
//	@RequiresPermissions("weixinMenu:v_edit")
//	@RequestMapping("/weixinMenu/v_edit.html")
//	public String edit(Integer id,Integer parentId,Integer pageNo,HttpServletRequest request, ModelMap model) {
//		WeixinMenu entity = manager.findById(id);
//		model.addAttribute("parentId", parentId);
//		model.addAttribute("pageNo", pageNo);
//		model.addAttribute("menu",entity);
//		return "weixinMenu/EDIT";
//	}
//
//	@RequiresPermissions("weixinMenu:o_save")
//	@RequestMapping("/weixinMenu/o_save.html")
//	public String save(WeixinMenu bean,Integer parentId,Integer pageNo,HttpServletRequest request, ModelMap model) {
//		Site site = CmsUtils.getSite(request);
//		bean.setSite(site);
//		if(parentId!=null){
//			bean.setParent(manager.findById(parentId));
//		}
//		manager.save(bean);
//		return list(parentId, pageNo, request, model);
//	}
//
//	@RequiresPermissions("weixinMenu:o_update")
//	@RequestMapping("/weixinMenu/o_update.html")
//	public String update(WeixinMenu bean,Integer parentId,Integer pageNo,HttpServletRequest request, ModelMap model) {
//		manager.update(bean);
//		return list(parentId, pageNo, request, model);
//	}
//
//	@RequiresPermissions("weixinMenu:o_menu")
//	@RequestMapping("/weixinMenu/o_menu.html")
//	public String menu(WeixinMenu bean,Integer parentId,
//			Integer pageNo,HttpServletRequest request, ModelMap model) {
//		Site site = CmsUtils.getSite(request);
//		weixinSvcMng.createMenu(manager.getMenuJsonString(site.getId()));
//		return list(parentId, pageNo, request, model);
//	}
//
//	@RequiresPermissions("weixinMenu:o_delete")
//	@RequestMapping("/weixinMenu/o_delete.html")
//	public String delete(Integer[] ids,Integer parentId,Integer pageNo,HttpServletRequest request, ModelMap model) {
//		WebErrors errors = validateDelete(ids, request);
//		if (errors.hasErrors()) {
//			return errors.showErrorPage(model);
//		}
//		WeixinMenu[] beans = manager.deleteByIds(ids);
//		for (WeixinMenu bean : beans) {
//			log.info("delete Brief id={}", bean.getId());
//		}
//		return list(parentId, pageNo, request, model);
//	}
//
//	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
//		WebErrors errors = WebErrors.create(request);
//		if (errors.ifEmpty(ids, "ids", true)) {
//			return errors;
//		}
//		for (Integer id : ids) {
//			vldExist(id, errors);
//		}
//		return errors;
//	}
//
//	private boolean vldExist(Integer id, WebErrors errors) {
//		if (errors.ifNull(id, "id", true)) {
//			return true;
//		}
//		WeixinMenu entity = manager.findById(id);
//		if (errors.ifNotExist(entity, WeixinMenu.class, id, true)) {
//			return true;
//		}
//		return false;
//	}
//
//
//
//	@Autowired
//	private WeixinMenuMng manager;
//	@Autowired
//	private WeiXinSvc weixinSvcMng;
//}