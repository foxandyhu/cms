package com.bfly.cms.api.member;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfly.core.web.WebErrors;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bfly.cms.api.ResponseCode;
import com.bfly.cms.annotation.SignValidate;
import com.bfly.cms.api.ApiResponse;
import com.bfly.cms.api.ApiValidate;
import com.bfly.cms.api.Constants;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.entity.CmsUser;
import com.bfly.core.entity.CmsUserExt;
import com.bfly.core.manager.CmsUserExtMng;
import com.bfly.core.manager.CmsUserMng;
import com.bfly.core.web.util.CmsUtils;

@Controller
public class PersonalApiAct {
	
	@SignValidate
	@RequestMapping("/personal/update")
	public void profileUpdate(String origPwd,String newPwd,
			String email,String realname,
			HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String body="\"\"";
		String message = Constants.API_MESSAGE_PARAM_REQUIRED;
		String code = ResponseCode.API_CODE_PARAM_REQUIRED;
		CmsUser user = CmsUtils.getUser(request);
		WebErrors errors = validatePasswordSubmit(user.getId(), origPwd,
				newPwd, email, realname,request);
		if (errors.hasErrors()) {
			message=errors.getErrors().get(0).toString();
			code = ResponseCode.API_CODE_PARAM_ERROR;
		}else{
			CmsUserExt ext = user.getUserExt();
			if (ext == null) {
				ext = new CmsUserExt();
			}
			ext.setRealname(realname);
			cmsUserExtMng.update(ext, user);
			cmsUserMng.updatePwdEmail(user.getId(), newPwd, email);
			message=Constants.API_MESSAGE_SUCCESS;
			code = ResponseCode.API_CODE_CALL_SUCCESS;
		}
		ApiResponse apiResponse=new ApiResponse(request, body, message,code);
		ResponseUtils.renderApiJson(response, request, apiResponse);
	}
	
	@SignValidate
	@RequestMapping("/personal/check_pwd")
	public void checkPwd(String origPwd, HttpServletRequest request,
			HttpServletResponse response) {
		String body="\"\"";
		String message = Constants.API_MESSAGE_PARAM_REQUIRED;
		String code = ResponseCode.API_CODE_PARAM_REQUIRED;
		WebErrors errors= WebErrors.create(request);
		//验证公共非空参数
		errors=ApiValidate.validateRequiredParams(request,errors, origPwd);
		if(!errors.hasErrors()){
			CmsUser user = CmsUtils.getUser(request);
			Boolean pass = cmsUserMng.isPasswordValid(user.getId(), origPwd);
			JSONObject json=new JSONObject();
			json.put("pass", pass);
			body=json.toString();
			message=Constants.API_MESSAGE_SUCCESS;
			code = ResponseCode.API_CODE_CALL_SUCCESS;
		}
		ApiResponse apiResponse=new ApiResponse(request, body, message,code);
		ResponseUtils.renderApiJson(response, request, apiResponse);
	}
	
	private WebErrors validatePasswordSubmit(Integer id, String origPwd,
											 String newPwd, String email, String realname,
											 HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifBlank(origPwd, "origPwd", 32, true)) {
			return errors;
		}
		if (errors.ifMaxLength(newPwd, "newPwd", 32, true)) {
			return errors;
		}
		if (errors.ifMaxLength(email, "email", 100, true)) {
			return errors;
		}
		if (errors.ifMaxLength(realname, "realname", 100, true)) {
			return errors;
		}
		if (!cmsUserMng.isPasswordValid(id, origPwd)) {
			errors.addErrorString("member.origPwdInvalid");
			return errors;
		}
		return errors;
	}


	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private CmsUserExtMng cmsUserExtMng;
	
}
