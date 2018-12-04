package com.bfly.cms.content.service.impl;

import static com.bfly.cms.content.entity.ContentCheck.DRAFT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bfly.cms.user.entity.CmsAdmin;
import com.bfly.cms.user.service.CmsAdminService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bfly.cms.content.dao.ContentDao;
import com.bfly.cms.resource.entity.CmsFile;
import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.channel.entity.Channel.AfterCheckEnum;
import com.bfly.cms.content.entity.CmsTopic;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.entity.Content.CheckResultStatus;
import com.bfly.cms.content.entity.Content.ContentStatus;
import com.bfly.cms.content.entity.ContentCharge;
import com.bfly.cms.content.entity.ContentCheck;
import com.bfly.cms.content.entity.ContentCount;
import com.bfly.cms.content.entity.ContentExt;
import com.bfly.cms.content.entity.ContentRecord.ContentOperateType;
import com.bfly.cms.words.entity.ContentTag;
import com.bfly.cms.content.entity.ContentTxt;
import com.bfly.cms.comment.service.CmsCommentMng;
import com.bfly.cms.resource.service.CmsFileMng;
import com.bfly.cms.channel.service.ChannelCountMng;
import com.bfly.cms.channel.service.ChannelMng;
import com.bfly.cms.content.service.CmsTopicMng;
import com.bfly.cms.content.service.ContentChargeMng;
import com.bfly.cms.content.service.ContentCheckMng;
import com.bfly.cms.content.service.ContentCountMng;
import com.bfly.cms.content.service.ContentExtMng;
import com.bfly.cms.content.service.ContentMng;
import com.bfly.cms.content.service.ContentRecordMng;
import com.bfly.cms.words.service.ContentTagMng;
import com.bfly.cms.content.service.ContentTxtMng;
import com.bfly.cms.content.service.ContentTypeMng;
import com.bfly.cms.content.service.ContentListener;
import com.bfly.cms.staticpage.service.StaticPageSvc;
import com.bfly.core.exception.ContentNotCheckedException;
import com.bfly.core.exception.GeneratedZeroStaticPageException;
import com.bfly.core.exception.StaticPageNotOpenException;
import com.bfly.core.exception.TemplateNotFoundException;
import com.bfly.core.exception.TemplateParseException;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.user.entity.CmsGroup;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.entity.CmsUserSite;
import com.bfly.cms.user.service.CmsGroupMng;
import com.bfly.cms.user.service.CmsUserMng;

