package com.bfly.cms.api.admin.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfly.core.web.WebErrors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bfly.cms.annotation.SignValidate;
import com.bfly.cms.api.ApiResponse;
import com.bfly.cms.api.ApiValidate;
import com.bfly.cms.api.Constants;
import com.bfly.cms.api.ResponseCode;
import com.bfly.cms.entity.assist.CmsConfigContentCharge;
import com.bfly.cms.manager.assist.CmsConfigContentChargeMng;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.entity.CmsConfig;
import com.bfly.core.entity.CmsSite;
import com.bfly.core.manager.CmsConfigMng;
import com.bfly.core.web.util.CmsUtils;

@Controller
public class CmsConfigContentChargeApiAct {
	private static final Logger log = LoggerFactory.getLogger(CmsConfigContentChargeApiAct.class);
	
	@RequestMapping("/config/content_charge_get")
	public void get(HttpServletRequest request,HttpServletResponse response){
		CmsConfigContentCharge bean = manager.getDefault();
		CmsConfig config = cmsConfigMng.get();
		Map<String, String> map = config.getRewardFixAttr();
		JSONArray jsonArray = new JSONArray();
		for(String key:map.keySet()){
			JSONObject json = new JSONObject();
			json.put("key", "attr_"+key);
			json.put("value", map.get(key));
			jsonArray.put(json);
		}
		JSONObject json = bean.convertToJson();
		json.put("fixMap", jsonArray);
		String body = json.toString();
		String message = Constants.API_MESSAGE_SUCCESS;
		String code = ResponseCode.API_CODE_CALL_SUCCESS;
		ApiResponse apiResponse = new ApiResponse(request, body, message, code);
		ResponseUtils.renderApiJson(response, request, apiResponse);
	}
	
	@SignValidate
	@RequestMapping("/config/content_charge_update")
	public void update(CmsConfigContentCharge bean,String weixinPassword,
			String weixinSecret,String alipayKey,String alipayPublicKey,
			String alipayPrivateKey,String transferApiPassword,String payTransferPassword,
			HttpServletRequest request,HttpServletResponse response){
		String body = "\"\"";
		String message = Constants.API_MESSAGE_PARAM_REQUIRED;
		String code = ResponseCode.API_CODE_PARAM_REQUIRED;
		WebErrors errors = WebErrors.create(request);
		errors = ApiValidate.validateRequiredParams(request, errors, bean.getWeixinAppId(),
				bean.getWeixinAccount(),bean.getRewardPattern(),bean.getRewardMin(),bean.getRewardMax(),
				bean.getAlipayAccount(),bean.getChargeRatio(),bean.getMinDrawAmount());
		if (!errors.hasErrors()) {
			errors = validateUpdate(bean.getId(), request);
			if (errors.hasErrors()) {
				message = errors.getErrors().get(0);
				code = ResponseCode.API_CODE_PARAM_ERROR;
			}else{
				Map<String,String>attrs=new HashMap<String,String>();
				attrs.put("weixinPassword", weixinPassword);
				attrs.put("weixinSecret", weixinSecret);
				attrs.put("alipayKey", alipayKey);
				attrs.put("alipayPublicKey", alipayPublicKey);
				attrs.put("alipayPrivateKey", alipayPrivateKey);
				attrs.put("transferApiPassword", transferApiPassword);
				Map<String,String>fixMap=RequestUtils.getRequestMap(request, "attr_");
				bean = manager.update(bean,payTransferPassword,attrs,fixMap);
				log.info("update CmsConfigContentCharge id={}.", bean.getId());
				body = "{\"id\":"+bean.getId()+"}";
				message = Constants.API_MESSAGE_SUCCESS;
				code = ResponseCode.API_CODE_CALL_SUCCESS;
			}
		}
		ApiResponse apiResponse = new ApiResponse(request, body, message, code);
		ResponseUtils.renderApiJson(response, request, apiResponse);
	}
	
	private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		return errors;
	}

	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id", false)) {
			return true;
		}
		CmsConfigContentCharge entity = manager.findById(id);
		if(errors.ifNotExist(entity, CmsConfigContentCharge.class, id, false)) {
			return true;
		}
		return false;
	}
	
	@Autowired
	private CmsConfigMng cmsConfigMng;
	@Autowired
	private CmsConfigContentChargeMng manager;
}
