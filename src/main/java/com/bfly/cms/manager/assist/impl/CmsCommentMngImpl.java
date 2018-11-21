package com.bfly.cms.manager.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.dao.assist.CmsCommentDao;
import com.bfly.cms.entity.assist.CmsComment;
import com.bfly.cms.entity.assist.CmsCommentExt;
import com.bfly.cms.manager.assist.CmsCommentExtMng;
import com.bfly.cms.manager.assist.CmsCommentMng;
import com.bfly.cms.manager.assist.CmsSensitivityMng;
import com.bfly.cms.manager.main.ContentCountMng;
import com.bfly.cms.manager.main.ContentMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.core.entity.CmsUser;
import com.bfly.core.entity.CmsUserExt;
import com.bfly.core.manager.CmsSiteMng;
import com.bfly.core.manager.CmsUserExtMng;
import com.bfly.core.manager.CmsUserMng;

@Service
@Transactional
public class CmsCommentMngImpl implements CmsCommentMng {
	@Override
    @Transactional(readOnly = true)
	public Pagination getPage(Integer siteId, Integer contentId,
			Integer greaterThen, Short checked, Boolean recommend,
			boolean desc, int pageNo, int pageSize) {
		Pagination page = dao.getPage(siteId, contentId, greaterThen, checked,
				recommend, desc, pageNo, pageSize, false);
		return page;
	}
	
	@Override
	public Pagination getNewPage(Integer siteId, Integer contentId, Short checked, Boolean recommend, int pageNo,
			int pageSize) {
		return dao.getNewPage(siteId, contentId, checked, recommend, pageNo, pageSize, false);
	}

	@Override
    @Transactional(readOnly = true)
	public Pagination getPageForTag(Integer siteId, Integer contentId,
			Integer greaterThen, Short checked, Boolean recommend,
			boolean desc, int pageNo, int pageSize) {
		Pagination page = dao.getPage(siteId, contentId, greaterThen, checked,
				recommend, desc, pageNo, pageSize, true);
		return page;
	}
	
	@Override
    @Transactional(readOnly = true)
	public Pagination getPageForMember(Integer siteId, Integer contentId,Integer toUserId,Integer fromUserId,
			Integer greaterThen, Short checked, Boolean recommend,
			boolean desc, int pageNo, int pageSize){
		Pagination page = dao.getPageForMember(siteId, contentId,toUserId,fromUserId, greaterThen, checked,
				recommend, desc, pageNo, pageSize, false);
		return page;
	}
	
	@Override
    @Transactional(readOnly = true)
	public List<CmsComment> getListForMember(Integer siteId, Integer contentId,
			Integer toUserId,Integer fromUserId,Integer greaterThen,
			Short checked, Boolean recommend,
			boolean desc, Integer first, Integer count){
		return dao.getListForMember(siteId, contentId, toUserId, 
				fromUserId, greaterThen, checked, recommend, 
				desc, first, count, true);
	}
	
	@Override
    @Transactional(readOnly = true)
	public List<CmsComment> getListForDel(Integer siteId, Integer userId,Integer commentUserId,String ip){
		return dao.getListForDel(siteId,userId,commentUserId,ip);
	}

	@Override
    @Transactional(readOnly = true)
	public List<CmsComment> getListForTag(Integer siteId, Integer contentId,
			Integer parentId,Integer greaterThen, Short checked, Boolean recommend,
			boolean desc, Integer first,int count) {
		return dao.getList(siteId, contentId,parentId,greaterThen, checked, recommend,
				desc, first,count, true);
	}

	@Override
    @Transactional(readOnly = true)
	public CmsComment findById(Integer id) {
		CmsComment entity = dao.findById(id);
		return entity;
	}

