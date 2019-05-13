//package com.bfly.web.statistic.action;
//
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.commons.lang.StringUtils;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.bfly.cms.statistic.service.CmsSiteFlowCache;
//import com.bfly.common.web.ResponseUtils;
//
//@Controller
//public class CmsSiteFlowAct {
//	@RequestMapping("/flow_statistic.html")
//	public void flowStatistic(HttpServletRequest request,
//			HttpServletResponse response, String page) throws JSONException {
//		Long[] counts = null;
//		String referer=request.getParameter("referer");
//		if (!StringUtils.isBlank(page)) {
//			counts=cmsSiteFlowCache.flow(request, page, referer);
//		}
//		String json;
//		if (counts != null) {
//			json = new JSONArray(counts).toString();
//			ResponseUtils.renderJson(response, json);
//		} else {
//			ResponseUtils.renderJson(response, "[]");
//		}
//	}
//
//	@Autowired
//	private CmsSiteFlowCache cmsSiteFlowCache;
//}
