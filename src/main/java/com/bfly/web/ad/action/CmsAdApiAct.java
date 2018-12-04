package com.bfly.web.ad.action;

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
import com.bfly.cms.ad.entity.CmsAdvertising;
import com.bfly.cms.ad.entity.CmsAdvertisingSpace;
import com.bfly.cms.ad.service.CmsAdvertisingMng;
import com.bfly.cms.ad.service.CmsAdvertisingSpaceMng;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.web.util.CmsUtils;

@Controller
@RequestMapping(value = "/api/front")
public class CmsAdApiAct {
	
	/**
	 * @param enabled 是否启用
	 */
	@RequestMapping(value = "/ad/list")
	public void adList(Integer adspaceId,Boolean enabled, HttpServletRequest request,HttpServletResponse response) throws JSONException {
		if (enabled == null) {
			enabled = true;
		}
		List<CmsAdvertising> list = advertisingMng.getList(adspaceId, enabled);
		JSONArray jsonArray=new JSONArray();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				jsonArray.put(i, list.get(i).convertToJson());
			}
		}
		String body = jsonArray.toString();
		String code = ResponseCode.API_CODE_CALL_SUCCESS;
		String message = Constants.API_MESSAGE_SUCCESS;
		ApiResponse apiResponse = new ApiResponse(request, body, message, code);
		ResponseUtils.renderApiJson(response, request, apiResponse);
	}
	
	/**
	 * 获取广告信息
	 * @param id 广告ID
	 */
	@RequestMapping(value = "/ad/get")
	public void adGet(Integer id,
			HttpServletRequest request,HttpServletResponse response) 
					throws JSONException {
		String body = "\"\"";
		String message = Constants.API_MESSAGE_PARAM_REQUIRED;
		String code = ResponseCode.API_CODE_PARAM_REQUIRED;
		CmsAdvertising bean = null;
		if (id!=null) {
			if (id.equals(0)) {
				bean = new CmsAdvertising();
			}else{
				bean = advertisingMng.findById(id);
			}
			if (bean==null) {
				message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
				code = ResponseCode.API_CODE_NOT_FOUND;
			}else{
				body = bean.convertToJson().toString();
				message = Constants.API_MESSAGE_SUCCESS;
				code = ResponseCode.API_CODE_CALL_SUCCESS;
			}
		}
		ApiResponse apiResponse = new ApiResponse(request, body, message, code);
		ResponseUtils.renderApiJson(response, request, apiResponse);
	}
	
	/**
	 * 广告版块
	 * @param siteId 站点ID
	 */
	@RequestMapping(value = "/adctg/list")
	public void adCtgList(Integer siteId,
			HttpServletRequest request,HttpServletResponse response) 
					throws JSONException {
		if(siteId==null){
			siteId=CmsUtils.getSiteId(request);
		}
		List<CmsAdvertisingSpace> list = advertisingSpaceMng.getList();
		JSONArray jsonArray=new JSONArray();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				jsonArray.put(i, list.get(i).convertToJson());
			}
		}
		String body = jsonArray.toString();
		String message =Constants.API_MESSAGE_SUCCESS;
		String code = ResponseCode.API_CODE_CALL_SUCCESS;
		ApiResponse apiResponse = new ApiResponse(request, body, message, code);
		ResponseUtils.renderApiJson(response, request, apiResponse);
	}
	
	@Autowired
	private CmsAdvertisingSpaceMng advertisingSpaceMng;
	@Autowired
	private CmsAdvertisingMng advertisingMng;
}

