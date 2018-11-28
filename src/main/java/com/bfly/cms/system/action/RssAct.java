package com.bfly.cms.system.action;

import static com.bfly.core.Constants.TPLDIR_SPECIAL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.core.web.util.CmsUtils;
import com.bfly.core.web.util.FrontUtils;

@Controller
public class RssAct {
	public static final String RSS_TPL = "tpl.rss";

	@RequestMapping(value = "/rss.html", method = RequestMethod.GET)
	public String rss(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		response.setContentType("text/xml;charset=UTF-8");
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, RSS_TPL);
	}
}
