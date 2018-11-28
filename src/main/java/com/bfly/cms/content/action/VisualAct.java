package com.bfly.cms.content.action;

import static com.bfly.core.Constants.TPLDIR_VISUAL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfly.core.web.WebErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.content.entity.CmsTopic;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.words.service.CmsKeywordMng;
import com.bfly.cms.channel.service.ChannelMng;
import com.bfly.cms.content.service.CmsTopicMng;
import com.bfly.cms.content.service.ContentMng;
import com.bfly.common.page.Paginable;
import com.bfly.common.page.SimplePage;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.core.web.util.CmsUtils;
import com.bfly.core.web.util.FrontUtils;

/**
 * @author Tom
 */
/**
 * 调用ajax生成的标签页面
 */
@Controller
public class VisualAct {
	/**
	 * 
	 * @param tempId
	 *            最大支持20
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/visual/getPage{tempId}.html", method = RequestMethod.GET)
	public String loadVisual(@PathVariable Integer tempId, Integer channelId,
			Integer contentId, Integer topicId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		if (tempId < 0 || tempId > 20) {
			WebErrors errors = WebErrors.create(request);
			errors.addErrorCode("error.tempIdOutOfRange");
			return FrontUtils.showError(request, response, model, errors);
		}
		if(channelId!=null){
			Channel channel=channelMng.findById(channelId);
			model.addAttribute("channel",channel );
		}
		if(contentId!=null){
			Content content=contentMng.findById(contentId);
			String txt = content.getTxtByNo(1);
			// 内容加上关键字
			txt = cmsKeywordMng.attachKeyword(site.getId(), txt);
			Paginable pagination = new SimplePage(1, 1, content.getPageCount());
			model.addAttribute("pagination", pagination);
			FrontUtils.frontPageData(request, model);
			model.addAttribute("content", content);
			model.addAttribute("channel", content.getChannel());
			model.addAttribute("title", content.getTitleByNo(1));
			model.addAttribute("txt", txt);
			model.addAttribute("pic", content.getPictureByNo(1));
		}
		if(topicId!=null){
			CmsTopic topic =topicMng.findById(topicId);
			model.addAttribute("topic",topic);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_VISUAL, "tpl.directive" + tempId);
	}
	@Autowired
	private ChannelMng channelMng;
	@Autowired
	private ContentMng contentMng;
	@Autowired
	private CmsTopicMng topicMng;
	@Autowired
	private CmsKeywordMng cmsKeywordMng;
}
