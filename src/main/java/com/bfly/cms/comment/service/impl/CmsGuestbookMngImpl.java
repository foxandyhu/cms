package com.bfly.cms.comment.service.impl;

import com.bfly.cms.comment.dao.CmsGuestbookDao;
import com.bfly.cms.comment.entity.CmsGuestbook;
import com.bfly.cms.comment.entity.CmsGuestbookExt;
import com.bfly.cms.comment.service.CmsGuestbookCtgMng;
import com.bfly.cms.comment.service.CmsGuestbookExtMng;
import com.bfly.cms.comment.service.CmsGuestbookMng;
import com.bfly.cms.user.entity.CmsAdmin;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.entity.CmsUserExt;
import com.bfly.cms.user.service.CmsUserExtMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * 
 * @author andy_hulibo@163.com
 * @date 2018/12/4 14:46
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsGuestbookMngImpl implements CmsGuestbookMng {

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public Pagination getPage(Integer ctgId,Integer ctgIds[], Integer userId, Boolean recommend,Short checked, boolean desc, boolean cacheable, int pageNo,int pageSize) {
		return dao.getPage(ctgId,ctgIds,userId, recommend, checked, desc, cacheable, pageNo, pageSize);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public List<CmsGuestbook> getList( Integer ctgId, Integer userId,Boolean recommend, Short checked, boolean desc, boolean cacheable, int first, int max) {
		return dao.getList( ctgId,userId, recommend, checked, desc, cacheable, first, max);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public CmsGuestbook findById(Integer id) {
		return dao.findById(id);
	}

	@Override
    public CmsGuestbook save(CmsGuestbook bean, CmsGuestbookExt ext, Integer ctgId, String ip) {
		bean.setCtg(cmsGuestbookCtgMng.findById(ctgId));
		bean.setIp(ip);
		bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
		bean.init();
		dao.save(bean);
		cmsGuestbookExtMng.save(ext, bean);
		return bean;
	}

	@Override
    public CmsGuestbook save(CmsUser member, Integer ctgId, String ip, String title, String content, String email, String phone, String qq) {
		CmsGuestbook guestbook = new CmsGuestbook();
		guestbook.setMember(member);
		guestbook.setIp(ip);
		CmsGuestbookExt ext = new CmsGuestbookExt();
		ext.setTitle(title);
		ext.setContent(content);
		ext.setEmail(email);
		ext.setPhone(phone);
		ext.setQq(qq);
		//累计今日留言数
		if(member!=null){
			CmsUserExt userExt=member.getUserExt();
			userExt.setTodayGuestbookTotal(userExt.getTodayGuestbookTotal()+1);
			userExtMng.update(userExt, member);
		}
		return save(guestbook, ext, ctgId, ip);
	}

	@Override
    public CmsGuestbook update(CmsGuestbook bean, CmsGuestbookExt ext, Integer ctgId) {
		Updater<CmsGuestbook> updater = new Updater<>(bean);
		bean = dao.updateByUpdater(updater);
		bean.setCtg(cmsGuestbookCtgMng.findById(ctgId));
		cmsGuestbookExtMng.update(ext);
		return bean;
	}

	@Override
    public CmsGuestbook deleteById(Integer id) {
		return dao.deleteById(id);
	}

	@Override
    public CmsGuestbook[] deleteByIds(Integer[] ids) {
		CmsGuestbook[] beans = new CmsGuestbook[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	
	@Override
    public CmsGuestbook[] checkByIds(Integer[] ids, CmsAdmin admin, Short checkStatus) {
		CmsGuestbook[] beans = new CmsGuestbook[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = checkById(ids[i],admin,checkStatus);
		}
		return beans;
	}
	
	private CmsGuestbook checkById(Integer id, CmsAdmin admin, Short checkStatus){
		CmsGuestbook bean=findById(id);
		Updater<CmsGuestbook> updater = new Updater<>(bean);
		bean = dao.updateByUpdater(updater);
		if(checkStatus!=null && !checkStatus.equals(0)){
			bean.setAdmin(admin);
		}
		bean.setChecked(checkStatus);
		return bean;
	}


	@Autowired
	private CmsGuestbookCtgMng cmsGuestbookCtgMng;
	@Autowired
	private CmsGuestbookExtMng cmsGuestbookExtMng;
	@Autowired
	private CmsGuestbookDao dao;
	@Autowired
	private CmsUserExtMng userExtMng;
}