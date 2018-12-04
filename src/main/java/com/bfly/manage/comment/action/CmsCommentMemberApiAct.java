package com.bfly.manage.comment.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfly.core.web.WebErrors;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.comment.entity.CmsComment;
import com.bfly.cms.channel.entity.ChannelExt;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.comment.service.CmsCommentMng;
import com.bfly.cms.content.service.ContentMng;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.core.web.util.CmsUtils;

@Controller
@RequestMapping(value = "/api/member")
public class CmsCommentMemberApiAct {
	/**
	 * 发布评论
	 * @param contentId  内容ID  必选
	 * @param parentId   父评论ID 非必选
	 * @param score      评分   非必须
	 * @param text       评论内容 必选
	 */
	@RequestMapping(value = "/comment/save")
	public void commentSave(
			Integer contentId, 
			Integer parentId,Integer score,String text,
			HttpServletRequest request,HttpServletResponse response) 
					throws JSONException {
		String body="\"\"";
		String message=Constants.API_MESSAGE_PARAM_REQUIRED;
		String code=ResponseCode.API_CODE_PARAM_REQUIRED;
		WebErrors errors= WebErrors.create(request);
		CmsUser user = null;
		//验证公共非空参数
		errors=ApiValidate.validateRequiredParams(request,errors, contentId,text);
		if(!errors.hasErrors()){
			Content content=contentMng.findById(contentId);
			if(content!=null){
				if(content.getChannel().getCommentControl() == ChannelExt.COMMENT_OFF){
					// 评论关闭
					message="\"comment off\"";
				}else if ((content.getChannel().getCommentControl() == ChannelExt.COMMENT_LOGIN|content.getChannel().getCommentControl() == ChannelExt.COMMENT_LOGIN_MANY)
						&& user == null){
					// 需要登录才能评论
					message="\"comment need login\"";
				}else if(content.getChannel().getCommentControl() == ChannelExt.COMMENT_LOGIN&&user!=null){
					if (hasCommented(user, content)) {
						// 已经评论过，不能重复评论
						message="\"has commented\"";
					}
				}else{
					short checked = 0;
					Integer userId = null;
					if (user != null) {
						if (!user.getGroup().getNeedCheck()) {
							checked = 1;
						}
						userId = user.getId();
					}
					CmsComment comment=cmsCommentMng.comment(score,text, RequestUtils.getIpAddr(request),
							contentId,
							userId, checked, false,parentId);
					body="{\"id\":"+"\""+comment.getId()+"\"}";
					message=Constants.API_MESSAGE_SUCCESS;
				}
			}else{
				message=Constants.API_MESSAGE_CONTENT_NOT_FOUND;
			}
		}
		ApiResponse apiResponse=new ApiResponse(request, body, message,code);
		ResponseUtils.renderApiJson(response, request, apiResponse);
	}
	
	/**
	 * 我的评论
	 * @param siteId 站点ID 非必须
	 * @param first 开始
	 * @param count 数量
	 */
	@RequestMapping(value = "/comment/mylist")
	public void myCommentList(Integer siteId,
			Integer first,Integer count,
			HttpServletRequest request,HttpServletResponse response) 
					throws JSONException {
		if (siteId == null) {
			siteId = CmsUtils.getSiteId(request);
		}
		if(first==null){
			first=0;
		}
		if(count==null){
			count=10;
		}
		CmsUser user = CmsUtils.getUser(request);
		List<CmsComment> list = null ;
		if(user!=null){
			list= cmsCommentMng.getListForMember( null,
					user.getId(), null, null, null, null, true,first,
					count);
		}
		JSONArray jsonArray=new JSONArray();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				jsonArray.put(i, list.get(i).convertToJson());
			}
		}
		String body= jsonArray.toString();
		String message=Constants.API_MESSAGE_SUCCESS;
		String code = ResponseCode.API_CODE_CALL_SUCCESS;
		ApiResponse apiResponse=new ApiResponse(request, body, message,code);
		ResponseUtils.renderApiJson(response, request, apiResponse);
	}
	
	private boolean hasCommented(CmsUser user, Content content) {
		if (content.hasCommentUser(user)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Autowired
	protected CmsCommentMng cmsCommentMng;
	@Autowired
	private ContentMng contentMng;
}