	@Override
    public CmsComment comment(Integer score, String text, String ip, Integer contentId,
                              Integer siteId, Integer userId, short checked, boolean recommend, Integer parentId) {
		CmsComment comment = new CmsComment();
		comment.setContent(contentMng.findById(contentId));
		comment.setSite(cmsSiteMng.findById(siteId));
		if (userId != null) {
			CmsUser user=cmsUserMng.findById(userId);
			comment.setCommentUser(user);
			//累计今日留言数
			if(user!=null){
				CmsUserExt userExt=user.getUserExt();
				userExt.setTodayCommentTotal(userExt.getTodayCommentTotal()+1);
				userExtMng.update(userExt, user);
			}
		}
		comment.setChecked(checked);
		comment.setRecommend(recommend);
		comment.setScore(score);
		comment.init();
		if(parentId!=null){
			CmsComment parent=findById(parentId);
			if(parent!=null){
				comment.setParent(parent);
				update(parent, parent.getCommentExt());
			}
		}
		dao.save(comment);
		text = cmsSensitivityMng.replaceSensitivity(text);
		cmsCommentExtMng.save(ip, text, comment);
		contentCountMng.commentCount(contentId);
		return comment;
	}

	@Override
    public CmsComment update(CmsComment bean, CmsCommentExt ext) {
		Updater<CmsComment> updater = new Updater<CmsComment>(bean);
		bean = dao.updateByUpdater(updater);
		cmsCommentExtMng.update(ext);
		return bean;
	}

	@Override
    public int deleteByContentId(Integer contentId) {
		cmsCommentExtMng.deleteByContentId(contentId);
		return dao.deleteByContentId(contentId);
	}

	@Override
    public CmsComment deleteById(Integer id) {
		CmsComment bean = dao.deleteById(id);
		CmsComment parent=bean.getParent();
		if(parent!=null&&bean.getChecked()==1){
			if(parent.getReplyCount()!=null&&parent.getReplyCount()>0){
				parent.setReplyCount(parent.getReplyCount()-1);
			}else{
				parent.setReplyCount(0);
			}
			update(parent, parent.getCommentExt());
		}
		bean.getChild().clear();
		return bean;
	}

	@Override
    public CmsComment[] deleteByIds(Integer[] ids) {
		CmsComment[] beans = new CmsComment[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	
	@Override
    public CmsComment[] checkByIds(Integer[] ids, CmsUser user, short checked) {
		CmsComment[] beans = new CmsComment[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = checkById(ids[i],user,checked);
		}
		return beans;
	}
	
	
	private CmsComment checkById(Integer id,CmsUser checkUser,short checked){
		CmsComment bean=findById(id);
		CmsComment parent=bean.getParent();
		if(parent!=null){
			//从未审核/审核不通过到审核审核通过
			if((bean.getChecked()==0||bean.getChecked()==2)&&checked==1){
				if(parent.getReplyCount()!=null&&parent.getReplyCount()>0){
					parent.setReplyCount(parent.getReplyCount()+1);
				}else{
					parent.setReplyCount(1);
				}
			}
			//从审核通过到审核不通过
			if(bean.getChecked()==1&&checked==2){
				if(parent.getReplyCount()!=null&&parent.getReplyCount()>0){
					parent.setReplyCount(parent.getReplyCount()-1);
				}else{
					parent.setReplyCount(0);
				}
			}
			update(parent, parent.getCommentExt());
		}
		Updater<CmsComment> updater = new Updater<CmsComment>(bean);
		bean = dao.updateByUpdater(updater);
		bean.setChecked(checked);
		return bean;
	}
	
	@Override
    public void ups(Integer id) {
		CmsComment comment = findById(id);
		comment.setUps((short) (comment.getUps() + 1));
	}

	@Override
    public void downs(Integer id) {
		CmsComment comment = findById(id);
		comment.setDowns((short) (comment.getDowns() + 1));
	}

	@Autowired
	private CmsSensitivityMng cmsSensitivityMng;
	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private CmsSiteMng cmsSiteMng;
	@Autowired
	private ContentMng contentMng;
	@Autowired
	private ContentCountMng contentCountMng;
	@Autowired
	private CmsCommentExtMng cmsCommentExtMng;
	@Autowired
	private CmsCommentDao dao;
	@Autowired
	private CmsUserExtMng userExtMng;
}