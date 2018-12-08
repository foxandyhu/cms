//package com.bfly.web.content.action;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.bfly.cms.content.entity.Topic;
//import com.bfly.cms.siteconfig.entity.Site;
//import org.apache.commons.lang.StringUtils;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.bfly.core.web.ApiResponse;
//import com.bfly.core.Constants;
//import com.bfly.core.web.ResponseCode;
//import com.bfly.cms.content.service.CmsTopicMng;
//import com.bfly.common.web.ResponseUtils;
//import com.bfly.cms.siteconfig.entity.Ftp;
//import com.bfly.core.web.util.CmsUtils;
//
//@Controller("frontTopicApiAct")
//@RequestMapping(value = "/api/front")
//public class TopicApiAct {
//
//	/**
//	 * 专题列表API
//	 * @param channelId 栏目ID
//	 * @param recommend 是否推荐 true推荐 false全部  非必选 默认false
//	 * @param first 第几条开始 默认0
//	 * @param count 数量 默认10
//	 */
//	@RequestMapping(value = "/topic/list")
//	public void topicList(Integer https,Integer channelId,
//			Boolean recommend,Integer first,Integer count,
//			HttpServletRequest request,HttpServletResponse response)
//					throws JSONException {
//		if(first==null){
//			first=0;
//		}
//		if(count==null){
//			count=10;
//		}
//		if(recommend==null){
//			recommend=false;
//		}
//		if(https==null){
//			https=Constants.URL_HTTP;
//		}
//		Site site=CmsUtils.getSite(request);
//		List<Topic> list;
//		list =topicMng.getListForTag(channelId, recommend,first,count);
//		JSONArray jsonArray=new JSONArray();
//		if(list!=null&&list.size()>0){
//			for(int i=0;i<list.size();i++){
//				jsonArray.put(i, convertToJson(site,list.get(i),https));
//			}
//		}
//		String body = jsonArray.toString();
//		String message = Constants.API_MESSAGE_SUCCESS;
//		String code = ResponseCode.API_CODE_CALL_SUCCESS;
//		ApiResponse apiResponse = new ApiResponse(request, body, message, code);
//		ResponseUtils.renderApiJson(response, request, apiResponse);
//	}
//
//	/**
//	 * 专题信息获取
//	 * @param id 专题id 必选
//	 */
//	@RequestMapping(value = "/topic/get")
//	public void topicGet(Integer https,
//			Integer id,HttpServletRequest request,
//			HttpServletResponse response) throws JSONException {
//		String body = "\"\"";
//		String message = Constants.API_MESSAGE_PARAM_REQUIRED;
//		String code = ResponseCode.API_CODE_PARAM_REQUIRED;
//		Topic topic = null;
//		Site site=CmsUtils.getSite(request);
//		if(https==null){
//			https=Constants.URL_HTTP;
//		}
//		if (id!=null) {
//			topic = topicMng.findById(id);
//			if (topic==null) {
//				message= Constants.API_MESSAGE_OBJECT_NOT_FOUND;
//				code = ResponseCode.API_CODE_NOT_FOUND;
//			}else{
//				body = convertToJson(site, topic, https).toString();
//				message = Constants.API_MESSAGE_SUCCESS;
//				code = ResponseCode.API_CODE_CALL_SUCCESS;
//			}
//		}
//		ApiResponse apiResponse = new ApiResponse(request, body, message, code);
//		ResponseUtils.renderApiJson(response, request, apiResponse);
//	}
//
//	private JSONObject convertToJson(Site site, Topic topic
//			, Integer https) throws JSONException{
//		JSONObject json=new JSONObject();
//		Ftp uploadFtp=site.getUploadFtp();
//		boolean uploadToFtp=false;
//		if(uploadFtp!=null){
//			uploadToFtp=true;
//		}
//		json.put("id", topic.getId());
//		json.put("name", topic.getName());
//		json.put("shortName", topic.getShortName());
//		json.put("description", topic.getDescription());
//
//		json.put("priority", topic.getPriority());
//		json.put("recommend", topic.getRecommend());
//		String urlPrefix="";
//		if(https== com.bfly.core.Constants.URL_HTTP){
//			urlPrefix=site.getUrlPrefixWithNoDefaultPort();
//		}else{
//			urlPrefix=site.getSafeUrlPrefix();
//		}
//		if(!uploadToFtp){
//			if(StringUtils.isNotBlank(topic.getTitleImg())){
//				json.put("titleImg", urlPrefix+topic.getTitleImg());
//				json.put("contentImg", urlPrefix+topic.getContentImg());
//			}else{
//				json.put("titleImg", "");
//				json.put("contentImg", "");
//			}
//		}else{
//			if(StringUtils.isNotBlank(topic.getTitleImg())){
//				json.put("titleImg", topic.getTitleImg());
//				json.put("contentImg", topic.getContentImg());
//			}else{
//				json.put("titleImg", "");
//				json.put("contentImg", "");
//			}
//		}
//		return json;
//	}
//	@Autowired
//	private CmsTopicMng topicMng;
//}
//