import freemarker.template.TemplateException;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:32
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ContentMngImpl implements ContentMng{

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public Pagination getPageByRight(Integer share,String title, Integer typeId,
			Integer currUserId,Integer inputUserId, boolean topLevel, boolean recommend,
			ContentStatus status, Byte checkStep,
			Integer channelId,Integer userId, int orderBy, int pageNo,
			int pageSize) {
		CmsAdmin admin = adminService.findById(userId);
		CmsUserSite us = admin.getUserSite();
		Pagination p;
		
		p = this.getPageCountByRight(share, title, typeId, currUserId, inputUserId, topLevel, recommend, status, checkStep, channelId, userId, orderBy, pageNo, pageSize);
		pageNo = p.getPageNo();
		
		boolean allChannel = us.getAllChannel();
		boolean selfData = admin.getSelfAdmin();
		if (allChannel && selfData) {
			// 拥有所有栏目权限，只能管理自己的数据
			p = dao.getPageBySelf(share,title, typeId, inputUserId, topLevel,
					recommend, status, checkStep,  channelId, userId,
					orderBy, pageNo, pageSize);
		} else if (allChannel && !selfData) {
			// 拥有所有栏目权限，能够管理不属于自己的数据
			p = dao.getPage(share,title, typeId,currUserId, inputUserId, topLevel, recommend,
					status, checkStep, null,channelId,orderBy, pageNo,
					pageSize);
		} else {
			p = dao.getPageByRight(share,title, typeId, currUserId,inputUserId, topLevel,
					recommend, status, checkStep, channelId,userId, selfData,
					orderBy, pageNo, pageSize);
		}
		return p;
	}
	
	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public Pagination getPageCountByRight(Integer share,String title, Integer typeId,
			Integer currUserId,Integer inputUserId, boolean topLevel, boolean recommend,
			ContentStatus status, Byte checkStep,
			Integer channelId,Integer userId, int orderBy, int pageNo,
			int pageSize) {
		CmsAdmin admin = adminService.findById(userId);
		CmsUserSite us = admin.getUserSite();
		Pagination p;
		boolean allChannel = us.getAllChannel();
		boolean selfData = admin.getSelfAdmin();
		if (allChannel && selfData) {
			// 拥有所有栏目权限，只能管理自己的数据
			p = dao.getPageCountBySelf(share,title, typeId, inputUserId, topLevel,
					recommend, status, checkStep,  channelId, userId,
					orderBy, pageNo, pageSize);
		} else if (allChannel && !selfData) {
			// 拥有所有栏目权限，能够管理不属于自己的数据
			p = dao.getPageCount(share,title, typeId,currUserId, inputUserId, topLevel, recommend,
					status, checkStep, null,channelId,orderBy, pageNo,
					pageSize);
		} else {
			p = dao.getPageCountByRight(share,title, typeId, currUserId,inputUserId, topLevel,
					recommend, status, checkStep,  channelId,userId, selfData,
					orderBy, pageNo, pageSize);
		}
		return p;
	}
	
	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public Pagination getPageBySite(String title, Integer typeId,Integer currUserId,Integer inputUserId,boolean topLevel,
			boolean recommend,ContentStatus status, int orderBy, int pageNo,int pageSize){
		return dao.getPage(Content.CONTENT_QUERY_NOT_SHARE,
				title, typeId, currUserId, inputUserId, topLevel, recommend, status, null, null, null, orderBy, pageNo, pageSize);
	}
	
	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public Pagination getPageCountBySite(String title, Integer typeId,Integer currUserId,Integer inputUserId,boolean topLevel,
			boolean recommend,ContentStatus status,int orderBy, int pageNo,int pageSize){
		return dao.getPageCount(Content.CONTENT_QUERY_NOT_SHARE,
				title, typeId, currUserId, inputUserId, topLevel, recommend, status, null, null, null, orderBy, pageNo, pageSize);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public Pagination getPageForMember(String title, Integer channelId,Integer modelId, Integer memberId, int pageNo, int pageSize) {
		return dao.getPageForMember(Content.CONTENT_QUERY_NOT_SHARE,
				title, null,memberId,memberId, false, false,ContentStatus.all, null, modelId,  channelId, 0, pageNo,pageSize);
	}
	
	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public List<Content> getListForMember(String title, Integer channelId,
		Integer modelId,Integer memberId, int first, int count){
		return dao.getList(title, null,memberId,memberId, false,
				false,ContentStatus.all, null, modelId,
				channelId, 0, first,count);
	}
	
	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public  List<Content> getExpiredTopLevelContents(byte topLevel,Date expiredDay){
		return dao.getExpiredTopLevelContents(topLevel,expiredDay);
	}
	
	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public  List<Content> getPigeonholeContents(Date pigeonholeDay){
		return dao.getPigeonholeContents(pigeonholeDay);
	}
	
	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public Content getSide(Integer id, Integer channelId,
			boolean next) {
		return dao.getSide(id,  channelId, next, true);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public List<Content> getListByIdsForTag(Integer[] ids, int orderBy) {
		if (ids.length == 1) {
			Content content = findById(ids[0]);
			List<Content> list;
			if (content != null) {
				list = new ArrayList<>(1);
				list.add(content);
			} else {
				list = new ArrayList<>(0);
			}
			return list;
		} else {
			return dao.getListByIdsForTag(ids, orderBy);
		}
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public Pagination getPageBySiteIdsForTag( Integer[] typeIds, Boolean titleImg, Boolean recommend,
			String title,Map<String,String[]>attr,int orderBy, int pageNo, int pageSize) {
		return dao.getPageBySiteIdsForTag( typeIds, titleImg,
				recommend, title, attr,orderBy, pageNo, pageSize);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public List<Content> getListBySiteIdsForTag(Integer[] typeIds, Boolean titleImg, Boolean recommend,
			String title, Map<String,String[]>attr,int orderBy,Integer first, Integer count) {
		return dao.getListBySiteIdsForTag(typeIds, titleImg,
				recommend, title,attr, orderBy, first, count);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public Pagination getPageByChannelIdsForTag(Integer[] channelIds,
			Integer[] typeIds, Boolean titleImg, Boolean recommend,
			String title,Map<String,String[]>attr,int orderBy, int option, int pageNo, int pageSize) {
		return dao.getPageByChannelIdsForTag(channelIds, typeIds, titleImg,
				recommend, title,attr, orderBy, option, pageNo, pageSize);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public List<Content> getListByChannelIdsForTag(Integer[] channelIds,
			Integer[] typeIds, Boolean titleImg, Boolean recommend,
			String title, Map<String,String[]>attr,int orderBy,int option, Integer first,Integer count) {
		return dao.getListByChannelIdsForTag(channelIds, typeIds, titleImg,
				recommend, title,attr, orderBy, option,first, count);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public Pagination getPageByChannelPathsForTag(String[] paths, Integer[] typeIds, Boolean titleImg,
			Boolean recommend, String title,Map<String,String[]>attr,int orderBy, int pageNo, int pageSize) {
		return dao.getPageByChannelPathsForTag(paths,typeIds,
				titleImg, recommend, title,attr, orderBy, pageNo, pageSize);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public List<Content> getListByChannelPathsForTag(String[] paths,
			 Integer[] typeIds, Boolean titleImg,
			Boolean recommend, String title,Map<String,String[]>attr,int orderBy, Integer first, Integer count) {
		return dao.getListByChannelPathsForTag(paths,  typeIds,
				titleImg, recommend, title,attr, orderBy, first, count);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public Pagination getPageByTopicIdForTag(Integer topicId,
			 Integer[] channelIds, Integer[] typeIds,
			Boolean titleImg, Boolean recommend, String title, Map<String,String[]>attr,int orderBy,int pageNo,
			int pageSize) {
		return dao.getPageByTopicIdForTag(topicId, channelIds,
				typeIds, titleImg, recommend, title,attr, orderBy, pageNo, pageSize);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public List<Content> getListByTopicIdForTag(Integer topicId,
			 Integer[] channelIds, Integer[] typeIds,
			Boolean titleImg, Boolean recommend, String title, Map<String,String[]>attr,int orderBy,Integer first,
			Integer count) {
		return dao.getListByTopicIdForTag(topicId,  channelIds,
				typeIds, titleImg, recommend, title,attr, orderBy, first, count);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public Pagination getPageByTagIdsForTag(Integer[] tagIds,
			 Integer[] channelIds, Integer[] typeIds,
			Integer excludeId, Boolean titleImg, Boolean recommend,
			String title, Map<String,String[]>attr,int orderBy,int pageNo, int pageSize) {
		return dao.getPageByTagIdsForTag(tagIds, channelIds, typeIds,
				excludeId, titleImg, recommend, title, attr,orderBy, pageNo,
				pageSize);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public List<Content> getListByTagIdsForTag(Integer[] tagIds,
			 Integer[] channelIds, Integer[] typeIds,
			Integer excludeId, Boolean titleImg, Boolean recommend,
			String title,Map<String,String[]>attr, int orderBy,Integer first, Integer count) {
		return dao.getListByTagIdsForTag(tagIds,  channelIds, typeIds,
				excludeId, titleImg, recommend, title,attr, orderBy, first, count);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public Content findById(Integer id) {
		Content entity = dao.findById(id);
		return entity;
	}

	@Override
    public Content save(Content bean, ContentExt ext, ContentTxt txt, Integer[] channelIds,
                        Integer[] topicIds, Integer[] viewGroupIds, String[] tagArr,
                        String[] attachmentPaths, String[] attachmentNames,
                        String[] attachmentFilenames, String[] picPaths,
                        String[] picDescs, Integer channelId, Integer typeId,
                        Boolean draft, Boolean contribute, Short charge,
                        Double chargeAmount, Boolean rewardPattern,
                        Double rewardRandomMin, Double rewardRandomMax,
                        Double[] rewardFix, CmsAdmin admin,
                        boolean forMember) {
		if(charge==null){
			charge=ContentCharge.MODEL_FREE;
		}
		saveContent(bean, ext, txt,channelId, typeId, draft, contribute,admin,forMember);
		// 保存副栏目
		if (channelIds != null && channelIds.length > 0) {
			for (Integer cid : channelIds) {
				bean.addToChannels(channelMng.findById(cid));
			}
		}
		// 主栏目也作为副栏目一并保存，方便查询，提高效率。
		Channel channel=channelMng.findById(channelId);
		bean.addToChannels(channel);
		// 保存专题
		if (topicIds != null && topicIds.length > 0) {
			for (Integer tid : topicIds) {
				if(tid!=null&&tid!=0){
					bean.addToTopics(cmsTopicMng.findById(tid));
				}
			}
		}
		// 保存浏览会员组
		if (viewGroupIds != null && viewGroupIds.length > 0) {
			for (Integer gid : viewGroupIds) {
				bean.addToGroups(cmsGroupMng.findById(gid));
			}
		}
		// 保存标签
		List<ContentTag> tags = contentTagMng.saveTags(tagArr);
		bean.setTags(tags);
		// 保存附件
		if (attachmentPaths != null && attachmentPaths.length > 0) {
			for (int i = 0, len = attachmentPaths.length; i < len; i++) {
				if (!StringUtils.isBlank(attachmentPaths[i])) {
					bean.addToAttachmemts(attachmentPaths[i],
							attachmentNames[i], attachmentFilenames[i]);
				}
			}
		}
		// 保存图片集
		bean.getPictures().clear();
		if (picPaths != null && picPaths.length > 0) {
			for (int i = 0, len = picPaths.length; i < len; i++) {
				if (!StringUtils.isBlank(picPaths[i])) {
					bean.addToPictures(picPaths[i], picDescs[i]);
				}
			}
		}
		//文章操作记录
		contentRecordMng.record(bean, admin, ContentOperateType.add);
		//栏目内容发布数（未审核通过的也算）
		channelCountMng.afterSaveContent(channel);
		
		contentChargeMng.save(chargeAmount,charge,
				rewardPattern,rewardRandomMin,rewardRandomMax,bean);
		//打赏固定值
		if(rewardPattern!=null&&rewardPattern){
			if (rewardFix != null && rewardFix.length > 0) {
				for (int i = 0, len = rewardFix.length; i < len; i++) {
					if (rewardFix[i]!=null) {
						bean.addToRewardFixs(rewardFix[i]);
					}
				}
			}
		}
		// 执行监听器,数据较多情况下可能静态化等较慢
		//afterSave(bean);
		return bean;
	}
	
	//导入word执行
	@Override
    public Content save(Content bean, ContentExt ext, ContentTxt txt, Integer channelId,
                        Integer typeId, Boolean draft, CmsAdmin admin, boolean forMember){
		saveContent(bean, ext, txt,channelId, typeId, draft, false,admin, forMember);
		// 执行监听器
		//afterSave(bean);
		return bean;
	}
	
	private Content saveContent(Content bean, ContentExt ext, ContentTxt txt,Integer channelId,
			Integer typeId,Boolean draft, Boolean contribute,CmsAdmin admin,boolean forMember){
		Channel channel = channelMng.findById(channelId);
		bean.setChannel(channel);
		bean.setType(contentTypeMng.findById(typeId));
		bean.setAdmin(admin);
		Byte userStep;
		if (forMember) {
			// 会员的审核级别按0处理
			userStep = 0;
		} else {
			userStep = admin.getCheckStep();
		}
		//推荐级别控制
		if(!bean.getRecommend() || bean.getRecommendLevel() < 0){
			bean.setRecommendLevel(new Byte("0"));
		}
		
		// 流程处理
		if(contribute!=null&&contribute){
			bean.setStatus(ContentCheck.CONTRIBUTE);
		}else if (draft != null && draft) {
			// 草稿
			bean.setStatus(ContentCheck.DRAFT);
		} else {
			if (userStep >= bean.getChannel().getFinalStepExtends()) {
				bean.setStatus(ContentCheck.CHECKED);
			} else {
				bean.setStatus(ContentCheck.CHECKING);
			}
		}
		// 是否有标题图
		bean.setHasTitleImg(!StringUtils.isBlank(ext.getTitleImg()));
		bean.init();
		// 执行监听器
		preSave(bean);
		dao.save(bean);
		contentExtMng.save(ext, bean);
		contentTxtMng.save(txt, bean);
		ContentCheck check = new ContentCheck();
		check.setCheckStep(userStep);
		contentCheckMng.save(check, bean);
		contentCountMng.save(new ContentCount(), bean);
		return bean;
	}

	@Override
    public Content update(Content bean, ContentExt ext, ContentTxt txt, String[] tagArr,
                          Integer[] channelIds, Integer[] topicIds, Integer[] viewGroupIds,
                          String[] attachmentPaths, String[] attachmentNames,
                          String[] attachmentFilenames, String[] picPaths,
                          String[] picDescs, Map<String, String> attr, Integer channelId,
                          Integer typeId, Boolean draft, Short charge,
                          Double chargeAmount, Boolean rewardPattern,
                          Double rewardRandomMin, Double rewardRandomMax,
                          Double[] rewardFix, CmsAdmin admin,
                          boolean forMember) {
		Content entity = findById(bean.getId());
		// 执行监听器
		List<Map<String, Object>> mapList = preChange(entity);
		
		// 审核信息修改 
		if(!bean.getRecommend() || bean.getRecommendLevel() < 0){
			bean.setRecommendLevel(new Byte("0"));
		}
		
		// 更新主表
		Updater<Content> updater = new Updater<>(bean);
		bean = dao.updateByUpdater(updater);
		// 审核更新处理，如果站点设置为审核退回，且当前文章审核级别大于管理员审核级别，则将文章审核级别修改成管理员的审核级别。
		Byte userStep;
		if (forMember) {
			// 会员的审核级别按0处理
			userStep = 0;
		} else {
			userStep = admin.getCheckStep();
		}
		AfterCheckEnum after = bean.getChannel().getAfterCheckEnum();
		if (after == AfterCheckEnum.BACK_UPDATE
				&& bean.getCheckStep() > userStep) {
			bean.getContentCheck().setCheckStep(userStep);
			if (bean.getCheckStep() >= bean.getChannel().getFinalStepExtends()) {
				bean.setStatus(ContentCheck.CHECKED);
			} else {
				bean.setStatus(ContentCheck.CHECKING);
			}
		}
		//修改后退回
		if (after == AfterCheckEnum.BACK_UPDATE) {
			reject(bean.getId(), admin, userStep, "");
		}
		// 草稿
		if (draft != null) {
			if (draft) {
				bean.setStatus(DRAFT);
			} else {
				if (bean.getStatus() == DRAFT) {
					if (bean.getCheckStep() >= bean.getChannel()
							.getFinalStepExtends()) {
						bean.setStatus(ContentCheck.CHECKED);
					} else {
						bean.setStatus(ContentCheck.CHECKING);
					}
				}
			}
		}
		// 是否有标题图
		bean.setHasTitleImg(!StringUtils.isBlank(ext.getTitleImg()));
		// 更新栏目
		if (channelId != null) {
			bean.setChannel(channelMng.findById(channelId));
		}
		// 更新类型
		if (typeId != null) {
			bean.setType(contentTypeMng.findById(typeId));
		}
		// 更新扩展表
		contentExtMng.update(ext);
		// 更新文本表
		contentTxtMng.update(txt, bean);
		//收费变更
		contentChargeMng.afterContentUpdate(bean, charge, chargeAmount,
				rewardPattern,rewardRandomMin,rewardRandomMax);
		// 更新属性表
		if (attr != null) {
			Map<String, String> attrOrig = bean.getAttr();
			attrOrig.clear();
			attrOrig.putAll(attr);
		}
		// 更新副栏目表
		Set<Channel> channels = bean.getChannels();
		channels.clear();
		if (channelIds != null && channelIds.length > 0) {
			for (Integer cid : channelIds) {
				channels.add(channelMng.findById(cid));
			}
		}
		channels.add(bean.getChannel());
		// 更新专题表
		Set<CmsTopic> topics = bean.getTopics();
		topics.clear();
		if (topicIds != null && topicIds.length > 0) {
			for (Integer tid : topicIds) {
				if(tid!=null&&tid!=0){
					topics.add(cmsTopicMng.findById(tid));
				}
			}
		}
		// 更新浏览会员组
		Set<CmsGroup> groups = bean.getViewGroups();
		groups.clear();
		if (viewGroupIds != null && viewGroupIds.length > 0) {
			for (Integer gid : viewGroupIds) {
				groups.add(cmsGroupMng.findById(gid));
			}
		}
		// 更新标签
		contentTagMng.updateTags(bean.getTags(), tagArr);
		// 更新附件
		bean.getAttachments().clear();
		if (attachmentPaths != null && attachmentPaths.length > 0) {
			for (int i = 0, len = attachmentPaths.length; i < len; i++) {
				if (!StringUtils.isBlank(attachmentPaths[i])) {
					bean.addToAttachmemts(attachmentPaths[i],
							attachmentNames[i], attachmentFilenames[i]);
				}
			}
		}
		// 更新图片集
		bean.getPictures().clear();
		if (picPaths != null && picPaths.length > 0) {
			for (int i = 0, len = picPaths.length; i < len; i++) {
				if (!StringUtils.isBlank(picPaths[i])) {
					bean.addToPictures(picPaths[i], picDescs[i]);
				}
			}
		}
		contentRecordMng.record(bean, admin, ContentOperateType.edit);
		//打赏固定值
		bean.getRewardFixs().clear();
		if(rewardPattern!=null&&rewardPattern){
			if (rewardFix != null && rewardFix.length > 0) {
				for (int i = 0, len = rewardFix.length; i < len; i++) {
					if (rewardFix[i]!=null) {
						bean.addToRewardFixs(rewardFix[i]);
					}
				}
			}
		}
		return bean;
	}
	
	@Override
    public Content update(Content bean){
		Updater<Content> updater = new Updater<Content>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}
	
	@Override
    public Content update(CmsAdmin admin, Content bean, ContentOperateType operate){
		// 保存操作记录
		contentRecordMng.record(bean, admin, operate);
		return update(bean);
	}
	
	@Override
    public Content updateByChannelIds(Integer contentId, Integer[]channelIds,
                                      Integer operate){
		Content bean=findById(contentId);
		Set<Channel>channels=bean.getChannels();
		if (channelIds != null && channelIds.length > 0) {
		//	channels.clear();
		//	channels.add(bean.getChannel());
			for (Integer cid : channelIds) {
				Channel c=channelMng.findById(cid);
				if(operate!=null&&operate.equals(Content.CONTENT_CHANNEL_DEL)){
					channels.remove(c);
				}else{
					channels.add(c);
				}
			}
		}
		return bean;
	}
	
	@Override
    public Content addContentToTopics(Integer contentId, Integer[]topicIds){
		Content bean=findById(contentId);
		Set<CmsTopic>topics=bean.getTopics();
		if (topicIds != null && topicIds.length > 0) {
			for (Integer tid : topicIds) {
				topics.add(cmsTopicMng.findById(tid));
			}
		}
		return bean;
	}
	
	/**
	 * 审核入口 
	 */
	@Override
    public Content check(Integer id, CmsAdmin admin) {
		Content content = findById(id);
		ContentCheck check = content.getContentCheck();
		byte userStep = admin.getCheckStep();
		byte contentStep = check.getCheckStep();
		byte finalStep = content.getChannel().getFinalStepExtends();
		// 用户审核级别小于当前审核级别，则不能审核
		if (userStep < contentStep) {
			content.setCheckResult(CheckResultStatus.nopass);
			return content;
		}
		check.setRejected(false);
		// 上级审核，清除退回意见。自我审核不清除退回意见。
		if (userStep > contentStep) {
			check.setCheckOpinion(null);
		}
		check.setCheckStep(userStep);
		// 终审
		if (userStep >= finalStep) {
			content.setStatus(ContentCheck.CHECKED);
			// 终审，清除退回意见
			check.setCheckOpinion(null);
			//终审，设置审核者
			check.setReviewer(admin);
			check.setCheckDate(Calendar.getInstance().getTime());
		}
		contentRecordMng.record(content, admin, ContentOperateType.check);
		return content;
	}
	
	@Override
    public Content[] check(Integer[] ids, CmsAdmin admin) {
		Content[] beans = new Content[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = check(ids[i], admin);
		}
		return beans;
	}
	
	@Override
    public Content submit(Integer id, CmsAdmin admin){
		Content content = check(id, admin);
		return content;
	}

	@Override
    public Content[] submit(Integer[] ids,CmsAdmin admin){
		Content[] beans = new Content[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = submit(ids[i], admin);
		}
		return beans;
	}

	@Override
    public Content reject(Integer id, CmsAdmin admin, Byte step, String opinion) {
		Content content = findById(id);
		// 执行监听器
		byte userStep = admin.getCheckStep();
		byte contentStep = content.getCheckStep();
		// 用户审核级别小于当前审核级别，则不能退回
		if (userStep < contentStep) {
			return content;
		}
		ContentCheck check = content.getContentCheck();
		if (!StringUtils.isBlank(opinion)) {
			check.setCheckOpinion(opinion);
		}
		check.setRejected(true);
		// 退回稿件一律为未终审
		content.setStatus(ContentCheck.CHECKING);

		if (step != null) {
			// 指定退回级别，不能大于自身级别
			if (step < userStep) {
				check.setCheckStep(step);
			} else {
				check.setCheckStep(userStep);
			}
		} else {
			// 未指定退回级别
			if (contentStep < userStep) {
				// 文档级别小于用户级别，为审核时退回，文档审核级别不修改。
			} else if (contentStep == userStep) {
				// 文档级别等于用户级别，为退回时退回，文档审核级别减一级。
				check.setCheckStep((byte) (check.getCheckStep() - 1));
			}
		}
		contentRecordMng.record(content, admin, ContentOperateType.rejected);
		// 执行监听器
		//afterChange(content, mapList);
		return content;
	}

	@Override
    public Content[] reject(Integer[] ids, CmsAdmin admin, Byte step, String opinion) {
		Content[] beans = new Content[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = reject(ids[i], admin,step, opinion);
		}
		return beans;
	}

	@Override
    public Content cycle(CmsAdmin admin, Integer id) {
		Content content = findById(id);
		// 执行监听器
		//List<Map<String, Object>> mapList = preChange(content);
		content.setStatus(ContentCheck.RECYCLE);
		// 执行监听器
		//afterChange(content, mapList);
		contentRecordMng.record(content, admin, ContentOperateType.cycle);
		return content;
	}

	@Override
    public Content[] cycle( CmsAdmin admin, Integer[] ids) {
		Content[] beans = new Content[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = cycle(admin,ids[i]);
		}
		return beans;
	}

	@Override
    public Content recycle(Integer id) {
		Content content = findById(id);
		// 执行监听器
		//List<Map<String, Object>> mapList = preChange(content);
		byte contentStep = content.getCheckStep();
		byte finalStep = content.getChannel().getFinalStepExtends();
		if (contentStep >= finalStep && !content.getRejected()) {
			content.setStatus(ContentCheck.CHECKED);
		} else {
			content.setStatus(ContentCheck.CHECKING);
		}
		// 执行监听器
		//afterChange(content, mapList);
		return content;
	}

	@Override
    public Content[] recycle(Integer[] ids) {
		Content[] beans = new Content[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = recycle(ids[i]);
		}
		return beans;
	}

	@Override
    public Content deleteById(Integer id) {
		Content bean = findById(id);
		// 执行监听器
		preDelete(bean);
		// 移除tag
		contentTagMng.removeTags(bean.getTags());
		bean.getTags().clear();
		// 删除评论
		cmsCommentMng.deleteByContentId(id);
		//删除附件记录
		fileMng.deleteByContentId(id);
		bean.clear();
		bean = dao.deleteById(id);
		//栏目内容计数（保存+1 真实删除-1）
		channelCountMng.afterDelContent(bean.getChannel());
		// 执行监听器
		//afterDelete(bean);
		return bean;
	}

	@Override
    public Content[] deleteByIds(Integer[] ids) {
		Content[] beans = new Content[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Override
    public Content[] contentStatic(CmsAdmin admin, Integer[] ids) throws TemplateNotFoundException, TemplateParseException, GeneratedZeroStaticPageException, StaticPageNotOpenException, ContentNotCheckedException {
		int count = 0;
		List<Content> list = new ArrayList<>();
		for (int i = 0, len = ids.length; i < len; i++) {
			Content content = findById(ids[i]);
			try {
				if (!content.getChannel().getStaticContent()) {
					throw new StaticPageNotOpenException(
							"content.staticNotOpen", count, content.getTitle());
				}
				if(!content.isChecked()){
					throw new ContentNotCheckedException("content.notChecked", count, content.getTitle());
				}
				if (staticPageSvc.content(content)) {
					list.add(content);
					count++;
				}
			} catch (IOException e) {
				throw new TemplateNotFoundException(
						"content.tplContentNotFound", count, content.getTitle());
			} catch (TemplateException e) {
				throw new TemplateParseException("content.tplContentException",
						count, content.getTitle());
			}
			contentRecordMng.record(content, admin, ContentOperateType.createPage);
		}
		Content[] beans = new Content[count];
		return list.toArray(beans);
	}
	
	@Override
    public Pagination getPageForCollection(Integer memberId, int pageNo, int pageSize){
		return dao.getPageForCollection(memberId,pageNo,pageSize);
	}
	
	@Override
    public List<Content> getListForCollection(Integer memberId, Integer first, Integer count){
		return dao.getListForCollection(memberId,first,count);
	}
	
	@Override
    public void updateFileByContent(Content bean, Boolean valid){
		Set<CmsFile>files;
		Iterator<CmsFile>it;
		CmsFile tempFile;
		//处理附件
		files=bean.getFiles();
		it=files.iterator();
		while(it.hasNext()){
			tempFile=it.next();
			tempFile.setFileIsvalid(valid);
			fileMng.update(tempFile);
		}
	}
	
	@Override
    public String checkForChannelDelete(Integer channelId) {
		int count = dao.countByChannelId(channelId);
		if (count > 0) {
			return "content.error.cannotDeleteChannel";
		} else {
			return null;
		}
	}
	
	private void preSave(Content content) {
		if (listenerList != null) {
			for (ContentListener listener : listenerList) {
				listener.preSave(content);
			}
		}
	}

	private void afterSave(Content content) {
		if (listenerList != null) {
			for (ContentListener listener : listenerList) {
				listener.afterSave(content);
			}
		}
	}

	@Override
    public List<Map<String, Object>> preChange(Content content) {
		if (listenerList != null) {
			int len = listenerList.size();
			List<Map<String, Object>> list = new ArrayList<>(len);
			for (ContentListener listener : listenerList) {
				list.add(listener.preChange(content));
			}
			return list;
		} else {
			return null;
		}
	}

	private void afterChange(Content content, List<Map<String, Object>> mapList) {
		if (listenerList != null) {
			Assert.notNull(mapList);
			Assert.isTrue(mapList.size() == listenerList.size());
			int len = listenerList.size();
			ContentListener listener;
			for (int i = 0; i < len; i++) {
				listener = listenerList.get(i);
				listener.afterChange(content, mapList.get(i));
			}
		}
	}

	private void preDelete(Content content) {
		if (listenerList != null) {
			for (ContentListener listener : listenerList) {
				listener.preDelete(content);
			}
		}
	}

	private void afterDelete(Content content) {
		if (listenerList != null) {
			for (ContentListener listener : listenerList) {
				listener.afterDelete(content);
			}
		}
	}

	private List<ContentListener> listenerList;

	public void setListenerList(List<ContentListener> listenerList) {
		this.listenerList = listenerList;
	}

	@Override
    public List<ContentListener> getListenerList() {
		return listenerList;
	}

	@Autowired
	private ChannelMng channelMng;
	@Autowired
	private ContentExtMng contentExtMng;
	@Autowired
	private ContentTxtMng contentTxtMng;
	@Autowired
	private ContentTypeMng contentTypeMng;
	@Autowired
	private ContentCountMng contentCountMng;
	@Autowired
	private ContentCheckMng contentCheckMng;
	@Autowired
	private ContentTagMng contentTagMng;
	@Autowired
	private CmsGroupMng cmsGroupMng;
	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private CmsTopicMng cmsTopicMng;
	@Autowired
	private CmsCommentMng cmsCommentMng;
	@Autowired
	private ContentDao dao;
	@Autowired
	private StaticPageSvc staticPageSvc;
	@Autowired
	private CmsFileMng fileMng;
	@Autowired
	private ContentRecordMng contentRecordMng;
	@Autowired
	private ChannelCountMng channelCountMng;
	@Autowired
	private ContentChargeMng contentChargeMng;
	@Autowired
	private CmsAdminService adminService;
}