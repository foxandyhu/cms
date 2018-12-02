package com.bfly.web.friendlink.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bfly.core.web.ApiResponse;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.friendlink.entity.CmsFriendlink;
import com.bfly.cms.friendlink.entity.CmsFriendlinkCtg;
import com.bfly.cms.friendlink.service.CmsFriendlinkCtgMng;
import com.bfly.cms.friendlink.service.CmsFriendlinkMng;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.web.util.CmsUtils;

@Controller
@RequestMapping(value = "/api/front")
public class CmsFriendlinkApiAct {
	
	/**
	 * @param siteId 站点ID  非必选
	 * @param ctgId 分类ID  非必选
	 * @param enabled 是否启用  非必选 默认是筛选启用
	 */
	@RequestMapping(value = "/friendlink/list")
	public void friendlinkList(Integer siteId,
			Integer ctgId,Boolean enabled,
			HttpServletRequest request,HttpServletResponse response) 
					throws JSONException {
		if(siteId==null){
			siteId=CmsUtils.getSiteId(request);
		}
		if (enabled == null) {
			enabled = true;
		}
		List<CmsFriendlink> list = cmsFriendlinkMng.getList(siteId, ctgId,
				enabled);
		JSONArray jsonArray=new JSONArray();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				jsonArray.put(i, list.get(i).convertToJson());
			}
		}
		String body = jsonArray.toString();
		String message = Constants.API_MESSAGE_SUCCESS;
		String code = ResponseCode.API_CODE_CALL_SUCCESS;
		ApiResponse apiResponse = new ApiResponse(request, body, message, code);
		ResponseUtils.renderApiJson(response, request, apiResponse);
	}
	
	/**
	 * 友情链接分类API
	 * @param siteId 站点ID 非必选
	 */
	@RequestMapping(value = "/friendlinkctg/list")
	public void friendlinkCtgList(Integer siteId,
			HttpServletRequest request,HttpServletResponse response) 
					throws JSONException {
		if(siteId==null){
			siteId=CmsUtils.getSiteId(request);
		}
		List<CmsFriendlinkCtg> list = cmsFriendlinkCtgMng.getList(siteId);
		JSONArray jsonArray=new JSONArray();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				jsonArray.put(i, list.get(i).convertToJson());
			}
		}
		String body = jsonArray.toString();
		String message = Constants.API_MESSAGE_SUCCESS;
		String code = ResponseCode.API_CODE_CALL_SUCCESS;
		ApiResponse apiResponse = new ApiResponse(request, body, message, code);
		ResponseUtils.renderApiJson(response, request, apiResponse);
	}
	
	@Autowired
	private CmsFriendlinkCtgMng cmsFriendlinkCtgMng;
	@Autowired
	private CmsFriendlinkMng cmsFriendlinkMng;
}

