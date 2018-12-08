//package com.bfly.web.dictionary.action;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.StringUtils;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.bfly.core.web.ApiResponse;
//import com.bfly.core.Constants;
//import com.bfly.core.web.ResponseCode;
//import com.bfly.common.web.ResponseUtils;
//import com.bfly.cms.dictionary.entity.Dictionary;
//import com.bfly.cms.dictionary.service.CmsDictionaryMng;
//
//@Controller
//@RequestMapping(value = "/api/front")
//public class DictionaryApiAct {
//
//	/**
//	 * 字典列表API
//	 * @param type 分类名 必选
//	 */
//	@RequestMapping(value = "/dictionary/list")
//	public void dictionaryList(String type,
//			HttpServletRequest request,HttpServletResponse response)
//					throws JSONException {
//		String body="\"\"";
//		String message=Constants.API_MESSAGE_PARAM_REQUIRED;
//		String code=ResponseCode.API_CODE_PARAM_REQUIRED;
//		if(StringUtils.isNotBlank(type)){
//			List<Dictionary> list;
//			list =manager.getList(type);
//			JSONArray jsonArray=new JSONArray();
//			if(list!=null&&list.size()>0){
//				for(int i=0;i<list.size();i++){
//					jsonArray.put(i, list.get(i).convertToJson());
//				}
//			}
//			body= jsonArray.toString();
//			message=Constants.API_MESSAGE_SUCCESS;
//			code = ResponseCode.API_CODE_CALL_SUCCESS;
//		}
//		ApiResponse apiResponse=new ApiResponse(request, body, message,code);
//		ResponseUtils.renderApiJson(response, request, apiResponse);
//	}
//
//	@Autowired
//	private CmsDictionaryMng manager;
//}
//